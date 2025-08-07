package pages;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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

    public List<WebElement> getButtonsInsideTableContainer() {
        System.out.println("Buttons inside table container: " + tableContainerCanvas.findElements(By.className("android.widget.Button")).size());
        return tableContainerCanvas.findElements(By.className("android.widget.Button"));
    }

    public void checkTablesByColor() {
        waitForVisibility(tableServiceSideBtn);
        tableServiceSideBtn.click();
        waitForVisibility(tableContainerCanvas);

        List<WebElement> allButtons = getButtonsInsideTableContainer();
        WebElement unknownColorButton = null;

        for (WebElement button : allButtons) {
            System.out.println("TEXT: " + button.getText());
            System.out.println("CONTENT-DESC: " + button.getAttribute("content-desc"));
            System.out.println("RESOURCE-ID: " + button.getAttribute("resource-id"));
            System.out.println("CLASS: " + button.getAttribute("class"));
//            System.out.println("DESCRIPTION: " + button.getAttribute("description")); // Some apps use this
//            System.out.println("BACKGROUND: " + button.getAttribute("background"));
        }

        for (WebElement button : allButtons) {
            String desc = button.getAttribute("content-desc");
//            System.out.println(button.getCssValue("background-color")); // This line is not necessary, but can be used to debug colors
            if (desc == null) continue;

            if (desc.contains("#09d0c5")) {
                System.out.println("✅ Green Table: " + button.getText());
            } else if (desc.contains("#b2b2b2")) {
                System.out.println("🪑 Gray Table: " + button.getText());
            } else if (desc.contains("#fa7f6f")) {
                System.out.println("🟥 Red Table: " + button.getText());
            } else if (desc.contains("#ffb42f")) {
                System.out.println("🟨 Yellow Table: " + button.getText());
            } else if (desc.contains("#042f6a")) {
                System.out.println("🔵 Dark Blue Table: " + button.getText());
            } else {
                unknownColorButton = button; // Save for later
                System.out.println("❓ Unknown color: " + desc + " for Table: " + button.getText());
            }
        }

        if (unknownColorButton != null) {
            unknownColorButton.click(); // Click AFTER loop is finished
        }
    }
}
