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

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.SharedMethods.getRandomIndex;
import static utils.WaitUtils.*;


public class HomePage {
    public HomePage() {
        // Initialize page elements with AppiumFieldDecorator
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.Button")
    public WebElement backBtn;
    @AndroidFindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[3]/android.view.View[1]/android.widget.Button")
    public WebElement page1Btn;
    @AndroidFindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[3]/android.view.View[2]/android.widget.Button")
    public WebElement page2Btn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/doneBtn")
    public WebElement doneBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/fragment_menu")
    public WebElement mainMenu;
    @AndroidFindBy(id = "com.figment.pos.dev:id/homeLayout")
    public WebElement sideMenuHomePage;
    @AndroidFindBy(id = "com.figment.pos.dev:id/managerDashboardLayout")
    public WebElement sideMenuDailySales;
    @AndroidFindBy(id = "com.figment.pos.dev:id/tabsLayout")
    public WebElement dailySalesMainBanner;
    @AndroidFindBy(id = "com.figment.pos.dev:id/proceed")
    public WebElement checkoutBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/park")
    public WebElement parkBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/decline")
    public WebElement voidBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/okBtn")
    public WebElement confirmVoidBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/payBtn")
    public WebElement payBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/secondPaymentContainer")
    public WebElement creditPaymentContainer;
    @AndroidFindBy(id = "com.figment.pos.dev:id/edittext")
    public WebElement parkNoteTxt;
    @AndroidFindBy(id = "com.figment.pos.dev:id/sendBtn")
    public WebElement parkNoteBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/splitByValueBtn")
    public WebElement splitByValueBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/addPayment")
    public WebElement addPaymentBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/charge")
    public WebElement chargeBtn;
    @AndroidFindBy(xpath = "(//android.widget.Button[@resource-id=\"com.figment.pos.dev:id/charge\"])[2]")
    public WebElement newChargeBtn;
    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@resource-id=\"com.figment.pos.dev:id/container\"])[2]")
    public WebElement creditPaymentSplit;
    @AndroidFindBy(id = "com.figment.pos.dev:id/splitByItemBtn")
    public WebElement splitByItemBtn;
    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.figment.pos.dev:id/productsRecyclerView']/android.view.ViewGroup")
    public WebElement productsRecyclerView;
    @AndroidFindBy(id = "com.figment.pos.dev:id/header")
    public WebElement dailySalesHeader;

    public int min;
    public int max;
    public int before;
    public int after;

    public int getCartItemsCount() {
        String cartItem = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.figment.pos.dev:id/cartItems\"]/android.view.ViewGroup";
        List<WebElement> items = DriverManager.getDriver().findElements(By.xpath(cartItem));
        return items.size();
    }

    /**
     * click a random order option.
     */
    public void clickAnyOfOrderOptions() {
        String orderOptions = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.figment.pos.dev:id/orderOptions\"]/android.view.ViewGroup";
        List<WebElement> options = DriverManager.getDriver().findElements(By.xpath(orderOptions));
        options.get(0).click();
    }
    /**
     * Selects a random product category.
     */
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

    /*new copilot code section start*/
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
    /*new copilot code section end*/

    /**
     * Entry point: select category and handle variants/modifiers.
     */
    public void Select() throws InterruptedException {
        sideMenuHomePage.click();
        try {
            sideMenuHomePage.click();
            if(!mainMenu.isDisplayed()) {
                sideMenuHomePage.click();
            }
        } catch (Exception e) {
            // If the main menu is not displayed, we assume we are already on the home page.
            System.out.println("Main menu is not displayed, assuming we are on the home page.");
        }
        getWait(1);
        getAllProducts();
        if (backBtn.isDisplayed()) getAllProducts();
        handleVariantsAndModifiers();
        before = getCartItemsCount();
        try {
            doneBtn.click();
        } catch (Exception ignored) {}
        after = getCartItemsCount();
        Assert.assertTrue(before<=after);
        clickAnyOfOrderOptions();
        waitForVisibility(sideMenuDailySales);
        sideMenuDailySales.click();
        waitForVisibility(dailySalesHeader);
        sideMenuHomePage.click();
        try {
            sideMenuHomePage.click();
            if(!mainMenu.isDisplayed()) {
                sideMenuHomePage.click();
            }
        } catch (Exception e) {
            // If the main menu is not displayed, we assume we are already on the home page.
            System.out.println("Main menu is not displayed, assuming we are on the home page.");
        }
        waitForVisibility(mainMenu);
    }

    public void checkoutOrder() {
        waitForVisibility(checkoutBtn);
        checkoutBtn.click();
        waitForVisibility(creditPaymentContainer);
        creditPaymentContainer.click();
        waitForVisibility(payBtn);
        payBtn.click();
    }

    public void parkOrder(String note) {
        waitForVisibility(parkBtn);
        parkBtn.click();
        waitForVisibility(parkNoteTxt);
        parkNoteTxt.sendKeys(note + " " + getRandomIndex(1000)); // Append a random number to the note
        waitForVisibility(parkNoteBtn);
        parkNoteBtn.click();
    }

    public void voidOrder() {
        waitForVisibility(voidBtn);
        voidBtn.click();
        waitForVisibility(confirmVoidBtn);
        confirmVoidBtn.click();
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

    // begin copilot code
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
    // end copilot code
}
