package com.bigint;

import java.util.HashMap;
import java.util.Map;

public class BigInt implements Comparable<BigInt> {
    public static final BigInt ZERO = new BigInt("0");
    public static final BigInt ONE = new BigInt("1");
    public static final BigInt NEGATIVE_ONE = ONE.negate();

    private int sign = 0;
    private String digits = "";
    private BigInt remainder = null;


    private BigInt(int sign, String digits) {
        this((sign == -1 ? "-" : "") + digits);
    }

    private BigInt(int sign, StringBuffer digits) {
        this(sign, digits.toString());
    }

    private BigInt(int sign, StringBuilder digits) {
        this(sign, digits.toString());
    }

    public BigInt(int i) {
        this(Integer.toString(i));
    }

    public BigInt(long l) {
        this(Long.toString(l));
    }

    public BigInt(StringBuffer s) {
        this(s.toString());
    }

    public BigInt(StringBuilder s) {
        this(s.toString());
    }

    public BigInt(String s) {
        boolean isValidInteger = true;

        if (s != null) {
            digits = s;
            boolean startsWithNegativeSign = (s.charAt(0) == '-');

            if (startsWithNegativeSign) {
                digits = s.substring(1);
            }

            if (!digits.isEmpty()) {
                digits = digits.replaceFirst("^0*", "");

                if (digits.isEmpty()) {
                    digits = "0";
                    sign = 0;
                } else {
                    for (int i = 0; i < digits.length(); i++) {
                        char c = digits.charAt(i);

                        if (!Character.isDigit(c)) {
                            isValidInteger = false;
                            break;
                        }
                    }

                    sign = startsWithNegativeSign ? -1 : 1;
                }
            } else {
                isValidInteger = false;
            }
        } else {
            isValidInteger = false;
        }

        if (!isValidInteger) {
            throw new RuntimeException("Invalid integer.");
        }
    }

    public BigInt add(BigInt addend) {
        BigInt augend = this;

        if (augend.equals(ZERO)) {
            return addend;
        }

        if (addend.equals(ZERO)) {
            return augend;
        }

        // Addition and subtraction are complicated by the fact that one or both of the operands might be negative.
        // In such cases, the strategy is to reorder operands and use negation to convert the original expression
        // to an equivalent expression of one of the following two forms:
        //
        //   positive + positive
        //   positive - positive (where minuend > subtrahend)
        //
        // Once in one of these two forms it's easy to just perform the operation digit by digit starting on the
        // right and working left.
        //
        // The following table describes the conversions that take place for addition.  Au represents 'this'
        // (the augend) and Ad represents the 'addend' parameter passed to the method.
        //
        // Au Ad
        //  +  +  =>  Au + Ad
        //  +  -  =>  Au - -Ad
        //  -  +  =>  Ad - -Au
        //  -  -  =>  -(-Au + -Ad)
        if ((augend.sign == 1) && (addend.sign == 1)) {
            String augendDigits = augend.digits;
            String addendDigits = addend.digits;
            int lengthDiff = augendDigits.length() - addendDigits.length();

            if (lengthDiff > 0) {
                addendDigits = "0".repeat(lengthDiff) + addendDigits;
            } else if (lengthDiff < 0) {
                augendDigits = "0".repeat(-lengthDiff) + augendDigits;
            }

            StringBuffer resultDigits = new StringBuffer();
            int carry = 0;

            for (int i = augendDigits.length() - 1; i >= 0; i--) {
                int augendDigitVal = augendDigits.charAt(i) - '0';
                int addendDigitVal = addendDigits.charAt(i) - '0';
                int sum = augendDigitVal + addendDigitVal + carry;
                int resultDigitVal = (sum % 10);
                carry = sum / 10;
                resultDigits.append((char) ('0' + resultDigitVal));
            }

            if (carry > 0) {
                resultDigits.append((char) ('0' + carry));
            }

            return new BigInt(resultDigits.reverse());
        } else if ((augend.sign == 1) && (addend.sign == -1)) {
            return augend.subtract(addend.negate());
        } else if ((augend.sign == -1) && (addend.sign == 1)) {
            return addend.subtract(augend.negate());
        }

        // ((sign == -1) && (addend.sign == -1)) {
        return augend.negate().add(addend.negate()).negate();
    }

    public BigInt subtract(BigInt subtrahend) {
        BigInt minuend = this;

        if (minuend.equals(ZERO)) {
            return subtrahend.negate();
        }

        if (subtrahend.equals(ZERO)) {
            return minuend;
        }

        if (minuend.equals(subtrahend)) {
            return ZERO;
        }

        // Addition and subtraction are complicated by the fact that one or both of the operands might be negative.
        // In such cases, the strategy is to reorder operands and use negation to convert the original expression
        // to an equivalent expression of one of the following two forms:
        //
        //   positive + positive
        //   positive - positive (where minuend > subtrahend)
        //
        // Once in one of these two forms it's easy to just perform the operation digit by digit starting on the
        // right and working left.
        //
        // The following table describes the conversions that take place for subtraction.  M represents 'this'
        // (the minuend) and S represents the 'subtrahend' parameter passed to the method.
        //
        // M S
        // + +  =>  M >= S ? M - S : -(S - M)
        // + -  =>  M + -S
        // - +  =>  -(-M + S)
        // - -  =>  -(-M - -S)
        if ((minuend.sign == 1) && (subtrahend.sign == 1)) {
            int minuendToSubtrahendCompare = minuend.compareTo(subtrahend);

            if (minuendToSubtrahendCompare < 0) {
                return subtrahend.subtract(minuend).negate();
            } else if (minuendToSubtrahendCompare == 0) {
                return ZERO;
            }

            // At this point, both minuend and subtrahend are guaranteed to be positive
            // with minuend > subtrahend.  Therefore, we can just add leading zeroes to
            // subtrahend if necessary and start doing the subtraction algorithm digit by
            // digit, starting from the right.
            String minuendDigits = minuend.digits;
            String subtrahendDigits = subtrahend.digits;
            int lengthDiff = minuendDigits.length() - subtrahendDigits.length();
            subtrahendDigits = "0".repeat(lengthDiff) + subtrahendDigits;

            StringBuffer resultDigits = new StringBuffer();
            int borrow = 0;

            for (int i = minuendDigits.length() - 1; i >= 0; i--) {
                int minuendDigitVal = minuendDigits.charAt(i) - '0' - borrow;
                int subtrahendDigitVal = subtrahendDigits.charAt(i) - '0';
                if (minuendDigitVal < subtrahendDigitVal) {
                    borrow = 1;
                    minuendDigitVal += 10;
                } else {
                    borrow = 0;
                }

                resultDigits.append((char) ('0' + minuendDigitVal - subtrahendDigitVal));
            }

            return new BigInt(resultDigits.reverse());
        } else if ((minuend.sign == 1) && (subtrahend.sign == -1)) {
            return minuend.add(subtrahend.negate());
        } else if ((minuend.sign == -1) && (subtrahend.sign == 1)) {
            return minuend.negate().add(subtrahend).negate();
        }

        // ((minuend.sign == -1) && (subtrahend.sign == -1)) {
        return minuend.negate().subtract(subtrahend.negate()).negate();
    }

    public BigInt multiply(BigInt multiplier) {
        BigInt multiplicand = this;

        if (multiplicand.equals(ZERO) || multiplier.equals(ZERO)) {
            return ZERO;
        }

        if (multiplicand.equals(ONE)) {
            return multiplier;
        }

        if (multiplier.equals(ONE)) {
            return multiplicand;
        }

        if (multiplicand.equals(NEGATIVE_ONE)) {
            return multiplier.negate();
        }

        if (multiplier.equals(NEGATIVE_ONE)) {
            return multiplicand.negate();
        }

        // Multiplication is performed by adding a number of partial products equal to the number of digits in the
        // multiplier as follows:
        //
        // multiplicand (this) =>      12345
        // multiplier          =>      x 221
        //                             -----
        //                             12345  <- partial product (1 x 12345 x 1)
        //                         +  246900  <- partial product (2 x 12345 x 10)
        //                         + 2469000  <- partial product (2 x 12345 x 100)
        //                         ---------
        //                           2728245
        BigInt result = ZERO;
        StringBuffer partialProductTrailingZeroes = new StringBuffer();

        // Performance enhancement: once we've calculated the partial product for a particular digit in the multiplier
        // there is no need to do the calculation again if that digit occurs in the multiplier again.  So we'll cache
        // the digits of the partial product (without trailing zeros) in the multiplierDigitToPartialProductDigitsMap
        // hash map to be retrieved the next time we encounter that digit in multiplier.
        //
        // Further, we can seed the map for the digit '1' as follows:
        //
        //     1 => digits of the multiplicand
        Map<Integer, String> multiplierDigitToPartialProductDigitsMap = new HashMap<>();
        multiplierDigitToPartialProductDigitsMap.put(1, multiplicand.digits);

        for (int i = multiplier.digits.length() - 1; i >= 0; i--) {
            int multiplierDigitVal = multiplier.digits.charAt(i) - '0';

            // if the multiplier digit is 0 there is nothing to do because the partial product will be 0
            if (multiplierDigitVal != 0) {
                String partialProductDigits = multiplierDigitToPartialProductDigitsMap.get(multiplierDigitVal);

                if (partialProductDigits == null) {
                    StringBuffer partialProductDigitsBuffer = new StringBuffer();

                    int carry = 0;

                    for (int j = multiplicand.digits.length() - 1; j >= 0; j--) {
                        int multiplicandDigitVal = multiplicand.digits.charAt(j) - '0';
                        int product = (multiplierDigitVal * multiplicandDigitVal) + carry;
                        int partialProductDigitVal = product % 10;
                        carry = product / 10;
                        partialProductDigitsBuffer.append((char) ('0' + partialProductDigitVal));
                    }

                    if (carry > 0) {
                        partialProductDigitsBuffer.append((char) ('0' + carry));
                    }

                    partialProductDigits = partialProductDigitsBuffer.reverse().toString();
                    multiplierDigitToPartialProductDigitsMap.put(multiplierDigitVal, partialProductDigits);
                }

                BigInt partialProduct = new BigInt(new StringBuffer(partialProductDigits).append(partialProductTrailingZeroes));
                result = result.add(partialProduct);
            }

            partialProductTrailingZeroes.append("0");
        }

        return new BigInt(multiplicand.sign * multiplier.sign, result.digits);
    }

    public BigInt divide(BigInt denominator) {
        BigInt numerator = this;

        if (denominator.equals(ZERO)) {
            throw new RuntimeException("Cannot divide by zero.");
        }

        if (numerator.equals(ZERO)) {
            return ZERO;
        }

        if (denominator.equals(ONE)) {
            return numerator;
        }

        if (denominator.equals(NEGATIVE_ONE)) {
            return numerator.negate();
        }

        BigInt numeratorAbs = numerator.abs();
        BigInt denominatorAbs = denominator.abs();

        int numeratorToDenominatorMagnitudeCompare = numeratorAbs.compareTo(denominatorAbs);

        if (numeratorToDenominatorMagnitudeCompare < 0) {
            BigInt result = ZERO;
            result.remainder = numerator;
            return result;
        } else if (numeratorToDenominatorMagnitudeCompare == 0) {
            BigInt result = numerator.sign * denominator.sign == 1 ? ONE : NEGATIVE_ONE;
            result.remainder = ZERO;
            return result;
        }

        StringBuffer resultDigits = new StringBuffer();
        StringBuffer remainderDigits = new StringBuffer();

        for (int i = 0; i < numerator.digits.length(); i++) {
            remainderDigits.append(numerator.digits.charAt(i));
            BigInt remainder = new BigInt(remainderDigits);

            int resultDigitVal = 0;

            while (remainder.compareTo(denominatorAbs) >= 0) {
                resultDigitVal++;
                remainder = remainder.subtract(denominatorAbs);
            }

            resultDigits.append((char) ('0' + resultDigitVal));
            remainderDigits = new StringBuffer(remainder.digits);
        }

        BigInt result = new BigInt(numerator.sign / denominator.sign, resultDigits);
        result.remainder = new BigInt(numerator.sign, remainderDigits);

        return result;
    }

    public BigInt remainder(BigInt denominator) {
        return this.divide(denominator).remainder;
    }

    public BigInt abs() {
        return this.sign == -1 ? this.negate() : this;
    }

    public BigInt negate() {
        return new BigInt(-this.sign, this.digits);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BigInt other) {
            return this.compareTo(other) == 0;
        }

        return false;
    }

    @Override
    public int compareTo(BigInt other) {
        if (this.sign == other.sign) {
            if (this.sign != 0) {
                int lengthDiff = this.digits.length() - other.digits.length();

                if (lengthDiff > 0) {
                    return this.sign;
                } else if (lengthDiff < 0) {
                    return -this.sign;
                }

                for (int i = 0; i < this.digits.length(); i++) {
                    if (this.digits.charAt(i) < other.digits.charAt(i)) {
                        return -this.sign;
                    } else if (this.digits.charAt(i) > other.digits.charAt(i)) {
                        return this.sign;
                    }
                }
            }

            return 0;
        }

        return this.sign > other.sign ? 1 : -1;
    }

    @Override
    public String toString() {
        return (this.sign == -1 ? "-" : "") + this.digits;
    }
}
