package hu.nye.progtech.foxandhounds.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NumberGeneratorTest {

    private static final int BOUND = 5;

    private NumberGenerator underTest;

    @BeforeEach
    public void setUp() {
        underTest = new NumberGenerator();
    }
    
    @Test
    public void testShouldReturnPositiveIntegerBelowBound () {
        // when
        int result = underTest.makeRandom(BOUND);
        
        // then
    }
}