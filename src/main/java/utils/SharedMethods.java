package utils;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static utils.WaitUtils.*;

import java.util.Random;

public class SharedMethods {
    // Utility for random index
    @AndroidFindBy(id = "com.figment.pos.dev:id/okBtn")
    public static WebElement confirmBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/syncBtn")
    public static WebElement syncBtn;

    public SharedMethods() {
        // Initialize page elements with AppiumFieldDecorator
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }
    public static int getRandomIndex(int upperBound) {
        return new Random().nextInt(upperBound);
    }
    /*public static void checkForSyncPopup() {
        try {
            waitForVisibility(confirmBtn);
            confirmBtn.click();
            waitForVisibility(syncBtn);
        } catch (Exception e) {
            // If the popup is not present, we can ignore the exception
            System.out.println("Sync popup not appeared, continuing without action.");
        }
    }*/
    public void checkForSyncPopup() {
        try {
            waitForVisibility(confirmBtn);
            confirmBtn.click();
            waitForVisibility(syncBtn);
        } catch (Exception e) {
            System.out.println("Sync popup not appeared, continuing without action.");
        }
    }
}