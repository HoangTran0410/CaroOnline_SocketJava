/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.DB.Layers.BUS.PlayerBUS;
import Server.DB.Layers.DTO.Player;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Admin implements Runnable {

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
//                System.out.println("> not available");
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
                        + "shortest-match:    thông tin trận đấu ngắn nhất\n"
                        + "block <user-emal>: block user có email là <user-email khỏi hệ thống>\n"
                        + "log <match-id>:    xem thông tin trận đấu có mã là <match-id>\n"
                        + "======= Thêm =======\n"
                        + "room-count: số phòng đang mở\n"
                        + "shutdown: tắt server\n"
                        + "=======================================");
            }
        }
    }

    private void showBestPlayerInfo(HashMap m) {
        Player p = (Player) m.keySet().toArray()[0];
        int winCount = (int) m.get(p);
        System.out.println("Player with the most win count: " + p.getName()+ " - " + p.getEmail());
        System.out.println("Win count: " + winCount);
    }

    private HashMap getBestUser() {
        HashMap m = new HashMap();
        Player bestPlayer = new Player();
        PlayerBUS pBus = new PlayerBUS();
        int max = 0;
        for (Player p : pBus.getList()) {
            int pWinCount = pBus.getWinCount(p.getID());
            if (pWinCount > max) {
                max = pWinCount;
                bestPlayer = p;
            }
        }
        m.put(bestPlayer, max);
        return m;
    }
    
    public static void main(String[] args) {
        Admin ad = new Admin();
        ad.run();
    }

}
