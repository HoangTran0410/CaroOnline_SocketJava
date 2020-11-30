/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.helper;

import client.model.PlayerInGame;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import shared.constant.Avatar;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
// https://www.codejava.net/java-se/swing/jlist-custom-renderer-example
public class PlayerInRoomCustomRenderer extends JLabel implements ListCellRenderer<PlayerInGame> {

    @Override
    public Component getListCellRendererComponent(JList<? extends PlayerInGame> jlist, PlayerInGame p, int index, boolean isSelected, boolean cellHasFocus) {
        ImageIcon imageIcon = new ImageIcon(Avatar.PATH + p.getAvatar());
        setIcon(imageIcon);
        setText(p.getNameId());

        if (isSelected) {
            setBackground(jlist.getSelectionBackground());
            setForeground(jlist.getSelectionForeground());
        } else {
            setBackground(jlist.getBackground());
            setForeground(jlist.getForeground());
        }

        return this;
    }

}
