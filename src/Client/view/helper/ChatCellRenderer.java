/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.helper;

import client.model.ChatItem;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
// https://stackoverflow.com/questions/8197167/word-wrap-in-jlist-items
// hay: //https://www.codejava.net/java-se/swing/jlist-custom-renderer-example
public class ChatCellRenderer extends DefaultListCellRenderer {

    private int width;

    public ChatCellRenderer(int width) {
        this.width = width;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        ChatItem c = (ChatItem) value;

        String text = String.format(
                "<html>"
                + "<body style='width: %spx'>"
                + "<i>[%s]</i> <u><b>%s</b></u>: <span>%s<span>"
                + "</body>"
                + "</html>",
                String.valueOf(width), c.getTime(), c.getOwner(), c.getContent());

        return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
    }
}
