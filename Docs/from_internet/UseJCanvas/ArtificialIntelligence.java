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

// +-------------------------------------------------------+
// | This class is the brain, intelligence of the software |
// +-------------------------------------------------------+
import java.util.Random;

public class ArtificialIntelligence {

    static int nextMoveX;
    static int nextMoveY;
    static Random generator = new Random();
    static int DX[] = {-1, 1, 0, 0, -1, -1, 1, 1};
    static int DY[] = {0, 0, -1, 1, -1, 1, -1, 1};

    public static int RandomInt(int n) {
        return generator.nextInt(n);
    }

    public static int getNextMoveX() {
        return nextMoveX;
    }

    public static int getNextMoveY() {
        return nextMoveY;
    }

    public static void createTable(int table[][], int N, int nSteps, int x[], int y[], int whoFirst) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                table[i][j] = 0;
            }
        }

        for (int i = 0; i < nSteps; i++) {
            if (whoFirst == -1) {
                if (i % 2 == 0) {
                    table[x[i]][y[i]] = -1;
                } else {
                    table[x[i]][y[i]] = 1;
                }
            } else if (i % 2 == 0) {
                table[x[i]][y[i]] = 1;
            } else {
                table[x[i]][y[i]] = -1;
            }
        }
    }

    public static int CheckWinner(int N, int nSteps, int x[], int y[], int whoFirst, int LengthWin) {
        int table[][] = new int[N][N];
        createTable(table, N, nSteps, x, y, whoFirst);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (table[i][j] != 0) {
                    for (int t = 0; t < 8; t++) {
                        int sum = 0;
                        for (int k = 0; k < LengthWin; k++) {
                            int u = i + k * DX[t];
                            int v = j + k * DY[t];
                            if ((u >= 0) && (u < N) && (v >= 0) && (v < N)) {
                                if (table[u][v] != table[i][j]) {
                                    break;
                                }
                                sum += table[u][v];
                            } else {
                                break;
                            }
                        }
                        if (sum == LengthWin) {
                            return 1;
                        }
                        if (sum == -LengthWin) {
                            return -1;
                        }
                    }
                }
            }
        }

        return 0;
    }

    public static boolean SearchSum(int table[][], int N, int length, int sum) {
        int FoundX = 0;
        int FoundY = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (table[i][j] * sum >= 0) {
                    for (int t = 0; t < 8; t++) {
                        boolean check = true;
                        int s = 0;

                        for (int k = 0; k < length; k++) {
                            int u = i + k * DX[t];
                            int v = j + k * DY[t];

                            if ((u >= 0) && (u < N) && (v >= 0) && (v < N)) {
                                if (table[u][v] * sum >= 0) {
                                    s += table[u][v];

                                    if (table[u][v] == 0) {
                                        FoundX = u;
                                        FoundY = v;
                                    }
                                } else {
                                    check = false;
                                    break;
                                }
                            } else {
                                check = false;
                                break;
                            }
                        }

                        if (check) {
                            if (s == sum) {
                                nextMoveX = FoundX;
                                nextMoveY = FoundY;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void findNextMove(int N, int nSteps, int x[], int y[], int whoFirst, boolean hasAI) {
        if (nSteps == 0) {
            nextMoveX = RandomInt(N);
            nextMoveY = RandomInt(N);
            return;
        }

        int table[][] = new int[N][N];
        createTable(table, N, nSteps, x, y, whoFirst);

        if (!hasAI) {
            for (int ntimes = 1; ntimes <= 100; ntimes++) {
                int i = RandomInt(N);
                int j = RandomInt(N);
                if (table[i][j] == 0) {
                    nextMoveX = i;
                    nextMoveY = j;
                    return;
                }
            }
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (table[i][j] == 0) {
                        nextMoveX = i;
                        nextMoveY = j;
                        return;
                    }
                }
            }
        }

        // AI program here
        if (SearchSum(table, N, 5, 4)) {
            return;
        }
        if (SearchSum(table, N, 5, -4)) {
            return;
        }

        if (SearchSum(table, N, 5, 3)) {
            return;
        }
        if (SearchSum(table, N, 5, -3)) {
            return;
        }

        if (SearchSum(table, N, 4, -3)) {
            return;
        }
        if (SearchSum(table, N, 4, 3)) {
            return;
        }

        if (SearchSum(table, N, 3, 2)) {
            return;
        }
        if (SearchSum(table, N, 3, -2)) {
            return;
        }
        if (SearchSum(table, N, 2, 1)) {
            return;
        }
        if (SearchSum(table, N, 2, -1)) {
            return;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (table[i][j] == 0) {
                    nextMoveX = i;
                    nextMoveY = j;
                    return;
                }
            }
        }
    }

}
