/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controllers;

import java.util.ArrayList;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class ClientManager {

    ArrayList<ClientHandler> clients;

    public ClientManager() {
        clients = new ArrayList<>();
    }

    public boolean add(ClientHandler c) {
        if (!clients.contains(c)) {
            clients.add(c);
            return true;
        }
        return true;
    }

    public boolean delete(ClientHandler c) {
        if (clients.contains(c)) {
            clients.remove(c);
            return true;
        }
        return false;
    }

    public ClientHandler find(String email) {
        for (ClientHandler c : clients) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

}
