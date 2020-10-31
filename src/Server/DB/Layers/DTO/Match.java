/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author nguye
 */
public class Match {
    int ID;
    String player1;
    String player2;
    String winnerID;
    int playTimeInSecond;
    int totalMove;
//    LocalDate startedDate;
    LocalDateTime startedTime;

    public Match() {
    }

    public Match(int ID, String p1, String p2, String winnerID, int playTimeInSecond, int totalMove, LocalDateTime startedTime) {
        this.ID = ID;
        this.player1 = p1;
        this.player2 = p2;
        this.winnerID = winnerID;
        this.playTimeInSecond = playTimeInSecond;
        this.totalMove = totalMove;
//        this.startedDate = startedDate;
        this.startedTime = startedTime;
    }
    //Consstrucor tao moi
    public Match(String p1, String p2, String winnerID, int playTimeInSecond, int totalMove, LocalDateTime startedTime) {
        this.player1 = p1;
        this.player2 = p2;
        this.winnerID = winnerID;
        this.playTimeInSecond = playTimeInSecond;
        this.totalMove = totalMove;
//        this.startedDate = startedDate;
        this.startedTime = startedTime;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }



    public int getPlayTimeInSecond() {
        return playTimeInSecond;
    }

    public void setPlayTimeInSecond(int playTimeInSecond) {
        this.playTimeInSecond = playTimeInSecond;
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
//    public LocalDate getStartedDate() {
//        return startedDate;
//    }
//
//    public void setStartedDate(LocalDate startedDate) {
//        this.startedDate = startedDate;
//    }
    
    
}
