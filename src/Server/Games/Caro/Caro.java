/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Games.Caro;

import Shared.Helpers.Line;
import Shared.Helpers.Point;
import Server.Games.GameLogic;
import Shared.Constants.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Caro extends GameLogic {

    final int ROW = 16, COL = 16;
    char[][] board;

    public Caro() {
        board = new char[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                board[i][j] = ' ';
            }
        }
    }

    @Override
    public void receiveDataFromClient(JsonObject rjson) {
        // TODO: return gì đó cho client
        JsonElement game_event = rjson.get("game_event");
//        game_event.
        
        switch(1) {
            case Type.MOVE:
                // TODO: add actions for game event
                break;
            case Type.NEW_GAME:
                break;
            case Type.UNDO:
                break;
            case Type.SURRENDER:
                break;
            default:
                // do something
                break;
        }
    }

    public boolean setValueAt(char value, int x, int y) {
        if (x >= 0 && x < COL && y >= 0 && y < ROW) {
            board[y][y] = value;
            return true;
        }
        return false;
    }

    public char getValueAt(int x, int y) {
        if (x >= 0 && x < COL && y >= 0 && y < ROW) {
            return board[y][x];
        }

        return ' ';
    }

    public Line CheckWin(int x, int y) {
        Point currentCell = new Point(x, y);
        Point backDir, frontDir;
        Line winPath;

        // ============ check chieu ngang =============
        backDir = new Point(-1, 0);
        frontDir = new Point(1, 0);
        winPath = this.checkWinTemplate(currentCell, backDir, frontDir);
        if (winPath != null) {
            return winPath;
        }
        // ============ check chieu doc ============
        backDir = new Point(0, -1);
        frontDir = new Point(0, 1);
        winPath = this.checkWinTemplate(currentCell, backDir, frontDir);
        if (winPath != null) {
            return winPath;
        }
        // ============ check cheo trai sang phai ============
        backDir = new Point(-1, -1);
        frontDir = new Point(1, 1);
        winPath = this.checkWinTemplate(currentCell, backDir, frontDir);
        if (winPath != null) {
            return winPath;
        }
        // ============ check cheo phai sang trai ============
        backDir = new Point(1, -1);
        frontDir = new Point(-1, 1);
        winPath = this.checkWinTemplate(currentCell, backDir, frontDir);
        if (winPath != null) {
            return winPath;
        }

        return null;
    }

    public Line checkWinTemplate(Point currentCell, Point backDir, Point frontDir) {
        // get data from current cell
        int currentData = this.getValueAt(currentCell.x, currentCell.y);

        // if there is nodata => out
        if (currentData == ' ') {
            return null;
        }

        // đếm số lượng ô thỏa điều kiện (>= 5 ô liên tiếp là win)
        int count = 1;
        Point from, to, temp;

        // count to back
        from = currentCell;
        while (true) {
            temp = new Point(from.x + backDir.x, from.y + backDir.y);
            char data = this.getValueAt(temp.x, temp.y);

            if (data != currentData) {
                break;
            }
            from = temp;
            count++;
        }

        // count to front
        to = currentCell;
        while (true) {
            temp = new Point(to.x + frontDir.x, to.y + frontDir.y);
            char data = this.getValueAt(temp.x, temp.y);

            if (data != currentData) {
                break;
            }
            to = temp;
            count++;
        }

        // nếu có 5 ô giống nhau liên tiếp nhau => win
        if (count == 5) {
            return new Line(from.x, from.y, to.x, to.y);
        }

        return null;
    }
}
