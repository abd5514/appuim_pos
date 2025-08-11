package pages;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.ActionUtils;
import utils.SharedMethods;

import java.util.*;

import static utils.WaitUtils.getWait;
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
    public WebElement doneEditBtn;
    @AndroidFindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.Button")
    public WebElement backBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/doneBtn")
    public WebElement doneBtn;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Page 1\"]")
    public WebElement page1Btn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/btnSplitTable")
    public WebElement splitTableBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/okBtn")
    public WebElement confirmVoidBtn;
    @AndroidFindBy(id = "com.figment.pos.dev:id/to")
    public WebElement transferToDDL;
    @AndroidFindBy(id = "com.figment.pos.dev:id/transfer")
    public WebElement transferBtn;

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
                                if(doneEditBtn.isDisplayed()){
                                    editTable();
                                    break;
                                }
                            } catch (NoSuchElementException e) {
                                System.out.println("⚠️ Done button not found, cannot proceed with edit.");
                            }
                        }
                    } else if("void order".equalsIgnoreCase(actionType)){
                        if(voidBtn.isEnabled()){
                            voidOrderTable();
                        }
                    }
                } else if (isReserved) {
                    reservedButtons.add(btn);
                } else if (isFree) {
                    freeButtons.add(btn);
                    if ("new order".equalsIgnoreCase(actionType)){
                        reserveTableAndMakeNewOrder();
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

    public void reserveTableAndMakeNewOrder() throws Exception {
        waitForVisibility(reserveBtn);
        reserveBtn.click();
        waitForVisibility(fiveBtn);
        fiveBtn.click();
        waitForVisibility(orderBtn);
        orderBtn.click();
        ActionUtils.runMultipleTimes(3, this::tableServiceSelectMenu);
        doneEditBtn.click();
        System.out.println("Reserving first free table found");
    }

    public void editTable() throws Exception {
        ActionUtils.runMultipleTimes(3, this::tableServiceSelectMenu);
        doneEditBtn.click();
    }

    public void tableServiceSelectMenu() throws InterruptedException {
        getWait(1);
        new SharedMethods().getAllProducts();
        if (backBtn.isDisplayed()) new SharedMethods().getAllProducts();
        new SharedMethods().handleVariantsAndModifiers();
        try {
            doneBtn.click();
        } catch (Exception ignored) {}

        try {
            backBtn.click();
            Thread.sleep(300);
            if(page1Btn.isDisplayed()) backBtn.click();
        }catch (Exception e) {
            System.out.println("backBtn not found, continuing without going back.");
        }
    }

    public void checkoutOrderTable(){
        waitForVisibility(checkoutTableBtn);
        checkoutTableBtn.click();
        new SharedMethods().checkoutOrder("table service");
    }

    public void splitByValueOrderTable(){
        waitForVisibility(checkoutTableBtn);
        checkoutTableBtn.click();
        new SharedMethods().splitByValue("table service");
    }

    public void voidOrderTable(){
        waitForVisibility(voidBtn);
        voidBtn.click();
        waitForVisibility(confirmVoidBtn);
        confirmVoidBtn.click();
    }

    public void transferItemTable(){
        waitForVisibility(transferItemBtn);
        transferItemBtn.click();
        waitForVisibility(transferToDDL);
        transferToDDL.click();
    }


}
