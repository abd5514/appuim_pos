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
    @AndroidFindBy(xpath = "//android.widget.Toast[@text=\"Please fill all required fields\"]")
    public WebElement modifiersToastMessage;
    @AndroidFindBy(id = "com.figment.pos.dev:id/homeLayout")
    public WebElement sideMenuHomePage;
    @AndroidFindBy(id = "com.figment.pos.dev:id/managerDashboardLayout")
    public WebElement sideMenuDailySales;
    @AndroidFindBy(id = "com.figment.pos.dev:id/tabsLayout")
    public WebElement dailySalesMainBanner;


    public int min;
    public int max;
    public int before;
    public int after;

    /**
     * get how many item in cart.
     */
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
        options.get(getRandomIndex(options.size())).click();
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

    /**
     * Handles clicking one random variant and then the required 'min' modifiers.
     * Variants are buttons above the Min/Max marker; modifiers are below.
     */
    /*public void handleVariantsAndModifiers() throws InterruptedException {
        String itemXPath   = "//androidx.recyclerview.widget.RecyclerView[@resource-id='com.figment.pos.dev:id/recyclerView']/android.view.ViewGroup";
        String buttonXPath = "//android.view.ViewGroup[@resource-id='com.figment.pos.dev:id/constraintLayout']";

        // 1. Find Min/Max marker position among items
        List<WebElement> items = DriverManager.getDriver().findElements(By.xpath(itemXPath));
        int markerItemIndex = -1;
        if(items.size()>1){
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
//                        System.out.println("🔍 Marker at item index " + i + ", Min=" + min + ", Max=" + max);
                            break;
                        }
                    }
                }
                if (markerItemIndex >= 0) break;
            }

            if (markerItemIndex < 0) {
                System.out.println("❌ Could not find Min/Max marker.");
                return;
            }

            // 2. Split buttons into variants and modifiers
            List<WebElement> allButtons = DriverManager.getDriver().findElements(By.xpath(buttonXPath));
            // assume one button per item
            int variantCount = markerItemIndex;

            // 3. Click a random variant (index 0..variantCount-1)
            if (variantCount > 0) {
                int randomIdx = getRandomIndex(variantCount);
                allButtons.get(randomIdx).click();
                Thread.sleep(300);
            }

            // 4. Click 'min' modifiers: buttons at indices variantCount .. variantCount+min-1
            for (int offset = 0; offset <min; offset++) {
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
                            System.out.println("🔄 Stale, retry modifier #" + (offset + 1));
                        }
                    } else {
                        // not loaded yet → scroll and retry
                        ScrollUtils.scrollDown(DriverManager.getDriver());
                        Thread.sleep(300);
                        tries++;
                        System.out.println("🔃 Scrolling for modifier #" + (offset + 1));
                    }
                }
                if (!clicked) {
                    System.out.println("❌ Failed modifier #" + (offset + 1));
                    break;
                }
            }
            doneBtn.click();
        }
    }*/

    /*new copiplet code section start*/
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
    /*new copiplet code section end*/

    /**
     * Entry point: select category and handle variants/modifiers.
     */
    public void Select() throws InterruptedException {
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
        sideMenuDailySales.click();
        waitForVisibility(dailySalesMainBanner);
        sideMenuHomePage.click();
    }

    private int getRandomIndex(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public void selectMultipleTimes(int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            Select();
        }
    }
}
