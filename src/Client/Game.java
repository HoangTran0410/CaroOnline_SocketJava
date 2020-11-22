/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.Scenes.InGame;
import Client.Scenes.Login;
import Client.Scenes.Signup;
import Shared.Constants.Type;
import Shared.StreamDTO.ChatMessage;
import Shared.StreamDTO.PlayerInfo;
import Shared.StreamDTO.RoomInfo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Game {
    
    Socket s;
    static DataInputStream dis;
    static DataOutputStream dos;
    public static InGame ig = new InGame();
    public static Login logIn = new Login();
    public static Signup signUp = new Signup();
    Gson gson = new Gson();
//    public static DataSender sender;

    public Game() {
//        ig.setVisible(true);
//        logIn.setVisible(true);
        signUp.setVisible(true);
    }

    // tam thoi, sau nay se de 1 class rieng
    public static void sendData(String s) {
        try {
            dos.writeUTF(s);
        } catch (IOException e) {
        }
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
            // Test login
//            JsonObject ljson = new JsonObject();
//            PlayerInfo p = new PlayerInfo();
//            p.setEmail("haha");
//            p.setUsername("username");
//            p.setPassword(addr);
//            ljson.addProperty("type", Type.JOIN_ROOM);
//            ljson.addProperty("content", gson.toJson(p));
//            dos.writeUTF(ljson.toString());

            // Temporary room to test sending data
            JsonObject sjson = new JsonObject();
            RoomInfo r = new RoomInfo();
            r.setId("1");
            sjson.addProperty("type", Type.JOIN_ROOM);
            sjson.addProperty("content", gson.toJson(r));
            dos.writeUTF(sjson.toString());

            // test gson
//            ChatMessage mess = new ChatMessage("15:15", "ME!", "alo 1 2 3 4");
//            
//            JsonObject jobj = new JsonObject();
//            jobj.addProperty("type", Type.CHAT_ROOM);
//            jobj.addProperty("content", gson.toJson(mess));
////            sender.sendData(jobj);
//            dos.writeUTF(jobj.toString());
        } catch (IOException e) {
            System.err.println("Loi. " + e.getMessage());
        }
    }
}
