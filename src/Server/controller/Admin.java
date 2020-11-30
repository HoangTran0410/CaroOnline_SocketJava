/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.io.IOException;
import server.RunServer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.db.layers.BUS.GameMatchBUS;
import server.db.layers.BUS.PlayerBUS;
import server.db.layers.DTO.GameMatch;
import server.db.layers.DTO.Player;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Admin implements Runnable {

    GameMatchBUS gameMatchBus;
    PlayerBUS playerBus;

    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        String inp;

        while (!RunServer.isShutDown) {
            System.out.print("AdminCommand> ");
            inp = s.nextLine();

            if (inp.equalsIgnoreCase("user-count")) {
                System.out.println("> " + RunServer.clientManager.getSize());
            } else if (inp.equalsIgnoreCase("best-user")) {
                showBestPlayerInfo(getBestUser());
            } else if (inp.equalsIgnoreCase("shortest-match")) {
                showShortestMatch(getShortestMatch());
            } else if (inp.indexOf("block") == 0) {
                System.out.println(blockUser(inp.split(" ")[1]));
            } else if (inp.indexOf("log") == 0) {
                showGameMatchDetails(inp.split(" ")[1]);
            } else if (inp.equalsIgnoreCase("room-count")) {
                System.out.println("> " + RunServer.roomManager.getSize());
            } else if (inp.equalsIgnoreCase("shutdown")) {
                System.out.println("shuting down...");
                RunServer.isShutDown = true;

                try {
                    RunServer.ss.close();
                } catch (IOException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (inp.equalsIgnoreCase("help")) {
                System.out.println("===[List commands]======================\n"
                        + "======= Thiết yếu =======\n"
                        + "user-count:        số người đang online\n"
                        + "best-user:         thông tin user thắng nhiều nhất\n"
                        + "shortest-match:    thông tin trận đấu có thời gian ngắn nhất\n"
                        + "block <user-emal>: block user có email là <user-email khỏi hệ thống>\n"
                        + "log <match-id>:    xem thông tin trận đấu có mã là <match-id>\n"
                        + "======= Thêm =======\n"
                        + "room-count: số phòng đang mở\n"
                        + "shutdown: tắt server\n"
                        + "=======================================");
            }
        }
    }

    // Get player with the most win count
    private Player getBestUser() {
        Player bestPlayer = null;
        playerBus = new PlayerBUS();
        int max = 0;
        for (Player p : playerBus.getList()) {
            if (p.getWinCount() > max) {
                max = p.getWinCount();
                bestPlayer = new Player(p);
            }
        }
        return bestPlayer;
    }

    private void showBestPlayerInfo(Player p) {
        System.out.println("Player with the most win count: "
                + p.getName() + " - " + p.getEmail());
        System.out.println("Win count: " + p.getWinCount());
    }

    // Get the match with the shortest play time
    public GameMatch getShortestMatch() {
        gameMatchBus = new GameMatchBUS();
        GameMatch shortestMatch = null;
        int min = gameMatchBus.getList().get(0).getTotalMove();
        for (GameMatch m : gameMatchBus.getList()) {
            if (m.getPlayTime() < min) {
                min = m.getPlayTime();
                shortestMatch = new GameMatch(m);
            }
        }
        return shortestMatch;
    }

    private void showShortestMatch(GameMatch m) {
        playerBus = new PlayerBUS();
        Player p1 = new Player(playerBus.getById(m.getPlayerID1()));
        Player p2 = new Player(playerBus.getById(m.getPlayerID2()));
        System.out.println("The match with shortest play time: ");
        System.out.println("Player 1: " + p1.getName());
        System.out.println("Player 1: " + p2.getName());
        System.out.println("Play time: " + m.getPlayTime() + " second");
    }

    // Block user with provided email
    private String blockUser(String email) {
        playerBus = new PlayerBUS();
        for (Player p : playerBus.getList()) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                p.setBlocked(true);
                return playerBus.update(p) ? "Success" : "Fail";
            }
        }
        return "Cant find user with provided email!";
    }

    // Get Game match with provide id
    private void showGameMatchDetails(String id) {
        gameMatchBus = new GameMatchBUS();
        playerBus = new PlayerBUS();
        GameMatch m = gameMatchBus.getById(Integer.parseInt(id));
        System.out.println("Match id: " + m.getId());
        System.out.println("    + Player 1: " + playerBus.getById(m.getPlayerID1()).getName());
        System.out.println("    + Player 2: " + playerBus.getById(m.getPlayerID2()).getName());
        System.out.println("    + Winner: " + playerBus.getById(m.getWinnerID()).getName());
        System.out.println("    + Play time in second: " + m.getPlayTime());
        System.out.println("    + Total move: " + m.getTotalMove());
    }

    public static void main(String[] args) {
        Admin ad = new Admin();
        ad.run();
    }
}
