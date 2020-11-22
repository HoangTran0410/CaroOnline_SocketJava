/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Games.CoTuong;

import Server.Games.GameLogic;
import com.google.gson.JsonObject;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class CoTuong extends GameLogic {

    public CoTuong() {
        System.out.println("Co Tuong constructor");
    }

    @Override
    public void receiveDataFromClient(JsonObject rjson) {
        System.out.println("Game CoTuong received: " + rjson.get("game_event").toString());
    }
}
