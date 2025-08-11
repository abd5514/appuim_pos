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
import utils.SharedMethods;

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
    @AndroidFindBy(id = "com.figment.pos.dev:id/park")
    public WebElement parkBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/decline")
    public WebElement voidBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/okBtn")
    public WebElement confirmVoidBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/edittext")
    public WebElement parkNoteTxt;
    @AndroidFindBy(id = "com.figment.pos.dev:id/sendBtn")
    public WebElement parkNoteBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/header")
    public WebElement dailySalesHeader;

    public int min;
    public int max;
    public int before;
    public int after;

    public int getCartItemsCount() {
        return new SharedMethods().getCartItemsCount();
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
        new SharedMethods().getAllProducts();
    }

    /*new copilot code section start*/
    public void handleVariantsAndModifiers() throws InterruptedException {
        new SharedMethods().handleVariantsAndModifiers();
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
        new SharedMethods().checkoutOrder("home");
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
        new SharedMethods().splitByValue("home");
    }

    // begin copilot code
    public void splitByItem() {
        new SharedMethods().splitByItem("home");
    }
    // end copilot code
}
