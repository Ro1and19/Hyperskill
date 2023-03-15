package bullscows;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int bulls = 0, cows = 0;
        System.out.println("Input the length of the secret code:");
        String snum = sc.nextLine();
        if (!snum.matches("\\d+")){
            System.out.println("Error: wrong input");
            return;
        }
        int num = Integer.parseInt(snum);
        if (num > 36 || num < 1) {
            System.out.println("Error: can't generate a secret number with a length of " + num
                    + " because there aren't enough unique digits.");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        String ssym = sc.nextLine();
        if (!ssym.matches("\\d+")){
            System.out.println("Error: wrong input");
            return;
        }
        int symb = Integer.parseInt(ssym);

        if (symb > 36 || symb < 1){
            System.out.println("Error: the number of possible symbols cant be higher than 36 or less than 1");
            return;
        }

        if (symb < num){
            System.out.println("Error: number of symbols cant be smaller than length of the code");
            return;
        }

        String code = generateRandomCode(num, symb);
        System.out.println("The secret is prepared: ****** " + (symb > 10 ? "(0-9, a-" + ((char)(symb + 86)) + ")." : "(0-" + (symb - 1) + ")."));
        System.out.println("Okay, let's start a game!");

        int turn = 0;
        String input;

        while (true) {

            System.out.println("Turn " + ++turn + ":");
            input = sc.next();
            bulls = 0; cows = 0;


            for (int i = 0; i < code.length(); i++) {
                if (code.contains("" + input.charAt(i))) {

                    if (code.charAt(i) == input.charAt(i)) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }

            if (bulls == num){
                System.out.println("Grade: " + num + " bulls");
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }

            if (bulls == 0 && cows == 0){

                System.out.println("Grade: None");

            } else if (bulls != 0 && cows != 0) {

                System.out.println("Grade: " + (bulls != 1 ? bulls + " bulls and " : bulls + " bull and ") + (cows != 1 ? cows + " cows" : cows + " cow"));

            } else {

                System.out.println("Grade: " + (bulls != 0 ? bulls + (bulls != 1 ? " bulls" : " bull") : cows + (cows != 1 ? " cows" : " cow")));

            }
        }

    }

    static public String generateRandomCode(int l, int sym) {
        ArrayList<String> code = new ArrayList<>();
        int num;
        String snum;
        while (code.size() < l) {
            num = (int) (Math.random() * sym - 1);
            snum = Integer.toString(num);
            if (num > 9){
                snum = "" + ((char) (num + 87));
                if (!code.contains(snum)){
                    code.add(snum);
                }

            } else if (!code.contains(snum)){
                code.add(snum);
            }
        }
        String result = "";
        for (String it : code){
            result += it;
        }
        return result;
    }

}
