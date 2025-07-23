// File: src/main/java/utils/ActionUtils.java
package utils;

import Interfaces.ThrowingRunnable;

public class ActionUtils {
    public static void runMultipleTimes(int count, ThrowingRunnable action) throws Exception {
        for (int i = 0; i < count; i++) {
            action.run();
        }
    }
}