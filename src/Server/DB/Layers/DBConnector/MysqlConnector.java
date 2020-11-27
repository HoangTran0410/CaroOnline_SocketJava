/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.db.layers.DBConnector;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nguye
 */
public class MysqlConnector {

    Connection conn = null;

    String server = "localhost:3306";
    String db = "carodb";
    String user = "root";
    String pass = "";

    public MysqlConnector() {
        checkDriver();
        setupConnection();
    }

    public void logIn(String userName, String pass) {
        this.user = userName;
        this.pass = pass;
        setupConnection();
    }

    boolean checkDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("Khong tim thay Driver mysql !!");
            return false;
        }
    }

    public boolean setupConnection() {
        try {
            String url = "jdbc:mysql://" + server + "/" + db + "?useUnicode=true&characterEncoding=UTF-8";
            conn = DriverManager.getConnection(url, user, pass);
            return true;
        } catch (SQLException e) {
            System.err.println("Loi ket noi DB: " + e.getMessage());
            return false;
        }
    }

    public ResultSet sqlQry(PreparedStatement stm) {
        if (checkConnection()) {
            try {
                ResultSet rs = stm.executeQuery();
                return rs;
            } catch (SQLException e) {
                System.err.println("Loi thuc thi sql query: " + stm.toString() + " , " + e.getMessage());
            }
        }
        return null;
    }

    public boolean sqlUpdate(PreparedStatement stm) {
        if (checkConnection()) {
            try {
                stm.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println("Loi thuc thi sql update: " + stm.toString() + " , " + e.getMessage());
            }
        }
        return false;
    }

    public boolean checkConnection() {
        if (conn == null) {
            return false;
        }
        return true;
    }

    public boolean closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
            return true;

        } catch (SQLException e) {
            System.err.println("Không thể đóng kết nối tới " + db);
            return false;
        }
    }

    public Connection getConnection() {
        return conn;
    }

}
