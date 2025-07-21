package utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

public class SwipeUtils {

    public static void swipeLeft(AndroidDriver driver) {
        performSwipe(driver, true);
    }

    public static void swipeRight(AndroidDriver driver) {
        performSwipe(driver, false);
    }

    private static void performSwipe(AndroidDriver driver, boolean toLeft) {
        Dimension size = driver.manage().window().getSize();

        int startX = (int) (size.width * (toLeft ? 0.8 : 0.2));
        int endX   = (int) (size.width * (toLeft ? 0.2 : 0.8));
        int y      = size.height / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, y));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, y));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }
}
