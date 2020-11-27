/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.RunClient;
import client.model.ProfileData;
import shared.helper.Util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
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
                    case CREATE_ROOM:
                    case JOIN_ROOM:
                    case LEAVE_ROOM:
                    case ROOM_CHAT:
                    case GET_PROFILE:
                        onReceiveGetProfile(received);
                        break;

                    case EDIT_PROFILE:
                        onReceivedEditProfile(received);
                        break;

                    case CHANGE_PASSWORD:
                        onReceiveChangePassword(received);
                        break;

                    case FIND_MATCH:
                        onReceiveFindMatch(received);
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
                JOptionPane.showMessageDialog(null, "Mất kết nối tới server: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                running = false;

                RunClient.closeAllScene();
                RunClient.openScene(RunClient.SceneName.CONNECTSERVER);
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
    }

    // listen events
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
            email = splitted[2];

            // chuyển scene
            RunClient.closeScene(RunClient.SceneName.LOGIN);
            RunClient.openScene(RunClient.SceneName.MAINMENU);
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
            // turn off loading
            RunClient.signupScene.setLoading(false);

            // show failed message
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
        email = null;

        // chuyển scene
        RunClient.closeAllScene();
        RunClient.openScene(RunClient.SceneName.LOGIN);
    }

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
            String currentStreakStr = splitted[10];
            String winRateStr = splitted[11];

            // validate
            try {
                int id = Integer.parseInt(idStr);
                int yearOfBirth = Integer.parseInt(yearOfBirthStr);
                int score = Integer.parseInt(scoreStr);
                int matchCount = Integer.parseInt(matchCountStr);
                int currentStreak = Integer.parseInt(currentStreakStr);
                float winRate = Float.parseFloat(winRateStr);

                // TODO check isMe
                ProfileData p = new ProfileData(id, email, name, avatar, gender, yearOfBirth, score, matchCount, currentStreak, winRate);

                // show data to UI
                RunClient.profileScene.setProfileData(p);

            } catch (Exception e) {
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
            String newEmail = splitted[2];
            RunClient.socketHandler.setEmail(newEmail);

            // load lại thông tin cá nhân mới - có thể ko cần! load lại cho chắc
            getProfile(newEmail);
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

    private void onReceiveFindMatch(String received) {

    }

    // functions
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

    public void findMatch() {
        sendData(StreamData.Type.FIND_MATCH.name());
    }

    // send data fucntions
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
