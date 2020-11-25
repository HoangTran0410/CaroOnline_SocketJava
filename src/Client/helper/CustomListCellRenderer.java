/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.helper;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
// https://stackoverflow.com/questions/8197167/word-wrap-in-jlist-items
// hay: https://cachhoc.net/2014/04/25/java-swing-tuy-bien-jlist-jlist-custom-renderer/
public class CustomListCellRenderer extends DefaultListCellRenderer {
    
    private int width;

    public CustomListCellRenderer(int width) {
        this.width = width;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        String text = String.format(
                "<html>"
                + "<body style='width: %spx'>"
                + "%s"
                + "</body>"
                + "</html>",
                String.valueOf(width), value.toString());

        return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
    }
}
