package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Converter {
    private final int sourceBase;
    private final int targetBase;
    private boolean fractal;

    Converter(int sourceBase, int targetBase) {
        if (sourceBase < 2 || targetBase < 2)
            throw new IllegalArgumentException("Wrong input.");
        this.sourceBase = sourceBase;
        this.targetBase = targetBase;
    }

    public String convert(String number) {
        fractal = number.contains(".");
        if (getSourceBase() != 10)
            number = toDecimal(number);
        if (getTargetBase() == 10)
            return number;
        String fractString = "";
        if (fractal) {
            BigDecimal fract = new BigDecimal(number).remainder( BigDecimal.ONE );
            StringBuilder num = new StringBuilder();
            int mod;
            for (int i = 0; i < 5; i++) {
                fract = fract.multiply(BigDecimal.valueOf(getTargetBase()));
                mod = fract.intValue();
                if (mod >= 10) num.append((char)(87 + mod));
                else num.append(mod);
                fract = fract.remainder(BigDecimal.ONE);
            }
            fractString = "." + num;
            number = number.substring(0, number.indexOf("."));
        }
        BigInteger bInt = new BigInteger(number);
        StringBuilder num = new StringBuilder();
        int mod;
        while (!bInt.equals(BigInteger.ZERO)){
            mod = bInt.mod(BigInteger.valueOf(getTargetBase())).intValue();
            if (mod >= 10) num.append((char)(87 + mod));
            else num.append(mod);
            bInt = bInt.divide(BigInteger.valueOf(getTargetBase()));
        }
        num.reverse();
        return (num.toString().isBlank() ? 0 : num.toString()) + fractString;
    }

    private String toDecimal(String number) {
        String fractString = "";
        if (fractal) {
            fractString = number.substring(number.indexOf(".") + 1);
            BigDecimal sum = BigDecimal.ZERO;
            int value;
            for (int i = 0; i < fractString.length(); i++){
                value = Character.getNumericValue(fractString.charAt(i));
                BigDecimal div = BigDecimal.valueOf(getSourceBase()).pow(i+1);
                sum = sum.add(BigDecimal.valueOf(value == -1? fractString.charAt(i) - 87 : value)
                        .divide(div, 5, RoundingMode.HALF_UP));
            }
            fractString = sum.toString().replaceFirst("0", "");
            number = number.substring(0, number.indexOf("."));
        }
        int length = number.length();
        BigInteger sum = BigInteger.ZERO;
        char[] chNumber = number.toCharArray();
        int value;
        for (char e : chNumber){
            value = Character.getNumericValue(e);
            if (value == -1)
                sum = sum.add(BigInteger.valueOf(e - 87).multiply(BigInteger.valueOf(getSourceBase()).pow(--length)));
            else
                sum = sum.add(BigInteger.valueOf(value).multiply(BigInteger.valueOf(getSourceBase()).pow(--length)));
        }
        return sum.toString() + fractString;
    }

    public int getSourceBase() {
        return sourceBase;
    }

    public int getTargetBase() {
        return targetBase;
    }

}
