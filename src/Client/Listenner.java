/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.Game.logIn;
import static Client.Game.ig;
import static Client.Game.signUp;
import Client.Scenes.InGame;
import Client.Scenes.Login;
import Client.Scenes.Signup;
import Shared.Constants.Type;
import Shared.Helpers.Json;
import Shared.StreamDTO.PlayerInfo;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.json.simple.JSONObject;
import com.google.gson.JsonObject;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Listenner implements Runnable {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    Gson gson;
    PlayerInfo player;

    public Listenner(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        gson = new Gson();
    }

    @Override
    public void run() {

        // listen to dis of client
        while (true) {
            try {
                // read input stream
                String received = dis.readUTF();

                // convert to json
                JsonObject rjson = gson.fromJson(received, JsonObject.class);
                System.out.println(received);

                // get received type
                int rtype = Integer.parseInt(rjson.get("type").getAsString());

                // exit if received.type == Exit
                if (rtype == Type.EXIT) {
//                    Game.ig.dispose();
                    break;
                }

                // write on output stream based on the answer from the server
                switch (rtype) {
                    case Type.LOGIN:
                        player = gson.fromJson(rjson.get("content").getAsString(), PlayerInfo.class);

                        if (player.getStatus().equalsIgnoreCase("ok")) {
                            System.out.println("Login successfully! Welcome " + player.getUsername());
                            logIn.dispose();
                            ig = new InGame();
                        } else {
                            System.out.println("Login fail!");
                            logIn.showMessageDialog("Log in fail!");
                        }
                        break;

                    case Type.LOGOUT:
                        player = gson.fromJson(rjson.get("content").toString(), PlayerInfo.class);

                        if (player.getStatus().equalsIgnoreCase("ok")) {
                            System.out.println("Login successfully!");
                        } else {
                            System.out.println("Login fail!");
                        }
                        break;
                    case Type.SIGNUP:
                        player = gson.fromJson(rjson.get("content").getAsString(), PlayerInfo.class);

                        if (player.getStatus().equalsIgnoreCase("ok")) {
                            System.out.println("Sign up successfully! ");
                            signUp.dispose();
                            logIn = new Login();
//                            ig = new InGame();
                        } else {
                            System.out.println("Login fail!");
                            logIn.showMessageDialog("Log in fail!");
                        }
                        break;

                    case Type.JOIN_ROOM:
                        System.out.println("Join room successfully");
                        break;
                    case Type.CHAT_ROOM:
                        System.out.println(rjson.get("content").toString());
//                        Game.ig.addChatLine(rjson.get("content").toString());
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
