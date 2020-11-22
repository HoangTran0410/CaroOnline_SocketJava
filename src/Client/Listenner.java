/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.Constants.Type;
import Shared.StreamDTO.BaseSDTO;
import Shared.StreamDTO.ChatMessageSDTO;
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

        while (true) {
            try {
                // read input stream
                String received = dis.readUTF();

                BaseSDTO b = gson.fromJson(received, BaseSDTO.class);
                int rType = b.type;
                String rContentStr = b.contentStr;

                // exit if received.type == Exit
                if (rType == Type.EXIT) {
                    break;
                }

                // write on output stream based on the answer from the server
                switch (rType) {
                    case Type.LOGIN:
                        break;

                    case Type.LOGOUT:
                        System.out.println("Logout successfully");
                        break;

                    case Type.JOIN_ROOM:
                        System.out.println("Join room successfully");
                        break;

                    case Type.CHAT_ROOM:
                        ChatMessageSDTO chat = gson.fromJson(rContentStr, ChatMessageSDTO.class);
                        System.out.println(chat.message);
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
            s.close();
            dis.close();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(Listenner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
