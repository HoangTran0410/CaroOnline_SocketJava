package Docs.FromInternet.UseJCanvas;

// +------------------------------------------------------------+
// | Software: Caro 3.0						|
// | Author: Hy Truong Son					|
// | Major: BSc. Computer Science				|
// | Class: 2013 - 2016						|
// | Institution: Eotvos Lorand University			|
// | Email: sonpascal93@gmail.com				|
// | Website: http://people.inf.elte.hu/hytruongson/		|
// | Copyright 2015 (c) Hy Truong Son. All rights reserved.	|
// +------------------------------------------------------------+
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class Main {

    // +----------------+
    // | GUI components |
    // +----------------+
    static JFrame frame;
    static JPanel panel;
    static JCanvas canvas;
    static JButton NewGameButton;
    static JButton UndoButton;
    static JButton AboutButton;
    static JLabel LevelLabel;
    static JLabel ColorXLabel;
    static JLabel ColorOLabel;
    static JLabel whoFirstLabel;
    static JLabel RepresentLabel;
    static JLabel TableSizeLabel;
    static JComboBox LevelBox;
    static JComboBox ColorXBox;
    static JComboBox ColorOBox;
    static JComboBox whoFirstBox;
    static JComboBox RepresentBox;
    static JComboBox TableSizeBox;
    static JTextField ScoreText;

    static JEventQueue Events;

    static Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    static TitledBorder titlePanel = BorderFactory.createTitledBorder(loweredetched, "Control");

    // +---------------+
    // | GUI constants |
    // +---------------+
    static int thicknessX = 4;
    static int thicknessO = 4;
    static int marginTableCell = 3;
    static int marginTable = 10;
    static int sizeTable = 600;
    static int widthCanvas = sizeTable + 2 * marginTable;
    static int widthPanel = 340;
    static int widthFrame = widthCanvas + widthPanel;
    static int height = widthCanvas;
    static int widthButton = 120;
    static int heightButton = 30;
    static int marginButton = 50;
    static int whoFirstBox_width = 200;
    static int ScoreText_width = 250;

    static Color Default_colorX = Color.orange;
    static Color Default_colorO = Color.green;
    static Color Default_colorTable = Color.GRAY;
    static Color colorX = Default_colorX;
    static Color colorO = Default_colorO;
    static Color colorTable = Default_colorTable;

    static String LevelData[] = {"AI program", "Random"};
    static String ColorSelectionData[] = {"Default", "Red", "Green", "Blue", "Yellow", "Orange", "Gray"};
    static String whoFirstData[] = {"User plays first", "Computer plays first"};
    static String RepresentData[] = {"plays as X", "plays as O"};
    static String TableSizeData[] = {"Default", "3 x 3", "5 x 5", "10 x 10", "15 x 15", "20 x 20", "25 x 25", "30 x 30"};

    static int MaxN = 30;
    static int Default_N = 20;
    static int whoFirst = -1;
    static boolean UserX = true;
    static boolean hasAI = true;
    static int LengthWin;
    static int nUserWin = 0;
    static int nComputerWin = 0;

    // +----------------------+
    // | Table representation |
    // +----------------------+
    static int N = Default_N;
    static int nSteps;
    static int x[] = new int[MaxN * MaxN];
    static int y[] = new int[MaxN * MaxN];
    static boolean used[][] = new boolean[MaxN][MaxN];

    public static void InitGUI() {
        System.out.println("Software: Caro 3.0");
        System.out.println("Author: Hy Truong Son");
        System.out.println("Major: BSc. Computer Science");
        System.out.println("Class: 2013 - 2016");
        System.out.println("Institution: Eotvos Lorand University");
        System.out.println("Email: sonpascal93@gmail.com");
        System.out.println("Website: http://people.inf.elte.hu/hytruongson");
        System.out.println("Copyright ©2013-2016, Hy Truong Son. All rights reserved.");

        frame = new JFrame();
        frame.setTitle("Caro 3.0 - Hy Truong Son");
        frame.setSize(widthFrame, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new JCanvas();
        canvas.setBounds(0, 0, widthCanvas, height);
        frame.add(canvas);

        panel = new JPanel();
        panel.setBounds(widthCanvas, 0, widthPanel, height);
        titlePanel.setTitleJustification(TitledBorder.RIGHT);
        panel.setBorder(titlePanel);
        panel.setLayout(null);
        frame.add(panel);

        int ButtonPositionX = widthCanvas + (widthPanel - widthButton) / 2;
        NewGameButton = new JButton("New Game");
        NewGameButton.setBounds(ButtonPositionX, marginButton, widthButton, heightButton);
        panel.add(NewGameButton);

        UndoButton = new JButton("Undo");
        UndoButton.setBounds(ButtonPositionX, 2 * marginButton, widthButton, heightButton);
        panel.add(UndoButton);

        AboutButton = new JButton("About");
        AboutButton.setBounds(ButtonPositionX, 3 * marginButton, widthButton, heightButton);
        panel.add(AboutButton);

        int LabelPositionX = widthCanvas + marginButton;
        int BoxPositionX = widthCanvas + (widthPanel - whoFirstBox_width) / 2;

        LevelLabel = new JLabel("Level:");
        LevelLabel.setBounds(LabelPositionX, 4 * marginButton, widthButton, heightButton);
        LevelLabel.setOpaque(true);
        panel.add(LevelLabel);

        LevelBox = new JComboBox(LevelData);
        LevelBox.setBounds(LabelPositionX + widthButton, 4 * marginButton, widthButton, heightButton);
        panel.add(LevelBox);

        TableSizeLabel = new JLabel("Table size:");
        TableSizeLabel.setBounds(LabelPositionX, 5 * marginButton, widthButton, heightButton);
        TableSizeLabel.setOpaque(true);
        panel.add(TableSizeLabel);

        TableSizeBox = new JComboBox(TableSizeData);
        TableSizeBox.setBounds(LabelPositionX + widthButton, 5 * marginButton, widthButton, heightButton);
        panel.add(TableSizeBox);

        whoFirstLabel = new JLabel("User or Computer plays first:");
        whoFirstLabel.setBounds(LabelPositionX, 6 * marginButton, 2 * widthButton, heightButton);
        whoFirstLabel.setOpaque(true);
        panel.add(whoFirstLabel);

        whoFirstBox = new JComboBox(whoFirstData);
        whoFirstBox.setBounds(BoxPositionX, 7 * marginButton, whoFirstBox_width, heightButton);
        panel.add(whoFirstBox);

        RepresentLabel = new JLabel("User:");
        RepresentLabel.setBounds(LabelPositionX, 8 * marginButton, widthButton, heightButton);
        RepresentLabel.setOpaque(true);
        panel.add(RepresentLabel);

        RepresentBox = new JComboBox(RepresentData);
        RepresentBox.setBounds(LabelPositionX + widthButton, 8 * marginButton, widthButton, heightButton);
        panel.add(RepresentBox);

        ColorXLabel = new JLabel("Color of X:");
        ColorXLabel.setBounds(LabelPositionX, 9 * marginButton, widthButton, heightButton);
        ColorXLabel.setOpaque(true);
        panel.add(ColorXLabel);

        ColorXBox = new JComboBox(ColorSelectionData);
        ColorXBox.setBounds(LabelPositionX + widthButton, 9 * marginButton, widthButton, heightButton);
        panel.add(ColorXBox);

        ColorOLabel = new JLabel("Color of O:");
        ColorOLabel.setBounds(LabelPositionX, 10 * marginButton, widthButton, heightButton);
        ColorOLabel.setOpaque(true);
        panel.add(ColorOLabel);

        ColorOBox = new JComboBox(ColorSelectionData);
        ColorOBox.setBounds(LabelPositionX + widthButton, 10 * marginButton, widthButton, heightButton);
        panel.add(ColorOBox);

        int TextPositionX = widthCanvas + (widthPanel - ScoreText_width) / 2;
        ScoreText = new JTextField();
        ScoreText.setBounds(TextPositionX, 11 * marginButton, ScoreText_width, heightButton);
        ScoreText.setEditable(false);
        ScoreText.setHorizontalAlignment(JTextField.CENTER);
        ScoreText.setText("User 0 - 0 Computer");
        panel.add(ScoreText);

        frame.setVisible(true);
    }

    public static void InitEventListener() {
        Events = new JEventQueue();
        Events.listenTo(canvas, "canvas");
        Events.listenTo(NewGameButton, "NewGame");
        Events.listenTo(UndoButton, "Undo");
        Events.listenTo(AboutButton, "About");
        Events.listenTo(LevelBox, "Level");
        Events.listenTo(TableSizeBox, "TableSize");
        Events.listenTo(whoFirstBox, "whoFirst");
        Events.listenTo(RepresentBox, "Represent");
        Events.listenTo(ColorXBox, "ColorX");
        Events.listenTo(ColorOBox, "ColorO");
    }

    public static void clearTable() {
        canvas.setBackground(panel.getBackground());
    }

    public static void drawTable() {
        int lengthCell = sizeTable / N;
        int x1 = marginTable;
        int x2 = marginTable + sizeTable;
        int y1 = marginTable;
        int y2 = marginTable + sizeTable;
        canvas.setColor(colorTable);
        for (int i = 0; i <= N; i++) {
            canvas.drawLine(x1, y1 + i * lengthCell, x2, y1 + i * lengthCell);
            canvas.drawLine(x1 + i * lengthCell, y1, x1 + i * lengthCell, y2);
        }
    }

    public static void drawX(int tableX, int tableY) {
        int lengthCell = sizeTable / N;
        int x1 = marginTable + tableX * lengthCell;
        int y1 = marginTable + tableY * lengthCell;
        int x2 = x1 + lengthCell;
        int y2 = y1 + lengthCell;
        x1 += marginTableCell;
        y1 += marginTableCell;
        x2 -= marginTableCell;
        y2 -= marginTableCell;
        canvas.setColor(colorX);
        for (int i = 0; i <= thicknessX; i++) {
            canvas.drawLine(x1, y1 + i, x2 - i, y2);
            canvas.drawLine(x1 + i, y1, x2, y2 - i);
            canvas.drawLine(x1, y2 - i, x2 - i, y1);
            canvas.drawLine(x1 + i, y2, x2, y1 + i);
        }
    }

    public static void drawO(int tableX, int tableY) {
        int lengthCell = sizeTable / N;
        int x = marginTable + tableX * lengthCell + marginTableCell;
        int y = marginTable + tableY * lengthCell + marginTableCell;
        int diameter = lengthCell - 2 * marginTableCell;
        canvas.setColor(colorO);
        for (int i = 0; i <= thicknessO; i++) {
            canvas.drawOval(x + i, y + i, diameter - 2 * i, diameter - 2 * i);
        }
    }

    public static void reDrawX() {
        for (int i = 0; i < nSteps; i++) {
            if (whoFirst == 1) {
                if ((i % 2 == 0) && (!UserX)) {
                    drawX(x[i], y[i]);
                }
                if ((i % 2 == 1) && (UserX)) {
                    drawX(x[i], y[i]);
                }
            } else {
                if ((i % 2 == 0) && (UserX)) {
                    drawX(x[i], y[i]);
                }
                if ((i % 2 == 1) && (!UserX)) {
                    drawX(x[i], y[i]);
                }
            }
        }
    }

    public static void reDrawO() {
        boolean UserO = false;
        if (!UserX) {
            UserO = true;
        }

        for (int i = 0; i < nSteps; i++) {
            if (whoFirst == 1) {
                if ((i % 2 == 0) && (!UserO)) {
                    drawO(x[i], y[i]);
                }
                if ((i % 2 == 1) && (UserO)) {
                    drawO(x[i], y[i]);
                }
            } else {
                if ((i % 2 == 0) && (UserO)) {
                    drawO(x[i], y[i]);
                }
                if ((i % 2 == 1) && (!UserO)) {
                    drawO(x[i], y[i]);
                }
            }
        }
    }

    public static void clearCell(int tableX, int tableY) {
        int lengthCell = sizeTable / N;
        int x1 = marginTable + tableX * lengthCell;
        int y1 = marginTable + tableY * lengthCell;
        int length = lengthCell - 2;
        canvas.setColor(panel.getBackground());
        canvas.fillRect(x1 + 1, y1 + 1, length, length);
    }

    public static void reDrawXO() {
        for (int i = 0; i < nSteps; i++) {
            clearCell(x[i], y[i]);
        }
        reDrawX();
        reDrawO();
    }

    public static void UpdateMove(int nextMoveX, int nextMoveY) {
        used[nextMoveX][nextMoveY] = true;
        x[nSteps] = nextMoveX;
        y[nSteps] = nextMoveY;
        nSteps++;
        if (UserX) {
            drawO(nextMoveX, nextMoveY);
        } else {
            drawX(nextMoveX, nextMoveY);
        }
    }

    public static Color getColor(String s) {
        if (s.equals("Red")) {
            return Color.red;
        }
        if (s.equals("Green")) {
            return Color.green;
        }
        if (s.equals("Blue")) {
            return Color.blue;
        }
        if (s.equals("Yellow")) {
            return Color.yellow;
        }
        if (s.equals("Orange")) {
            return Color.orange;
        }
        return Color.gray;
    }

    public static void change_colorX(int index) {
        String color = ColorSelectionData[index];
        if (color.equals("Default")) {
            colorX = Default_colorX;
        } else {
            colorX = getColor(color);
        }
        reDrawX();
    }

    public static void change_colorO(int index) {
        String color = ColorSelectionData[index];
        if (color.equals("Default")) {
            colorO = Default_colorO;
        } else {
            colorO = getColor(color);
        }
        reDrawO();
    }

    public static int getTableSize(int index) {
        if (index == 0) {
            return Default_N;
        }
        if (index == 1) {
            return 3;
        }
        if (index == 2) {
            return 5;
        }
        if (index == 3) {
            return 10;
        }
        if (index == 4) {
            return 15;
        }
        if (index == 5) {
            return 20;
        }
        if (index == 6) {
            return 25;
        }
        return 30;
    }

    public static void DeleteMove(int MoveX, int MoveY) {
        used[MoveX][MoveY] = false;
        clearCell(MoveX, MoveY);
        nSteps--;
    }

    public static void UndoMove() {
        if (nSteps == 0) {
            return;
        }
        if (nSteps == 1) {
            JOptionPane.showMessageDialog(frame, "You cannot undo!", "Notice", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DeleteMove(x[nSteps - 1], y[nSteps - 1]);
        DeleteMove(x[nSteps - 1], y[nSteps - 1]);
    }

    public static boolean CheckFinalState() {
        int result = ArtificialIntelligence.CheckWinner(N, nSteps, x, y, whoFirst, LengthWin);
        if (result != 0) {
            if (result == 1) {
                JOptionPane.showMessageDialog(frame, "Computer and Artificial Intelligence win!");
                nComputerWin++;
            } else {
                JOptionPane.showMessageDialog(frame, "User wins!");
                nUserWin++;
            }
            ScoreText.setText("User " + Integer.toString(nUserWin) + " - " + Integer.toString(nComputerWin) + " Computer");
            return true;
        }
        if (nSteps == N * N) {
            JOptionPane.showMessageDialog(frame, "Computer and user are drawn!");
            return true;
        }
        return false;
    }

    public static void GamePlaying() {
        int nextMoveX, nextMoveY;

        clearTable();
        drawTable();

        nSteps = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                used[i][j] = false;
            }
        }

        LengthWin = Math.min(5, N);

        if (whoFirst == 1) {
            ArtificialIntelligence.findNextMove(N, nSteps, x, y, whoFirst, hasAI);
            nextMoveX = ArtificialIntelligence.getNextMoveX();
            nextMoveY = ArtificialIntelligence.getNextMoveY();
            UpdateMove(nextMoveX, nextMoveY);
        }

        while (true) {
            EventObject AnEvent = Events.waitEvent();

            if (Events.isMouseEvent(AnEvent)) {
                if (Events.isMousePressed(AnEvent)) {
                    int MouseX = Events.getMouseX(AnEvent);
                    int MouseY = Events.getMouseY(AnEvent);
                    if ((MouseX > marginTable) && (MouseX < marginTable + sizeTable)) {
                        if ((MouseY > marginTable) && (MouseY < marginTable + sizeTable)) {
                            int lengthCell = sizeTable / N;
                            int TableX = (MouseX - marginTable) / lengthCell;
                            int TableY = (MouseY - marginTable) / lengthCell;

                            if (!used[TableX][TableY]) {
                                used[TableX][TableY] = true;
                                x[nSteps] = TableX;
                                y[nSteps] = TableY;
                                nSteps++;
                                if (UserX) {
                                    drawX(TableX, TableY);
                                } else {
                                    drawO(TableX, TableY);
                                }

                                if (CheckFinalState()) {
                                    GamePlaying();
                                    return;
                                }

                                ArtificialIntelligence.findNextMove(N, nSteps, x, y, whoFirst, hasAI);
                                nextMoveX = ArtificialIntelligence.getNextMoveX();
                                nextMoveY = ArtificialIntelligence.getNextMoveY();
                                UpdateMove(nextMoveX, nextMoveY);

                                if (CheckFinalState()) {
                                    GamePlaying();
                                    return;
                                }
                            }
                        }
                    }
                }
            }

            String name = Events.getName(AnEvent);

            if (name.equals("NewGame")) {
                GamePlaying();
                return;
            }

            if (name.equals("Undo")) {
                UndoMove();
                continue;
            }

            if (name.equals("About")) {
                JOptionPane.showMessageDialog(frame,
                        "Software: Caro 3.0\n"
                        + "Author: Hy Truong Son\n"
                        + "Major: BSc. Computer Science\n"
                        + "Class: 2013 - 2016\n"
                        + "Institution: Eotvos Lorand University\n"
                        + "Email: sonpascal93@gmail.com\n"
                        + "Website: http://people.inf.elte.hu/hytruongson\n"
                        + "Copyright ©2013-2016, Hy Truong Son. All rights reserved.");
                continue;
            }

            if (name.equals("Level")) {
                if (LevelBox.getSelectedIndex() == 0) {
                    if (!hasAI) {
                        hasAI = true;
                        GamePlaying();
                        return;
                    }
                } else {
                    if (hasAI) {
                        hasAI = false;
                        GamePlaying();
                        return;
                    }
                }
                continue;
            }

            if (name.equals("ColorX")) {
                change_colorX(ColorXBox.getSelectedIndex());
                continue;
            }

            if (name.equals("ColorO")) {
                change_colorO(ColorOBox.getSelectedIndex());
                continue;
            }

            if (name.equals("whoFirst")) {
                if (whoFirstBox.getSelectedIndex() == 0) {
                    if (whoFirst == 1) {
                        whoFirst = -1;
                        GamePlaying();
                        return;
                    }
                } else if (whoFirst == -1) {
                    whoFirst = 1;
                    GamePlaying();
                    return;
                }
                continue;
            }

            if (name.equals("Represent")) {
                int index = RepresentBox.getSelectedIndex();
                if (index == 0) {
                    if (!UserX) {
                        UserX = true;
                        reDrawXO();
                    }
                } else if (UserX) {
                    UserX = false;
                    reDrawXO();
                }
                continue;
            }

            if (name.equals("TableSize")) {
                int n = getTableSize(TableSizeBox.getSelectedIndex());
                if (n != N) {
                    N = n;
                    GamePlaying();
                    return;
                }
                continue;
            }
        }
    }

    // +-------------+
    // | Main method |
    // +-------------+
    public static void main(String args[]) {
        InitGUI();
        InitEventListener();
        GamePlaying();
    }

}
