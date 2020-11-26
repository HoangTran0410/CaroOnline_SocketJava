/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.helper.security.AES;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class SocketHandler {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    Thread listener = null;
    AES aes;

    public String connect(String addr, int port) {
        try {
            // getting ip 
            InetAddress ip = InetAddress.getByName(addr);

            // establish the connection with server port 
            s = new Socket();
            s.connect(new InetSocketAddress(ip, port), 4000);
            System.out.println("Connected to " + ip + ":" + port + ", localport:" + s.getLocalPort());

            // obtaining input and output streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            // close old listener
            if (listener != null && listener.isAlive()) {
                listener.interrupt();
            }

            // listen to server
            listener = new Thread(() -> {
                listen();
            });
            listener.start();

            // TODO: send AES client's key to server
            // create new aes key
            aes = new AES();

            // connect success
            return "success";

        } catch (IOException e) {

            // connect failed
            return "failed;" + e.getMessage();
        }
    }

    private void listen() {
        while (true) {
            try {
                // read input stream
                String received = dis.readUTF();

                // process received data
                if (received.indexOf("login_result") == 0) {

                } else if (received.indexOf("signup_result") == 0) {

                } else if (received.indexOf("join_room_result") == 0) {

                } else if (received.indexOf("leave_room_result") == 0) {

                } else if (received.indexOf("chat_room") == 0) {

                } else if (received.indexOf("game_event_result") == 0) {

                } else if (received.indexOf("update_profile_result") == 0) {

                }

            } catch (IOException ex) {
                Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }

        // close resource if something failed
        closeResources();
    }

    private void closeResources() {
        try {
            // closing resources
            s.close();
            dis.close();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
