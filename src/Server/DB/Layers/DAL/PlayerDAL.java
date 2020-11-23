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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguye
 */
public class PlayerDAL {

    MysqlConnector connector;

    public PlayerDAL() {

    }

    public ArrayList readDB() {
        ArrayList<Player> result = new ArrayList<>();
        connector = new MysqlConnector();

        try {
            String qry = "SELECT * FROM Player;";
            PreparedStatement stm = connector.getConnection().prepareStatement(qry);
            ResultSet rs = connector.sqlQry(stm);

            if (rs != null) {
                while (rs.next()) {
                    Player p = new Player(
                            rs.getInt("ID"),
                            rs.getString("Email"),
                            rs.getString("Password"),
                            rs.getString("Avatar"),
                            rs.getString("Name"),
                            rs.getString("Gender"),
                            rs.getInt("YearOfBirth"),
                            rs.getInt("Score"),
                            rs.getInt("MatchCount"),
                            rs.getFloat("WinRate"),
                            rs.getInt("WinStreak"),
                            rs.getInt("Rank"),
                            rs.getBoolean("Blocked")
                    );
                    result.add(p);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error while trying to read Players info from database!");
        } finally {
            connector.closeConnection();
        }

        return result;
    }

    public boolean add(Player p) {
        boolean result = false;
        connector = new MysqlConnector();

        try {
            String qry = "INSERT INTO Player(Email,Password,Avatar,Name,Gender,YearOfBirth,Score,MatchCount,WinRate,WinStreak,Rank,Blocked) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement stm = connector.getConnection().prepareStatement(qry);
            stm.setString(1, p.getEmail());
            stm.setString(2, p.getPassword());
            stm.setString(3, p.getAvatar());
            stm.setString(4, p.getName());
            stm.setString(5, p.getGender());
            stm.setInt(6, p.getYearOfBirth());
            stm.setInt(7, p.getScore());
            stm.setInt(8, p.getMatchCount());
            stm.setFloat(9, p.getWinRate());
            stm.setInt(10, p.getWinStreak());
            stm.setInt(11, p.getRank());
            stm.setBoolean(12, p.isBlocked());

            result = connector.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connector.closeConnection();
        }

        return result;
    }

    public boolean update(Player p) {
        boolean result = false;
        connector = new MysqlConnector();

        try {
            String qry = "UPDATE Player SET "
                    + "Email=?,"
                    + "Password=?,"
                    + "Avatar=?,"
                    + "Name=?,"
                    + "Gender=?,"
                    + "YearOfBirth=?,"
                    + "Score=?,"
                    + "MatchCount=?,"
                    + "WinRate=?,"
                    + "WinStreak=?,"
                    + "Rank=?,"
                    + "Blocked=?"
                    + " WHERE ID=?";

            PreparedStatement stm = connector.getConnection().prepareStatement(qry);

            stm.setString(1, p.getEmail());
            stm.setString(2, p.getPassword());
            stm.setString(3, p.getAvatar());
            stm.setString(4, p.getName());
            stm.setString(5, p.getGender());
            stm.setInt(6, p.getYearOfBirth());
            stm.setInt(7, p.getScore());
            stm.setInt(8, p.getMatchCount());
            stm.setFloat(9, p.getWinRate());
            stm.setInt(10, p.getWinStreak());
            stm.setInt(11, p.getRank());
            stm.setBoolean(12, p.isBlocked());
            stm.setInt(13, p.getId());

            result = connector.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connector.closeConnection();
        }

        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        connector = new MysqlConnector();

        try {
            String qry = "DELETE FROM player WHERE ID=?";

            PreparedStatement stm = connector.getConnection().prepareStatement(qry);
            stm.setInt(1, id);

            result = connector.sqlUpdate(stm);
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
