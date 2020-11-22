/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controllers;

import Server.Games.CoTuong.CoTuong;
import Shared.Constants.Type;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Server.Server.roomManager;
import Shared.StreamDTO.BaseDTO;
import com.google.gson.Gson;

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
        Gson gson = new Gson();

        // listen to dis of server
        while (true) {
            try {
                // receive the request from client
                received = dis.readUTF();
                System.out.println(received);

                // convert to json
                BaseDTO b = gson.fromJson(received, BaseDTO.class);

                // get received type, content string
                int rType = b.getType();
                String rContentStr = b.getContentStr();

                // exit if received.type == Exit
                if (rType == Type.EXIT) {
                    System.out.println("Client " + this.s + " sends exit...");
                    this.leaveRoom();
                    break;
                }

                // write on output stream based on the answer from the client
                switch (rType) {
                    // game events
                    case Type.GAME_EVENT:
                        if (this.room != null) {
//                            this.room.getGamelogic().receiveDataFromClient(rjson);
                        }
                        break;
                    
                    case Type.CHANGE_GAME:
                        this.room.setGamelogic(new CoTuong());
                        // TODO: đổi game dựa theo game id của client gửi tới
                        break;

                    //  Join room
                    case Type.JOIN_ROOM:
                        if (this.room == null) {
//                            Long roomId = (Long) rjson.get("id");
//                            JSONObject sjson = new JSONObject();
//                            sjson.put("id", roomId);
//                            sjson.put("type", Type.JOIN_ROOM);
//                            this.joinRoom(roomId.toString());
//                            sendMessage(sjson.toString());
                        }
                        break;

                    // chat
                    case Type.CHAT_ROOM:
                        if (this.room != null) {
                            this.room.broadcast(received);
                        }
                        Server.Server.clientManager.broadcast(received);
                        break;
                    
                    case Type.CHAT_ALL:
//                        String msg = (String) rjson.get("message");
//                        Server.clientManager.broadcast(msg);
                        break;

                    // auth
                    case Type.LOGIN:
//                        String e = (String) rjson.get("email");
//                        String p = (String) rjson.get("password");
//                        if (this.login(e, p)) {
//                            // TODO: check database login
//                            this.email = e;
//                            System.out.println(e + " login sucessfully.");
//                        }
//                        // TODO: return login status to client
//                        JSONObject j = new JSONObject();
//                        j.put("type", Type.LOGIN);
//                        j.put("status", "ok");
//                        dos.writeUTF(j.toJSONString());
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
            roomManager.update(room);
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
