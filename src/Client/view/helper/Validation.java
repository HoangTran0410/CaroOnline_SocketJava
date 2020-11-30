/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.helper;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Validation {

    public static boolean checkEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static boolean checkPassword(String pass) {
        return pass.length() >= 6 && pass.length() <= 30;
    }

    //  Name is invalid if it changed after removing accents, or it has blank space
    public static boolean checkName(String name) {
        if (!removeAccent(name).equalsIgnoreCase(name) || name.contains(" ")) {
            return false;
        }
        return name.length() > 0 && name.length() <= 15;
    }

    //http://sinhviencntt.blogspot.com/2015/01/code-java-chuyen-oi-tieng-viet-co-dau.html
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static boolean checkYearOfBirth(int year) {
        LocalDate date = LocalDate.now();
        return year <= date.getYear() && year > date.minusYears(100).getYear();
    }

    public static boolean checkInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

//     Listener for NumberInput fields.
//     Need to invoke changes to the document from the Event Dispatcher Thread,
//     otherwise 'Attempt to mutate notification' exception is Throwed
//     https://stackoverflow.com/questions/15206586/getting-attempt-to-mutate-notification-exception
    public static void checkNumberInputChanged(JTextField numberFormatedField) {
        Runnable doAssist = new Runnable() {
            String temp = numberFormatedField.getText();//temp = 1234a

            @Override
            public void run() {
//      check if input is Integer
                if (!checkInt(temp)) {          // temp = 1234a
//      loop to remove the last char if not Integer until temp is Integer
                    while (!checkInt(temp)) {   // temp = 1234a
                        temp = temp.substring(0, temp.length() - 1);// temp = 1234
                    }
//                  Set temp as text for the textField
                    numberFormatedField.setText(temp);
                }

            }
        };
        SwingUtilities.invokeLater(doAssist);
    }
}
