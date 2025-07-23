// File: src/main/java/utils/ThrowingRunnable.java
package Interfaces;

@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Exception;
}