/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB;

import Server.DB.Layers.BUS.GameMatchBUS;
import Server.DB.Layers.BUS.PlayerBUS;
import Server.DB.Layers.DTO.GameMatch;
import Server.DB.Layers.DTO.Player;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

/**
 *
 * @author nguye
 */
public class Seeds {

    public Seeds() {
    }

    public void generate() {
//        PlayerBUS pbus = new PlayerBUS();
//        for (Player p : pbus.getList()) {
//            pbus.delete(p.getId());
//        }
//
//        Player p1 = new Player("user1", "123abc", "uwu@yahoo.com", "Nam", LocalDate.now());
//        Player p2 = new Player("bemeovuive", "123456", "meomeo@gmail.com", "Nam", LocalDate.now());
//        Player p3 = new Player("BeChoGauGau", "abcxyz", "hasagi@yahoo.com", "Nam", LocalDate.now());
//        Player p4 = new Player("BeThoQuacQuac", "carotxanh", "asayo@skrr.com", "Nam", LocalDate.now());
//        Player p5 = new Player("BeGauEcEc", "gaudien", "ecec@ecec.com", "Nữ", LocalDate.now());
//        Player p6 = new Player("BeVitMooMoo", "vittiem", "haha@yaha.com", "Nữ", LocalDate.now());
//        Player p7 = new Player("ToiletSangChoi", "Vimconvit", "poof@shit.com", "Nam", LocalDate.now());
//        Player p8 = new Player("GaConXamTro", "talavit", "kimochi@jav.con", "Nam", LocalDate.now());
//
//        pbus.add(p1);
//        pbus.add(p2);
//        pbus.add(p3);
//        pbus.add(p4);
//        pbus.add(p5);
//        pbus.add(p6);
//        pbus.add(p7);
//        pbus.add(p8);
//
//        GameMatchBUS mbus = new GameMatchBUS();
//        for (GameMatch m : mbus.getList()) {
//            mbus.delete(m.getID());
//        }
//
//        GameMatch m1 = new GameMatch(p1.getUsername(), p2.getUsername(), p1.getUsername(), 60, 15, LocalDateTime.now());
//        GameMatch m2 = new GameMatch(p2.getUsername(), p5.getUsername(), p2.getUsername(), 160, 50, LocalDateTime.now());
//        GameMatch m3 = new GameMatch(p5.getUsername(), p6.getUsername(), p6.getUsername(), 260, 75, LocalDateTime.now());
//        GameMatch m4 = new GameMatch(p7.getUsername(), p1.getUsername(), p1.getUsername(), 630, 35, LocalDateTime.now());
//        GameMatch m5 = new GameMatch(p8.getUsername(), p7.getUsername(), p8.getUsername(), 601, 45, LocalDateTime.now());
//        GameMatch m6 = new GameMatch(p4.getUsername(), p3.getUsername(), p4.getUsername(), 460, 25, LocalDateTime.now());
//
//        mbus.add(m1);
//        mbus.add(m2);
//        mbus.add(m3);
//        mbus.add(m4);
//        mbus.add(m5);
//        mbus.add(m6);
    }
}
