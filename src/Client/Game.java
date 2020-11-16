/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Game {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    public Game() {

    }

    public void connect(String addr, int port) {
        try {
            // getting ip 
            InetAddress ip = InetAddress.getByName(addr);

            // establish the connection with server port 
            s = new Socket(ip, port);
            System.out.println("Connected to " + ip + ":" + port);

            // obtaining input and output streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            new Thread(new Listenner(s, dis, dos)).start();

        } catch (IOException e) {
            System.err.println("Loi. " + e.getMessage());
        }
    }
}

//            // test change game
//            JSONObject j = new JSONObject();
//            j.put("type", Type.CHANGE_GAME);
//            dos.writeUTF(j.toJSONString());
//
//            // test send game event
//            JSONObject j2 = new JSONObject();
//            j2.put("type", Type.GAME_EVENT);
//            j2.put("game_event", "New Game");
//            dos.writeUTF(j2.toJSONString());
