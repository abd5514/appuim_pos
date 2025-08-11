package utils;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static utils.WaitUtils.*;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @AndroidFindBy(id = "com.figment.pos.dev:id/doneBtn")
    public WebElement doneBtn;

    public int min;
    public int max;
    public int before;
    public int after;

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

    public void checkoutOrder(String page) {
        if(page == null || page.isEmpty() || page.equalsIgnoreCase("park")) {
            waitForVisibility(parkCheckoutBtn);
            parkCheckoutBtn.click();
        } else if (page.equalsIgnoreCase("home")){
            waitForVisibility(checkoutBtn);
            checkoutBtn.click();
        }
        waitForVisibility(creditPaymentContainer);
        creditPaymentContainer.click();
        waitForVisibility(payBtn);
        payBtn.click();
    }

    public void splitByValue(String page) {
        if(page == null || page.isEmpty() || page.equalsIgnoreCase("park")) {
            waitForVisibility(parkCheckoutBtn);
            parkCheckoutBtn.click();
        } else if(page.equalsIgnoreCase("home")){
            waitForVisibility(checkoutBtn);
            checkoutBtn.click();
        }
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

    public void splitByItem(String page) {
        if(page == null || page.isEmpty() || page.equalsIgnoreCase("park")) {
            waitForVisibility(parkCheckoutBtn);
            parkCheckoutBtn.click();
        } else if(page.equalsIgnoreCase("home")){
            waitForVisibility(checkoutBtn);
            checkoutBtn.click();
        }
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

    public void getAllProducts() throws InterruptedException {
        List<WebElement> categories = DriverManager.getDriver().findElements(
                By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View")
        );
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }
        WebElement chosen = categories.get(getRandomIndex(categories.size()));
        if (chosen.isDisplayed()) {
            chosen.click();
            Thread.sleep(500);
        }
    }

    public void handleVariantsAndModifiers() throws InterruptedException {
        String itemXPath   = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.figment.pos.dev:id/recyclerView']/android.view.ViewGroup";
        String buttonXPath = "//android.view.ViewGroup[@resource-id='com.figment.pos.dev:id/constraintLayout']";

        List<WebElement> items = DriverManager.getDriver().findElements(By.xpath(itemXPath));
        int markerItemIndex = -1;
        min = 0;
        max = 0;

        for (int i = 0; i < items.size(); i++) {
            WebElement item = items.get(i);
            for (WebElement tv : item.findElements(By.className("android.widget.TextView"))) {
                String text = tv.getText();
                if (text.contains("Min:") && text.contains("Max:")) {
                    Matcher m = Pattern.compile("Min:\\s*(\\d+)\\s*\\|\\s*Max:\\s*(\\d+)").matcher(text);
                    if (m.find()) {
                        min = Integer.parseInt(m.group(1));
                        max = Integer.parseInt(m.group(2));
                        markerItemIndex = i;
                        break;
                    }
                }
            }
            if (markerItemIndex >= 0) break;
        }

        List<WebElement> allButtons = DriverManager.getDriver().findElements(By.xpath(buttonXPath));
        int variantCount = markerItemIndex;

        // Handle variants if present
        if (variantCount > 0 && allButtons.size() >= variantCount) {
            int randomIdx = getRandomIndex(variantCount);
            allButtons.get(randomIdx).click();
            Thread.sleep(300);
        } else {
            System.out.println("No variants found for this product.");
        }

        // Handle modifiers if present
        if (min > 0 && allButtons.size() >= (variantCount + min)) {
            for (int offset = 0; offset < min; offset++) {
                int globalIdx = variantCount + offset;
                int tries = 0;
                boolean clicked = false;
                while (!clicked && tries < 6) {
                    List<WebElement> buttons = DriverManager.getDriver().findElements(By.xpath(buttonXPath));
                    if (buttons.size() > globalIdx) {
                        try {
                            buttons.get(globalIdx).click();
                            Thread.sleep(300);
                            clicked = true;
                        } catch (StaleElementReferenceException stale) {
                            System.out.println("Stale, retry modifier #" + (offset + 1));
                        }
                    } else {
                        ScrollUtils.scrollDown(DriverManager.getDriver());
                        Thread.sleep(300);
                        tries++;
                        System.out.println("Scrolling for modifier #" + (offset + 1));
                    }
                }
                if (!clicked) {
                    System.out.println("Failed modifier #" + (offset + 1));
                    break;
                }
            }
            doneBtn.click();
        } else {
            System.out.println("No modifiers required for this product.");
        }
    }

    public int getCartItemsCount() {
        String cartItem = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.figment.pos.dev:id/cartItems\"]/android.view.ViewGroup";
        List<WebElement> items = DriverManager.getDriver().findElements(By.xpath(cartItem));
        return items.size();
    }
}