/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.BUS;

import Server.DB.Layers.DAL.FriendDAL;
import Server.DB.Layers.DTO.Friend;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class FriendBUS {

    ArrayList<Friend> list = new ArrayList<>();;
    FriendDAL friendDAL;
    public FriendBUS() {
        readDB();
    }

    public boolean readDB() {
        friendDAL = new FriendDAL();
        list = friendDAL.readDB();
//        friendDAL.closeConnection();
        return list!=null;
    }
    
    public boolean add(Friend f){
//        friendDAL = new FriendDAL();
        return friendDAL.add(f);
    }
    
    public boolean add(String u1,String u2, LocalDate addedDate){
        Friend f = new Friend(u1, u2, addedDate);
        return add(f);
    }
    
    public boolean update(Friend f){
//        friendDAL = new FriendDAL();
        return friendDAL.update(f);
    }
    
    public boolean update(int id,String u1, String u2, LocalDate addedDate){
        Friend f = new Friend(id,u1, u2, addedDate);
        return update(f);
    }
    
    public boolean delete(int id){
        return friendDAL.delete(id);
    }
    public boolean delete(Friend f){
        return delete(f.getID());
    }
    public ArrayList<Friend> getList(){
        return list;
    }
}
