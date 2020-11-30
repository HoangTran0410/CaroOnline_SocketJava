/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.game.caro;

import shared.helper.CountDownTimer;
import java.util.ArrayList;
import shared.helper.Line;
import shared.helper.Point;
import server.game.GameLogic;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Caro extends GameLogic {

    public static final int ROW = 16, COL = 16;
    public static final int TURN_TIME_LIMIT = 30, MATCH_TIME_LIMIT = 10 * 60;

    ArrayList<History> history;
    History preMove;
    String[][] board;

    CountDownTimer turnTimer;
    CountDownTimer matchTimer;

    public Caro() {
        init();
    }

    public void init() {
        turnTimer = new CountDownTimer(TURN_TIME_LIMIT);
        matchTimer = new CountDownTimer(MATCH_TIME_LIMIT);

        board = new String[ROW][COL];
        history = new ArrayList<>();
        preMove = null;

        // init board
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                board[i][j] = " ";
            }
        }
    }

    public void restartTurnTimer() {
        turnTimer.restart();
    }

    public void resumeTimer() {
        turnTimer.resume();
        matchTimer.resume();
    }

    public void pauseTimer() {
        turnTimer.pause();
        matchTimer.pause();
    }

    public void cancelTimer() {
        if (turnTimer != null) {
            turnTimer.cancel();
        }

        if (matchTimer != null) {
            matchTimer.cancel();
        }
    }

    public void addHistory(int row, int col, String playerEmail) {
        History newHis = new History(row, col, playerEmail);
        history.add(newHis);
        preMove = newHis;
    }

    public boolean move(int row, int col, String playerEmail) {
        // nếu người này đã đánh trước đó thì không cho đánh nữa
        if (preMove != null && preMove.getPlayerEmail().equals(playerEmail)) {
            return false;
        }

        // nếu vị trí đánh nằm ngoài board
        if (row < 0 && row >= ROW && col < 0 && col >= COL) {
            return false;
        }

        // nếu vị trí đó đã đánh rồi
        if (!board[row][col].equals(" ")) {
            return false;
        }

        board[row][col] = playerEmail;
        addHistory(row, col, playerEmail);
        return true;
    }

    public String getValueAt(int x, int y) {
        if (x >= 0 && x < COL && y >= 0 && y < ROW) {
            return board[y][x];
        }

        return " ";
    }

    public Line CheckWin(int row, int column) {
        Point currentCell = new Point(column, row);
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
        String currentData = this.getValueAt(currentCell.x, currentCell.y);

        // if there is nodata => out
        if (currentData.equals(" ")) {
            return null;
        }

        // đếm số lượng ô thỏa điều kiện (>= 5 ô liên tiếp là win)
        int count = 1;
        Point from, to, temp;

        // count to back
        from = currentCell;
        while (true) {
            temp = new Point(from.x + backDir.x, from.y + backDir.y);
            String data = this.getValueAt(temp.x, temp.y);

            if (!data.equals(currentData)) {
                break;
            }
            from = temp;
            count++;
        }

        // count to front
        to = currentCell;
        while (true) {
            temp = new Point(to.x + frontDir.x, to.y + frontDir.y);
            String data = this.getValueAt(temp.x, temp.y);

            if (!data.equals(currentData)) {
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

    public CountDownTimer getTurnTimer() {
        return turnTimer;
    }

    public CountDownTimer getMatchTimer() {
        return matchTimer;
    }

    public String getLastMoveEmail() {
        return history.get(history.size() - 1).getPlayerEmail();
    }

    public int getProgressTurnTimeValue() {
        return 100 * turnTimer.getCurrentTick() / TURN_TIME_LIMIT;
    }

    public int getProgressMatchTimeValue() {
        return 100 * matchTimer.getCurrentTick() / MATCH_TIME_LIMIT;
    }

    public String[][] getBoard() {
        return board;
    }
}
