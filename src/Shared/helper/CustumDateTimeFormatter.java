/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class CustumDateTimeFormatter {

    // https://stackoverflow.com/a/6953926
    public static String getCurrentTimeFormatted() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static String secondsToMinutes(int seconds) {
        return addZero(seconds / 60) + ":" + addZero(seconds % 60);
    }

    public static String addZero(int n) {
        if (n < 10) {
            return "0" + n;
        }
        return "" + n;
    }
}
