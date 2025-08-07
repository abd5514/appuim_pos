package utils;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static utils.WaitUtils.*;

import java.util.List;
import java.util.Random;

public class SharedMethods {
    @AndroidFindBy(id = "com.figment.pos.dev:id/okBtn")
    public static WebElement confirmBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/syncBtn")
    public static WebElement syncBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/secondPaymentContainer")
    public WebElement creditPaymentContainer;
    @AndroidFindBy(id = "com.figment.pos.dev:id/payBtn")
    public WebElement payBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/splitByValueBtn")
    public WebElement splitByValueBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/charge")
    public WebElement chargeBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/addPayment")
    public WebElement addPaymentBtn;
    @AndroidFindBy(xpath = "(//android.widget.Button[@resource-id=\"com.figment.pos.dev:id/charge\"])[2]")
    public WebElement newChargeBtn;
    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@resource-id=\"com.figment.pos.dev:id/container\"])[2]")
    public WebElement creditPaymentSplit;
    @AndroidFindBy(id = "com.figment.pos.dev:id/splitByItemBtn")
    public WebElement splitByItemBtn;
    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.figment.pos.dev:id/productsRecyclerView']/android.view.ViewGroup")
    public WebElement productsRecyclerView;
    @AndroidFindBy(id = "com.figment.pos.dev:id/proceed")
    public WebElement checkoutBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/checkout")
    public WebElement parkCheckoutBtn;

    public SharedMethods() {
        // Initialize page elements with AppiumFieldDecorator
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }
    public static int getRandomIndex(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public void checkForSyncPopup() {
        try {
            waitForVisibility(confirmBtn);
            confirmBtn.click();
            waitForVisibility(syncBtn);
        } catch (Exception e) {
            System.out.println("Sync popup not appeared, continuing without action.");
        }
    }

    /*public void checkoutOrder() {

        waitForVisibility(checkoutBtn);
        checkoutBtn.click();
        waitForVisibility(creditPaymentContainer);
        creditPaymentContainer.click();
        waitForVisibility(payBtn);
        payBtn.click();
    }*/

    public void checkoutOrder(String page) {
        if(page == null || page.isEmpty() || page.equalsIgnoreCase("park")) {
            waitForVisibility(parkCheckoutBtn);
            parkCheckoutBtn.click();
        } else {
            waitForVisibility(checkoutBtn);
            checkoutBtn.click();
        }
        waitForVisibility(creditPaymentContainer);
        creditPaymentContainer.click();
        waitForVisibility(payBtn);
        payBtn.click();
    }

    public void splitByValue() {
        waitForVisibility(checkoutBtn);
        checkoutBtn.click();
        waitForVisibility(splitByValueBtn);
        splitByValueBtn.click();
        waitForVisibility(chargeBtn);
        addPaymentBtn.click();
        waitForVisibility(newChargeBtn);
        newChargeBtn.click();
        waitForVisibility(creditPaymentSplit);
        creditPaymentSplit.click();
        waitForVisibility(payBtn);
        payBtn.click();
        waitForVisibility(chargeBtn);
        chargeBtn.click();
        waitForVisibility(creditPaymentSplit);
        creditPaymentSplit.click();
        waitForVisibility(payBtn);
        payBtn.click();
    }

    public void splitByItem() {
        waitForVisibility(checkoutBtn);
        checkoutBtn.click();
        waitForVisibility(splitByItemBtn);
        splitByItemBtn.click();
        waitForVisibility(productsRecyclerView);
        getWait(2);
        List<WebElement> products = DriverManager.getDriver().findElements(
                By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.figment.pos.dev:id/productsRecyclerView']/android.view.ViewGroup")
        );
        for (int i = 1; i <= products.size(); i++) {
            String indexedXPath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.figment.pos.dev:id/productsRecyclerView']/android.view.ViewGroup[" + i + "]";
            try {
                WebElement product = DriverManager.getDriver().findElement(By.xpath(indexedXPath));
                System.out.println("Checking product at index " + i + ": " + product.getText());
                if (product.isDisplayed()) {
                    product.click();
                    break;
                }
            } catch (Exception e) {
                System.out.println("Product at index " + i + " not found or not visible.");
            }
        }
        payBtn.click();
        waitForVisibility(creditPaymentSplit);
        creditPaymentSplit.click();
        waitForVisibility(payBtn);
        payBtn.click();
        Assert.assertTrue(payBtn.isDisplayed(), "Pay button should be displayed after splitting by item.");
    }
}