/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controllers;

import Server.DB.Layers.BUS.PlayerBUS;
import Server.DB.Layers.DTO.Player;
import Server.Games.CoTuong.CoTuong;
import Shared.Constants.Type;
import Shared.Helpers.Json;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.json.simple.JSONObject;
import Server.Server;
import static Server.Server.roomManager;
import Shared.StreamDTO.ChatMessage;
import Shared.StreamDTO.PlayerInfo;
import Shared.StreamDTO.RoomInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Client implements Runnable {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    PlayerBUS playerBUS;
    JsonObject sjson;
    PlayerInfo p;
    ChatMessage mess;
    RoomInfo r ;

    String email; // if == null => chua dang nhap
    Room room; // if == null => chua vao phong nao het
    Gson gson = new Gson();

    public Client(Socket s) throws IOException {
        this.s = s;

        // obtaining input and output streams 
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {

        String received;

        // listen to dis of server
        while (true) {
            try {
                // receive the request from client
                received = dis.readUTF();
                System.out.println(received);

                // convert recieved data to json
                JsonObject rjson = gson.fromJson(received, JsonObject.class);

                // get received type
                int rtype = Integer.parseInt(rjson.get("type").getAsString());

                // exit if received.type == Exit
                if (rtype == Type.EXIT) {
                    System.out.println("Client " + this.s + " sends exit...");
                    this.leaveRoom();
                    break;
                }

                // write on output stream based on the answer from the client
                switch (rtype) {
                    // game events
                    case Type.GAME_EVENT:
                        if (this.room != null) {
                            this.room.getGamelogic().receiveDataFromClient(rjson);
                        }
                        break;

                    case Type.SIGNUP:
//                        p = new PlayerInfo();
                        sjson = new JsonObject();
                        p = gson.fromJson(rjson.get("content").getAsString(), PlayerInfo.class);
                        p.setStatus("error");
                        sjson.addProperty("type", Type.SIGNUP);
                        if(signUpPlayer(p)){
                            p.setStatus("ok");
                        }
                        sjson.addProperty("content", gson.toJson(p));
                        break;

                    case Type.CHANGE_GAME:
                        this.room.setGamelogic(new CoTuong());
                        // TODO: đổi game dựa theo game id của client gửi tới
                        break;

                    //  Join room
                    case Type.JOIN_ROOM:
                        if (this.room == null) {
                            r = gson.fromJson(rjson.get("content").getAsString(), RoomInfo.class);
                            sjson = new JsonObject();
                            this.joinRoom(r.getId());
                            r.setClientsCount(this.room.clients.size());
                            switch (r.getClientsCount()) {
                                case 0:
                                    r.setPlayer1(this.getEmail());
                                    break;
                                case 1:
                                    r.setPlayer2(this.getEmail());
                                    break;
                                default:
                                    break;
                            }
                            sjson.addProperty("content", gson.toJson(r));
                            sjson.addProperty("type", Type.JOIN_ROOM);

                            sendMessage(sjson.toString());
                        }
                        break;

                    // chat
                    case Type.CHAT_ROOM:
                        if (this.room != null) {
                            mess = gson.fromJson(rjson.get("content").toString(), ChatMessage.class);
                            sjson = new JsonObject();
                            sjson.addProperty("type", Type.CHAT_ROOM);
                            sjson.addProperty("content", gson.toJson(mess));
                            this.room.broadcast(sjson.toString());
                        }
                        break;

                    case Type.CHAT_ALL:
                        mess = gson.fromJson(rjson.get("content").getAsString(), ChatMessage.class);
                        sjson = new JsonObject();
                        sjson.addProperty("type", Type.CHAT_ROOM);
                        sjson.addProperty("content", gson.toJson(mess));
                        Server.clientManager.broadcast(sjson.toString());
                        break;

                    // auth
                    case Type.LOGIN:
                        p = gson.fromJson(rjson.get("content").getAsString(), PlayerInfo.class);
                        if (this.login(p.getEmail(), p.getPassword())) {
                            // TODO: check database login
                            this.email = p.getEmail();
                            p.setStatus("ok");
                            System.out.println(email + " login sucessfully.");
                        }
                        // TODO: return login status to client
                        JsonObject j = new JsonObject();
                        j.addProperty("type", Type.LOGIN);
                        j.addProperty("content", gson.toJson(p));
                        dos.writeUTF(j.toString());
                        break;

                    case Type.LOGOUT:
                        if (this.logout()) {
                            System.out.println(this.email + " logout sucessfully.");
                            this.email = null;
                        }
                        // TODO: return logout status to client
                        break;

                    default:

                        break;
                }

            } catch (IOException ex) {
                System.out.println("Connection lost with " + s.getPort());
                break;
            }
        }

        try {
            // closing resources 
            this.s.close();
            this.dis.close();
            this.dos.close();
            System.out.println("- Client disconnected: " + s);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // send message
    public boolean sendMessage(String mes) {
        try {
            this.dos.writeUTF(mes);
            return true;
        } catch (IOException e) {
            System.err.println("Send message failed to " + this.getEmail());
            return false;
        }
    }
    
    // signup handle - add new player into database
    public boolean signUpPlayer(PlayerInfo p){
        Player pDTO = p;
        return playerBUS.add(pDTO);
    }

    // room handle
    public boolean joinRoom(String id) {
        if (this.room == null) {
            Room r = new Room();
            r.setId(id);
            if (roomManager.find(id) == null) {
                roomManager.add(r);
            } else {
                r = roomManager.get(id);
            }
            r.addClient(this);
            this.room = r;
            System.out.println("Current clients in room:" + this.room.clients.size());
            return true;
        }
        return false;
    }

    public boolean joinRoom(Room room) {
        return joinRoom(room.getId());
    }

    public boolean leaveRoom() {
        if (this.room != null) {
            this.room.removeClient(this);
//            roomManager.update(room);
            System.out.println("A client left, " + this.room.clients.size() + " remaining.");
            this.room = null;
            return true;
        }
        System.out.println("Not in a room, cant leave!x");
        return true;
    }

    // auth handlers
    public boolean login(String username, String password) {
        // xu ly db
        return true;
    }

    public boolean logout() {
        this.email = null;
        return true;
    }

    // gets sets
    public String getEmail() {
        return email;
    }
}
