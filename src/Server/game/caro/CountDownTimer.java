/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.game.caro;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class CountDownTimer {

    int timeLimit;
    int currentTick;
    Timer timer;
    ExecutorService executor;

    public CountDownTimer(int _timeLimit) {
        timeLimit = _timeLimit;
        currentTick = _timeLimit;
        timer = new Timer();
        executor = Executors.newFixedThreadPool(2);
    }

    public void cancel() {
        timer.cancel();
        timer.purge();
        executor.shutdownNow();
    }

    // https://stackoverflow.com/a/4685606
    public void setTimerCallBack(Callable endCallback, Callable tickCallback) {
        currentTick = timeLimit;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentTick--;

                try {
                    executor.submit(tickCallback);
                } catch (Exception ex) {
                    Logger.getLogger(Caro.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (currentTick < 0) {
                    try {
                        executor.submit(endCallback);
                        executor.shutdown();
                    } catch (Exception ex) {
                        Logger.getLogger(Caro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    timer.cancel();
                    timer.purge();
                }
            }
        }, 0, 1000);
    }

    public int getCurrentTick() {
        return currentTick;
    }
}
