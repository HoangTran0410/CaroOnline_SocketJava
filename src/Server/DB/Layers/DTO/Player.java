/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DTO;

import java.time.*;

/**
 *
 * @author nguye
 */
public class Player {

    int ID;
    String username;    //  Tên hiển thị trong game
    String password;
    String email;       //  Email, dùng để đăng nhập
    String gender;
    String rankID;
    LocalDate dateOfBirth;
    int score;
    int matchCount;
    float winRate;
    int winStreak;
    boolean blocked;
    

    public Player() {

    }
    // Constructor tạo player mới hoàn toàn để thêm vào db
    public Player(String username, String password, String email, String gender, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.score = 0;
        this.matchCount = 0;
        this.winRate = 0;
        this.winStreak = 0; 
        this.blocked = false;
        this.rankID = "none";
    }
    
    //Constructor để tạo Player chứa dữ liệu từ db
    public Player(int ID, String username, String password, String email, String gender, String rankID, LocalDate dateOfBirth, int score, int matchCount, float winRate, int winStreak, boolean blocked) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.rankID = rankID;
        this.dateOfBirth = dateOfBirth;
        this.score = score;
        this.matchCount = matchCount;
        this.winRate = winRate;
        this.winStreak = winStreak;
        this.blocked = blocked;
    }
    
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public float getWinRate() {
        return winRate;
    }

    public void setWinRate(float winRate) {
        this.winRate = winRate;
    }

    public int getWinStreak() {
        return winStreak;
    }

    public void setWinStreak(int winStreak) {
        this.winStreak = winStreak;
    }

    public boolean isIsBlocked() {
        return blocked;
    }

    public void setIsBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getRankID() {
        return rankID;
    }

    public void setRankID(String rankID) {
        this.rankID = rankID;
    }
    
    public boolean getBlockedStatus(){
        return blocked;
    }
    public void setBlocked(boolean b){
        blocked = b;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
