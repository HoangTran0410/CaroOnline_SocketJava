/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Layers.BUS;

import Server.Layers.DAL.MatchDAL;
import Server.Layers.DAL.PlayerDAL;
import Server.Layers.DTO.Player;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class PlayerBUS {
    ArrayList<Player> listPlayer = new ArrayList<>();

    public PlayerBUS() {
        readDB();
    }
    
    public boolean readDB(){
        PlayerDAL dal = new PlayerDAL();
        listPlayer = dal.readDB();
        return (listPlayer != null);
    }
    
    public boolean add(Player p){
        PlayerDAL dal = new PlayerDAL();
        return dal.add(p);
    }
    
    public boolean add(String username,String password,String displayName,String gender,LocalDate dateOfBirth ){
        Player p = new Player(username, password, displayName, gender, dateOfBirth);
        return add(p);
    }
    
    protected int getScore(Player p){
        MatchBUS mDal = new MatchBUS();
        return mDal.getPlayerScore(p);
    }
    
}
