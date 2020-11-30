/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import server.game.caro.Caro;
import server.game.GameLogic;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import shared.constant.StreamData;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Room {

    String id;
    Caro gamelogic;
    Client client1 = null, client2 = null;
    ArrayList<Client> clients = new ArrayList<>();
    boolean gameStarted = false;

    public Room(String id) {
        // room id
        this.id = id;

        // create game logic
        gamelogic = new Caro();
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void startGame() {
        gameStarted = true;
        gamelogic.getTurnTimer()
                .setTimerCallBack(
                        // end turn callback
                        (Callable) () -> {
                            // TURN_TIMER_END;<winner-email>
                            broadcast(
                                    StreamData.Type.GAME_EVENT + ";"
                                    + StreamData.Type.TURN_TIMER_END.name() + ";"
                                    + gamelogic.getLastMoveEmail()
                            );
                            return null;
                        },
                        // tick turn callback
                        (Callable) () -> {
                            broadcast(
                                    StreamData.Type.GAME_EVENT + ";"
                                    + StreamData.Type.TURN_TICK.name() + ";"
                                    + gamelogic.getProgressTurnTimeValue() + ";"
                                    + gamelogic.getTurnTimer().getCurrentTick()
                            );
                            return null;
                        },
                        // tick interval
                        Caro.TURN_TIME_LIMIT / 10
                );

        gamelogic.getMatchTimer()
                .setTimerCallBack(
                        // end match callback
                        (Callable) () -> {
                            broadcast(
                                    StreamData.Type.GAME_EVENT + ";"
                                    + StreamData.Type.MATCH_TIMER_END.name()
                            );
                            return null;
                        },
                        // tick match callback
                        (Callable) () -> {
                            broadcast(
                                    StreamData.Type.GAME_EVENT + ";"
                                    + StreamData.Type.MATCH_TICK.name() + ";"
                                    + gamelogic.getProgressMatchTimeValue() + ";"
                                    + gamelogic.getMatchTimer().getCurrentTick()
                            );
                            return null;
                        },
                        // tick interval
                        Caro.MATCH_TIME_LIMIT / 10
                );
    }

    // add/remove client
    public boolean addClient(Client c) {
        if (!clients.contains(c)) {
            clients.add(c);

            if (client1 == null) {
                client1 = c;
            } else if (client2 == null) {
                client2 = c;
            }

            return true;
        }
        return false;
    }

    public boolean removeClient(Client c) {
        if (clients.contains(c)) {
            clients.remove(c);
            return true;
        }
        return false;
    }

    // broadcast messages
    public void broadcast(String msg) {
        clients.forEach((c) -> {
            c.sendData(msg);
        });
    }

    public void close() {
        // TODO code here
    }

    // get room data
    public String getFullData() {
        String data = "";

        // player data
        data += getClient12InGameData() + ";";
        data += getListClientData() + ";";
        // timer
        data += getTimerData() + ";";
        // board
        data += getBoardData();

        return data;
    }

    public String getTimerData() {
        String data = "";

        data += Caro.MATCH_TIME_LIMIT + ";" + gamelogic.getMatchTimer().getCurrentTick() + ";";
        data += Caro.TURN_TIME_LIMIT + ";" + gamelogic.getTurnTimer().getCurrentTick();

        return data;
    }

    public String getBoardData() {
        String[][] board = gamelogic.getBoard();

        String data = "";
        for (String[] row : board) {
            for (String cell : row) {
                data += cell + ";";
            }
        }

        return data.substring(0, data.length() - 1); // bỏ dấu ; ở cuối
    }

    public String getClient12InGameData() {
        String data = "";

        data += (client1 == null ? Client.getEmptyInGameData() : client1.getInGameData() + ";");
        data += (client2 == null ? Client.getEmptyInGameData() : client2.getInGameData());

        return data;
    }

    public String getListClientData() {
        // kết quả trả về có dạng playerCount;player1_data;player2_data;...;playerN_data

        String data = clients.size() + ";";

        for (Client c : clients) {
            data += c.getInGameData() + ";";
        }

        return data.substring(0, data.length() - 1); // bỏ dấu ; ở cuối
    }

    // gets sets
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameLogic getGamelogic() {
        return gamelogic;
    }

    public void setGamelogic(Caro gamelogic) {
        this.gamelogic = gamelogic;
    }

    public Client getClient1() {
        return client1;
    }

    public void setClient1(Client client1) {
        this.client1 = client1;
    }

    public Client getClient2() {
        return client2;
    }

    public void setClient2(Client client2) {
        this.client2 = client2;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

}
