package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        File in;
        char cj = 'c';
        String alg = "shift";
        String out = "";
        String mode = "enc";
        String line = "";
        int key = 0;
        if (Arrays.asList(args).contains("-in") && Arrays.asList(args).contains("-data"))
            Arrays.asList(args).remove("-in");
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    mode = args[++i];
                    break;
                case "-key":
                    key = Integer.parseInt(args[++i]);
                    break;
                case "-data":
                    line = args[++i];
                    break;
                case "-in":
                    in = new File(args[++i]);
                    try (Scanner scanner = new Scanner(in)) {
                        line = scanner.nextLine();
                    } catch (FileNotFoundException e) {
                        System.out.println("No file found: ");
                    }
                    break;
                case "-out":
                    out = args[++i];
                    break;
                case "-alg":
                    alg = args[++i];
                    break;
            }
        }

        switch (mode) {
            case "enc":
                if (out.equals("")) {
                    encript(line, key, alg);
                }
                else {
                    try (FileWriter writer = new FileWriter(out)) {
                        writer.write(encript(line, key, alg));
                    } catch (IOException e) {
                        System.out.printf("An exception occurred %s", e.getMessage());
                    }
                }
                break;
            case "dec":
                if (out.equals("")) {
                    decript(line, key, alg);;
                }
                else {
                    try (FileWriter writer = new FileWriter(out)) {
                        writer.write(decript(line, key, alg));
                    } catch (IOException e) {
                        System.out.printf("An exception occurred %s", e.getMessage());
                    }
                }
                break;
        }
    }

    public static String encript(String line, int key, String alg) {
        StringBuilder code = new StringBuilder();
        if (alg.equals("unicode")) {
            for (char c : line.toCharArray())
                code.append((char) (c + key));
        } else {
            for (char c : line.toCharArray()) {
                if (Character.toLowerCase(c) < 'a' || Character.toLowerCase(c) > 'z')
                    code.append(c);
                else
                    code.append(Character.toLowerCase(c)+key > 'z' ?
                               (Character.isLowerCase(c) ? (char)(c+key - 'z' + 'a'-1) : (char)(c+key - 'Z' + 'A'-1)) : (char)(c+key));
            }
        }
        return code.toString();
    }

    public static String decript(String line, int key, String alg) {
        StringBuilder code = new StringBuilder();
        if (alg.equals("unicode")) {
            for (char c : line.toCharArray())
                code.append((char) (c - key));
        } else {
            for (char c : line.toCharArray()) {
                if (Character.toLowerCase(c) < 'a' || Character.toLowerCase(c) > 'z')
                    code.append(c);
                else
                    code.append(Character.toLowerCase(c)-key < 'a' ?
                            (Character.isLowerCase(c) ? (char)(c-key + 'z' - 'a'+1) : (char)(c+key + 'Z' - 'A'+1)) : (char)(c-key));
            }
        }
        return code.toString();
    }
}
