package utils;

import drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    public static WebDriverWait getWait(int time) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(time));
    }
    public static WebElement waitForVisibility(WebElement element) {
        return getWait(10)
                .until(ExpectedConditions.visibilityOf(element));
    }
}
