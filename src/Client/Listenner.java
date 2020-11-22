/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.Constants.Type;
import Shared.StreamDTO.BaseDTO;
import Shared.StreamDTO.ChatMessage;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Listenner implements Runnable {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    public Listenner(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {

        Gson gson = new Gson();

        // listen to dis of client
        while (true) {
            try {
                // read input stream
                String received = dis.readUTF();

                // convert to json
                BaseDTO b = gson.fromJson(received, BaseDTO.class);

                // get received type, content string
                int rType = b.getType();
                String rContentStr = b.getContentStr();
                System.out.println("received " + rContentStr);

                // exit if received.type == Exit
                if (rType == Type.EXIT) {
                    break;
                }

                // write on output stream based on the answer from the server
                switch (rType) {
                    case Type.LOGIN:
//                        if (rjson.get("status").equals("ok")) {
//                            System.out.println("Login successfully");
//                        } else {
//                            System.out.println("");
//                        }
                        break;

                    case Type.LOGOUT:
                        System.out.println("Logout successfully");
                        break;

                    case Type.JOIN_ROOM:
                        System.out.println("Join room successfully");
                        break;

                    case Type.CHAT_ROOM:
                        ChatMessage chat = gson.fromJson(rContentStr, ChatMessage.class);
                        System.out.println(chat.getMessage());
                        break;

                    default:
                        // do something
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(Listenner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            // closing resources
            dis.close();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(Listenner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
