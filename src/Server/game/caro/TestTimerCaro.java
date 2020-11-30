/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.game.caro;

import java.util.concurrent.Callable;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class TestTimerCaro {

    public TestTimerCaro() {
        Caro caro = new Caro();
        Callable endCallback, tickCallback;

        endCallback = (Callable) () -> {
            System.out.println("END");
            throw new Exception();
        };

        tickCallback = (Callable) () -> {
            if (caro.getTurnTimer().getCurrentTick() == 10) {
                caro.cancelTimer();
            }
            System.out.println(caro.getTurnTimer().getCurrentTick());
            throw new Exception();
        };

        caro.getTurnTimer().setTimerCallBack(endCallback, tickCallback, 1);

        //  thằng caro làm việc shutdown chương trình trở nên khó khăn...
        // chưa biết cách tắt thread do thằng caro callable tạo ra
    }

    public static void main(String[] args) {
        new TestTimerCaro();
    }
}
