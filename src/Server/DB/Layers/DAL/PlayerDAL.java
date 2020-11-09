/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DAL;

import Server.DB.Layers.DBConnector.MysqlConnector;
import Server.DB.Layers.DTO.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguye
 */
public class PlayerDAL {

    PreparedStatement stm;
    MysqlConnector conn = new MysqlConnector();

    public PlayerDAL() {
        conn.connectDB();
    }

    public ArrayList readDB() {
        ArrayList<Player> result = new ArrayList<>();
        try {
            String qry = "select * from Player;";
            stm = conn.getConnection().prepareStatement(qry);
            ResultSet rs = conn.sqlQry(stm);
            if (rs != null) {
                while (rs.next()) {
                    Player p = new Player();
                    p.setID(rs.getInt("ID"));
                    p.setUsername(rs.getString("Username"));
                    p.setPassword(rs.getString("Password"));
                    p.setEmail(rs.getString("Email"));
                    p.setGender(rs.getString("Gender"));
                    p.setRankID(rs.getString("RankID"));
                    p.setDateOfBirth(LocalDate.parse(rs.getString("DateOfBirth")));
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
        try {
            String qry = "insert into Player(Username,Password,Email,Gender,DateOfBirth,Score,MatchCount,WinRate,WinStreak,Blocked,RankID) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?)";
            stm = conn.getConnection().prepareStatement(qry);
            stm.setString(1, p.getUsername());
            stm.setString(2, p.getPassword());
            stm.setString(3, p.getEmail());
            stm.setString(4, p.getGender());
            stm.setString(5, p.getDateOfBirth().toString());
            stm.setInt(6, p.getScore());
            stm.setInt(7, p.getMatchCount());
            stm.setFloat(8, p.getWinRate());
            stm.setInt(9, p.getWinStreak());
            stm.setBoolean(10, p.getBlockedStatus());
            stm.setString(11, p.getRankID());

            return conn.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(Player p) {
        try {
            String qry = "update Player set "
                    + "Password=?,"
                    + "Email=?,"
                    + "Gender=?,"
                    + "RankID=?,"
                    + "DateOfBirth=?,"
                    + "Score=?,"
                    + "MatchCount=?,"
                    + "WinRate=?,"
                    + "WinStreak=?,"
                    + "Blocked=?"
                    + " where username=?";
            stm = conn.getConnection().prepareStatement(qry);
            stm.setString(11, p.getUsername());
            stm.setString(1, p.getPassword());
            stm.setString(2, p.getEmail());
            stm.setString(3, p.getGender());
            stm.setString(5, p.getDateOfBirth().toString());
            stm.setInt(6, p.getScore());
            stm.setInt(7, p.getMatchCount());
            stm.setFloat(8, p.getWinRate());
            stm.setInt(9, p.getWinStreak());
            stm.setBoolean(10, p.getBlockedStatus());
            stm.setString(4, p.getRankID());
            return conn.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Player p){
        return delete(p.getUsername());
    }
    
    public boolean delete(String username){
        String qry = "delete from player where username=?";
        try {
            stm = conn.getConnection().prepareStatement(qry);
            stm.setString(1, username);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn.sqlUpdate(stm);
    }
    
    public boolean closeConnection() {
        return conn.closeConnection();
    }

}
