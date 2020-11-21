/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.Controllers.Client;
import Server.Controllers.ClientManager;
import Server.Controllers.RoomManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Server {

    int port = 5056;

    int corePoolSize = 10;
    int maximumPoolSize = 100;
    int queueCapacity = 8;

    public static volatile ClientManager clientManager;
    public static volatile RoomManager roomManager;

    public Server() {
        
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Created Server at port " + port + ".");

            // init managers
            clientManager = new ClientManager();
            roomManager = new RoomManager();

            // create threadpool
            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    corePoolSize,
                    maximumPoolSize,
                    10,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(queueCapacity)
            );

            while (true) {
                Socket s = null;

                try {
                    // socket object to receive incoming client requests
                    s = ss.accept();
                    System.out.println("+ New Client connected: " + s);

                    // create new client runnable object
                    Client c = new Client(s);
                    clientManager.add(c);

                    // execute client
                    executor.execute(c);

                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
