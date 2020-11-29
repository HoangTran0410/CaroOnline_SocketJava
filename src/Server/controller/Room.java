/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import server.game.caro.Caro;
import server.game.GameLogic;
import java.util.ArrayList;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Room {

    String id;
    GameLogic gamelogic;
    Client client1 = null, client2 = null; // TODO: tách người chơi và người xem
    ArrayList<Client> clients = new ArrayList<>();

    public Room(String id) {
        // room id
        this.id = id;

        // create game logic
        gamelogic = new Caro();
    }

    // add/remove client
    public boolean addClient(Client c) {
        if (!clients.contains(c)) {
            clients.add(c);

            if (client1 == null) {
                client1 = c;
            } else if (client2 == null) {
                client2 = c;
            }

            return true;
        }
        return false;
    }

    public boolean removeClient(Client c) {
        if (clients.contains(c)) {
            clients.remove(c);
            return true;
        }
        return false;
    }

    // broadcast messages
    public void broadcast(String msg) {
        clients.forEach((c) -> {
            c.sendData(msg);
        });
    }
    
    public void close() {
        // TODO code here
    }

    // gets sets
    public String getClient12InGameData() {
        String data = "";

        data += (client1 == null ? Client.getEmptyInGameData() : client1.getInGameData() + ";");
        data += (client2 == null ? Client.getEmptyInGameData() : client2.getInGameData());

        return data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameLogic getGamelogic() {
        return gamelogic;
    }

    public void setGamelogic(GameLogic gamelogic) {
        this.gamelogic = gamelogic;
    }

    public Client getClient1() {
        return client1;
    }

    public void setClient1(Client client1) {
        this.client1 = client1;
    }

    public Client getClient2() {
        return client2;
    }

    public void setClient2(Client client2) {
        this.client2 = client2;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

}
