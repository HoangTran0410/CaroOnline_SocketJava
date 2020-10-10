/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Layers.DataObjectLayer;

import java.time.*;

/**
 *
 * @author nguye
 */
public class Player {

    int ID;
    String username;    //  Email, player use this email to login
    String password;
    String disPlayName;
    String gender;
    LocalDate dateOfBirth;
    boolean isActive;
    boolean isBlocked;
    int totalWin;
    int totalLose;

    public Player() {

    }

    public Player(int ID, String username, String password, String disPlayName, String gender, LocalDate dateOfBirth, int totalWin, int totalLose) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.disPlayName = disPlayName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.totalWin = totalWin;
        this.totalLose = totalLose;
        this.isActive = false;
        this.isBlocked = false;
    }
    
    public boolean getActiveStatus(){
        return isActive;
    }
    
    public void setActive(boolean b){
        isActive = b;
    }
    
    public boolean getBlockedStatus(){
        return isBlocked;
    }
    public void setBlocked(boolean b){
        isBlocked = b;
    }

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisPlayName() {
        return disPlayName;
    }

    public void setDisPlayName(String disPlayName) {
        this.disPlayName = disPlayName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(int totalWin) {
        this.totalWin = totalWin;
    }

    public int getTotalLose() {
        return totalLose;
    }

    public void setTotalLose(int totalLose) {
        this.totalLose = totalLose;
    }

}
