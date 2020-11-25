/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.game.cotuong;

import server.game.GameLogic;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class CoTuong extends GameLogic {

    public CoTuong() {
        System.out.println("Co Tuong constructor");
    }

    @Override
    public void receiveDataFromClient(String received) {
        System.out.println("Game CoTuong received: " + received);
    }
}
