/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.RunClient;
import client.model.PlayerInGame;
import client.model.ProfileData;
import client.view.scene.MainMenu;
import shared.helper.Util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import shared.constant.StreamData;
import shared.security.AES;
import shared.security.RSA;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class SocketHandler {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    String email = null; // lưu tài khoản đăng nhập hiện tại
    String roomId = null; // lưu room hiện tại

    Thread listener = null;
    AES aes;

    public String connect(String addr, int port) {
        try {
            // getting ip 
            InetAddress ip = InetAddress.getByName(addr);

            // establish the connection with server port 
            s = new Socket();
            s.connect(new InetSocketAddress(ip, port), 4000);
            System.out.println("Connected to " + ip + ":" + port + ", localport:" + s.getLocalPort());

            // obtaining input and output streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            // close old listener
            if (listener != null && listener.isAlive()) {
                listener.interrupt();
            }

            // listen to server
            listener = new Thread(this::listen);
            listener.start();

            // security
            initSecurityAES();

            // connect success
            return "success";

        } catch (IOException e) {

            // connect failed
            return "failed;" + e.getMessage();
        }
    }

    private void listen() {
        boolean running = true;

        while (running) {
            try {
                // receive the data from server
                String received = dis.readUTF();

                // decrypt data if needed
                if (aes != null) {
                    received = aes.decrypt(received);
                }

                // process received data
                StreamData.Type type = StreamData.getTypeFromData(received);

                switch (type) {
                    case AESKEY:
                        onReceiveAESKey(received);
                        break;

                    case LOGIN:
                        onReceiveLogin(received);
                        break;

                    case SIGNUP:
                        onReceiveSignup(received);
                        break;

                    case LOGOUT:
                        onReceiveLogout(received);
                        break;

                    case LIST_ROOM:
                        onReceiveListRoom(received);
                        break;

                    case LIST_ONLINE:
                        onReceiveListOnline(received);
                        break;

                    case CREATE_ROOM:
                        onReceiveCreateRoom(received);
                        break;

                    case JOIN_ROOM:
                        onReceiveJoinRoom(received);
                        break;

                    case WATCH_ROOM:
                        onReceiveWatchRoom(received);
                        break;

                    case FIND_MATCH:
                        onReceiveFindMatch(received);
                        break;

                    case CANCEL_FIND_MATCH:
                        onReceiveCancelFindMatch(received);
                        break;

                    case REQUEST_PAIR_MATCH:
                        onReceiveRequestPairMatch(received);
                        break;

                    case RESULT_PAIR_MATCH:
                        onReceiveResultPairMatch(received);
                        break;

                    case DATA_ROOM:
                        onReceiveDataRoom(received);
                        break;

                    case CHAT_ROOM:
                        onReceiveChatRoom(received);
                        break;

                    case LEAVE_ROOM:
                        onReceiveLeaveRoom(received);
                        break;

                    case GET_PROFILE:
                        onReceiveGetProfile(received);
                        break;

                    case EDIT_PROFILE:
                        onReceivedEditProfile(received);
                        break;

                    case CHANGE_PASSWORD:
                        onReceiveChangePassword(received);
                        break;

                    case MOVE:
                    case UNDO:
                    case UNDO_ACCEPT:
                    case NEW_GAME:
                    case NEW_GAME_ACCEPT:
                    case EXIT:
                        running = false;
                }

            } catch (IOException ex) {
                Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
                running = false;
            }
        }

        try {
            // closing resources
            s.close();
            dis.close();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        // alert if connect interup
        JOptionPane.showMessageDialog(null, "Mất kết nối tới server", "Lỗi", JOptionPane.ERROR_MESSAGE);
        RunClient.closeAllScene();
        RunClient.openScene(RunClient.SceneName.CONNECTSERVER);
    }

    // ================ listen events ============
    // auth
    private void onReceiveAESKey(String received) {
        // khi client nhận được phản hồi "đã nhận được aes key" từ server
        // tắt scene connectServer
        RunClient.closeScene(RunClient.SceneName.CONNECTSERVER);
        // mở scene login
        RunClient.openScene(RunClient.SceneName.LOGIN);
    }

    private void onReceiveLogin(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        // turn off loading
        RunClient.loginScene.setLoading(false);

        if (status.equals("failed")) {
            // hiển thị lỗi
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.loginScene, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            // lưu email login
            this.email = splitted[2];

            // chuyển scene
            RunClient.closeScene(RunClient.SceneName.LOGIN);
            RunClient.openScene(RunClient.SceneName.MAINMENU);

            // tự động lấy danh sách phòng
            listRoom();
        }
    }

    private void onReceiveSignup(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        // turn off loading
        RunClient.signupScene.setLoading(false);

        // check status
        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.signupScene, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            JOptionPane.showMessageDialog(RunClient.signupScene, "Đăng ký thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            RunClient.closeScene(RunClient.SceneName.SIGNUP);
            RunClient.openScene(RunClient.SceneName.LOGIN);
        }
    }

    private void onReceiveLogout(String received) {
        // xóa email login
        this.email = null;

        // chuyển scene
        RunClient.closeAllScene();
        RunClient.openScene(RunClient.SceneName.LOGIN);
    }

    // main menu
    private void onReceiveListRoom(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("failed")) {

        } else if (status.equals("success")) {
            int roomCount = Integer.parseInt(splitted[2]);

            // https://niithanoi.edu.vn/huong-dan-thao-tac-voi-jtable-lap-trinh-java-swing.html
            Vector vheader = new Vector();
            vheader.add("Mã");
            vheader.add("Cặp đấu");
            vheader.add("Số người");

            Vector vdata = new Vector();

            // i += 3: 3 là số cột trong bảng
            // i = 3; i < roomCount + 3: dữ liệu phòng bắt đầu từ index 3 trong mảng splitted
            for (int i = 3; i < roomCount + 3; i += 3) {

                String roomId = splitted[i];
                String title = splitted[i + 1];
                String clientCount = splitted[i + 2];

                Vector vrow = new Vector();
                vrow.add(roomId);
                vrow.add(title);
                vrow.add(clientCount);

                vdata.add(vrow);
            }

            RunClient.mainMenuScene.setListRoom(vdata, vheader);
        }
    }

    private void onReceiveListOnline(String received) {

    }

    private void onReceiveCreateRoom(String received) {

    }

    private void onReceiveJoinRoom(String received) {
        String[] splitted = received.split(";");
        String roomId = splitted[2];

        // save room id
        this.roomId = roomId;

        // change scene
        RunClient.closeScene(RunClient.SceneName.MAINMENU);
        RunClient.openScene(RunClient.SceneName.INGAME);
        RunClient.inGameScene.setTitle("Phòng #" + roomId);

        // get room data
        dataRoom(roomId);
    }

    private void onReceiveWatchRoom(String received) {

    }

    // pair match
    private void onReceiveFindMatch(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        // check status
        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.mainMenuScene, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            RunClient.mainMenuScene.setDisplayState(MainMenu.State.FINDING_MATCH);
        }
    }

    private void onReceiveCancelFindMatch(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        // check status
        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.mainMenuScene, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            RunClient.mainMenuScene.setDisplayState(MainMenu.State.DEFAULT);
        }
    }

    private void onReceiveRequestPairMatch(String received) {
        String[] splitted = received.split(";");
        String competitorName = splitted[1];

        // show data
        RunClient.mainMenuScene.foundMatch(competitorName);
    }

    private void onReceiveResultPairMatch(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        // reset display state of main menu
        RunClient.mainMenuScene.setDisplayState(MainMenu.State.DEFAULT);

        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.mainMenuScene, failedMsg, "Không thể ghép trận", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            System.out.println("Ghép trận thành công, đang chờ server cho vào phòng.");
        }
    }

    // in game
    private void onReceiveDataRoom(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.mainMenuScene, failedMsg, "Không lấy được dữ liệu phòng", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            PlayerInGame p1 = new PlayerInGame(splitted[2], splitted[3], splitted[4]);
            PlayerInGame p2 = new PlayerInGame(splitted[5], splitted[6], splitted[7]);

            System.out.println(p1.getAvatar() + ", " + p2.getAvatar());

            RunClient.inGameScene.setPlayerInGame(p1, p2);
        }
    }

    private void onReceiveChatRoom(String received) {

    }

    private void onReceiveLeaveRoom(String received) {

    }

    // profile
    private void onReceiveGetProfile(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        // turn off loading
        RunClient.profileScene.setLoading(false);

        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.profileScene, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            // get player data from received
            String idStr = splitted[2];
            String email = splitted[3];
            String name = splitted[4];
            String avatar = splitted[5];
            String gender = splitted[6];
            String yearOfBirthStr = splitted[7];
            String scoreStr = splitted[8];
            String matchCountStr = splitted[9];
            String winCountStr = splitted[10];
            String tieCountStr = splitted[11];
            String loseCountStr = splitted[12];
            String currentStreakStr = splitted[13];
            String winRateStr = splitted[14];

            // validate
            try {
                int id = Integer.parseInt(idStr);
                int yearOfBirth = Integer.parseInt(yearOfBirthStr);
                int score = Integer.parseInt(scoreStr);
                int matchCount = Integer.parseInt(matchCountStr);
                int winCount = Integer.parseInt(winCountStr);
                int tieCount = Integer.parseInt(tieCountStr);
                int loseCount = Integer.parseInt(loseCountStr);
                int currentStreak = Integer.parseInt(currentStreakStr);
                float winRate = Float.parseFloat(winRateStr);

                ProfileData p = new ProfileData(id, email, name, avatar, gender, yearOfBirth, score, matchCount, winCount, tieCount, loseCount, currentStreak, winRate);

                // show data to UI
                RunClient.profileScene.setProfileData(p);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(RunClient.profileScene, "Dữ liệu hồ sơ bị lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);

                // tự động đóng scene profile nếu có lỗi
                RunClient.closeScene(RunClient.SceneName.PROFILE);
            }
        }
    }

    private void onReceivedEditProfile(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        // turn off loading
        RunClient.profileScene.setProfileSaveLoading(false);

        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.profileScene, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            JOptionPane.showMessageDialog(RunClient.profileScene, "Đổi thông tin thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // lưu lại email
            this.email = splitted[2];

            // load lại thông tin cá nhân mới - có thể ko cần! nhưng cứ load lại cho chắc
            getProfile(this.email);
        }
    }

    private void onReceiveChangePassword(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        // turn off loading
        RunClient.changePasswordScene.setLoading(false);

        // check status
        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.changePasswordScene, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            RunClient.closeScene(RunClient.SceneName.CHANGEPASSWORD);
            JOptionPane.showMessageDialog(RunClient.changePasswordScene, "Đổi mật khẩu thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ============= functions ===============
    // auth
    private void initSecurityAES() {
        // create new key
        aes = new AES();

        // encrypt aes key using rsa with server's public key 
        RSA clientSideRSA = new RSA()
                .preparePublicKey("src/Server/rsa_keypair/publicKey");

        String aesKey = aes.getSecretKey();
        String aesKeyEncrypted = clientSideRSA.encrypt(aesKey);

        // send to server
        sendPureData(StreamData.Type.AESKEY.name() + ";" + aesKeyEncrypted);
    }

    public void login(String email, String password) {
        // hasing password
        String passwordHash = Util.hash(password);

        // prepare data
        String data = StreamData.Type.LOGIN.name() + ";" + email + ";" + passwordHash;

        // send data
        sendData(data);
    }

    public void signup(String email, String password, String name, String gender, int yearOfBirth, String avatar) {
        // prepare data
        String data = StreamData.Type.SIGNUP.name() + ";"
                + email + ";"
                + Util.hash(password) + ";"
                + avatar + ";"
                + name + ";"
                + gender + ";"
                + String.valueOf(yearOfBirth);

        // send data
        sendData(data);
    }

    public void logout() {
        // prepare data
        String data = StreamData.Type.LOGOUT.name();

        // send data
        sendData(data);
    }

    // main menu
    public void listRoom() {
        sendData(StreamData.Type.LIST_ROOM.name());
    }

    // pair match
    public void findMatch() {
        sendData(StreamData.Type.FIND_MATCH.name());
    }

    public void cancelFindMatch() {
        sendData(StreamData.Type.CANCEL_FIND_MATCH.name());
    }

    public void declinePairMatch() {
        sendData(StreamData.Type.REQUEST_PAIR_MATCH.name() + ";no");
    }

    public void acceptPairMatch() {
        sendData(StreamData.Type.REQUEST_PAIR_MATCH.name() + ";yes");
    }

    // in game
    public void dataRoom(String roomId) {
        sendData(StreamData.Type.DATA_ROOM.name() + ";" + roomId);
    }

    // profile
    public void changePassword(String oldPassword, String newPassword) {
        // hasing password
        String oldPasswordHash = Util.hash(oldPassword);
        String newPasswordHash = Util.hash(newPassword);

        // prepare data
        String data = StreamData.Type.CHANGE_PASSWORD.name() + ";" + oldPasswordHash + ";" + newPasswordHash;

        // send data
        sendData(data);
    }

    public void getProfile(String email) {
        // prepare data
        String data = StreamData.Type.GET_PROFILE.name() + ";" + email;

        // send data
        sendData(data);
    }

    public void editProfile(String newEmail, String name, String avatar, String yearOfBirth, String gender) {
        // prepare data
        String data = StreamData.Type.EDIT_PROFILE + ";"
                + newEmail + ";"
                + name + ";"
                + avatar + ";"
                + yearOfBirth + ";"
                + gender;

        // send data
        sendData(data);
    }

    // send data
    public void sendPureData(String data) {
        try {
            dos.writeUTF(data);

        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendData(String data) {
        try {

            String encrypted = aes.encrypt(data);
            dos.writeUTF(encrypted);

        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    // get set
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
