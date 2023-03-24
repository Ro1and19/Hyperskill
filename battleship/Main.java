package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static char[][] field1 = new char[10][10];
    static char[][] field2 = new char[10][10];
    static char[][] fog1 = new char[10][10];
    static char[][] fog2 = new char[10][10];
    static int player = 1;
    static Ship[] ships = {
            new Ship("Aircraft Carrier", 5),
            new Ship("Battleship", 4),
            new Ship("Submarine", 3),
            new Ship("Cruiser", 3),
            new Ship("Destroyer", 2)
    };

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            field1[i / 10][i % 10] = '~';
            field2[i / 10][i % 10] = '~';
            fog1[i / 10][i % 10] = '~';
            fog2[i / 10][i % 10] = '~';
        }
        System.out.println("Player 1, place your ships on the game field\n");
        for (Ship ship : ships) {
            placeShip(ship, field1);
        }
        System.out.println("Press Enter and pass the move to another player\n");
        sc.nextLine();
        System.out.println("Player 2, place your ships to the game field\n");
        for (Ship ship : ships) {
            placeShip(ship, field2);
        }
        System.out.println("Press Enter and pass the move to another player\n");
        sc.nextLine();
        play();
        if (player == 1) {
            printField(field2);
            System.out.println("---------------------");
            printField(field1);
        } else {
            printField(field1);
            System.out.println("---------------------");
            printField(field2);
        }
    }

    public static void printField(char[][] field) {
        char ch = 65;
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++){
            System.out.print(ch + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
            ch++;
        }
    }

    public static boolean checkNotValidPlacement(int clmn1, int clmn2, int row1, int row2, char[][] field){
        clmn1 = (clmn1 == 0 ? clmn1 : clmn1 - 1);
        clmn2 = (clmn2 == 9 ? clmn2 : clmn2 + 1);
        row1 = (row1 == 0 ? row1 : row1 - 1);
        row2 = (row2 == 9 ? row2 : row2 + 1);

        for (int i = row1; i <= row2; i++) {
            for (int j = clmn1; j <= clmn2; j++) {
                if (field[i][j] != '~') {
                    return true;
                }
            }
        }
        return  false;
    }

    public static boolean checkShipSank(int row, int clmn, char[][] field){
        for (int i = (clmn == 0 ? clmn : clmn - 1); i <= (clmn == 9 ? clmn : clmn + 1); i++){
            if (field[row][i] == 'O') return false;
        }
        for (int i = (row == 0 ? row : row - 1); i <= (row == 9 ? row : row + 1); i++){
            if (field[i][clmn] == 'O') return false;
        }
        return true;
    }

    public static void placeShip(Ship ship, char[][] field){
        int row1;
        int row2;
        int clmn1;
        int clmn2;
        printField(field);
        System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getSize());
        while (true) {
            String line = sc.nextLine().toUpperCase();
            String[] row = line.split(" ");
            String[] num = line.split("\\D+");

            row1 = row[0].charAt(0) - 65;
            row2 = row[1].charAt(0) - 65;
            clmn1 = Integer.parseInt(num[1]) - 1;
            clmn2 = Integer.parseInt(num[2]) - 1;
            try {
                if (row1 > 9 || row1 < 0 ||
                    row2 > 9 || row2 < 0 ||
                    clmn1 > 9 || clmn1 < 0 ||
                    clmn2 > 9 || clmn2 < 0 )
                    throw new Exception("Error! Wrong input!\nTry again:");
                if (clmn1 == clmn2) {
                    if (row2 < row1)
                        row2 = (row2 + row1) - (row1 = row2); //swapping values
                    if (row2 - row1 != ship.getSize() - 1)
                        throw new Exception("Error! Wrong length of the " + ship.getName() + "!\nTry again:");
                    if (checkNotValidPlacement(clmn1, clmn2, row1, row2, field))
                        throw new Exception("Error! You placed it too close to another one.\nTry again:");

                    for (int i = row1; i <= row2; i++)
                        field[i][clmn1] = 'O';
                } else if (row1 == row2) {
                    if (clmn2 < clmn1)
                        clmn2 = (clmn2 + clmn1) - (clmn1 = clmn2); //swapping values
                    if (clmn2 - clmn1 != ship.getSize() - 1)
                        throw new Exception("Error! Wrong length of the " + ship.getName() + "!\nTry again:");
                    if (checkNotValidPlacement(clmn1, clmn2, row1, row2, field))
                        throw new Exception("Error! You placed it too close to another one.\nTry again:");

                    for (int i = clmn1; i <= clmn2; i++)
                        field[row1][i] = 'O';
                } else
                    throw new Exception("Error! Wrong ship location!\nTry again:");

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        printField(field);
    }

    public static void play(){
        System.out.println("The game starts!");
        char[][] field;
        char[][] enemyField = field2;
        char[][] fog;
        while(shipsRemain(enemyField)) {
            if (player == 1){
                field = field1;
                enemyField = field2;
                fog = fog2;
            } else {
                field = field2;
                enemyField = field1;
                fog = fog1;
            }
            printField(fog);
            System.out.println("---------------------");
            printField(field);
            System.out.println("Player " + player + ", it's your turn:");
            try {
                String line = sc.nextLine().toUpperCase();
                int row = line.charAt(0) - 65;
                int clmn = Integer.parseInt(line.substring(1)) - 1;

                if (enemyField[row][clmn] == 'O') {
                    fog[row][clmn] = 'X';
                    enemyField[row][clmn] = 'X';
                    if (!shipsRemain(enemyField))
                        System.out.println("You sank the last ship. You won.\nCongratulations!");
                    else if (checkShipSank(row, clmn, enemyField)) {
                        System.out.println("You sank a ship!");
                        System.out.println("Press Enter and pass the move to another player");
                        sc.nextLine();
                        if (player == 1)
                            player++;
                        else
                            player--;
                    }
                    else {
                        System.out.println("You hit a ship!");
                        System.out.println("Press Enter and pass the move to another player");
                        sc.nextLine();
                        if (player == 1)
                            player++;
                        else
                            player--;
                    }
                } else if (enemyField[row][clmn] == 'X') {
                    System.out.println("Error! Wrong coordinates!");
                    System.out.println("Press Enter and pass the move to another player");
                    sc.nextLine();
                    if (player == 1)
                        player++;
                    else
                        player--;
                } else {
                    fog[row][clmn] = 'M';
                    enemyField[row][clmn] = 'M';
                    System.out.println("You missed!");
                    System.out.println("Press Enter and pass the move to another player");
                    sc.nextLine();
                    if (player == 1)
                        player++;
                    else
                        player--;
                }

            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error! Wrong input!");
                System.out.println("Press Enter and pass the move to another player");
                sc.nextLine();
                if (player == 1)
                    player++;
                else
                    player--;
            }
        }
    }

    public static boolean shipsRemain(char[][] field){
        for (char[] it : field) {
            for (char ch : it) {
                if (ch == 'O')
                    return true;
            }
        }
        return false;
    }
}

