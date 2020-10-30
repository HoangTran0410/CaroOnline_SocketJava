/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controllers;

import Helpers.Json;
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
public class ClientHandler extends Thread {

    final Socket s;
    final DataInputStream dis;
    final DataOutputStream dos;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {

        String received;

        while (true) {
            try {
                // receive the answer from client
                received = dis.readUTF();

                JSONObject jreceived = Json.parse(received);

                // check exit
                if (jreceived.get("type").equals("Exit")) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection...");
                    this.s.close();
                    System.out.println("Connection closed.");
                    break;
                }

                // write on output stream based on the 
                // answer from the client 
                switch ((String) jreceived.get("type")) {
                    case "abc":
                        // do something
                        break;
                    default:
                        dos.writeUTF("Invalid input");
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

}
