package pages;

import drivers.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static utils.WaitUtils.getWait;

public class SelectBranchPage {

    public SelectBranchPage() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AndroidFindBy(xpath="//android.widget.Spinner[@text=\"Branch Name\"]")
    public  WebElement branchDLL;
    @AndroidFindBy(xpath="//android.widget.ScrollView/android.view.View[1]")
    public  WebElement defaultBranch ;
    @AndroidFindBy( xpath = "//android.widget.Button")
    public WebElement nextBtn;

    public void Select() {
        getWait(10).until(ExpectedConditions.visibilityOf(branchDLL)).click();
        getWait(10).until(ExpectedConditions.visibilityOf(defaultBranch)).click();
        nextBtn.click();
    }
}
