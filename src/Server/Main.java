/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.Controllers.ClientHandler;
import Server.Controllers.Room;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5056);

        Room roomtest = new Room();

        while (true) {
            Socket s = null;

            try {
                // socket object to receive incoming client requests
                s = ss.accept();
                System.out.println("New Client connected: " + s);

                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new Thread to this client...");

                // create new thread object
                ClientHandler client = new ClientHandler(s, dis, dos);
                client.start();

                roomtest.addClient(client);

            } catch (IOException e) {
                s.close();
                System.err.println("Error. " + e.getMessage());
            }
        }
    }
}
