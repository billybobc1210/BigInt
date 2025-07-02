import com.bigint.BigInt;

import java.math.BigInteger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BigInteger b1 = new BigInteger("36");
        BigInteger b2 = new BigInteger("-7");
        System.out.println(b1.remainder(b2));

        for (int i = 0; i < 10; i++) {
            String digits1 = buildNumber((int) (Math.random() * 1000) + 1);
            String digits2 = buildNumber((int) (Math.random() * 1000) + 1);
            BigInt bigInt1 = new BigInt(digits1);
            BigInt bigInt2 = new BigInt(digits2);
            BigInteger bigInteger1 = new BigInteger(digits1);
            BigInteger bigInteger2 = new BigInteger(digits2);

            System.out.println("Num 1: " + bigInt1);
            System.out.println("Num 2: " + bigInt2);
            BigInt sumBigInt = bigInt1.add(bigInt2);
            BigInteger sumBigInteger = bigInteger1.add(bigInteger2);
            System.out.println("BigInt sum:            " + sumBigInt);
            System.out.println("BigInteger sum:        " + sumBigInteger);

            BigInt differenceBigInt = bigInt1.subtract(bigInt2);
            BigInteger differenceBigInteger = bigInteger1.subtract(bigInteger2);
            System.out.println("BigInt difference:     " + differenceBigInt);
            System.out.println("BigInteger difference: " + differenceBigInteger);

            BigInt productBigInt = bigInt1.multiply(bigInt2);
            BigInteger productBigInteger = bigInteger1.multiply(bigInteger2);
            System.out.println("BigInt product:        " + productBigInt);
            System.out.println("BigInteger product:    " + productBigInteger);

            BigInt quotientBigInt = bigInt1.divide(bigInt2);
            BigInteger quotientBigInteger = bigInteger1.divide(bigInteger2);
            System.out.println("BigInt quotient:       " + quotientBigInt);
            System.out.println("BigInteger quotient:   " + quotientBigInteger);

            BigInt modulusBigInt = bigInt1.remainder(bigInt2);
            BigInteger modulusBigInteger = bigInteger1.remainder(bigInteger2);
            System.out.println("BigInt modulus:        " + modulusBigInt);
            System.out.println("BigInteger modulus:    " + modulusBigInteger);
        }
    }

    private static String buildNumber(int digitCount) {
        int s = (int) (Math.random() * 2);
        StringBuffer result = new StringBuffer(s == 0 ? "-" : "");

        for (int j = 0; j < digitCount; j++) {
            int c = (int) (Math.random() * 10);
            result.append((char) ('0' + c));
        }

        return result.toString();
    }
}