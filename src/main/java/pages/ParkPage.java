package pages;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.ScrollUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.WaitUtils.getWait;
import static utils.WaitUtils.waitForVisibility;


public class ParkPage {
    public ParkPage() {
        // Initialize page elements with AppiumFieldDecorator
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(id = "com.figment.pos.dev:id/parkedBillLayout")
    public WebElement parkBtn;

    public List<WebElement> getAllParkedOrders() {
        List<WebElement> orderList = new ArrayList<>();
        List<WebElement> orders = DriverManager.getDriver().findElements(
                By.xpath("(//androidx.cardview.widget.CardView[@resource-id=\"com.figment.pos.dev:id/card\"])"));
        if (orders.isEmpty()) {
            System.out.println("No parked orders found.");
        }
        for (int i = 1; i <= orders.size(); i++) {
            String indexedXPath = "(//androidx.cardview.widget.CardView[@resource-id=\"com.figment.pos.dev:id/card\"])[" + i + "]";
            WebElement orderElement = DriverManager.getDriver().findElement(By.xpath(indexedXPath));
            orderList.add(orderElement);
        }
        return orderList;
    }
}
