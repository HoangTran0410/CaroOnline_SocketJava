/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Controllers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Server.Server.roomManager;

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

    public Client(Socket s) throws IOException {
        this.s = s;

        // obtaining input and output streams 
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {

        String received;

        while (true) {
            try {
                // receive the request from client
                received = dis.readUTF();

                if (received.indexOf("login") == 0) {

                } else if (received.indexOf("signup") == 0) {

                } else if (received.indexOf("join_room") == 0) {

                } else if (received.indexOf("leave_room") == 0) {

                } else if (received.indexOf("chat_room") == 0) {

                } else if (received.indexOf("game_event") == 0) {

                } else if (received.indexOf("update_profile") == 0) {

                } else if (received.indexOf("exit") == 0) {
                    // TODO do something here
                    break;
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

    // chat handlers
    public boolean sendMessage(String mes) {
        try {
            this.dos.writeUTF(mes);
            return true;
        } catch (IOException e) {
            System.err.println("Send message failed to " + this.getEmail());
            return false;
        }
    }

    // room handlers
    public boolean joinRoom(String id) {
        // đang trong phòng rồi ?
        if (this.room != null) {
            return false;
        }

        Room room = roomManager.find(id);

        // không tìm thấy phòng cần join ?
        if (room == null) {
            return false;
        }

        // join vào phòng thanh cong hay khong ?
        if (room.addClient(this)) {
            this.room = room;
            return true;
        }

        return false;
    }

    public boolean joinRoom(Room room) {
        return joinRoom(room.getId());
    }

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

    public boolean signup(String email, String password, String name, String gender, String birthday) {
        // TODO: xu ly db , neu oke thi dang nhap luon
        return true;
    }

    // gets sets
    public String getEmail() {
        return email;
    }
}
