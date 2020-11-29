/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.util.ArrayList;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class ClientManager {

    ArrayList<Client> clients;

    public ClientManager() {
        clients = new ArrayList<>();
    }

    public boolean add(Client c) {
        if (!clients.contains(c)) {
            clients.add(c);
            return true;
        }
        return true;
    }

    public boolean remove(Client c) {
        if (clients.contains(c)) {
            clients.remove(c);
            return true;
        }
        return false;
    }

    public Client find(String email) {
        for (Client c : clients) {
            if (c.getLoginPlayer() != null && c.getLoginPlayer().getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

    public void broadcast(String msg) {
        clients.forEach((c) -> {
            c.sendData(msg);
        });
    }

    public Client findClientFindingMatch() {
        for (Client c : clients) {
            if (c.isFindingMatch()) {
                return c;
            }
        }

        return null;
    }

    public int getSize() {
        return clients.size();
    }
}
