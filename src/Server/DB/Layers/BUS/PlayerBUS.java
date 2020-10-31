/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.BUS;

import Server.DB.Layers.DAL.MatchDAL;
import Server.DB.Layers.DAL.PlayerDAL;
import Server.DB.Layers.DTO.Match;
import Server.DB.Layers.DTO.Player;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class PlayerBUS {

    ArrayList<Player> listPlayer = new ArrayList<>();
    PlayerDAL dal;
    MatchBUS matchBus;

    public PlayerBUS() {
        readDB();
    }

    public boolean readDB() {
        dal = new PlayerDAL();
        listPlayer = dal.readDB();
        return (listPlayer != null);
    }

    public boolean add(Player p) {
        dal = new PlayerDAL();
        return dal.add(p);
    }

    public boolean add(String username, String password, String displayName, String gender, LocalDate dateOfBirth) {
        Player p = new Player(username, password, displayName, gender, dateOfBirth);
        return add(p);
    }

    public boolean update(Player p) {
        dal = new PlayerDAL();
        return dal.update(p);
    }

    public boolean update(String username, String password, String displayName, String gender, LocalDate dateOfBirth) {
        Player p = new Player(username, password, displayName, gender, dateOfBirth);
        return update(p);
    }

    public boolean delete(String username) {
        dal = new PlayerDAL();
        return dal.delete(username);
    }

    public int getWinCount(String username) {
        int result = 0;
        matchBus = new MatchBUS();
        for (Match m : matchBus.getList()) {
            if (m.getWinnerID().equalsIgnoreCase(username)) {
                result++;
            }
        }
        return result;
    }

    public int getLongestWinStreak(String username) {
        matchBus = new MatchBUS();
        int result = 0;
        int temp = 0;
        for (Match m : matchBus.getList()) {
            if (m.getPlayer1().equalsIgnoreCase(username) || m.getPlayer2().equalsIgnoreCase(username)) {
                if (m.getWinnerID().equalsIgnoreCase(username)) {
                    temp++;
                } else {
                    if (temp > result) {
                        result = temp;
                    }
                }
            }
        }
        return result;
    }

    public float calculateWinRate(Player p) {
        return (float) (100.00 * (getWinCount(p.getUsername())/p.getMatchCount()));
    }

}
