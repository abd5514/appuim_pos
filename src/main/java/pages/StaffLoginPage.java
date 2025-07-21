package pages;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

import java.time.Duration;

public class StaffLoginPage {

    public StaffLoginPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[11]/android.widget.Button")
    public  WebElement zeroBtn;
    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[2]/android.widget.Button")
    public  WebElement oneBtn;
    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[3]/android.widget.Button")
    public  WebElement twoBtn;
    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[4]/android.widget.Button")
    public  WebElement threeBtn;
    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[5]/android.widget.Button")
    public  WebElement fourBtn;
    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[6]/android.widget.Button")
    public  WebElement fiveBtn;
    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[7]/android.widget.Button")
    public  WebElement sixBtn;
    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[8]/android.widget.Button")
    public  WebElement sevenBtn;
    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[9]/android.widget.Button")
    public  WebElement eightBtn;
    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[10]/android.widget.Button")
    public  WebElement nineBtn;

    private WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }

    public void Enter(String passCode) {
        if (passCode.length() != 4) {
            System.out.println("Passcode must be exactly 4 digits.");
            return;
        }

        for (char digit : passCode.toCharArray()) {
            WebElement button = null;

            if (digit == '0') button = zeroBtn;
            else if (digit == '1') button = oneBtn;
            else if (digit == '2') button = twoBtn;
            else if (digit == '3') button = threeBtn;
            else if (digit == '4') button = fourBtn;
            else if (digit == '5') button = fiveBtn;
            else if (digit == '6') button = sixBtn;
            else if (digit == '7') button = sevenBtn;
            else if (digit == '8') button = eightBtn;
            else if (digit == '9') button = nineBtn;

            WaitUtils.getWait(10).until(ExpectedConditions.elementToBeClickable(button)).click();
        }
    }
}
