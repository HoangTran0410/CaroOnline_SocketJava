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
public class Main {

    public static void main(String[] args) {
        try {
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056 
            Socket s = new Socket(ip, 5056);

            // obtaining input and out streams 
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            while (true) {
                // printing date or time as requested by client 
                String received = dis.readUTF();

                if (received.equals("Exit")) {
                    break;
                }

                switch (received) {
                    case "abc":
                        // do something
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
