package pages;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.SharedMethods.getRandomIndex;
import static utils.WaitUtils.waitForVisibility;


public class TableServicePage {
    public TableServicePage() {
        // Initialize page elements with AppiumFieldDecorator
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(id = "com.figment.pos.dev:id/tablesLayout")
    public WebElement tableServiceSideBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/canvasLayout")
    public WebElement tableContainerCanvas;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btnReserve")
    public WebElement reserveBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btnVoid")
    public WebElement voidBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btnEdit")
    public WebElement editBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btnTransferItems")
    public WebElement transferItemBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btnTransferTable")
    public WebElement transferTableBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btnMergeTable")
    public WebElement mergeBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btnProceed")
    public WebElement checkoutTableBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btnCancel")
    public WebElement cancelBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btn5")
    public WebElement fiveBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btnOrder")
    public WebElement orderBtn;


    List<WebElement> reservedButtons = new ArrayList<>();
    List<WebElement> freeButtons = new ArrayList<>();
    List<WebElement> busyButtons = new ArrayList<>();
    public List<WebElement> getButtonsInsideTableContainer() {
        return tableContainerCanvas.findElements(By.className("android.widget.Button"));
    }

    public List<WebElement> checkButtonsStatus(String tableStatus) {
        waitForVisibility(tableServiceSideBtn);
        tableServiceSideBtn.click();
        waitForVisibility(tableContainerCanvas);
        List<WebElement> allButtons = getButtonsInsideTableContainer();

        for (int i = 0; i < allButtons.size(); i++) {
            allButtons = getButtonsInsideTableContainer();
            if (i >= allButtons.size()) break;
            WebElement btn = allButtons.get(i);
            try {
                btn.click();
                boolean isBusy = false, isReserved = false, isFree = false;
                try { isBusy = voidBtn.isDisplayed(); } catch (Exception ignored) {}
                try { isReserved = cancelBtn.isDisplayed(); } catch (Exception ignored) {}
                try { isFree = reserveBtn.isDisplayed(); } catch (Exception ignored) {}

                if (isBusy) {
                    busyButtons.add(btn);
                } else if (isReserved) {
                    reservedButtons.add(btn);
                } else if (isFree) {
                    freeButtons.add(btn);
                    System.out.println(freeButtons.size() + " free buttons found at index " + i);
                } else {
                    System.out.println("⚠️ Could not determine status at index " + i + "\n");
                }

            } catch (Exception e) {
                System.out.println("⚠️ Click/status failed at index " + i + ": " + e.getMessage() + "\n");
            }
        }
        if ("free".equalsIgnoreCase(tableStatus)) {
            System.out.println("Returning free buttons: " + freeButtons.size());
            return freeButtons;
        } else if ("busy".equalsIgnoreCase(tableStatus)) {
            return busyButtons;
        } else if ("reserved".equalsIgnoreCase(tableStatus)) {
            return reservedButtons;
        }
        return new ArrayList<>(); // Return empty list if no valid status is provided
    }

    public void reserveTable() {
        List<WebElement> free = checkButtonsStatus("free");
        free.get(0).click();
        System.out.println("Reserving table...");
    }
}
