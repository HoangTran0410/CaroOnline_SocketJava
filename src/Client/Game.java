/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.Scenes.InGame;
import Shared.Constants.Type;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import org.json.simple.JSONObject;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Game {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    
    public static InGame ig = new InGame(); // TODO: bỏ static

    public Game() {
        ig.setVisible(true);
    }

    public void connect(String addr, int port) {
        try {
            // getting ip 
            InetAddress ip = InetAddress.getByName(addr);

            // establish the connection with server port 
            s = new Socket(ip, port);
            System.out.println("Connected to " + ip + ":" + port + ", localport:" + s.getLocalPort());

            // obtaining input and output streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            // listen to server
            new Thread(new Listenner(s, dis, dos)).start();
            
            // Temporary room to test sending data
            JSONObject sjson = new JSONObject();
            sjson.put("type", Type.JOIN_ROOM);
            sjson.put("id", 1);
            dos.writeUTF(sjson.toString());

        } catch (IOException e) {
            System.err.println("Loi. " + e.getMessage());
        }
    }
}
