package hu.nye.progtech.foxandhounds.service.map;

import hu.nye.progtech.foxandhounds.service.map.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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