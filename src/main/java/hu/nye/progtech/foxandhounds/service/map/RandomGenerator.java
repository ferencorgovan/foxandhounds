package hu.nye.progtech.foxandhounds.service.map;

import java.util.Random;

/**
 * Class for random number generation.
 */
public class RandomGenerator {

    Random random = new Random();

    public int makeRandomNumber(int bound) {
        return random.nextInt(bound);
    }

    public boolean makeRandomBool() {
        return random.nextBoolean();
    }
}
