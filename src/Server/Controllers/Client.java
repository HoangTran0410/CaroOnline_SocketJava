/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controllers;

import Server.Games.CoTuong.CoTuong;
import Shared.Constants.Type;
import Shared.Helpers.Json;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import Server.Server;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Client implements Runnable {
    
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    
    String email; // if == null => chua dang nhap
    Room room; // if == null => chua vao phong nao het

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

                // convert to json
                JSONObject rjson = Json.parse(received);

                // get received type
                int rtype = ((Long) rjson.get("type")).intValue();

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
                    
                    case Type.CHANGE_GAME:
                        this.room.setGamelogic(new CoTuong());
                        // TODO: đổi game dựa theo game id của client gửi tới
                        break;
                        
                    //  Join room
                        case Type.JOIN_ROOM:
                        if (this.room == null) {
                            Long roomId = (Long) rjson.get("id");
//                            String sender = rjson.get("sender").toString();
                            JSONObject sjson = new JSONObject();
//                            sjson.put("sender", sender);
                            this.room = new Room();
                            room.setId(roomId.toString());
                            sjson.put("id", roomId);
                            sjson.put("type", Type.JOIN_ROOM);
                            this.joinRoom(room);
                            sendMessage(sjson.toString());
                        }
                        break;

                    // chat
                    case Type.CHAT_ROOM:
                        if (this.room != null) {
                            String msg = (String) rjson.get("message");
                            String sender = rjson.get("sender").toString();
                            JSONObject sjson = new JSONObject();
                            sjson.put("sender", sender);
                            sjson.put("message", msg);
                            sjson.put("type", Type.CHAT_ROOM);
                            this.room.broadcast(sjson.toString());
                        }
                        break;
                    
                    case Type.CHAT_ALL:
                        String msg = (String) rjson.get("message");
                        Server.clientManager.broadcast(msg);
                        break;

                    // auth
                    case Type.LOGIN:
                        String e = (String) rjson.get("email");
                        String p = (String) rjson.get("password");
                        if (this.login(e, p)) {
                            // TODO: check database login
                            this.email = e;
                            System.out.println(e + " login sucessfully.");
                        }
                        // TODO: return login status to client
                        JSONObject j = new JSONObject();
                        j.put("type", Type.LOGIN);
                        j.put("status", "ok");
                        dos.writeUTF(j.toJSONString());
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
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
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

    // room handle
    public boolean joinRoom(Room room) {
        if (this.room != null) {
            room.addClient(this);
            this.room = room;
            return true;
        }
        return false;
    }
    
    public boolean leaveRoom() {
        if (this.room != null) {
            this.room.removeClient(this);
            this.room = null;
        }
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
