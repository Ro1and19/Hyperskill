package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        File file = new File("rating.txt");
        boolean win = false;
        int idx;
        int AI;
        int score = 0;
        String name;
        String option;
        String[] line;
        String[] options = { "rock", "paper", "scissors"};
        System.out.print("Enter your name: ");
        name = sc.nextLine();
        System.out.println("Hello, " + name);
        try (Scanner in = new Scanner(file)){
            while (in.hasNextLine()) {
                line = in.nextLine().split(" ");
                if (line[0].equalsIgnoreCase(name))
                    score = Integer.parseInt(line[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + file);
        }
        option = sc.nextLine();
        if (!Objects.equals(option, "")){
            options = option.split(",");
        }
        System.out.println("Okay, let's start");
        while (true) {
            String input = sc.nextLine().toLowerCase().trim();
            AI = (int) (Math.random() * options.length);
            if (input.equals("!exit")) break;
            if (input.equals("!rating")){
                System.out.println("Your rating: " + score);
                continue;
            }
            if(!Arrays.asList(options).contains(input)){
                System.out.println("Invalid input");
                continue;
            }
            if (options[AI].equals(input)){
                System.out.println("There is a draw (" + options[AI] + ")");
                score += 50;
                continue;
            }
            idx = Arrays.asList(options).indexOf(input);
            for (int i = 1; i <= options.length / 2; i++) {
                if (idx + i == options.length) idx = -i;
                if (options[idx + i].equals(options[AI])){
                    System.out.println("Sorry, but the computer chose " + options[AI]);
                    win = false;
                    break;
                }
                win = true;
            }
            if (win){
                System.out.println("Well done. The computer chose " + options[AI] + " and failed");
                score += 100;
            }

        }
        System.out.println("Bye!");
    }
}
