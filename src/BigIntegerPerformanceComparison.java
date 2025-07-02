import com.bigint.BigInt;

import java.math.BigInteger;
import java.util.ArrayList;

public class BigIntegerPerformanceComparison {
    public static void main(String[] args) {
        ArrayList<String> digits1 = new ArrayList<>();
        ArrayList<String> digits2 = new ArrayList<>();

        for (int i = 0; i < 2000; i++) {
            digits1.add(buildRandomNumber((int) (Math.random() * 1000) + 1));
            digits2.add(buildRandomNumber((int) (Math.random() * 1000) + 1));
        }

        long startTimeBigInt = System.nanoTime() / 1000;

        for (int i = 0; i < digits1.size(); i++) {
            BigInt bigInt1 = new BigInt(digits1.get(i));
            BigInt bigInt2 = new BigInt(digits2.get(i));
            bigInt1.add(bigInt2);
            bigInt1.subtract(bigInt2);
            bigInt1.multiply(bigInt2);
            try {
                bigInt1.divide(bigInt2);
                bigInt1.remainder(bigInt2);
            } catch (Exception ex) {}
        }

        long endTimeBigInt = System.nanoTime() / 1000;
        long startTimeBigInteger = endTimeBigInt;

        for (int i = 0; i < digits1.size(); i++) {
            BigInteger bigInteger1 = new BigInteger(digits1.get(i));
            BigInteger bigInteger2 = new BigInteger(digits2.get(i));
            bigInteger1.add(bigInteger2);
            bigInteger1.subtract(bigInteger2);
            bigInteger1.multiply(bigInteger2);
            try {
                bigInteger1.divide(bigInteger2);
                bigInteger1.remainder(bigInteger2);
            } catch (Exception ex) {}
        }

        long endTimeBigInteger = System.nanoTime() / 1000;
        long bigIntElapsedTime = endTimeBigInt - startTimeBigInt;
        long bigIntegerElapsedTime = endTimeBigInteger - startTimeBigInteger;

        System.out.println("Results:");
        System.out.println("========");
        System.out.println("BigInt elapsed microseconds:     " + bigIntElapsedTime);
        System.out.println("BigInteger elapsed microseconds: " + bigIntegerElapsedTime);
        System.out.println("Performance factor:              " + (bigIntElapsedTime / bigIntegerElapsedTime));
    }

    private static String buildRandomNumber(int digitCount) {
        int s = (int) (Math.random() * 2);
        StringBuffer result = new StringBuffer(s == 0 ? "-" : "");

        for (int j = 0; j < digitCount; j++) {
            int c = (int) (Math.random() * 10);
            result.append((char) ('0' + c));
        }

        return result.toString();
    }
}