/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.helper;

import client.RunClient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.util.converter.LocalDateTimeStringConverter;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class Validation {

    private final String SPECIAL_CHARS = "[$&+,:;=\\\\\\\\?@#|/'<>^*()%!-]";

    public Validation() {
    }

    public boolean checkSpecialChar(String source) {
        Pattern pattern = Pattern.compile(SPECIAL_CHARS);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }

    public boolean checkEmail(String email) {
        String[] parts = email.split("@");
        String[] dotParts = email.split("\\.");
        if (parts.length != 2
                || checkSpecialChar(parts[0])
                || checkSpecialChar(parts[1])
                || email.endsWith("@")
                || email.endsWith("\\.")
                || dotParts.length < 2) {
            return false;
        }

        String TLD = dotParts[dotParts.length - 1];
        return email.indexOf(TLD) > email.indexOf("@");
    }

    public boolean checkPassword(String pass) {

        return pass.length() >= 6 && pass.length() <= 30;
    }

    public boolean checkName(String name) {
        return name.length() > 0 && name.length() <= 15 && !checkSpecialChar(name) ;
    }

    public boolean checkYearOfBirth(int year) {
        LocalDate date = LocalDate.now();
        return year <= date.getYear() && year > date.minusYears(100).getYear();
    }

    public boolean checkInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void checkNumberInputChanged(JTextField txYearOfBirth) {
        Runnable doAssist = new Runnable() {
            String temp = txYearOfBirth.getText();

            @Override
            public void run() {
                if (!checkInt(temp)) {
                    while (!checkInt(temp)) {
                        temp = temp.substring(0, temp.length() - 1);
                    }
                    System.out.println("done");
                    txYearOfBirth.setText(temp);
                }

            }
        };
        SwingUtilities.invokeLater(doAssist);
    }
}
