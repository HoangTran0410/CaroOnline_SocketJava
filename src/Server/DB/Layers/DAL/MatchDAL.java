/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DAL;

import Server.DB.Layers.DBConnector.MysqlConnector;
import Server.DB.Layers.DTO.Match;
import Server.DB.Layers.DTO.Player;
//import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguye
 */
public class MatchDAL {

    MysqlConnector conn = new MysqlConnector();
    PreparedStatement stm;
//    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddThh:mm:ss.zzz");

    public MatchDAL() {
        conn.connectDB();
    }

    public ArrayList readDB() {
        ArrayList<Match> result = new ArrayList<>();
        try {
            String qry = "select * from gamematch;";
            stm = conn.getConnection().prepareStatement(qry);
            ResultSet rs = conn.sqlQry(stm);
            if (rs != null) {
                while (rs.next()) {
                    Match m = new Match();
                    m.setID(rs.getInt("ID"));
                    m.setPlayer1(rs.getString("Player1"));
                    m.setPlayer2(rs.getString("Player2"));
                    m.setWinnerID(rs.getString("Winner"));
                    m.setPlayTimeInSecond(rs.getInt("PlayTimeInSecond"));
                    m.setTotalMove(rs.getInt("TotalMove"));
//                    m.setStartedDate(rs.getDate("StartedDate").toLocalDate());
                    m.setStartedTime(LocalDateTime.parse(rs.getString("StartedTime")));

                    result.add(m);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while trying to read Matchs info from database!");
        };
        return result;
    }
    public boolean add(Match m) {
//        stm = conn.getConnection().prepareStatement();
        String sql = "insert into gamematch(Player1,Player2,Winner,PlayTimeInSecond,TotalMove,StartedTime) values(?,?,?,?,?,?)";
        try {
            stm = conn.getConnection().prepareStatement(sql);
            stm.setString(1, m.getPlayer1());
            stm.setString(2, m.getPlayer2());
            stm.setString(3, m.getWinnerID());
            stm.setInt(4, m.getPlayTimeInSecond());
            stm.setInt(5, m.getTotalMove());
            stm.setString(6, m.getStartedTime().toString());
        } catch (SQLException ex) {
            Logger.getLogger(MatchDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println(stm);
        return conn.sqlUpdate(stm);
    }

    public boolean update(Match m) {

        try {
            String sql = "update gamematch set "
                    + "Player1=?,"
                    + "player2=?,"
                    + "winner=?,"
                    + "playTimeInSecond=?,"
                    + "totalMove=?,"
                    + "startedTime=?"
                    + " where id=?;";
            stm = conn.getConnection().prepareStatement(sql);
            stm.setString(1, m.getPlayer1());
            stm.setString(2, m.getPlayer2());
            stm.setString(3, m.getWinnerID());
            stm.setInt(4, m.getPlayTimeInSecond());
            stm.setInt(5, m.getTotalMove());
            stm.setString(6, m.getStartedTime().toString());
            stm.setInt(7, m.getID());
//            System.out.println(stm.toString());
            return conn.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(MatchDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(int id){
        String qry = "delete from gamematch where ID=?";
        try {
            stm = conn.getConnection().prepareStatement(qry);
            stm.setInt(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(MatchDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn.sqlUpdate(stm);
    }
    
    public boolean delete(Match m){
        return delete(m.getID());
    }
    public boolean closeConnection() {
        return conn.closeConnection();
    }

}
