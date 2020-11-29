/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

import java.util.ArrayList;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class CurrentGame {

    ArrayList<ProfileData> listViewer;
    ProfileData player1;
    ProfileData player2;
    boolean player1Turn;

    int[][] listCell;

    public CurrentGame() {

    }

    public ArrayList<ProfileData> getListViewer() {
        return listViewer;
    }

    public void setListViewer(ArrayList<ProfileData> listViewer) {
        this.listViewer = listViewer;
    }

    public ProfileData getPlayer1() {
        return player1;
    }

    public void setPlayer1(ProfileData player1) {
        this.player1 = player1;
    }

    public ProfileData getPlayer2() {
        return player2;
    }

    public void setPlayer2(ProfileData player2) {
        this.player2 = player2;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }

    public int[][] getListCell() {
        return listCell;
    }

    public void setListCell(int[][] listCell) {
        this.listCell = listCell;
    }

}
