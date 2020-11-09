/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controllers;

import Server.Games.Caro.Caro;
import Server.Games.GameLogic;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Room {

    String id;
    GameLogic gamelogic;
    HashMap<String, ClientHandler> clients = new HashMap<>();
    String player1ID, player2ID; // TODO: tách người chơi và người xem

    public Room() {
        // room id
        this.id = UUID.randomUUID().toString();

        // create game logic
        gamelogic = new Caro();
    }

    // add/remove client
    public boolean addClient(ClientHandler client) {
        String email = client.getEmail();
        if (!clients.containsKey(email)) {
            clients.put(email, client);
            client.joinRoom(this);
            return true;
        }

        return false;
    }

    public boolean removeClient(ClientHandler client) {
        String email = client.getEmail();
        if (this.removeClient(email)) {
            client.leaveRoom();
            return true;
        }

        return false;
    }

    public boolean removeClient(String email) {
        if (!clients.containsKey(email)) {
            clients.remove(email);

            return true;
        }

        return false;
    }

    // send messages
    public void broadcast(String str) {
        clients.forEach((key, client) -> {
            this.sendMessage(client, str);
        });
    }

    public boolean sendMessage(ClientHandler client, String str) {
        return client.sendMessage(str);
    }

    public boolean sendMessage(String username, String str) {
        if (clients.containsKey(username)) {
            ClientHandler client = clients.get(username);
            return this.sendMessage(client, str);
        }

        return false;
    }

    // get set
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