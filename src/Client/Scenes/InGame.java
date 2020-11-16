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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalTime;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author nguye
 */
public class InGame extends JFrame {

//    JTable tb = null;
//    DefaultTableModel model = null;
    JPanel pnlInteraction = new JPanel();
    JPanel pnlGameBoard = new JPanel();
    JPanel pnlGameOptions = new JPanel();
    JPanel pnlClients = new JPanel();
    JPanel pnlGuests = new JPanel();
    JPanel pnlMessages = new JPanel();
//    MyTable tbChat = new MyTable();
    JList<String> chatLine = new JList();
    DefaultListModel<String> lModel = new DefaultListModel<>();
    JScrollPane pnlChat = new JScrollPane(chatLine);
    JTextField txtChatInput = new JTextField("Nhập tin nhắn ở đây...");

    JButton btnNewGame = new JButton("Ván mới");
    JButton btnUndo = new JButton("Đánh lại");
    JButton btnGiveUp = new JButton("Đầu hàng");
    JButton btnExit = new JButton("Thoát");

    JPanel pnlPlayers = new JPanel();
    JLabel lbPlayer1 = new JLabel("Player1");
    JLabel lbText = new JLabel("VS");
    JLabel lbPlayer2 = new JLabel("Player2");
    JLabel lbGuest1 = new JLabel();
    JLabel lbGuest2 = new JLabel();
    JLabel lbGuest3 = new JLabel();

    public InGame() {
        layoutSetup();
    }

    private void layoutSetup() {
        ImageIcon userIcon = new ImageIcon(getClass().getResource("/Client/Assets/icons8_male_user_48px.png"));
        lbPlayer1.setIcon(userIcon);
        lbPlayer1.setHorizontalTextPosition(JLabel.CENTER);
        lbPlayer1.setVerticalTextPosition(JLabel.BOTTOM);
        lbPlayer2.setIcon(userIcon);
        lbPlayer2.setHorizontalTextPosition(JLabel.CENTER);
        lbPlayer2.setVerticalTextPosition(JLabel.BOTTOM);
        lbGuest1.setIcon(userIcon);
        lbGuest2.setIcon(userIcon);
        lbGuest3.setIcon(userIcon);

//        this.setPreferredSize(new Dimension(1250, 800));
        this.setSize(1250, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        this.setResizable(false);

        //  Game board section, contains game play interface
        pnlGameBoard.setSize(850, 750);
        pnlGameBoard.setBackground(Color.yellow);
//        pnlGameBoard.setBorder(1);
        this.add(pnlGameBoard);
        //  Interaction section, contains options, Clients, chat
        pnlInteraction.setBounds(850, 0, 400, 800);
//        pnlInteraction.setBackground(Color.BLUE);
        pnlInteraction.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 5));
        this.add(pnlInteraction);
        // Game option section, contains buttons like "new game", "give up", ...
        pnlGameOptions.setPreferredSize(new Dimension(400, 200));
        pnlGameOptions.setBorder(BorderFactory.createTitledBorder("Chức năng"));
//        pnlGameOptions.setBackground(Color.white);
        pnlInteraction.add(pnlGameOptions); // add  panel game options into pnlinteraction

        // Add buttons for option section
        pnlGameOptions.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
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
//        pnlClients.setBackground(Color.red);
        pnlInteraction.add(pnlClients);
//      Players section
        pnlPlayers.setPreferredSize(new Dimension(400, 190));
        pnlPlayers.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 50));
//        pnlPlayers.setBackground(Color.pink);
        pnlPlayers.add(lbPlayer1);
        pnlPlayers.add(lbText);
        pnlPlayers.add(lbPlayer2);
        pnlClients.add(pnlPlayers);
//        Guests section
        pnlGuests.setPreferredSize(new Dimension(400, 60));
        pnlGuests.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        pnlGuests.setBackground(Color.WHITE);
        pnlGuests.add(lbGuest1);
        pnlGuests.add(lbGuest2);
        pnlGuests.add(lbGuest3);
        pnlClients.add(pnlGuests, BorderLayout.SOUTH);

        // Chat section
        pnlChat.setPreferredSize(new Dimension(380, 230));

        pnlChat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pnlChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        GridLayout grid_1col_unspecRow = new GridLayout(0, 1);
        pnlMessages.setLayout(grid_1col_unspecRow);

        JPanel pnlChatInput = new JPanel();
        pnlChatInput.setLayout(new BorderLayout());

        JButton btnSend = new JButton("Gửi");
        btnSend.setPreferredSize(new Dimension(50, 50));
//        tbChat.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        btnSend.addActionListener(new ActionListener() {
            boolean tempTest = true;

            @Override
            public void actionPerformed(ActionEvent ae) {
                sendMessage(true);
            }
        });

        pnlInteraction.add(pnlChat);
        // Chat input
        txtChatInput.setPreferredSize(new Dimension(330, 50));
        txtChatInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtChatInput.getText().equals("Nhập tin nhắn ở đây...")) {
                    txtChatInput.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtChatInput.getText().isEmpty()) {
                    if (txtChatInput.getText().equalsIgnoreCase("Nhập tin nhắn ở đây...")) {
                        txtChatInput.setText("");
                    }
                }
            }
        });
        txtChatInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {

            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage(true);
                    pnlChatInput.requestFocusInWindow();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {

            }
        });
        pnlChatInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {

            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtChatInput.requestFocusInWindow();
                }
            }
            @Override
            public void keyReleased(KeyEvent ke) {
            }
        });
        chatLine.setModel(lModel);
        pnlChatInput.add(txtChatInput, BorderLayout.WEST);
        pnlChatInput.add(btnSend, BorderLayout.EAST);
        pnlInteraction.add(pnlChatInput);
        // TODO: add txtChatInput eventlistener here

    }

    public void sendMessage(boolean self) {
        if (!txtChatInput.getText().trim().equalsIgnoreCase("") && !txtChatInput
                .getText().trim().equalsIgnoreCase("Nhập tin nhắn ở đây...")) {
            addChatLine(self, txtChatInput.getText(), "my name");
        }
        txtChatInput.setText("Nhập tin nhắn ở đây...");
    }

    private String lineWrap(String s) {
        String result = "";
//        String[] pureText = s.split("</html>");
//        result+= pureText[0] + "</html>";
        String[] words = s.split(" ");
        int lineLimit = 30;
        int count = 0;
        int tempCount = 0;

        for (int i = 0; i < words.length; i++) {
            if (tempCount < 15) {
                lineLimit = 15;
            } else {
                lineLimit = 30;
            }
            if (words[i].length() < lineLimit) {

                if (count + words[i].length() < lineLimit) {
                    result += words[i] + " ";
                    count += words[i].length();
                } else {
                    tempCount = count + words[i].length();
                    count = 0;
                    result += "<br/>" + words[i] + " ";
                }
            } else {
                String lastTemp = words[i];
                for (int j = 0; j < ((words[i].length() % lineLimit) == 0 ? words[i].length() / lineLimit : (words[i].length() / lineLimit) + 1); j++) {
                    if (j == 0) {
                        lineLimit /= 2;
                    } else {
                        if (j == 1) {
                            lineLimit *= 2;
                        }
                    }
                    String temp = lastTemp.substring(0, lastTemp.length() < lineLimit ? lastTemp.length() : lineLimit);
                    result += temp + "<br/>";
                    lastTemp = words[i].substring(lastTemp.length() < lineLimit ? lastTemp.length() : lineLimit, lastTemp.length());
                }
            }
        }
        return result;
    }

    private void addChatLine(boolean selfChatLine, String text, String name) {
        String username;
        JLabel textLine = new JLabel();
        String font;
        textLine.setPreferredSize(new Dimension(120, 50));
        font = "style='font-size:15px;'";
        LocalTime currentTime = LocalTime.now();
        String hour = String.valueOf(currentTime.getHour());
        String min = currentTime.getMinute() < 10
                ? String.valueOf("0" + currentTime.getMinute())
                : String.valueOf(currentTime.getMinute());
        username = "[" + hour + ":" + min + "] " + "<b style='color:red;'>" + name + "</b>" + ": ";
        textLine.setText("<html><div " + font + ">"
                + username + lineWrap(text) + "</div></html>");

        lModel.addElement(textLine.getText());
        chatLine.ensureIndexIsVisible(lModel.getSize() - 1);
    }

    public static void main(String[] args) {
        InGame ig = new InGame();
        ig.setVisible(true);
    }

}
