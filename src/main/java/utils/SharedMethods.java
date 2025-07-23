package utils;

import java.util.Random;

public class SharedMethods {
    // Utility for random index
    public static int getRandomIndex(int upperBound) {
        return new Random().nextInt(upperBound);
    }
}