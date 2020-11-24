/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

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

        while (true) {
            try {
                // read input stream
                String received = dis.readUTF();

                if (received.indexOf("login_result") == 0) {

                } else if (received.indexOf("signup_result") == 0) {

                } else if (received.indexOf("join_room_result") == 0) {

                } else if (received.indexOf("leave_room_result") == 0) {

                } else if (received.indexOf("chat_room") == 0) {

                } else if (received.indexOf("game_event_result") == 0) {

                } else if (received.indexOf("update_profile_result") == 0) {

                }

            } catch (IOException ex) {
                Logger.getLogger(Listenner.class.getName()).log(Level.SEVERE, null, ex);
                break;
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
