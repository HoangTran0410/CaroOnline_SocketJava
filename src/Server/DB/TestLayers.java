/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB;

import Server.DB.Layers.DAL.PlayerDAL;
import Server.DB.Layers.DTO.Player;
import java.time.LocalDate;

/**
 *
 * @author nguye
 */
public class TestLayers {
    public static void main(String[] args) {
        PlayerDAL dal = new PlayerDAL();
        Player p = new Player("Player2", "123abc", "Huu", "Nam", LocalDate.now());
        
//        dal.add(p);
dal.delete("player1");
        
    }
}
