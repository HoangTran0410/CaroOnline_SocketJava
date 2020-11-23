/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DAL;

import Server.DB.Layers.DBConnector.MysqlConnector;
import Server.DB.Layers.DTO.GameMatch;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguye
 */
public class GameMatchDAL {

    MysqlConnector connector;

    public GameMatchDAL() {

    }

    public ArrayList readDB() {
        ArrayList<GameMatch> result = new ArrayList<>();
        connector = new MysqlConnector();

        try {
            String qry = "SELECT * FROM gamematch;";
            PreparedStatement stm = connector.getConnection().prepareStatement(qry);
            ResultSet rs = connector.sqlQry(stm);

            if (rs != null) {
                while (rs.next()) {
                    GameMatch g = new GameMatch(
                            rs.getInt("ID"),
                            rs.getInt("PlayerID1"),
                            rs.getInt("PlayerID2"),
                            rs.getInt("WinnerID"),
                            rs.getInt("PlayTime"),
                            rs.getInt("TotalMove"),
                            LocalDateTime.parse(rs.getString("StartedTime")),
                            rs.getString("Chat")
                    );

                    result.add(g);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while trying to read Matchs info from database!");
        } finally {
            connector.closeConnection();
        }

        return result;
    }

    public boolean add(GameMatch m) {
        boolean result = false;
        connector = new MysqlConnector();

        try {
            String sql = "INSERT INTO GameMatch(PlayerID1,PlayerID2,WinnerID,PlayTime,TotalMove,StartedTime) "
                    + "VALUES(?,?,?,?,?,?)";
            PreparedStatement stm = connector.getConnection().prepareStatement(sql);
            stm.setInt(1, m.getPlayerID1());
            stm.setInt(2, m.getPlayerID2());
            stm.setInt(3, m.getWinnerID());
            stm.setInt(4, m.getPlayTime());
            stm.setInt(5, m.getTotalMove());
            stm.setString(6, m.getStartedTime().toString());

            result = connector.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(GameMatchDAL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connector.closeConnection();
        }

        return result;
    }

    public boolean update(GameMatch m) {
        boolean result = false;
        connector = new MysqlConnector();

        try {
            String sql = "UPDATE GameMatch SET "
                    + "PlayerID1=?,"
                    + "PlayerID2=?,"
                    + "WinnerID=?,"
                    + "PlayTime=?,"
                    + "TotalMove=?,"
                    + "StartedTime=?"
                    + " WHERE ID=?";

            PreparedStatement stm = connector.getConnection().prepareStatement(sql);
            stm.setInt(1, m.getPlayerID1());
            stm.setInt(2, m.getPlayerID2());
            stm.setInt(3, m.getWinnerID());
            stm.setInt(4, m.getPlayTime());
            stm.setInt(5, m.getTotalMove());
            stm.setString(6, m.getStartedTime().toString());
            stm.setInt(7, m.getId());

            result = connector.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(GameMatchDAL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connector.closeConnection();
        }

        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        connector = new MysqlConnector();

        try {
            String qry = "DELETE FROM GameMatch WHERE ID=?";

            PreparedStatement stm = connector.getConnection().prepareStatement(qry);
            stm.setInt(1, id);

            result = connector.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(GameMatchDAL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connector.closeConnection();
        }

        return result;
    }

}
