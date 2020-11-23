/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.DB.Layers.BUS.GameMatchBUS;
import Server.DB.Layers.BUS.PlayerBUS;
import Server.DB.Layers.DTO.GameMatch;
import Server.DB.Layers.DTO.Player;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Admin implements Runnable {

    GameMatchBUS gameMatchBus;

    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        String inp;

        while (true) {
            System.out.print("AdminCommand> ");
            inp = s.nextLine();

            if (inp.equalsIgnoreCase("user-count")) {
                System.out.println("> " + Server.clientManager.getSize());
            } else if (inp.equalsIgnoreCase("best-user")) {
                showBestPlayerInfo(getBestUser());
            } else if (inp.equalsIgnoreCase("shortest-match")) {
                System.out.println("> not available");
            } else if (inp.indexOf("block") == 0) {
                System.out.println("> not available");
            } else if (inp.indexOf("log") == 0) {
                System.out.println("> not available");
            } else if (inp.equalsIgnoreCase("room-count")) {
                System.out.println("> not available");
            } else if (inp.equalsIgnoreCase("shutdown")) {
                System.out.println("> not available");
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

    private void showBestPlayerInfo(Player p) {
        System.out.println("Player with the most win count: " + p.getName() + " - " + p.getEmail());
        System.out.println("Win count: " + p.getWinCount());
    }

    // Get player with the most win count
    private Player getBestUser() {
        Player bestPlayer = null;
        PlayerBUS pBus = new PlayerBUS();
        int max = 0;
        for (Player p : pBus.getList()) {
            if (p.getWinCount() > max) {
                max = p.getWinCount();
                bestPlayer = new Player(p);
            }
        }
        return bestPlayer;
    }
    // Get the match with the shortest play time
    public GameMatch getShortestMatch() {
        gameMatchBus = new GameMatchBUS();
        GameMatch shortestMatch = null;
        int min = gameMatchBus.getList().get(0).getTotalMove();
        for (GameMatch m : gameMatchBus.getList()) {
            if(m.getPlayTime() < min){
                min = m.getPlayTime();
                shortestMatch = new GameMatch(m);
            }
        }
        return shortestMatch;
    }

    public static void main(String[] args) {
        Admin ad = new Admin();
        ad.run();
    }

}
