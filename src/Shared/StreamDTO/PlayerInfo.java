/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared.StreamDTO;

import Server.DB.Layers.DTO.Player;
import java.time.LocalDate;

/**
 *
 * @author nguye
 */
public class PlayerInfo extends Player{

    String status = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public PlayerInfo() {
        super();
    }

    public PlayerInfo(String status, String username, String password, String email, String gender, LocalDate dateOfBirth) {
        super(username, password, email, gender, dateOfBirth);
        this.status = status;
    }

    public PlayerInfo(String status, int ID, String username, String password, String email, String gender, String rankID, LocalDate dateOfBirth, int score, int matchCount, float winRate, int winStreak, boolean blocked) {
        super(ID, username, password, email, gender, rankID, dateOfBirth, score, matchCount, winRate, winStreak, blocked);
        this.status = status;
    }
    
}
