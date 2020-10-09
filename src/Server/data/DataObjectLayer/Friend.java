/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.data.DataObjectLayer;

/**
 *
 * @author nguye
 */
public class Friend {
    int ID;
    String userID;
    String friendID;
    int totalMatchTogether;

    public Friend() {
    }

    public Friend(int ID, String userID, String friendID, int totalMatchTogether) {
        this.ID = ID;
        this.userID = userID;
        this.friendID = friendID;
        this.totalMatchTogether = totalMatchTogether;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public int getTotalMatchTogether() {
        return totalMatchTogether;
    }

    public void setTotalMatchTogether(int totalMatchTogether) {
        this.totalMatchTogether = totalMatchTogether;
    }
    
    
}
