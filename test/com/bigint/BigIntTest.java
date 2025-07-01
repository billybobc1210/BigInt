package com.bigint;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BigIntTest {
    @Test
    public void constructFromIntegerValueTest() {
        BigInt b = new BigInt(0);
        assertEquals(BigInt.ZERO, b);

        b = new BigInt(1);
        assertEquals(BigInt.ONE, b);

        b = new BigInt(-1);
        assertEquals(BigInt.NEGATIVE_ONE, b);

        b = new BigInt(12345);
        assertEquals(new BigInt("12345"), b);

        b = new BigInt(-12345);
        assertEquals(new BigInt("-12345"), b);
    }

    @Test
    public void constructFromLongValueTest() {
        BigInt b = new BigInt(0L);
        assertEquals(BigInt.ZERO, b);

        b = new BigInt(1L);
        assertEquals(BigInt.ONE, b);

        b = new BigInt(-1L);
        assertEquals(BigInt.NEGATIVE_ONE, b);

        b = new BigInt(1234567890123456789L);
        assertEquals(new BigInt("1234567890123456789"), b);

        b = new BigInt(-1234567890123456789L);
        assertEquals(new BigInt("-1234567890123456789"), b);
    }

    @Test
    public void constructionFromAllZeroesInStringTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt("000000000000");
        } catch (Exception ex) {
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void constructionFromAllZeroesWithLeadingNegativeSignInStringTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt("-000000000000");
        } catch (Exception ex) {
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void constructionFromSingleZeroTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt("0");
            assertEquals(BigInt.ZERO, b);
        } catch (Exception ex) {
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void constructionFromSingleZeroWithLeadingNegativeSignTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt("-0");
            assertEquals(BigInt.ZERO, b);
        } catch (Exception ex) {
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void constructionFromValidPositiveIntegerTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt("12345");
        } catch (Exception ex) {
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void constructionFromValidNegativeIntegerTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt("-12345");
        } catch (Exception ex) {
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void constructionFailureFromNullStringTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt((String) null);
        } catch (Exception ex) {
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void constructionFailureFromEmptyStringTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt("");
        } catch (Exception ex) {
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void constructionFailureOnlyNegativeSignInStringTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt("-");
        } catch (Exception ex) {
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void constructionFailureFromLeadingSpacesInStringTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt(" 12345");
        } catch (Exception ex) {
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void constructionFailureFromInvalidCharactersInStringTest() {
        boolean success = true;

        try {
            BigInt b = new BigInt("123abc45");
        } catch (Exception ex) {
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void negatePositiveTest() {
        BigInt b = new BigInt("12345");
        assertEquals(new BigInt("-12345"), b.negate());
    }

    @Test
    public void negateNegativeTest() {
        BigInt b = new BigInt("-12345");
        assertEquals(new BigInt("12345"), b.negate());
    }

    @Test
    public void negateZeroTest() {
        BigInt b = BigInt.ZERO;
        assertEquals(BigInt.ZERO, b.negate());
    }

    @Test
    public void addPositiveToPositiveTest1() {
        BigInt b1 = new BigInt("11111");
        BigInt b2 = new BigInt("22222");
        assertEquals(new BigInt("33333"), b1.add(b2));
    }

    @Test
    public void addPositiveToPositiveTest2() {
        BigInt b1 = new BigInt("11111");
        BigInt b2 = new BigInt("222");
        assertEquals(new BigInt("11333"), b1.add(b2));
    }

    @Test
    public void addPositiveToPositiveTest3() {
        BigInt b1 = new BigInt("111");
        BigInt b2 = new BigInt("22222");
        assertEquals(new BigInt("22333"), b1.add(b2));
    }

    @Test
    public void addPositiveToPositiveTest4() {
        BigInt b1 = new BigInt("88888");
        BigInt b2 = new BigInt("99999");
        assertEquals(new BigInt("188887"), b1.add(b2));
    }

    @Test
    public void addPositiveToPositiveTest5() {
        BigInt b1 = new BigInt("829735293293577235");
        BigInt b2 = new BigInt("2935872938523985");
        assertEquals(new BigInt("832671166232101220"), b1.add(b2));
    }

    @Test
    public void addNegativeToNegativeTest1() {
        BigInt b1 = new BigInt("-11111");
        BigInt b2 = new BigInt("-22222");
        assertEquals(new BigInt("-33333"), b1.add(b2));
    }

    @Test
    public void addNegativeToNegativeTest2() {
        BigInt b1 = new BigInt("-11111");
        BigInt b2 = new BigInt("-222");
        assertEquals(new BigInt("-11333"), b1.add(b2));
    }

    @Test
    public void addNegativeToNegativeTest3() {
        BigInt b1 = new BigInt("-111");
        BigInt b2 = new BigInt("-22222");
        assertEquals(new BigInt("-22333"), b1.add(b2));
    }

    @Test
    public void addNegativeToNegativeTest4() {
        BigInt b1 = new BigInt("-88888");
        BigInt b2 = new BigInt("-99999");
        assertEquals(new BigInt("-188887"), b1.add(b2));
    }

    @Test
    public void addNegativeToNegativeTest5() {
        BigInt b1 = new BigInt("-829735293293577235");
        BigInt b2 = new BigInt("-2935872938523985");
        assertEquals(new BigInt("-832671166232101220"), b1.add(b2));
    }

    @Test
    public void addNegativeToPositiveTest() {
        BigInt b1 = new BigInt("3136654372");
        BigInt b2 = new BigInt("-7342768707");
        assertEquals(new BigInt("-4206114335"), b1.add(b2));
    }

    @Test
    public void addPositiveToNegativeTest() {
        BigInt b1 = new BigInt("-7342768707");
        BigInt b2 = new BigInt("3136654372");
        assertEquals(new BigInt("-4206114335"), b1.add(b2));
    }

    @Test
    public void addNegativeToNegativeTest() {
        BigInt b1 = new BigInt("-7342768707");
        BigInt b2 = new BigInt("-3136654372");
        assertEquals(new BigInt("-10479423079"), b1.add(b2));
    }

    @Test
    public void addZeroToPositiveTest1() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = new BigInt("9238472234234");
        assertEquals(new BigInt("9238472234234"), b1.add(b2));
    }

    @Test
    public void addZeroToPositiveTest2() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = BigInt.ZERO;
        assertEquals(new BigInt("9238472234234"), b1.add(b2));
    }

    @Test
    public void addZeroToNegativeTest1() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = new BigInt("-9238472234234");
        assertEquals(new BigInt("-9238472234234"), b1.add(b2));
    }

    @Test
    public void addZeroToNegativeTest2() {
        BigInt b1 = new BigInt("-9238472234234");
        BigInt b2 = BigInt.ZERO;
        assertEquals(new BigInt("-9238472234234"), b1.add(b2));
    }

    @Test
    public void subtractPositiveFromZeroTest() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = new BigInt("9238472234234");
        assertEquals(new BigInt("-9238472234234"), b1.subtract(b2));
    }

    @Test
    public void subtractZeroFromPositiveTest() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = BigInt.ZERO;
        assertEquals(new BigInt("9238472234234"), b1.add(b2));
    }

    @Test
    public void subtractNegativeFromZeroTest() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = new BigInt("-9238472234234");
        assertEquals(new BigInt("9238472234234"), b1.subtract(b2));
    }

    @Test
    public void subtractZeroFromNegativeTest() {
        BigInt b1 = new BigInt("-9238472234234");
        BigInt b2 = BigInt.ZERO;
        assertEquals(new BigInt("-9238472234234"), b1.add(b2));
    }

    @Test
    public void subtractPositiveFromBiggerPositiveTest() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = new BigInt("9387529385");
        assertEquals(new BigInt("9229084704849"), b1.subtract(b2));
    }

    @Test
    public void subtractPositiveFromSmallerPositiveTest() {
        BigInt b1 = new BigInt("9387529385");
        BigInt b2 = new BigInt("9238472234234");
        assertEquals(new BigInt("-9229084704849"), b1.subtract(b2));
    }

    @Test
    public void subtractNegativeFromPositiveTest() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = new BigInt("-6785697976");
        assertEquals(new BigInt("9245257932210"), b1.subtract(b2));
    }

    @Test
    public void subtractPositiveFromNegativeTest() {
        BigInt b1 = new BigInt("-6785697976");
        BigInt b2 = new BigInt("9238472234234");
        assertEquals(new BigInt("-9245257932210"), b1.subtract(b2));
    }

    @Test
    public void subtractNegativeFromNegativeTest1() {
        BigInt b1 = new BigInt("-123");
        BigInt b2 = new BigInt("-123");
        assertEquals(BigInt.ZERO, b1.subtract(b2));
    }

    @Test
    public void subtractNegativeFromNegativeTest2() {
        BigInt b1 = new BigInt("-123");
        BigInt b2 = new BigInt("-124");
        assertEquals(BigInt.ONE, b1.subtract(b2));
    }

    @Test
    public void subtractNegativeFromNegativeTest3() {
        BigInt b1 = new BigInt("-5");
        BigInt b2 = new BigInt("-3");
        assertEquals(new BigInt("-2"), b1.subtract(b2));
    }

    @Test
    public void subtractNegativeFromNegativeTest4() {
        BigInt b1 = new BigInt("-5");
        BigInt b2 = new BigInt("-7");
        assertEquals(new BigInt("2"), b1.subtract(b2));
    }

    @Test
    public void subtractWhereBorrowFromZeroRequiredTest() {
        BigInt b1 = new BigInt("1200000004");
        BigInt b2 = new BigInt("9999");
        assertEquals(new BigInt("1199990005"), b1.subtract(b2));
    }

    @Test
    public void subtractEqualsTest1() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = new BigInt("9238472234234");
        assertEquals(BigInt.ZERO, b1.subtract(b2));
    }

    @Test
    public void subtractEqualsTest2() {
        BigInt b1 = new BigInt("-9238472234234");
        BigInt b2 = new BigInt("-9238472234234");
        assertEquals(BigInt.ZERO, b1.subtract(b2));
    }

    @Test
    public void subtractEqualsTest3() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = BigInt.ZERO;
        assertEquals(BigInt.ZERO, b1.subtract(b2));
    }

    @Test
    public void multiplyPositiveByZeroTest() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = BigInt.ZERO;
        assertEquals(BigInt.ZERO, b1.multiply(b2));
    }

    @Test
    public void multiplyZeroByPositiveTest() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = new BigInt("9238472234234");
        assertEquals(BigInt.ZERO, b1.multiply(b2));
    }

    @Test
    public void multiplyZeroByZeroTest() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = BigInt.ZERO;
        assertEquals(BigInt.ZERO, b1.multiply(b2));
    }

    @Test
    public void multiplyNegativeByZeroTest() {
        BigInt b1 = new BigInt("-9238472234234");
        BigInt b2 = BigInt.ZERO;
        assertEquals(BigInt.ZERO, b1.multiply(b2));
    }

    @Test
    public void multiplyZeroByNegativeTest() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = new BigInt("-9238472234234");
        assertEquals(BigInt.ZERO, b1.multiply(b2));
    }

    @Test
    public void multiplyPositiveByOneTest() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = BigInt.ONE;
        assertEquals(b1, b1.multiply(b2));
    }

    @Test
    public void multiplyOneByPositiveTest() {
        BigInt b1 = BigInt.ONE;
        BigInt b2 = new BigInt("9238472234234");
        assertEquals(b2, b1.multiply(b2));
    }

    @Test
    public void multiplyOneByOneTest() {
        BigInt b1 = BigInt.ONE;
        BigInt b2 = BigInt.ONE;
        assertEquals(BigInt.ONE, b1.multiply(b2));
    }

    @Test
    public void multiplyNegativeByOneTest() {
        BigInt b1 = new BigInt("-9238472234234");
        BigInt b2 = BigInt.ONE;
        assertEquals(b1, b1.multiply(b2));
    }

    @Test
    public void multiplyOneByNegativeTest() {
        BigInt b1 = BigInt.ONE;
        BigInt b2 = new BigInt("-9238472234234");
        assertEquals(b2, b1.multiply(b2));
    }

    @Test
    public void multiplyPositiveByNegativeOneTest() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = BigInt.NEGATIVE_ONE;
        assertEquals(b1.negate(), b1.multiply(b2));
    }

    @Test
    public void multiplyNegativeOneByPositiveTest() {
        BigInt b1 = BigInt.NEGATIVE_ONE;
        BigInt b2 = new BigInt("9238472234234");
        assertEquals(b2.negate(), b1.multiply(b2));
    }

    @Test
    public void multiplyNegativeOneByNegativeOneTest() {
        BigInt b1 = BigInt.NEGATIVE_ONE;
        BigInt b2 = BigInt.NEGATIVE_ONE;
        assertEquals(BigInt.ONE, b1.multiply(b2));
    }

    @Test
    public void multiplyNegativeByNegativeOneTest() {
        BigInt b1 = new BigInt("-9238472234234");
        BigInt b2 = BigInt.NEGATIVE_ONE;
        assertEquals(b1.negate(), b1.multiply(b2));
    }

    @Test
    public void multiplyNegativeOneByNegativeTest() {
        BigInt b1 = BigInt.NEGATIVE_ONE;
        BigInt b2 = new BigInt("-9238472234234");
        assertEquals(b2.negate(), b1.multiply(b2));
    }

    @Test
    public void multiplyPositiveByPositiveTest() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = new BigInt("32947012");
        assertEquals(new BigInt("304380055562974408808"), b1.multiply(b2));

    }

    @Test
    public void multiplyPositiveByNegativeTest() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = new BigInt("-32947012");
        assertEquals(new BigInt("-304380055562974408808"), b1.multiply(b2));
    }

    @Test
    public void multiplyNegativeByPositiveTest() {
        BigInt b1 = new BigInt("-9238472234234");
        BigInt b2 = new BigInt("32947012");
        assertEquals(new BigInt("-304380055562974408808"), b1.multiply(b2));
    }

    @Test
    public void multiplyNegativeByNegativeTest() {
        BigInt b1 = new BigInt("-9238472234234");
        BigInt b2 = new BigInt("-32947012");
        assertEquals(new BigInt("304380055562974408808"), b1.multiply(b2));
    }

    @Test
    public void dividePositiveByZeroTest() {
        boolean success = true;

        try {
            BigInt b1 = new BigInt("9238472234234");
            BigInt b2 = BigInt.ZERO;
            BigInt test = b1.divide(b2);
        } catch (Exception ex) {
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void divideZeroByZeroTest() {
        boolean success = true;

        try {
            BigInt b1 = BigInt.ZERO;
            BigInt b2 = BigInt.ZERO;
            BigInt test = b1.divide(b2);
        } catch (Exception ex) {
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void divideNegativeByZeroTest() {
        boolean success = true;

        try {
            BigInt b1 = new BigInt("-9238472234234");
            BigInt b2 = BigInt.ZERO;
            BigInt test = b1.divide(b2);
        } catch (Exception ex) {
            success = false;
        }

        assertFalse(success);
    }

    @Test
    public void divideZeroByPositiveTest() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = new BigInt("9238472234234");
        assertEquals(BigInt.ZERO, b1.divide(b2));
    }

    @Test
    public void divideZeroByNegativeTest() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = new BigInt("-9238472234234");
        assertEquals(BigInt.ZERO, b1.divide(b2));
    }

    @Test
    public void dividePositiveByOneTest() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = BigInt.ONE;
        assertEquals(b1, b1.divide(b2));
    }

    @Test
    public void divideNegativeByOneTest() {
        BigInt b1 = new BigInt("-9238472234234");
        BigInt b2 = BigInt.ONE;
        assertEquals(b1, b1.divide(b2));
    }

    @Test
    public void dividePositiveByNegativeOneTest() {
        BigInt b1 = new BigInt("9238472234234");
        BigInt b2 = BigInt.NEGATIVE_ONE;
        assertEquals(b1.negate(), b1.divide(b2));
    }

    @Test
    public void divideNegativeByNegativeOneTest() {
        BigInt b1 = new BigInt("-9238472234234");
        BigInt b2 = BigInt.NEGATIVE_ONE;
        assertEquals(b1.negate(), b1.divide(b2));
    }

    @Test
    public void divideSmallerByBiggerTest() {
        BigInt b1 = new BigInt("123");
        BigInt b2 = new BigInt("10000");
        assertEquals(BigInt.ZERO, b1.divide(b2));
    }

    @Test
    public void dividePositiveByPositiveTest() {
        //56812023881611315081 / 8205618 = 6923552117782, remainder: 1215805
        BigInt b1 = new BigInt("56812023881611315081");
        BigInt b2 = new BigInt("8205618");
        BigInt result = b1.divide(b2);
        assertEquals(new BigInt("6923552117782"), result);
    }

    @Test
    public void dividesEvenlyTest() {
        BigInt b1 = new BigInt("10");
        BigInt b2 = new BigInt("2");
        BigInt result = b1.divide(b2);
        assertEquals(new BigInt("5"), result);
    }

    @Test
    public void divideBySameAbsoluteValueTest() {
        BigInt b1 = new BigInt("56812023881611315081");
        BigInt b2 = new BigInt("56812023881611315081");
        BigInt result = b1.divide(b2);
        assertEquals(BigInt.ONE, result);

        b1 = new BigInt("-56812023881611315081");
        b2 = new BigInt("-56812023881611315081");
        result = b1.divide(b2);
        assertEquals(BigInt.ONE, result);

        b1 = new BigInt("-56812023881611315081");
        b2 = new BigInt("56812023881611315081");
        result = b1.divide(b2);
        assertEquals(BigInt.NEGATIVE_ONE, result);

        b1 = new BigInt("56812023881611315081");
        b2 = new BigInt("-56812023881611315081");
        result = b1.divide(b2);
        assertEquals(BigInt.NEGATIVE_ONE, result);
    }

    @Test
    public void dividesEvenlyModulusTest() {
        BigInt b1 = new BigInt("10");
        BigInt b2 = new BigInt("2");
        BigInt result = b1.modulus(b2);
        assertEquals(BigInt.ZERO, result);
    }

    @Test
    public void divideBySameAbsoluteValueModulusTest() {
        BigInt b1 = new BigInt("56812023881611315081");
        BigInt b2 = new BigInt("56812023881611315081");
        BigInt result = b1.modulus(b2);
        assertEquals(BigInt.ZERO, result);

        b1 = new BigInt("-56812023881611315081");
        b2 = new BigInt("-56812023881611315081");
        result = b1.modulus(b2);
        assertEquals(BigInt.ZERO, result);

        b1 = new BigInt("-56812023881611315081");
        b2 = new BigInt("56812023881611315081");
        result = b1.modulus(b2);
        assertEquals(BigInt.ZERO, result);

        b1 = new BigInt("56812023881611315081");
        b2 = new BigInt("-56812023881611315081");
        result = b1.modulus(b2);
        assertEquals(BigInt.ZERO, result);
    }

    @Test
    public void modulusNegativeNumbersTest() {
        // -36/5 = -7, rem = -1  =>  (-7 x 5) - 1 = -36
        BigInt b1 = new BigInt("-36");
        BigInt b2 = new BigInt("5");
        BigInt result = b1.modulus(b2);
        assertEquals(BigInt.NEGATIVE_ONE, result);

        //  36/-5 = -7, rem =  1  =>  (-7 x -5) + 1 =  36
        b1 = new BigInt("36");
        b2 = new BigInt("-5");
        result = b1.modulus(b2);
        assertEquals(new BigInt("1"), result);

        // -36/-5 =  7, rem = -1  =>  7 × -5 - 1 = -36
        b1 = new BigInt("-36");
        b2 = new BigInt("-5");
        result = b1.modulus(b2);
        assertEquals(BigInt.NEGATIVE_ONE, result);
    }

    @Test
    public void dividePositiveByPositiveTest2() {
        BigInt b1 = new BigInt("123");
        BigInt b2 = new BigInt("7");
        assertEquals(new BigInt("17"), b1.divide(b2));
    }

    @Test
    public void compareTest1() {
        BigInt b1 = new BigInt("123");
        BigInt b2 = new BigInt("123");
        assertEquals(0, b1.compareTo(b2));
    }

    @Test
    public void compareTest2() {
        BigInt b1 = new BigInt("-123");
        BigInt b2 = new BigInt("123");
        assertEquals(-1, b1.compareTo(b2));
    }

    @Test
    public void compareTest3() {
        BigInt b1 = new BigInt("123");
        BigInt b2 = new BigInt("-123");
        assertEquals(1, b1.compareTo(b2));
    }

    @Test
    public void compareTest4() {
        BigInt b1 = new BigInt("-123");
        BigInt b2 = new BigInt("-123");
        assertEquals(0, b1.compareTo(b2));
    }

    @Test
    public void compareTest5() {
        BigInt b1 = new BigInt("-456");
        BigInt b2 = new BigInt("-123");
        assertEquals(-1, b1.compareTo(b2));
    }

    @Test
    public void compareTest6() {
        BigInt b1 = new BigInt("-123");
        BigInt b2 = new BigInt("-456");
        assertEquals(1, b1.compareTo(b2));
    }

    @Test
    public void compareTest7() {
        BigInt b1 = new BigInt("1234");
        BigInt b2 = new BigInt("123");
        assertEquals(1, b1.compareTo(b2));
    }

    @Test
    public void compareTest8() {
        BigInt b1 = new BigInt("123");
        BigInt b2 = new BigInt("1234");
        assertEquals(-1, b1.compareTo(b2));
    }

    @Test
    public void compareTest9() {
        BigInt b1 = new BigInt("-1234");
        BigInt b2 = new BigInt("-123");
        assertEquals(-1, b1.compareTo(b2));
    }

    @Test
    public void compareTest10() {
        BigInt b1 = new BigInt("-123");
        BigInt b2 = new BigInt("-1234");
        assertEquals(1, b1.compareTo(b2));
    }

    @Test
    public void compareTest11() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = new BigInt("1234");
        assertEquals(-1, b1.compareTo(b2));
    }

    @Test
    public void compareTest12() {
        BigInt b1 = BigInt.ZERO;
        BigInt b2 = new BigInt("-1234");
        assertEquals(1, b1.compareTo(b2));
    }

    @Test
    public void compareTest13() {
        BigInt b1 = new BigInt("1234");
        BigInt b2 = BigInt.ZERO;
        assertEquals(-1, b1.compareTo(b2));
    }

    @Test
    public void compareTest14() {
        BigInt b1 = new BigInt("-1234");
        BigInt b2 = BigInt.ZERO;
        assertEquals(1, b1.compareTo(b2));
    }

    @Test
    public void absoluteValueTest1() {
        BigInt b = new BigInt("123");
        assertEquals(new BigInt("123"), b.abs());
    }

    @Test
    public void absoluteValueTest2() {
        BigInt b = BigInt.ZERO;
        assertEquals(BigInt.ZERO, b.abs());
    }

    @Test
    public void absoluteValueTest3() {
        BigInt b = new BigInt("-123");
        assertEquals(new BigInt("123"), b.abs());
    }

    @Test
    public void equalityTest1() {
        BigInt b1 = new BigInt("123");
        BigInt b2 = new BigInt("123");
        assertEquals(b1, b2);
    }

    @Test
    public void equalityTest2() {
        BigInt b1 = new BigInt("-123");
        BigInt b2 = new BigInt("123");
        assertNotEquals(b1, b2);
    }

    @Test
    public void equalityTest3() {
        BigInt b1 = new BigInt("123");
        BigInt b2 = new BigInt("-123");
        assertNotEquals(b1, b2);
    }

    @Test
    public void equalityTest4() {
        BigInt b1 = new BigInt("-123");
        BigInt b2 = new BigInt("-123");
        assertEquals(b1, b2);
    }

    @Test
    public void equalityTest5() {
        BigInt b1 = new BigInt("-456");
        BigInt b2 = new BigInt("-123");
        assertNotEquals(b1, b2);
    }

    @Test
    public void equalityTest6() {
        BigInt b1 = new BigInt("-123");
        BigInt b2 = new BigInt("-456");
        assertNotEquals(b1, b2);
    }

    @Test
    public void equalityTest7() {
        BigInt b1 = new BigInt("1234");
        BigInt b2 = new BigInt("123");
        assertNotEquals(b1, b2);
    }

    @Test
    public void equalityTest8() {
        BigInt b1 = new BigInt("123");
        BigInt b2 = new BigInt("1234");
        assertNotEquals(b1, b2);
    }

    @Test
    public void equalityTest9() {
        BigInt b1 = new BigInt("-1234");
        BigInt b2 = new BigInt("-123");
        assertNotEquals(b1, b2);
    }

    @Test
    public void equalityTest10() {
        BigInt b1 = new BigInt("-123");
        BigInt b2 = new BigInt("-1234");
        assertNotEquals(b1, b2);
    }
}