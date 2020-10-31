/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB;

import Server.DB.Layers.BUS.MatchBUS;
import Server.DB.Layers.BUS.PlayerBUS;
import Server.DB.Layers.DAL.MatchDAL;
import Server.DB.Layers.DAL.PlayerDAL;
import Server.DB.Layers.DTO.Match;
import Server.DB.Layers.DTO.Player;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author nguye
 */
public class TestLayers {
    public static void main(String[] args) {
//        PlayerDAL dal = new PlayerDAL();
        PlayerBUS pbus = new PlayerBUS();
        Player p1 = new Player("Player1", "123abc", "Huu", "Nam", LocalDate.now());
        Player p2 = new Player("Player2", "123abc", "hoang", "Nam", LocalDate.now());
        Player p3 = new Player("Player3", "abcxyz", "hahaha", "Nam", LocalDate.now());
        p3.setPassword("newpass");
        pbus.add(p3);
//        MatchDAL mdal = new MatchDAL();
//        MatchBUS mbus = new MatchBUS();
//        Match m1 = new Match(1, "p1", "p2", "aa", 0, 0, LocalDateTime.now());
//        Match m2 = new Match(5, "p9", "p5", "aa", 0, 0, LocalDateTime.now());
//        Match m3 = new Match(5, "p3", "p33", "haha", 0, 0, LocalDateTime.now());
//        Match alter = mbus.getList().get(2);
//        alter.setPlayer1("p4");
//        System.out.println(alter.getID());
//        System.out.println(mbus.delete(alter.getID()));
//        System.out.println(mdal.add(m1));
//        for(Match i : mbus.getList()){
//            System.out.println(i.getID());
//        }
//System.out.println(LocalDateTime.now().toString());
//        
////        dal.add(p);
//dal.delete("player1");
        
    }
}
