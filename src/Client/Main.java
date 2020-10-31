/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.Constants.Type;
import Shared.Helpers.Json;
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
public class Main {

    public static void main(String[] args) {
        try {
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056 
            Socket s = new Socket(ip, 5056);

            // obtaining input and output streams 
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // test change game
            JSONObject j = new JSONObject();
            j.put("type", Type.CHANGE_GAME);
            dos.writeUTF(j.toJSONString());
            
            // test send game event
            JSONObject j2 = new JSONObject();
            j2.put("type", Type.GAME_EVENT);
            j2.put("game_event", "New Game");
            dos.writeUTF(j2.toJSONString());

            // listen to dis of client
            while (true) {
                // read input stream
                String received = dis.readUTF();

                // convert to json
                JSONObject rjson = Json.parse(received);

                // get received type
                int rtype = ((Long) rjson.get("type")).intValue();

                // exit if received.type == Exit
                if (rtype == Type.EXIT) {
                    break;
                }

                // write on output stream based on the answer from the server
                switch (rtype) {
                    case Type.LOGIN_RESULT:
                        if (rjson.get("status").equals("ok")) {
                            System.out.println("Login successfully");
                        } else {
                            System.out.println("");
                        }
                        break;

                    case Type.LOGOUT_RESULT:
                        System.out.println("Logout successfully");
                        break;

                    default:
                        // do something
                        break;
                }
            }

            // closing resources 
            dis.close();
            dos.close();

        } catch (IOException e) {
            System.err.println("Loi. " + e.getMessage());
        }
    }
}
