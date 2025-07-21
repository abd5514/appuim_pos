package pages;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static utils.WaitUtils.getWait;

public class RegisterStorePage {

    public RegisterStorePage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(id = "com.example.app:id/username")
    public WebElement usernameField;

    @AndroidFindBy(id = "com.example.app:id/password")
    public WebElement passwordField;

    @AndroidFindBy(id = "com.example.app:id/login")
    public WebElement loginButton;

    @AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
    public   WebElement AllowLocation ;
    @AndroidFindBy(id="com.android.permissioncontroller:id/permission_allow_button")
    public   WebElement AllowNotification ;
    @AndroidFindBy(xpath="//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.widget.EditText[1]")
    public  WebElement StoreCodeTxt;
    @AndroidFindBy(xpath="//android.widget.EditText[@text=\"SM-X700\"]")
    public  WebElement DeviceName ;
    @AndroidFindBy( xpath = "//android.widget.Button")
    public WebElement RegisterBtn;

    /*private WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }*/

    public void StoreCode(String storeCode, String deviceName) {
        DriverManager.getDriver().resetInputState();
        getWait(10).until(ExpectedConditions.visibilityOf(AllowLocation)).click();
        getWait(10).until(ExpectedConditions.visibilityOf(AllowNotification)).click();
        StoreCodeTxt.sendKeys(storeCode);
        DeviceName.sendKeys(deviceName);
        RegisterBtn.click();
    }
}
