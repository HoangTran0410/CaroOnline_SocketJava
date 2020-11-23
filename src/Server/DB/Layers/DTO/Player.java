/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DTO;

/**
 *
 * @author nguye
 */
public class Player {

    int id;
    String email;
    String password;
    String avatar;
    String name;
    String gender;
    int yearOfBirth;
    int score = 0; // gia tri mac dinh
    int matchCount = 0;
    float winRate = 0;
    int winStreak = 0;
    int rank = 0;
    boolean blocked = false;

    public Player(int id, String email, String password, String avatar, String name, String gender, int yearOfBirth, int score, int matchCount, float winRate, int winStreak, int rank, boolean blocked) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.name = name;
        this.gender = gender;
        this.yearOfBirth = yearOfBirth;
        this.score = score;
        this.matchCount = matchCount;
        this.winRate = winRate;
        this.winStreak = winStreak;
        this.rank = rank;
        this.blocked = blocked;
    }

    public Player(String email, String password, String avatar, String name, String gender, int yearOfBirth) {
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.name = name;
        this.gender = gender;
        this.yearOfBirth = yearOfBirth;
    }

    public Player(Player p) {
        this.id = p.id;
        this.email = p.email;
        this.password = p.password;
        this.avatar = p.avatar;
        this.name = p.name;
        this.gender = p.gender;
        this.yearOfBirth = p.yearOfBirth;
        this.score = p.score;
        this.matchCount = p.matchCount;
        this.winRate = p.winRate;
        this.winStreak = p.winStreak;
        this.rank = p.rank;
        this.blocked = p.blocked;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

}
