/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Player {

    int id;
    String email = "#"; // cài giá trị mặc định
    String name = "#";
    String avatar = "icons8_circled_user_male_skin_type_7_96px.png";
    int yearOfBirth = 1999;
    String gender = "Nam";

    String rank = "#";
    int totalMatch = 0;
    int currentStreak = 0;
    float winRate = 0;

    boolean isMe;

    public Player(boolean isMe) {
        this.isMe = isMe;
    }

    public Player(boolean isMe, int id, String email, String name, String avatar, int yearOfBirth, String gender, String rank, int totalMatch, int currentStreak, float winRate) {
        this.isMe = isMe;

        this.id = id;
        this.email = email;
        this.name = name;
        this.avatar = avatar;
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
        this.rank = rank;
        this.totalMatch = totalMatch;
        this.currentStreak = currentStreak;
        this.winRate = winRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getTotalMatch() {
        return totalMatch;
    }

    public void setTotalMatch(int totalMatch) {
        this.totalMatch = totalMatch;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public float getWinRate() {
        return winRate;
    }

    public void setWinRate(float winRate) {
        this.winRate = winRate;
    }

}
