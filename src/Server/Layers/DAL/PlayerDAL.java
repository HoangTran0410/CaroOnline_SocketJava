/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Layers.DAL;

import Server.Layers.DBConnector.MysqlConnector;
import Server.Layers.DTO.Player;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class PlayerDAL {

    MysqlConnector conn = new MysqlConnector();

    public PlayerDAL() {
        conn.connectDB();
    }

    public ArrayList readDB() {
        ArrayList<Player> result = new ArrayList<>();
        try {
            String qry = "select * from Player;";
            ResultSet rs = conn.sqlQry(qry);
            if (rs != null) {
                while (rs.next()) {
                    Player p = new Player();
                    p.setID(rs.getInt("ID"));
                    p.setUsername(rs.getString("Username"));
                    p.setPassword(rs.getString("Password"));
                    p.setDisplayName(rs.getString("DisplayName"));
                    p.setGender(rs.getString("Gender"));
                    p.setRankID(rs.getString("RankID"));
                    p.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
                    p.setScore(rs.getInt("Score"));
                    p.setMatchCount(rs.getInt("MatchCount"));
                    p.setWinRate(rs.getFloat("WinRate"));
                    p.setWinStreak(rs.getInt("WinStreak"));
                    p.setBlocked(rs.getBoolean("Blocked"));

                    result.add(p);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while trying to read Players info from database!");
        };
        return result;
    }

    public boolean add(Player p) {
        String qry = "insert into Player(Username,Password,DisplayName,Gender,DateOfBirth,Score,MatchCount,WinRate,WinStreak,Blocked,RankID) "
                + "values('"
                + p.getUsername() + "','"
                + p.getPassword() + "','"
                + p.getDisplayName() + "','"
                + p.getGender() + "','"
                + p.getDateOfBirth() + "',"
                + p.getScore() + ","
                + p.getMatchCount() + ","
                + p.getWinRate() + ","
                + p.getWinStreak() + ",'"
                + p.getBlockedStatus() + "','"
                + p.getRankID()
                + "');";
        return conn.sqlUpdate(qry);
    }

    public boolean update(Player p) {
        String qry = "update Player set "
                + "Username='" + p.getUsername() + "',"
                + "Password='" + p.getPassword() + "',"
                + "Displayname='" + p.getDisplayName() + "',"
                + "Gender='" + p.getGender() + "',"
                + "RankID='" + p.getRankID() + "',"
                + "DateOfBirth='" + p.getDateOfBirth() + "',"
                + "Score='" + p.getScore() + "',"
                + "MatchCount='" + p.getMatchCount() + "',"
                + "WinRate='" + p.getWinRate() + "',"
                + "WinStreak='" + p.getWinStreak() + "',"
                + "Blocked='" + p.getBlockedStatus()
                + "';";
        return conn.sqlUpdate(qry);
    }
    
    public boolean delete(Player p){
        String qry = "delete from Player where ID='" + p.getID() + "'";
        return conn.sqlUpdate(qry);
    }
    
    public boolean delete(String username){
        String qry = "delete from Player where Username='" + username + "'";
        return conn.sqlUpdate(qry);
    }
    
    public boolean closeConnection(){
        return conn.closeConnection();
    }

}
