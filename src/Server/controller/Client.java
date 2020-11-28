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
import server.db.layers.DTO.Player;
import shared.constant.Code;
import shared.constant.StreamData;
import shared.security.AES;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Client implements Runnable {

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    boolean findingMatch = false;
    String loginEmail = ""; // if == "" => chua dang nhap
    Room room; // if == null => chua vao phong nao het
    AES aes;

    String competitorEmail = "";
    String acceptPairMatchStatus = "_"; // yes/no/_

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

                    case CREATE_ROOM:
                        onReceiveCreateRoom(received);
                        break;

                    case JOIN_ROOM:
                        onReceiveJoinRoom(received);
                        break;

                    case LEAVE_ROOM:
                        onReceiveLeaveRoom(received);
                        break;

                    case CHAT_ROOM:
                        onReceiveChatRoom(received);
                        break;

                    case GET_PROFILE:
                        onReceiveGetProfile(received);
                        break;

                    case EDIT_PROFILE:
                        onReceiveEditProfile(received);
                        break;

                    case CHANGE_PASSWORD:
                        onReceiveChangePassword(received);
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
                        // type này có 1 chiều server->client
                        // gửi khi ghép cặp bị đối thủ từ chối
                        // nếu ghép cặp được đồng ý thì server gửi type join-room luôn chứ ko cần gửi type này
                        // client không gửi type này cho server
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

            // remove from clientManager
            RunServer.clientManager.remove(this);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // listen events
    private void onReceiveAESKey(String received) {
        // get encrypted key
        String keyEncrypted = received.split(";")[1];

        // decrypt key
        String aesKey = RunServer.serverSide.decrypt(keyEncrypted);

        // save AES
        setAes(new AES(aesKey));

        // notify client
        sendData(StreamData.Type.AESKEY.name());
    }

    private void onReceiveLogin(String received) {
        // get email / password from data
        String[] splitted = received.split(";");
        String email = splitted[1];
        String password = splitted[2];

        // check đã được đăng nhập ở nơi khác
        if (RunServer.clientManager.find(email) != null) {
            sendData(StreamData.Type.LOGIN.name() + ";failed;" + Code.ACCOUNT_LOGEDIN);
            return;
        }

        // check login
        String result = new PlayerBUS().checkLogin(email, password);

        // set login email
        this.loginEmail = email;

        // send result
        sendData(StreamData.Type.LOGIN.name() + ";" + result);
    }

    private void onReceiveSignup(String received) {
        // get data from received
        String[] splitted = received.split(";");
        String email = splitted[1];
        String password = splitted[2];
        String avatar = splitted[3];
        String name = splitted[4];
        String gender = splitted[5];
        int yearOfBirth = Integer.parseInt(splitted[6]);

        // sign up
        String result = new PlayerBUS().signup(email, password, avatar, name, gender, yearOfBirth);

        // send data
        sendData(StreamData.Type.SIGNUP.name() + ";" + result);
    }

    private void onReceiveLogout(String received) {
        // log out now
        this.loginEmail = "";
        this.findingMatch = false;

        // TODO broadcast to all clients
        // send status
        sendData(StreamData.Type.LOGOUT.name() + ";success");
    }

    private void onReceiveListRoom(String received) {

    }

    private void onReceiveCreateRoom(String received) {

    }

    private void onReceiveJoinRoom(String received) {

    }

    private void onReceiveLeaveRoom(String received) {

    }

    private void onReceiveChatRoom(String received) {

    }

    private void onReceiveGetProfile(String received) {
        String result;

        // get email from data
        String email = received.split(";")[1];

        // get player data
        Player p = new PlayerBUS().getByEmail(email);
        if (p == null) {
            result = "failed;" + Code.ACCOUNT_NOT_FOUND;
        } else {
            result = "success;"
                    + p.getId() + ";"
                    + p.getEmail() + ";"
                    + p.getName() + ";"
                    + p.getAvatar() + ";"
                    + p.getGender() + ";"
                    + p.getYearOfBirth() + ";"
                    + p.getScore() + ";"
                    + p.getMatchCount() + ";"
                    + p.getWinCount() + ";"
                    + p.calculateTieCount() + ";"
                    + p.getLoseCount() + ";"
                    + p.getCurrentStreak() + ";"
                    + p.calculateWinRate();
        }

        // send result
        sendData(StreamData.Type.GET_PROFILE.name() + ";" + result);
    }

    private void onReceiveEditProfile(String received) {
        try {
            // get data from received
            String[] splitted = received.split(";");
            String newEmail = splitted[1];
            String name = splitted[2];
            String avatar = splitted[3];
            int yearOfBirth = Integer.parseInt(splitted[4]);
            String gender = splitted[5];

            // edit profile
            String result = new PlayerBUS().editProfile(loginEmail, newEmail, name, avatar, yearOfBirth, gender);

            // lưu lại newEmail vào Client nếu cập nhật thành công
            String status = result.split(";")[0];
            if (status.equals("success")) {
                loginEmail = newEmail;
            }

            // send result
            sendData(StreamData.Type.EDIT_PROFILE + ";" + result);

        } catch (NumberFormatException e) {
            // send failed format
            sendData(StreamData.Type.EDIT_PROFILE + ";failed;Năm sinh phải là số nguyên");
        }
    }

    private void onReceiveChangePassword(String received) {
        // get old pass, new pass from data
        String[] splitted = received.split(";");
        String oldPassword = splitted[1];
        String newPassword = splitted[2];

        // check change pass
        String result = new PlayerBUS().changePassword(loginEmail, oldPassword, newPassword);

        // send result
        sendData(StreamData.Type.CHANGE_PASSWORD.name() + ";" + result);
    }

    private void onReceiveFindMatch(String received) {
        // nếu đang trong phòng rồi thì báo lỗi ngay
        if (this.room != null) {
            sendData(StreamData.Type.FIND_MATCH.name() + ";failed;" + Code.ALREADY_INROOM + " #" + this.room.getId());
            return;
        }

        // kiểm tra xem có ai đang tìm phòng không
        Client cCompetitor = RunServer.clientManager.findClientFindingMatch();

        if (cCompetitor == null) {
            // đặt cờ là đang tìm phòng
            this.findingMatch = true;

            // trả về success để client hiển thị giao diện Đang tìm phòng
            sendData(StreamData.Type.FIND_MATCH.name() + ";success");

        } else {
            // nếu có người cũng đang tìm trận thì hỏi ghép cặp pairMatch
            // trong lúc hỏi thì phải tắt tìm trận bên đối thủ đi (để nếu client khác tìm trận thì ko bị ghép đè)
            cCompetitor.findingMatch = false;
            this.findingMatch = false;

            // lưu email đối thủ để dùng khi server nhận được result-pair-match
            this.competitorEmail = cCompetitor.loginEmail;
            cCompetitor.competitorEmail = this.loginEmail;

            // lấy thông tin 2 người chơi
            Player me = new PlayerBUS().getByEmail(loginEmail);
            Player pCompetitor = new PlayerBUS().getByEmail(cCompetitor.loginEmail);

            // trả thông tin đối thủ về cho 2 clients
            this.sendData(StreamData.Type.REQUEST_PAIR_MATCH.name() + ";" + pCompetitor.getName() + " #" + pCompetitor.getId());
            cCompetitor.sendData(StreamData.Type.REQUEST_PAIR_MATCH.name() + ";" + me.getName() + " #" + me.getId());
        }
    }

    private void onReceiveCancelFindMatch(String received) {
        // gỡ cờ đang tìm phòng
        this.findingMatch = false;

        // báo cho client để tắt giao diện đang tìm phòng
        sendData(StreamData.Type.CANCEL_FIND_MATCH.name() + ";success");
    }

    private void onReceiveRequestPairMatch(String received) {
        String[] splitted = received.split(";");
        String requestResult = splitted[1];

        // save accept pair status
        this.acceptPairMatchStatus = requestResult;

        // get competitor
        Client cCompetitor = RunServer.clientManager.find(this.competitorEmail);
        if (cCompetitor == null) {
            sendData(StreamData.Type.RESULT_PAIR_MATCH.name() + ";failed;" + Code.COMPETITOR_LEAVE);
            return;
        }

        // if once say no
        if (requestResult.equals("no")) {
            this.sendData(StreamData.Type.RESULT_PAIR_MATCH.name() + ";failed;" + Code.YOU_CHOOSE_NO);
            cCompetitor.sendData(StreamData.Type.RESULT_PAIR_MATCH.name() + ";failed;" + Code.COMPETITOR_CHOOSE_NO);

            // reset acceptPairMatchStatus
            this.acceptPairMatchStatus = "_";
            cCompetitor.acceptPairMatchStatus = "_";
        }

        // if both say yes
        if (requestResult.equals("yes") && cCompetitor.acceptPairMatchStatus.equals("yes")) {
            // send success pair match
            this.sendData(StreamData.Type.RESULT_PAIR_MATCH.name() + ";success");
            cCompetitor.sendData(StreamData.Type.RESULT_PAIR_MATCH.name() + ";success");

            // create new room
            Room newRoom = RunServer.roomManager.createRoom();

            // join room
            this.joinRoom(newRoom);
            cCompetitor.joinRoom(newRoom);

            // send join room status to client
            sendData(StreamData.Type.JOIN_ROOM.name() + ";success;" + newRoom.getId());
            cCompetitor.sendData(StreamData.Type.JOIN_ROOM.name() + ";success;" + newRoom.getId());

            // reset acceptPairMatchStatus
            this.acceptPairMatchStatus = "_";
            cCompetitor.acceptPairMatchStatus = "_";
        }
    }

    // send data fucntions
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
            System.err.println("Send data failed to " + this.getLoginEmail());
            return "failed;" + e.getMessage();
        }
    }

    public String sendPureData(String data) {
        try {
            this.dos.writeUTF(data);
            return "success";
        } catch (IOException e) {
            System.err.println("Send data failed to " + this.getLoginEmail());
            return "failed;" + e.getMessage();
        }
    }

    // room handlers
    public String joinRoom(String id) {
        Room found = RunServer.roomManager.find(id);

        // không tìm thấy phòng cần join ?
        if (found == null) {
            return "failed;Không tìm thấy phòng " + id;
        }

        return joinRoom(found);
    }

    public String joinRoom(Room room) {
        // đang trong phòng rồi ?
        if (this.room != null) {
            return "failed;" + Code.CANNOT_JOINROOM + Code.ALREADY_INROOM + " #" + this.room.getId();
        }

        // join vào phòng thanh cong hay khong ?
        if (room.addClient(this)) {
            this.room = room;
            return "success";
        }

        return "failed;" + Code.CANNOT_JOINROOM + " room.addClient trả về false";
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

    // get set
    public String getLoginEmail() {
        return loginEmail;
    }

    public boolean isFindingMatch() {
        return findingMatch;
    }

    public void setFindingMatch(boolean findingMatch) {
        this.findingMatch = findingMatch;
    }

    public void setAes(AES aes) {
        this.aes = aes;
    }

}
