/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.RunServer;
import server.db.layers.BUS.PlayerBUS;
import shared.constant.StreamData;
import shared.security.AES;
import shared.security.RSA;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Client implements Runnable {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    String email; // if == null => chua dang nhap
    Room room; // if == null => chua vao phong nao het

    AES aes;

    public Client(Socket s) throws IOException {
        this.s = s;

        // obtaining input and output streams 
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {

        String received;
        boolean running = true;

        while (running) {
            try {
                // receive the request from client
                received = dis.readUTF();

                // decrypt data if needed
                if (aes != null) {
                    received = aes.decrypt(received);
                }

                // process received data
                StreamData.Type type = StreamData.getTypeFromData(received);

                switch (type) {
                    case AESKEY:
                        // get encrypted key
                        String keyEncrypted = received.split(";")[1];

                        // init rsa
                        RSA serverSide = new RSA()
                                .preparePrivateKey("src/Server/rsa_keypair/privateKey");

                        // decrypt key
                        String aesKey = serverSide.decrypt(keyEncrypted);
                        System.out.println("Server receive AES key: " + aesKey);

                        // set AES key
                        setSecretKey(aesKey);

                        // notify client
                        sendData(StreamData.Type.AESKEY.name());
                        break;

                    case LOGIN:
                        // get email / pass from data
                        String[] splitted = received.split(";");
                        String email = splitted[1];
                        String password = splitted[2];

                        // check login
                        String status = new PlayerBUS().checkLogin(email, password);

                        // send status
                        sendData(StreamData.Type.LOGIN.name() + ";" + status);
                        break;

                    case SIGNUP:
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
                System.out.println("Connection lost with " + s.getPort());
                break;
            }
        }

        try {
            // closing resources 
            this.s.close();
            this.dis.close();
            this.dos.close();
            System.out.println("- Client disconnected: " + s);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // security handlers
    public void setSecretKey(String seckey) {
        aes = new AES(seckey);
    }

    // communicating handlers
    public String sendData(String data) {
        try {
            if (aes != null) {
                String encrypted = aes.encrypt(data);
                this.dos.writeUTF(encrypted);
            } else {
                this.dos.writeUTF(data);
            }
            return "success";
        } catch (IOException e) {
            System.err.println("Send data failed to " + this.getEmail());
            return "failed;" + e.getMessage();
        }
    }

    public String sendPureData(String data) {
        try {
            this.dos.writeUTF(data);
            return "success";
        } catch (IOException e) {
            System.err.println("Send data failed to " + this.getEmail());
            return "failed;" + e.getMessage();
        }
    }

    // room handlers
    public String joinRoom(String id) {
        // đang trong phòng rồi ?
        if (this.room != null) {
            return "failed;Không thể chuyển phòng. Đang trong phòng " + this.room.getId() + " rồi!";
        }

        Room room = RunServer.roomManager.find(id);

        // không tìm thấy phòng cần join ?
        if (room == null) {
            return "failed;Không tìm thấy phòng " + id;
        }

        // join vào phòng thanh cong hay khong ?
        if (room.addClient(this)) {
            this.room = room;
            return "success";
        }

        return "failed;Không thể tham gia phòng";
    }

    public String joinRoom(Room room) {
        return joinRoom(room.getId());
    }

    // TODO chuyển return type về String
    public boolean leaveRoom() {
        // hien tai chua trong phong nao het ?
        if (this.room == null) {
            return false;
        }

        // roi phong thanh cong hay khong ?
        if (this.room.removeClient(this)) {
            this.room = null;
            return true;
        }

        return false;
    }

    // auth handlers
    public boolean login(String email, String password) {
        // xu ly db
        return true;
    }

    public boolean logout() {
        this.email = null;
        return true;
    }

    public boolean signup(String email, String password, String name, String gender, int yearOfBirth, String avatar) {
        // TODO: xu ly db , neu oke thi dang nhap luon
        return true;
    }

    // gets sets
    public String getEmail() {
        return email;
    }
}
