/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.Layers.DAL.PlayerDAL;
import Server.Layers.DBConnector.MysqlConnector;
import Server.Layers.DTO.Player;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PlayerDAL dal = new PlayerDAL();
        Player p = new Player("Player2", "123abc", "Huu", "Nam", LocalDate.now());
        
//        dal.add(p);
dal.delete("player1");
        
    }
    
}
