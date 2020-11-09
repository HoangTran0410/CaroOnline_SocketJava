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

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class ClientHandler implements Runnable {

    final Socket s;
    final DataInputStream dis;
    final DataOutputStream dos;

    String email; // if == null => chua dang nhap
    Room room; // if == null => chua vao phong nao het

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {

        String received;

        // listen to dis of server
        while (true) {
            try {
                // receive the request from client
                received = dis.readUTF();

                // convert to json
                JSONObject rjson = Json.parse(received);

                // get received type
                int rtype = ((Long) rjson.get("type")).intValue();

                // exit if received.type == Exit
                if (rtype == Type.EXIT) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection...");
                    // TODO: leave rooms before close socket
                    this.s.close();
                    System.out.println("Connection closed.");
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

                    // change game
                    case Type.CHANGE_GAME:
                        this.room.setGamelogic(new CoTuong());
                        // TODO: đổi game dựa theo game id của client gửi tới
                        break;

                    // dang nhap
                    case Type.LOGIN:
                        String u = (String) rjson.get("username");
                        String p = (String) rjson.get("password");
                        if (this.login(u, p)) {
                            // TODO: check database login
                            this.email = u;
                            System.out.println(u + " login sucessfully.");
                        }
                        // TODO: return login status to client
                        JSONObject j = new JSONObject();
                        j.put("type", Type.LOGIN);
                        j.put("status", "ok");
                        dos.writeUTF(j.toJSONString());
                        break;

                    // dang xuat
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
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }

        try {
            // closing resources 
            this.dis.close();
            this.dos.close();

        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // room handle
    public boolean joinRoom(Room room) {
        if (this.room == null) {
            this.room = room;
            return true;
        }
        return false;
    }

    public boolean leaveRoom() {
        this.room = null;
        return true;
    }

    // auth handle
    public boolean login(String username, String password) {
        // xu ly db
        return true;
    }

    public boolean logout() {
        this.email = null;
        return true;
    }

    // get set
    public String getUsername() {
        return email;
    }
}
