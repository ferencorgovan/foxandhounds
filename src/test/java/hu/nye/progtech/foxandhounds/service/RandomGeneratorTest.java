package hu.nye.progtech.foxandhounds.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

class RandomGeneratorTest {

    private static final int BOUND = 5;
    private static final int MIN = 0;
    private RandomGenerator underTest;

    @BeforeEach
    public void setUp() {
        underTest = new RandomGenerator();
    }
    @Test
    public void testMakeRandomNumberShouldReturnNumber() {
        // when
        int result = underTest.makeRandomNumber(BOUND);
        
        // then
        assertTrue(MIN <= result && result < BOUND);
    }

    @Test
    public void testMakeRandomBoolShouldReturnBoolean() {
        // when
        boolean result = underTest.makeRandomBool();

        // then
        assertTrue(result || !result);
    }
}