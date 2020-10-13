/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Layers.BUS;

import Server.Layers.DAL.MatchDAL;
import Server.Layers.DTO.Match;
import Server.Layers.DTO.Player;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class MatchBUS {
    
    ArrayList<Match> listMatch = new ArrayList<Match>();
    
    public MatchBUS() {
    }
    
    public boolean readDB(){
        MatchDAL dal = new MatchDAL();
        listMatch = dal.readDB();
        return listMatch != null;
    }
    
    public int getPlayerScore(Player p){
        int result = 0;
        for(Match m : listMatch){
            if(m.getWinnerID().equalsIgnoreCase(String.valueOf(p.getID())))
                result++;
        }
        return result;
    }
    
    public int getMatchCount(Player p){
        int result = 0;
        for(Match m : listMatch){
            if(m.getPlayer1().equalsIgnoreCase(String.valueOf(p.getID())) || m.getPlayer2().equalsIgnoreCase(String.valueOf(p.getID())))
                result++;
        }
        return result;
    }
    
    public int getPlayerWinStreak(Player p){
        return 0;
    }
}
