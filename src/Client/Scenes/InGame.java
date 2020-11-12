/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author nguye
 */
public class InGame extends JFrame {

    JPanel pnlInteraction = new JPanel();
    JPanel pnlGameBoard = new JPanel();
    JPanel pnlGameOptions = new JPanel();
    JPanel pnlClients = new JPanel();
    JPanel pnlGuests = new JPanel();
    JPanel pnlMessages = new JPanel();
    JScrollPane pnlChat = new JScrollPane(pnlMessages,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
    JTextField txtChatInput = new JTextField("Nhập tin nhắn ở đây...");

    JButton btnNewGame = new JButton("Ván mới");
    JButton btnUndo = new JButton("Đánh lại");
    JButton btnGiveUp = new JButton("Đầu hàng");
    JButton btnExit = new JButton("Thoát");

    JPanel pnlPlayers = new JPanel();
    JLabel lbPlayer1 = new JLabel("Player1");
    JLabel lbText = new JLabel("VS");
    JLabel lbPlayer2 = new JLabel("Player2");

    public InGame() {
        layoutSetup();
    }

    private void layoutSetup() {
//        this.setPreferredSize(new Dimension(1250, 800));
        this.setSize(1250, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //  Game board section, contains game play interface
        pnlGameBoard.setSize(850, 800);
        pnlGameBoard.setBackground(Color.yellow);
//        pnlGameBoard.setBorder(1);
        this.add(pnlGameBoard);
        //  Interaction section, contains options, Clients, chat
        pnlInteraction.setBounds(850, 0, 400, 800);
        pnlInteraction.setBackground(Color.BLUE);
        pnlInteraction.setLayout(new FlowLayout());
        this.add(pnlInteraction);
        // Game option section, contains buttons like "new game", "give up", ...
        pnlGameOptions.setPreferredSize(new Dimension(400, 150));
        pnlGameOptions.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        pnlGameOptions.setBackground(Color.white);
        pnlInteraction.add(pnlGameOptions); // add  panel game options into pnlinteraction

        // Add buttons for option section
        pnlGameOptions.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        btnNewGame.setPreferredSize(new Dimension(120, 50));
        pnlGameOptions.add(btnNewGame);
        btnUndo.setPreferredSize(new Dimension(120, 50));
        pnlGameOptions.add(btnUndo);
        btnGiveUp.setPreferredSize(new Dimension(120, 50));
        pnlGameOptions.add(btnGiveUp);
        btnExit.setPreferredSize(new Dimension(120, 50));
        pnlGameOptions.add(btnExit);

//      Clients section, contains Player and guest section
        pnlClients.setPreferredSize(new Dimension(400, 250));
        pnlClients.setLayout(new FlowLayout());
        pnlClients.setBackground(Color.red);
        pnlInteraction.add(pnlClients);
//      Players section
        pnlPlayers.setPreferredSize(new Dimension(400, 200));
        pnlPlayers.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 100));
        pnlPlayers.setBackground(Color.pink);
        pnlPlayers.add(lbPlayer1);
        pnlPlayers.add(lbText);
        pnlPlayers.add(lbPlayer2);
        pnlClients.add(pnlPlayers);
//        Guests section
        pnlGuests.setPreferredSize(new Dimension(400, 50));
        pnlGuests.setBackground(Color.green);
        pnlClients.add(pnlGuests, BorderLayout.SOUTH);

        // Chat section
        pnlChat.setPreferredSize(new Dimension(400, 280));
//        pnlChat.set
        pnlMessages.setBackground(Color.magenta);
        pnlMessages.setPreferredSize(new Dimension(400, 280));
        GridLayout grid_1col_unspecRow = new GridLayout(0,1);
        grid_1col_unspecRow.setColumns(1);
//        grid_1col_unspecRow.setRows(1);
        pnlMessages.setLayout(grid_1col_unspecRow);
//        pnlChat.setBackground(Color.CYAN);
        pnlInteraction.add(pnlChat);
        // Chat input
        txtChatInput.setPreferredSize(new Dimension(400, 50));
        txtChatInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtChatInput.getText().equals("Nhập tin nhắn ở đây...")) {
                    txtChatInput.setText("");
                    addChatLine(true, "clicked input line", "my icon");
                    btnExit.setVisible(false);
                    
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtChatInput.getText().isEmpty()) {
//                    txtChatInput.setForeground(Color.GRAY);
                    txtChatInput.setText("Nhập tin nhắn ở đây...");
                    addChatLine(true, "out of input line", "other icon");
                    btnExit.setVisible(true);
                }
            }
        });
        pnlInteraction.add(txtChatInput);
        // TODO: add txtChatInput eventlistener here

        
    }

    private void addChatLine(boolean selfChatLine,String text,String icon) {
        JLabel avatar = new JLabel("icon");
        avatar.setText(icon);
        
        avatar.setPreferredSize(new Dimension(50, 50));
        JLabel chatLine = new JLabel();
        avatar.setLabelFor(chatLine);
        chatLine.setText(text);
//        chatLine.add(avatar);
        chatLine.setPreferredSize(new Dimension(250, 50));
        if(selfChatLine){
//            pnlMessages.setLayout(new FlowLayout(FlowLayout.RIGHT));
            pnlMessages.add(chatLine);
//            pnlMessages.add(avatar);
        }else{
//            pnlMessages.setLayout(new FlowLayout(FlowLayout.LEFT));
            pnlMessages.add(chatLine);
//            pnlMessages.add(avatar);
        }
        
        
    }

    public static void main(String[] args) {
        InGame ig = new InGame();
        ig.setVisible(true);
    }

}
