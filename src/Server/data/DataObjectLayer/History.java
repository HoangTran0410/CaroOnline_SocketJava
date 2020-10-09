/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.data.DataObjectLayer;

import java.time.LocalDateTime;

/**
 *
 * @author nguye
 */
public class History {
    int ID;
    String winnerID;
    String loserID;
    int totalTimeInSecond;
    int totalMove;
    LocalDateTime startedTime;

    public History() {
    }
    
    public History(int ID, String winnerID, String loserID, int totalTimeInSecond, int totalMove, LocalDateTime startedTime) {
        this.ID = ID;
        this.winnerID = winnerID;
        this.loserID = loserID;
        this.totalTimeInSecond = totalTimeInSecond;
        this.totalMove = totalMove;
        this.startedTime = startedTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(String winnerID) {
        this.winnerID = winnerID;
    }

    public String getLoserID() {
        return loserID;
    }

    public void setLoserID(String loserID) {
        this.loserID = loserID;
    }

    public int getTotalTimeInSecond() {
        return totalTimeInSecond;
    }

    public void setTotalTimeInSecond(int totalTimeInSecond) {
        this.totalTimeInSecond = totalTimeInSecond;
    }

    public int getTotalMove() {
        return totalMove;
    }

    public void setTotalMove(int totalMove) {
        this.totalMove = totalMove;
    }

    public LocalDateTime getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(LocalDateTime startedTime) {
        this.startedTime = startedTime;
    }
    
    
}
