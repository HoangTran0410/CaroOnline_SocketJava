/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.RunClient;
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
            listener = new Thread(() -> {
                listen();
            });
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

                    case CHANGE_PASSWORD:
                        onReceiveChangePassword(received);
                        break;

                    case LIST_ROOM:
                    case CREATE_ROOM:
                    case JOIN_ROOM:
                    case LEAVE_ROOM:
                    case ROOM_CHAT:
                    case PROFILE:
                    case FIND_GAME:
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

        // check status
        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.loginScene, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            RunClient.closeScene(RunClient.SceneName.LOGIN);
            RunClient.openScene(RunClient.SceneName.MAINMENU);
        }
    }

    private void onReceiveSignup(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

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
        RunClient.closeScene(RunClient.SceneName.MAINMENU);
        RunClient.openScene(RunClient.SceneName.LOGIN);
    }

    private void onReceiveChangePassword(String received) {
        String[] splitted = received.split(";");
        String status = splitted[1];

        // turn off loading
        RunClient.changePasswordScene.setLoading(false);

        // check status
        if (status.equals("failed")) {
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(RunClient.loginScene, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            RunClient.closeScene(RunClient.SceneName.CHANGEPASSWORD);
            JOptionPane.showMessageDialog(RunClient.loginScene, "Đổi mật khẩu thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }
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

    // send data fucntions
    public void sendPureData(String data) {
        try {
            dos.writeUTF(data);
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendData(String data) {
        try {

            String encrypted = aes.encrypt(data);
            dos.writeUTF(encrypted);

        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
