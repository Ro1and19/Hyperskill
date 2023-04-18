package converter;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Converter converter;
        String[] input;
        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            input = sc.nextLine().split(" ");
            if (input[0].equals("/exit")) break;
            converter = new Converter(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
            while (true){
                System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ", converter.getSourceBase(), converter.getTargetBase());
                input[0] = sc.nextLine();
                if (input[0].equals("/back")) break;
                System.out.println("Conversion result: " + converter.convert(input[0]));
            }
        }
    }
}

/*BigInteger decimalNumber = new BigInteger(inputTwo, sourceBase);
  String convertedNumber = new BigInteger(String.valueOf(decimalNumber)).toString(targetBase);
  System.out.println("Conversion result: " + convertedNumber + "\n");*/