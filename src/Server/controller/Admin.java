/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import server.RunServer;
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
                System.out.println("> " + RunServer.clientManager.getSize());
            } else if (inp.equalsIgnoreCase("best-user")) {
                System.out.println("> not available");
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

}
