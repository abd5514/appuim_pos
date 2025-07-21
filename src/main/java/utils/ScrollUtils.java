package utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

public class ScrollUtils {

    public static void scrollUp(AndroidDriver driver) {
        performScroll(driver, Direction.UP);
    }

    public static void scrollDown(AndroidDriver driver) {
        performScroll(driver, Direction.DOWN);
    }

    public static void scrollLeft(AndroidDriver driver) {
        performScroll(driver, Direction.LEFT);
    }

    public static void scrollRight(AndroidDriver driver) {
        performScroll(driver, Direction.RIGHT);
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private static void performScroll(AndroidDriver driver, Direction direction) {
        Dimension size = driver.manage().window().getSize();

        int startX, endX, startY, endY;

        int width = size.width;
        int height = size.height;

        switch (direction) {
            case UP:
                startX = width / 2;
                startY = (int) (height * 0.3);
                endX = startX;
                endY = (int) (height * 0.7);
                break;
            case DOWN:
                startX = width / 2;
                startY = (int) (height * 0.7);
                endX = startX;
                endY = (int) (height * 0.3);
                break;
            case LEFT:
                startY = height / 2;
                startX = (int) (width * 0.7);
                endY = startY;
                endX = (int) (width * 0.3);
                break;
            case RIGHT:
                startY = height / 2;
                startX = (int) (width * 0.3);
                endY = startY;
                endX = (int) (width * 0.7);
                break;
            default:
                return;
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scroll = new Sequence(finger, 1);

        scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scroll.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
        scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(scroll));
    }
}