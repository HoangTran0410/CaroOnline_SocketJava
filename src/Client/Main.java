/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.Scenes.MainMenu;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.connect("localhost", 5056);

//        new MainMenu().setVisible(true);
    }
}
