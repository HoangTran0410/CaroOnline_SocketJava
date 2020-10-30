/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DAL;

import Server.DB.Layers.DBConnector.MysqlConnector;
import Server.DB.Layers.DTO.Match;
import Server.DB.Layers.DTO.Player;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class MatchDAL {

    MysqlConnector conn = new MysqlConnector();
    
    public MatchDAL() {
        conn.connectDB();
    }
    
    public ArrayList readDB() {
        ArrayList<Match> result = new ArrayList<>();
        try {
            String qry = "select * from Match;";
            ResultSet rs = conn.sqlQry(qry);
            if (rs != null) {
                while (rs.next()) {
                    Match m = new Match();
                    m.setID(rs.getInt("ID"));
                    m.setPlayer1(rs.getString("Player1"));
                    m.setPlayer2(rs.getString("Player2"));
                    m.setWinnerID(rs.getString("WinnerID"));
                    m.setPlayTimeInSecond(rs.getInt("PlayTimeInSecond"));
                    m.setTotalMove(rs.getInt("TotalMove"));
                    m.setStartedDate(rs.getDate("StartedDate").toLocalDate());
                    m.setStartedTime(rs.getTime("StartedTime").toLocalTime());

                    result.add(m);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while trying to read Players info from database!");
        };
        return result;
    }

    public boolean add(Match m) {
        String qry = "insert into Match(Player1,Player2,WinnerID,PlayTimeInSecond,TotalMove,StartedDate,StartedTime) "
                + "values('"
//                + m.getID() + "','"
                + m.getPlayer1() + "','"
                + m.getPlayer2() + "','"
                + m.getWinnerID() + "','"
                + m.getPlayTimeInSecond() + "','"
                + m.getTotalMove() + "','"
                + m.getStartedDate() + "','"
                + m.getStartedTime()
                + "');";
        return conn.sqlUpdate(qry);
    }

    public boolean update(Match m) {
        String qry = "update Match set "
//                + "ID='" + p.getUsername() + "',"
                + "Player1='" + m.getPlayer1() + "',"
                + "player2='" + m.getPlayer2() + "',"
                + "winnerID='" + m.getWinnerID() + "',"
                + "playTimeInSecond='" + m.getPlayTimeInSecond() + "',"
                + "totalMove='" + m.getTotalMove() + "',"
                + "startedDate='" + m.getStartedDate() + "',"
                + "startedTime='" + m.getStartedTime()
                + "';";
        return conn.sqlUpdate(qry);
    }
    
    public boolean delete(Match m){
        String qry = "delete from Match where ID='" + m.getID() + "'";
        return conn.sqlUpdate(qry);
    }
    
    public boolean closeConnection(){
        return conn.closeConnection();
    }
    
}
