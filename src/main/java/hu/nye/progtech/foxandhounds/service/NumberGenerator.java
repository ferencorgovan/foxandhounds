package hu.nye.progtech.foxandhounds.service;

import java.util.Random;

/**
 * Class for random number generation.
 */
public class NumberGenerator {

    Random random = new Random();

    public int makeRandom(int bound) {
        return random.nextInt(bound);
    }
}
