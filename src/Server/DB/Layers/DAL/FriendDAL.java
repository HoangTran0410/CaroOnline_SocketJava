/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DAL;

import Server.DB.Layers.DBConnector.MysqlConnector;
import java.sql.PreparedStatement;

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
    
}
