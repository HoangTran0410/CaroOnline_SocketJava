/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import server.controller.Admin;
import server.controller.Client;
import server.controller.ClientManager;
import server.controller.RoomManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.security.RSA;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class RunServer {

    public static volatile ClientManager clientManager;
    public static volatile RoomManager roomManager;
    public static volatile RSA serverSide;

    public RunServer() {

        try {
            int port = 5056;

            ServerSocket ss = new ServerSocket(port);
            System.out.println("Created Server at port " + port + ".");

            // init rsa key
            serverSide = new RSA()
                    .preparePrivateKey("src/Server/rsa_keypair/privateKey");

            // init managers
            clientManager = new ClientManager();
            roomManager = new RoomManager();

            // create threadpool
            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    10, // corePoolSize
                    100, // maximumPoolSize
                    10, // thread timeout
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(8) // queueCapacity
            );

            // admin
            executor.execute(new Admin());

            // server main loop - listen to client's connection
            while (true) {
                try {
                    // socket object to receive incoming client requests
                    Socket s = ss.accept();
                    // System.out.println("+ New Client connected: " + s);

                    // create new client runnable object
                    Client c = new Client(s);
                    clientManager.add(c);

                    // execute client runnable
                    executor.execute(c);

                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RunServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new RunServer();
    }
}
