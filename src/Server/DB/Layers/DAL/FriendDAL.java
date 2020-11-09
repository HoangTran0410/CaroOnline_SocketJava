/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DAL;

import Server.DB.Layers.DBConnector.MysqlConnector;
import Server.DB.Layers.DTO.Friend;
import Server.DB.Layers.DTO.Match;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguye
 */
public class FriendDAL {

    MysqlConnector conn = new MysqlConnector();
    PreparedStatement stm;

    public FriendDAL() {
        conn.connectDB();
    }

    public ArrayList<Friend> readDB() {
        ArrayList<Friend> result = new ArrayList<>();
        try {
            String sql = "select * from Friend;";
            stm = conn.getConnection().prepareStatement(sql);
            ResultSet rs = conn.sqlQry(stm);
            if (rs != null) {
                while (rs.next()) {
                    Friend f = new Friend();
                    f.setID(rs.getInt("ID"));
                    f.setUser1(rs.getString("User1"));
                    f.setUser2(rs.getString("User2"));
                    f.setAddedDate(LocalDate.parse(rs.getString("AddedDate")));
                    result.add(f);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error!!! Cant get Friends data !!!");
        }
        return result;
    }

    public boolean add(Friend f) {
        String sql = "insert into Friend(User1,User2,AddedDate) values(?,?,?)";
        try {
            stm = conn.getConnection().prepareStatement(sql);
//            stm.setInt(1, f.getID());
            stm.setString(1, f.getUser1());
            stm.setString(2, f.getUser2());
            stm.setString(3, f.getAddedDate().toString());
        } catch (SQLException ex) {
            Logger.getLogger(MatchDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn.sqlUpdate(stm);
    }

    public boolean update(Friend f) {
        String sql = "update Friend set "
                + "User1=?,"
                + "User2=?,"
                + "AddedDate=?"
                + " where ID=?";
        try {
            stm = conn.getConnection().prepareStatement(sql);
            stm.setString(1, f.getUser1());
            stm.setString(2, f.getUser2());
            stm.setString(3, f.getAddedDate().toString());
            stm.setInt(4, f.getID());
        } catch (SQLException ex) {
            Logger.getLogger(MatchDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn.sqlUpdate(stm);
    }

    public boolean delete(int id) {
        String sql = "delete from Friend where ID=?";
        try {
            stm = conn.getConnection().prepareStatement(sql);
            stm.setInt(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(MatchDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn.sqlUpdate(stm);
    }

    public boolean delete(Friend f) {
        return delete(f.getID());
    }

    public boolean closeConnection() {
        return conn.closeConnection();
    }
}
