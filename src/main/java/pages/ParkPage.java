package pages;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.SharedMethods;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.SharedMethods.getRandomIndex;
import static utils.WaitUtils.waitForVisibility;


public class ParkPage {
    public ParkPage() {
        // Initialize page elements with AppiumFieldDecorator
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(id = "com.figment.pos.dev:id/parkedBillLayout")
    public WebElement parkBtn;
    @AndroidFindBy(xpath = "(//androidx.cardview.widget.CardView[@resource-id=\"com.figment.pos.dev:id/card\"])")
    public WebElement parkOrderContainer;
    @AndroidFindBy(id = "com.figment.pos.dev:id/decline")
    public WebElement voidBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/restore")
    public WebElement restoreBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/checkout")
    public WebElement checkoutBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/okBtn")
    public WebElement confirmVoidBtn;

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
    // Clicks the first parked order if available
    public void clickAnyParkedOrder(int index) {
        List<WebElement> parkedOrders = getAllParkedOrders();
        if (!parkedOrders.isEmpty() && index < parkedOrders.size()) {
            parkedOrders.get(index).click();
        } else {
            System.out.println("No parked orders to click or index out of bounds.");
        }
    }

    public void restoreOrVoidParkedOrder(boolean shouldVoid) {
        parkBtn.click();
        waitForVisibility(parkOrderContainer);
        List<WebElement> parkedOrders = getAllParkedOrders();
        int size = parkedOrders.size();
        if (size == 0) {
            System.out.println("No parked orders to restore.");
            return;
        }
        Set<Integer> triedIndices = new HashSet<>();
        restoreOrVoidParkedOrderRecursive(parkedOrders, triedIndices, shouldVoid);
    }

    private void restoreOrVoidParkedOrderRecursive(List<WebElement> parkedOrders, Set<Integer> triedIndices, boolean shouldVoid) {
        if (triedIndices.size() == parkedOrders.size()) {
            System.out.println("All parked orders are partially paid, cannot restore any.");
            return;
        }
        int index;
        do {
            index = getRandomIndex(parkedOrders.size());
        } while (triedIndices.contains(index));
        triedIndices.add(index);

        clickAnyParkedOrder(index);
        waitForVisibility(checkoutBtn);
        try {
            if( shouldVoid) {
                if (!voidBtn.isDisplayed()) {
                    System.out.println("Order at index " + index + " is partially paid, retrying...");
                    restoreOrVoidParkedOrderRecursive(parkedOrders, triedIndices, shouldVoid);
                }else {
                    voidBtn.click();
                    waitForVisibility(confirmVoidBtn);
                    confirmVoidBtn.click();
                }
            } else {
                if (!restoreBtn.isDisplayed()) {
                    System.out.println("Order at index " + index + " is partially paid, retrying...");
                    restoreOrVoidParkedOrderRecursive(parkedOrders, triedIndices, shouldVoid);
                }else {
                    restoreBtn.click();
                }
            }
        } catch (Exception e) {
            System.out.println("Order at index " + index + " is partially paid, retrying...");
            restoreOrVoidParkedOrderRecursive(parkedOrders, triedIndices, shouldVoid);
        }
    }

    public void checkoutParkOrder(String page) {
        new SharedMethods().checkoutOrder(page);
    }
}
