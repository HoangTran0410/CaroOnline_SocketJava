/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DTO;

import java.time.LocalDate;

/**
 *
 * @author nguye
 */
public class Friend {
    int ID;
    String user1;
    String user2;
    LocalDate addedDate;

    public Friend() {
    }

    public Friend(int ID, String user1, String user2, LocalDate addedDate) {
        this.ID = ID;
        this.user1 = user1;
        this.user2 = user2;
        this.addedDate = addedDate;
    }
    
    public Friend(String user1, String user2, LocalDate addedDate) {
        this.user1 = user1;
        this.user2 = user2;
        this.addedDate = addedDate;
    }



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    
    
}
