package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] field = new char[3][3];

        int Xs = 0;
        int Os = 0;
        boolean winX = false;
        boolean winO = false;
        boolean turnX = true;

        for (int i = 0; i < 9; i++)
            field[i/3][i%3] = ' ';

        printGrid(field);

        String snum1;
        String snum2;
        int num1;
        int num2;

        while(true) {
            snum1 = sc.next();
            snum2 = sc.next();
            if (!snum1.matches("\\d+") || !snum2.matches("\\d+")){
                System.out.println("You should enter numbers!");
                continue;
            }
            num1 = Integer.parseInt(snum1);
            num2 = Integer.parseInt(snum2);
            if (num1 > 3 || num1 < 1 || num2 > 3 || num2 < 1){
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (field[num1 - 1][num2 - 1] != ' '){
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            if (turnX) {
                field[num1 - 1][num2 - 1] = 'X';
                turnX = false;
                Xs++;
            }
            else {
                field[num1 - 1][num2 - 1] = 'O';
                turnX = true;
                Os++;
            }

            printGrid(field);

            for (int i = 0; i < 3; i++) {
                int row = 0;
                int clm = 0;
                int mDiag = 0;
                int aDiag = 0;

                for (int j = 0; j < 3; j++) {
                    row += field[i][j];
                    clm += field[j][i];
                    mDiag += field[j][j];
                    aDiag += field[j][2-j];
                }

                // ASCII value for X is 88 (X+X+X is 264)
                // ASCII value for O is 79 (O+O+O is 237)
                winX = winX || row == 264 || clm == 264 || mDiag == 264 || aDiag == 264;
                winO = winO || row == 237 || clm == 237 || mDiag == 237 || aDiag == 237;
            }

            String result = winX ? "X wins"
                    : winO ? "O wins"
                    : Xs + Os == 9 ? "Draw"
                    : "continue";

            if (result.equals("continue"))
                continue;

            System.out.println(result);

            break;
        }

    }

    public static void printGrid(char[][] field) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.println(String.format("| %c %c %c |", field[i][0], field[i][1], field[i][2]));
        }
        System.out.println("---------");
    }

}

