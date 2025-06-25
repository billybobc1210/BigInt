package com.bigint;

public class BigInt implements Comparable<BigInt> {
    public static final BigInt ZERO = new BigInt("0");
    public static final BigInt ONE = new BigInt("1");
    public static final BigInt NEGATIVE_ONE = ONE.negate();

    private int sign = 0;
    private String digits = "";
    private BigInt remainder = null;

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
        if (this.equals(ZERO)) {
            return addend;
        }

        if (addend.equals(ZERO)) {
            return this;
        }

        // The strategy for both addition and subtraction is to reorder operands and use negation to convert
        // the original expression to an equivalent expression of one of the following two forms:
        //
        //   positive + positive
        //   positive - positive (where first operand > second operand)
        //
        // Once in one of these two forms it's easy to just perform the operation digit by digit starting on the
        // right.  The following table describes the conversions that take place for addition.  A1 represents 'this'
        // and A2 represents the 'addend' parameter passed to the method.
        //
        // A1 A2
        //  +  +  =>  A1 + A2
        //  +  -  =>  A1 - -A2
        //  -  +  =>  A2 - -A1
        //  -  -  =>  -(-A1 + -A2)
        if ((this.sign == 1) && (addend.sign == 1)) {
            String addendDigits1 = this.digits;
            String addendDigits2 = addend.digits;
            int lengthDiff = addendDigits1.length() - addendDigits2.length();

            if (lengthDiff > 0) {
                addendDigits2 = "0".repeat(lengthDiff) + addendDigits2;
            } else if (lengthDiff < 0) {
                addendDigits1 = "0".repeat(-lengthDiff) + addendDigits1;
            }

            StringBuffer resultDigits = new StringBuffer();
            int carry = 0;

            for (int i = addendDigits1.length() - 1; i >= 0; i--) {
                int addendDigitVal1 = addendDigits1.charAt(i) - '0';
                int addendDigitVal2 = addendDigits2.charAt(i) - '0';
                int sum = addendDigitVal1 + addendDigitVal2 + carry;
                int resultDigitVal = (sum % 10);
                carry = sum / 10;
                resultDigits.append((char) ('0' + resultDigitVal));
            }

            if (carry > 0) {
                resultDigits.append((char) ('0' + carry));
            }

            return new BigInt((this.sign < 0 ? "-" : "") + resultDigits.reverse());
        } else if ((this.sign == 1) && (addend.sign == -1)) {
            return addend.subtract(addend.negate());
        } else if ((this.sign == -1) && (addend.sign == 1)) {
            return addend.subtract(this.negate());
        }

        // ((sign == -1) && (addend.sign == -1)) {
        return this.negate().add(addend.negate()).negate();
    }

    public BigInt subtract(BigInt subtrahend) {
        if (this.equals(ZERO)) {
            return subtrahend.negate();
        }

        if (subtrahend.equals(ZERO)) {
            return this;
        }

        if (this.equals(subtrahend)) {
            return ZERO;
        }

        // The strategy for both addition and subtraction is to reorder operands and use negation to convert
        // the original expression to an equivalent expression of one of the following two forms:
        //
        //   positive + positive
        //   positive - positive (where first operand > second operand)
        //
        // Once in one of these two forms it's easy to just perform the operation digit by digit starting on the
        // right.  The following table describes the conversions that take place for subtraction.  M represents 'this'
        // (the minuend) and S represents the 'subtrahend' parameter passed to the method.
        //
        // M S
        // + +  =>  M >= S ? M - S : -(S - M)
        // + -  =>  M + -S
        // - +  =>  -(-M + S)
        // - -  =>  -(-M - -S)
        if ((this.sign == 1) && (subtrahend.sign == 1)) {
            if (this.compareTo(subtrahend) < 0) {
                return subtrahend.subtract(this).negate();
            }

            // At this point, both minuend and subtrahend are guaranteed to be positive
            // with minuend >= subtrahend.  Therefore, we can just add leading zeroes to
            // subtrahend if necessary and start doing the subtraction algorithm digit by
            // digit, starting from the right.
            String minuendDigits = this.digits;
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
        } else if ((this.sign == 1) && (subtrahend.sign == -1)) {
            return this.add(subtrahend.negate());
        } else if ((this.sign == -1) && (subtrahend.sign == 1)) {
            return this.negate().add(subtrahend).negate();
        }

        // ((this.sign == -1) && (subtrahend.sign == -1)) {
        return this.negate().subtract(subtrahend.negate()).negate();
    }

    public BigInt multiply(BigInt multiplier) {
        if (this.equals(ZERO) || multiplier.equals(ZERO)) {
            return ZERO;
        }

        if (this.equals(ONE)) {
            return multiplier;
        }

        if (multiplier.equals(ONE)) {
            return this;
        }

        if (this.equals(NEGATIVE_ONE)) {
            return multiplier.negate();
        }

        if (multiplier.equals(NEGATIVE_ONE)) {
            return this.negate();
        }

        BigInt result = ZERO;
        StringBuffer partialSumTrailingZeroes = new StringBuffer();

        for (int i = multiplier.digits.length() - 1; i >= 0; i--) {
            int multiplierDigitVal = multiplier.digits.charAt(i) - '0';
            StringBuffer partialSumDigits = new StringBuffer();

            if (multiplierDigitVal == 0) {
                partialSumDigits.append("0");
            } else if (multiplierDigitVal == 1)  {
                partialSumDigits.append(this.digits);
            } else {
                int carry = 0;

                for (int j = this.digits.length() - 1; j >= 0; j--) {
                    int multiplicandDigitVal = this.digits.charAt(j) - '0';
                    int product = (multiplierDigitVal * multiplicandDigitVal) + carry;
                    int resultDigitVal = product % 10;
                    carry = product / 10;
                    partialSumDigits.append((char) ('0' + resultDigitVal));
                }

                if (carry > 0) {
                    partialSumDigits.append((char) ('0' + carry));
                }

                partialSumDigits = partialSumDigits.reverse();
            }

            BigInt partialSum = new BigInt(partialSumDigits.append(partialSumTrailingZeroes));
            result = result.add(partialSum);
            partialSumTrailingZeroes.append("0");
        }

        result = new BigInt((this.sign * multiplier.sign == -1 ? "-" : "") + result.digits);

        return result;
    }

    public BigInt divide(BigInt divisor) {
        if (divisor.equals(ZERO)) {
            throw new RuntimeException("Cannot divide by zero.");
        }

        if (this.equals(ZERO)) {
            return ZERO;
        }

        if (divisor.equals(ONE)) {
            return this;
        }

        if (divisor.equals(NEGATIVE_ONE)) {
            return this.negate();
        }

        BigInt dividendAbs = this.abs();
        BigInt divisorAbs = divisor.abs();

        int dividendToDivisorMagnitudeCompare = dividendAbs.compareTo(divisorAbs);

        if (dividendToDivisorMagnitudeCompare < 0) {
            BigInt result = ZERO;
            result.remainder = this;
            return result;
        } else if (dividendToDivisorMagnitudeCompare == 0) {
            BigInt result = this.sign == divisor.sign ? ONE : NEGATIVE_ONE;
            result.remainder = ZERO;
            return result;
        }

        StringBuffer resultDigits = new StringBuffer();
        StringBuffer remainderDigits = new StringBuffer();
        BigInt remainder = null;

        for (int i = 0; i < dividendAbs.digits.length(); i++) {
            remainderDigits.append(dividendAbs.digits.charAt(i));
            remainder = new BigInt(remainderDigits);

            int resultDigitVal = 0;
            BigInt tmp = remainder.subtract(divisorAbs);

            while (tmp.compareTo(ZERO) >= 0) {
                resultDigitVal++;
                remainder = tmp;
                tmp = remainder.subtract(divisorAbs);
            }

            resultDigits.append((char) ('0' + resultDigitVal));
            remainderDigits = new StringBuffer(remainder.digits);
        }

        if ((remainder != null) && !remainder.equals(ZERO)) {
            remainder = new BigInt((this.sign == -1 ? "-" : "") + remainder.digits);
        }

        BigInt result = new BigInt((this.sign * divisor.sign == -1 ? "-" : "") + resultDigits);
        result.remainder = remainder;

        return result;
    }

    public BigInt modulus(BigInt divisor) {
        BigInt quotient = this.divide(divisor);

        return quotient.remainder;
    }

    public BigInt abs() {
        return this.sign == -1 ? this.negate() : this;
    }

    public BigInt negate() {
        if (this.sign == 1) {
            return new BigInt("-" + this.digits);
        } else {
            return new BigInt(this.digits);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BigInt b) {
            return this.compareTo(b) == 0;
        }

        return false;
    }

    @Override
    public int compareTo(BigInt b) {
        String digits1 = this.digits;
        String digits2 = b.digits;

        if (this.sign == b.sign) {
            if (digits1.equals(digits2)) {
                return 0;
            }

            if (this.sign == -1) {
                String tmp = digits1;
                digits1 = digits2;
                digits2 = tmp;
            }

            int lengthDiff = digits1.length() - digits2.length();

            if (lengthDiff > 0) {
                digits2 = "0".repeat(lengthDiff) + digits2;
            } else if (lengthDiff < 0) {
                digits1 = "0".repeat(-lengthDiff) + digits1;
            }

            for (int i = 0; i < digits1.length(); i++) {
                if (digits1.charAt(i) < digits2.charAt(i)) {
                    return -1;
                } else if (digits1.charAt(i) > digits2.charAt(i)) {
                    return 1;
                }
            }
        } else if (this.sign == -1) {
            return -1;
        }

        return 1;
    }

    @Override
    public String toString() {
        return (this.sign == -1 ? "-" : "") + this.digits;
    }
}
