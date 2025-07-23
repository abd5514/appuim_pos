package tests;

import base.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ActionUtils;
import utils.RetryAnalyzer;

@Listeners(utils.TestListener.class)
public class HomePageTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void checkoutOrder() throws Exception {
        HomePage homePage = new HomePage();
        ActionUtils.runMultipleTimes(3, homePage::Select);
        homePage.checkoutOrder();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void parkOrder() throws Exception {
        HomePage homePage = new HomePage();
        ActionUtils.runMultipleTimes(3, homePage::Select);
        homePage.parkOrder("Test note for parking order");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void voidOrder() throws Exception {
        HomePage homePage = new HomePage();
        ActionUtils.runMultipleTimes(3, homePage::Select);
        homePage.voidOrder();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void splitByValueOrder() throws Exception {
        HomePage homePage = new HomePage();
        ActionUtils.runMultipleTimes(3, homePage::Select);
        homePage.splitByValue();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void splitByItemOrder() throws Exception {
        HomePage homePage = new HomePage();
        ActionUtils.runMultipleTimes(3, homePage::Select);
        homePage.splitByItem();
    }
}
