/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import server.game.caro.Caro;
import server.game.GameLogic;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Room {

    String id;
    GameLogic gamelogic;
    ArrayList<Client> clients = new ArrayList<>();
    Client player1, player2; // TODO: tách người chơi và người xem

    public Room() {
        // room id
        this.id = UUID.randomUUID().toString();

        // create game logic
        gamelogic = new Caro();
    }

    // add/remove client
    public boolean addClient(Client c) {
        if (!clients.contains(c)) {
            clients.add(c);
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

    // gets sets
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
}
