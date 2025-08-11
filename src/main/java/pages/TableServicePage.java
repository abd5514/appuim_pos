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
import java.util.*;

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
    @AndroidFindBy(id = "com.figment.pos.dev:id/proceed")
    public WebElement doneBtn;

    public List<WebElement> getButtonsInsideTableContainer() {
        return tableContainerCanvas.findElements(By.className("android.widget.Button"));
    }

    public void checkTableStatusAndMakeAction(String actionType) {
        List<WebElement> freeButtons = new ArrayList<>();
        List<WebElement> reservedButtons = new ArrayList<>();
        List<WebElement> busyButtons = new ArrayList<>();
        waitForVisibility(tableServiceSideBtn);
        tableServiceSideBtn.click();
        waitForVisibility(tableContainerCanvas);
        int count = getButtonsInsideTableContainer().size();
        for (int i = 0; i < count; i++) {
            List<WebElement> allButtons = getButtonsInsideTableContainer();
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
                    if ("edit order".equalsIgnoreCase(actionType)){
                        if(editBtn.isEnabled()){
                            editBtn.click();
                            try {
                                if(doneBtn.isDisplayed()){
                                    editTable();
                                    break;
                                }
                            } catch (NoSuchElementException e) {
                                System.out.println("⚠️ Done button not found, cannot proceed with edit.");
                            }
                        }
                    }
                } else if (isReserved) {
                    reservedButtons.add(btn);
                } else if (isFree) {
                    freeButtons.add(btn);
                    if ("new order".equalsIgnoreCase(actionType)){
                        reserveTable();
                        break;
                    }
                } else {
                    System.out.println("⚠️ Could not determine status at index " + i + "\n");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Click/status failed at index " + i + ": " + e.getMessage() + "\n");
            }
        }
        if ("reserved".equalsIgnoreCase(actionType)) {}
    }

    public void reserveTable() {
        waitForVisibility(reserveBtn);
        reserveBtn.click();
        System.out.println("Reserving first free table found");
    }

    public void editTable() {

        System.out.println("Editing table");
    }
}
