/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.BUS;

import Server.DB.Layers.DAL.MatchDAL;
import Server.DB.Layers.DTO.Match;
import Server.DB.Layers.DTO.Player;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class MatchBUS {

    ArrayList<Match> listMatch = new ArrayList<Match>();
    MatchDAL dal;

    public MatchBUS() {
        readDB();
    }

    public boolean readDB() {
        dal = new MatchDAL();
        listMatch = dal.readDB();
        dal.closeConnection();
        return listMatch != null;
    }

    public boolean add(Match m) {
        dal = new MatchDAL();
        return dal.add(m);
    }

    public boolean add(String p1, String p2, String winnerID, int playTimeInSecond, int totalMove, LocalDateTime startedTime) {
        Match m = new Match(p1, p2, winnerID, playTimeInSecond, totalMove, startedTime);
        return add(m);
    }

    public boolean update(Match m) {
        dal = new MatchDAL();
        return dal.update(m);
    }
    
    public boolean update(int id,String p1, String p2, String winnerID, int playTimeInSecond, int totalMove, LocalDateTime startedTime){
        Match m = new Match(id,p1, p2, winnerID, playTimeInSecond, totalMove, startedTime);
        return update(m);
    }

    public boolean delete(int id) {
        dal = new MatchDAL();
        return dal.delete(id);
    }

    public ArrayList<Match> getList() {
        return listMatch;
    }
}
