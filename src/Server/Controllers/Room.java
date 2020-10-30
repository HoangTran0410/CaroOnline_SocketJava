/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controllers;

import Server.Games.Caro.Caro;
import Server.Games.GameLogic;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Room {

    String id;
    String ownerID;
    HashMap<String, ClientHandler> clients = new HashMap<>();
    GameLogic gamelogic;

    public Room(String id, ClientHandler owner) {
        // room id
        this.id = id;

        // track owner id
        this.ownerID = UUID.randomUUID().toString();

        // add owner of this room to hashmap
        clients.put(this.ownerID, owner);

        // create game logic for room
        gamelogic = new Caro();
    }

}
