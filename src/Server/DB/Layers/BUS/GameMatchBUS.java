/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.db.layers.BUS;

import server.db.layers.DAL.GameMatchDAL;
import server.db.layers.DTO.GameMatch;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class GameMatchBUS {

    ArrayList<GameMatch> listGameMatch = new ArrayList<>();
    GameMatchDAL gameMatchDAL = new GameMatchDAL();

    public GameMatchBUS() {
        readDB();
    }

    public void readDB() {
        listGameMatch = gameMatchDAL.readDB();
    }

    public boolean add(GameMatch g) {
        boolean status = gameMatchDAL.add(g);

        if (status == true) {
            listGameMatch.add(g);
        }

        return status;
    }

    public boolean delete(int id) {
        boolean status = gameMatchDAL.delete(id);

        if (status == true) {
            for (int i = (listGameMatch.size() - 1); i >= 0; i--) {
                if (listGameMatch.get(i).getId() == id) {
                    listGameMatch.remove(i);
                }
            }
        }

        return status;
    }

    public boolean update(GameMatch g) {
        boolean status = gameMatchDAL.update(g);

        if (status == true) {
            listGameMatch.forEach((gm) -> {
                gm = new GameMatch(g);
            });
        }

        return status;
    }

    public GameMatch getById(int id) {
        for (GameMatch g : listGameMatch) {
            if (g.getId() == id) {
                return g;
            }
        }
        return null;
    }

    // ================ utils ===================
    public int calculateTotalMatch(int playerId) {
        int result = 0;

        for (GameMatch m : listGameMatch) {
            if (m.getPlayerID1() == playerId || m.getPlayerID2() == playerId) {
                result++;
            }
        }
        return result;
    }

    public int calculateWinCount(int playerId) {
        int result = 0;

        for (GameMatch m : listGameMatch) {
            if (m.getWinnerID() == playerId) {
                result++;
            }
        }
        return result;
    }

    public int calculateLongestWinStreak(int playerId) {
        int longest = 0;
        int current = 0;

        for (GameMatch m : listGameMatch) {
            if (m.getPlayerID1() == playerId || m.getPlayerID2() == playerId) {
                if (m.getWinnerID() == playerId) {
                    current++;
                } else {
                    if (current > longest) {
                        longest = current;
                    }
                    current = 0;
                }
            }
        }

        return longest;
    }

    public float calculateWinRate(int playerId) {
        return (float) (100.00 * (calculateWinCount(playerId) / calculateTotalMatch(playerId)));
    }

    public ArrayList<GameMatch> getList() {
        return listGameMatch;
    }
}
