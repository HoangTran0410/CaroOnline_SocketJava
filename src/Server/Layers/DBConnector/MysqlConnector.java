/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Layers.DBConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author nguye
 */
public class MysqlConnector {

    Statement stm = null;
    Connection conn = null;
    ResultSet rs = null;
    
    String server = "localhost:3306";
    String db = "CaroDB";
    String user = "root";
    String pass = "123456abc";
    
    public MysqlConnector() {
        connectDB();
    }
    
    public void connectDB(){
        checkDriver();
        setupConnection();
    }
    
    boolean checkDriver(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Khong tim thay Driver mysql !!");
            return false;
        }
    }
    
    public void setupConnection(){
        try{
            String url = "jdbc:mysql://" + server + "/" + db;
            conn = DriverManager.getConnection(url, user, pass);
            stm = conn.createStatement();
            System.out.println("success!");
        }catch(SQLException e){
            System.err.println("" + e); 
        }
    }
    
}

