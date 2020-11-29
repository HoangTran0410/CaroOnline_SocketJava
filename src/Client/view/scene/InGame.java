/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view.scene;

import client.RunClient;
import client.model.ChatItem;
import client.model.PlayerInGame;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import shared.constant.Avatar;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Hoang Tran < hoang at 99.hoangtran@gmail.com >
 */
public class InGame extends javax.swing.JFrame {

    DefaultListModel<ChatItem> chatModel;
    ArrayList<PlayerInGame> listPlayers;
    PlayerInGame player1;
    PlayerInGame player2;
    int turn = 0;

    final ImageIcon p1Icon = new ImageIcon(Avatar.ASSET_PATH + "icons8_round_24px.png");
    final ImageIcon p2Icon = new ImageIcon(Avatar.ASSET_PATH + "icons8_delete_24px_1.png");

    // https://codelearn.io/sharing/lam-game-caro-don-gian-bang-java
    final int COLUMN = 16, ROW = 16;
    JButton btnOnBoard[][];
    JButton lastMove = null;

    /**
     * Creates new form InGame
     */
    public InGame() {
        initComponents();
        this.setLocationRelativeTo(null);

        // list players (player + viewer)
        listPlayers = new ArrayList<>();

        // board
        plBoardContainer.setLayout(new GridLayout(ROW, COLUMN));
        initBoard();

        // close window event
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(InGame.this,
                        "Bạn có chắc muốn thoát phòng?", "Thoát phòng?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    RunClient.socketHandler.leaveRoom();
                }
            }
        });
    }

    public void setPlayerInGame(PlayerInGame p1, PlayerInGame p2) {
        // save data
        player1 = p1;
        player2 = p2;

        // player 1
        lbPlayerNameId1.setText(p1.getNameId());
        if (p1.getAvatar().equals("")) {
            lbAvatar1.setIcon(new ImageIcon(Avatar.PATH + Avatar.EMPTY_AVATAR));
        } else {
            lbAvatar1.setIcon(new ImageIcon(Avatar.PATH + p1.getAvatar()));
        }

        // player 2
        lbPlayerNameId2.setText(p2.getNameId());
        lbAvatar2.setIcon(new ImageIcon(Avatar.PATH + Avatar.EMPTY_AVATAR));
        if (p2.getAvatar().equals("")) {
            lbAvatar2.setIcon(new ImageIcon(Avatar.PATH + Avatar.EMPTY_AVATAR));
        } else {
            lbAvatar2.setIcon(new ImageIcon(Avatar.PATH + p2.getAvatar()));
        }

        // reset turn
        lbActive1.setVisible(false);
        lbActive2.setVisible(false);
    }

    public void setWin(String winEmail) {
        String myEmail = RunClient.socketHandler.getLoginEmail();

        if (winEmail.equals(myEmail)) {
            // là email của mình thì win
            JOptionPane.showMessageDialog(this, "Chúc mừng. Bạn đã chiến thắng.", "Chiến thắng", JOptionPane.INFORMATION_MESSAGE);

        } else if (myEmail.equals(player1.getEmail()) || myEmail.equals(player2.getEmail())) {
            // nếu mình là 1 trong 2 người chơi, mà winEmail ko phải mình => thua
            JOptionPane.showMessageDialog(this, "Rất tiếc. Bạn đã thua cuộc.", "Thua cuộc", JOptionPane.INFORMATION_MESSAGE);

        } else {
            // còn lại là viewers
            String nameId = "";
            if (player1.getEmail().equals(winEmail)) {
                nameId = player1.getNameId();
            } else {
                nameId = player2.getNameId();
            }
            JOptionPane.showMessageDialog(this, "Người chơi " + nameId + " đã thắng", "Kết quả", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // change turn sang cho email đầu vào
    public void setTurn(String email) {
        if (player1.getEmail().equals(email)) {
            turn = 1;
            lbActive1.setVisible(true);
            lbActive2.setVisible(false);
            lbAvatar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Đang đánh.."));
            lbAvatar2.setBorder(javax.swing.BorderFactory.createTitledBorder("Chờ"));
        }

        if (player2.getEmail().equals(email)) {
            turn = 2;
            lbActive1.setVisible(false);
            lbActive2.setVisible(true);
            lbAvatar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Chờ"));
            lbAvatar2.setBorder(javax.swing.BorderFactory.createTitledBorder("Đang đánh.."));
        }
    }

    // change turn sang cho đối thủ của email đầu vào
    public void changeTurnFrom(String email) {
        if (email.equals(player1.getEmail())) {
            setTurn(player2.getEmail());
        } else {
            setTurn(player1.getEmail());
        }
    }

    public void initBoard() {
        plBoardContainer.removeAll();
        btnOnBoard = new JButton[COLUMN + 2][ROW + 2];

        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COLUMN; column++) {
                btnOnBoard[row][column] = createBoardButton(row, column);
                plBoardContainer.add(btnOnBoard[row][column]);
            }
        }
    }

    public void setLastMove(int row, int column) {
        lastMove = btnOnBoard[row][column];
    }

    public void addPoint(int row, int column, String email) {
        if (lastMove != null) {
            lastMove.setBackground(new Color(180, 180, 180));
        }

        lastMove = btnOnBoard[row][column];
        lastMove.setBackground(Color.yellow);
        lastMove.setActionCommand(email); // save email as state

        if (email.equals(player1 != null ? player1.getEmail() : "")) {
            lastMove.setIcon(p1Icon);
        } else {
            lastMove.setIcon(p2Icon);
        }
    }

    public void removePoint(int row, int column) {
        https://stackoverflow.com/a/2235596
        btnOnBoard[row][column].setIcon(null);
        btnOnBoard[row][column].setActionCommand("");
    }

    public void clickOnBoard(int row, int column) {
        RunClient.socketHandler.move(row, column);
    }

    public JButton createBoardButton(int row, int column) {
        JButton b = new JButton();
        b.setFocusPainted(false);
        b.setBackground(new Color(180, 180, 180));
        b.setActionCommand("");

        b.addActionListener((ActionEvent e) -> {
            clickOnBoard(row, column);

            // test
            // addPoint(row, column, "");
        });

        // https://stackoverflow.com/a/22639054
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (b.getActionCommand().equals("")) {

                    String myEmail = RunClient.socketHandler.getLoginEmail();

                    if (myEmail.equals(player1.getEmail()) && (turn == 1 || turn == 0)) {
                        b.setIcon(p1Icon);
                    }

                    if (myEmail.equals(player2.getEmail()) && (turn == 2 || turn == 0)) {
                        b.setIcon(p2Icon);
                    }
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (b.getActionCommand().equals("")) {
                    b.setIcon(null);
                }
            }
        });

        return b;
    }

    public void addChat(ChatItem c) {
        txaChat.setText(txaChat.getText() + "\n" + c.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plRightContainer = new javax.swing.JPanel();
        plToolContainer = new javax.swing.JPanel();
        btnNewGame = new javax.swing.JButton();
        btnUndo = new javax.swing.JButton();
        btnLeaveRoom = new javax.swing.JButton();
        plPlayerContainer = new javax.swing.JPanel();
        plPlayer = new javax.swing.JPanel();
        lbAvatar1 = new javax.swing.JLabel();
        lbPlayerNameId1 = new javax.swing.JLabel();
        lbActive1 = new javax.swing.JLabel();
        lbVersus = new javax.swing.JLabel();
        lbAvatar2 = new javax.swing.JLabel();
        lbPlayerNameId2 = new javax.swing.JLabel();
        lbActive2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        plTimer = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pgbTurnTimer = new javax.swing.JProgressBar();
        pgbMatchTimer = new javax.swing.JProgressBar();
        tpChatAndViewerContainer = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txChatInput = new javax.swing.JTextField();
        btnSendMessage = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaChat = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        plBoardContainer = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Caro Game");
        setResizable(false);

        plToolContainer.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));

        btnNewGame.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnNewGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_new_file_24px.png"))); // NOI18N
        btnNewGame.setText("Ván mới");

        btnUndo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_undo_24px.png"))); // NOI18N
        btnUndo.setText("Đánh lại");

        btnLeaveRoom.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnLeaveRoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_exit_sign_24px.png"))); // NOI18N
        btnLeaveRoom.setText("Thoát phòng");
        btnLeaveRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveRoomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout plToolContainerLayout = new javax.swing.GroupLayout(plToolContainer);
        plToolContainer.setLayout(plToolContainerLayout);
        plToolContainerLayout.setHorizontalGroup(
            plToolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plToolContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(plToolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(plToolContainerLayout.createSequentialGroup()
                        .addComponent(btnNewGame)
                        .addGap(6, 6, 6)
                        .addComponent(btnUndo))
                    .addComponent(btnLeaveRoom))
                .addGap(42, 42, 42))
        );
        plToolContainerLayout.setVerticalGroup(
            plToolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plToolContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(plToolContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewGame)
                    .addComponent(btnUndo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLeaveRoom)
                .addGap(18, 18, 18))
        );

        plPlayer.setBorder(javax.swing.BorderFactory.createTitledBorder("Người chơi"));

        lbAvatar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/avatar/icons8_circled_user_male_skin_type_7_96px.png"))); // NOI18N
        lbAvatar1.setBorder(javax.swing.BorderFactory.createTitledBorder("..."));
        lbAvatar1.setOpaque(true);

        lbPlayerNameId1.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbPlayerNameId1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPlayerNameId1.setText("Hoang");

        lbActive1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_sphere_30px.png"))); // NOI18N

        lbVersus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbVersus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_sword_48px.png"))); // NOI18N

        lbAvatar2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbAvatar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/avatar/icons8_circled_user_female_skin_type_7_96px.png"))); // NOI18N
        lbAvatar2.setBorder(javax.swing.BorderFactory.createTitledBorder("..."));

        lbPlayerNameId2.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbPlayerNameId2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPlayerNameId2.setText("Hien");

        lbActive2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_sphere_30px.png"))); // NOI18N

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_round_24px.png"))); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_delete_24px_1.png"))); // NOI18N

        javax.swing.GroupLayout plPlayerLayout = new javax.swing.GroupLayout(plPlayer);
        plPlayer.setLayout(plPlayerLayout);
        plPlayerLayout.setHorizontalGroup(
            plPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbAvatar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbPlayerNameId1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbVersus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(plPlayerLayout.createSequentialGroup()
                        .addComponent(lbActive1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbActive2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbAvatar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbPlayerNameId2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        plPlayerLayout.setVerticalGroup(
            plPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(plPlayerLayout.createSequentialGroup()
                        .addGroup(plPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(plPlayerLayout.createSequentialGroup()
                                .addComponent(lbAvatar1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbPlayerNameId1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(plPlayerLayout.createSequentialGroup()
                                .addComponent(lbAvatar2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbPlayerNameId2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(plPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(plPlayerLayout.createSequentialGroup()
                        .addGroup(plPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbVersus, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(plPlayerLayout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addGroup(plPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbActive1)
                                    .addComponent(lbActive2))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        plTimer.setBorder(javax.swing.BorderFactory.createTitledBorder("Thời gian"));

        jLabel4.setText("Nước đi");

        jLabel5.setText("Trận đấu");

        pgbTurnTimer.setValue(40);
        pgbTurnTimer.setString("00:20");
        pgbTurnTimer.setStringPainted(true);

        pgbMatchTimer.setValue(75);
        pgbMatchTimer.setString("08:50");
        pgbMatchTimer.setStringPainted(true);

        javax.swing.GroupLayout plTimerLayout = new javax.swing.GroupLayout(plTimer);
        plTimer.setLayout(plTimerLayout);
        plTimerLayout.setHorizontalGroup(
            plTimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plTimerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plTimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plTimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pgbTurnTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pgbMatchTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        plTimerLayout.setVerticalGroup(
            plTimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plTimerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(plTimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pgbTurnTimer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(plTimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pgbMatchTimer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout plPlayerContainerLayout = new javax.swing.GroupLayout(plPlayerContainer);
        plPlayerContainer.setLayout(plPlayerContainerLayout);
        plPlayerContainerLayout.setHorizontalGroup(
            plPlayerContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(plTimer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        plPlayerContainerLayout.setVerticalGroup(
            plPlayerContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plPlayerContainerLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(plPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plTimer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txChatInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txChatInputKeyPressed(evt);
            }
        });

        btnSendMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/view/asset/icons8_paper_plane_24px.png"))); // NOI18N
        btnSendMessage.setText("Gửi");
        btnSendMessage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSendMessageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txChatInput, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSendMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSendMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txChatInput))
                .addContainerGap())
        );

        txaChat.setEditable(false);
        txaChat.setColumns(20);
        txaChat.setRows(5);
        jScrollPane3.setViewportView(txaChat);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tpChatAndViewerContainer.addTab("Nhắn tin", jPanel3);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Người 1", "Người 2", "Người 3", "Người 4" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );

        tpChatAndViewerContainer.addTab("Người xem", jPanel4);

        javax.swing.GroupLayout plRightContainerLayout = new javax.swing.GroupLayout(plRightContainer);
        plRightContainer.setLayout(plRightContainerLayout);
        plRightContainerLayout.setHorizontalGroup(
            plRightContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plPlayerContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(plToolContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tpChatAndViewerContainer)
        );
        plRightContainerLayout.setVerticalGroup(
            plRightContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plRightContainerLayout.createSequentialGroup()
                .addComponent(plToolContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plPlayerContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tpChatAndViewerContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        plBoardContainer.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(plBoardContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(plRightContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plBoardContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plRightContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendMessageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendMessageMouseClicked
        String chatMsg = txChatInput.getText();
        txChatInput.setText("");

        if (!chatMsg.equals("")) {
            RunClient.socketHandler.chatRoom(chatMsg);
        }
    }//GEN-LAST:event_btnSendMessageMouseClicked

    private void txChatInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txChatInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSendMessageMouseClicked(null);
        }
    }//GEN-LAST:event_txChatInputKeyPressed

    private void btnLeaveRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveRoomActionPerformed
        // https://stackoverflow.com/a/8689130
        if (JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn thoát phòng?", "Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            RunClient.socketHandler.leaveRoom();
        }
    }//GEN-LAST:event_btnLeaveRoomActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InGame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLeaveRoom;
    private javax.swing.JButton btnNewGame;
    private javax.swing.JButton btnSendMessage;
    private javax.swing.JButton btnUndo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbActive1;
    private javax.swing.JLabel lbActive2;
    private javax.swing.JLabel lbAvatar1;
    private javax.swing.JLabel lbAvatar2;
    private javax.swing.JLabel lbPlayerNameId1;
    private javax.swing.JLabel lbPlayerNameId2;
    private javax.swing.JLabel lbVersus;
    private javax.swing.JProgressBar pgbMatchTimer;
    private javax.swing.JProgressBar pgbTurnTimer;
    private javax.swing.JPanel plBoardContainer;
    private javax.swing.JPanel plPlayer;
    private javax.swing.JPanel plPlayerContainer;
    private javax.swing.JPanel plRightContainer;
    private javax.swing.JPanel plTimer;
    private javax.swing.JPanel plToolContainer;
    private javax.swing.JTabbedPane tpChatAndViewerContainer;
    private javax.swing.JTextField txChatInput;
    private javax.swing.JTextArea txaChat;
    // End of variables declaration//GEN-END:variables
}
