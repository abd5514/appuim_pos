package tests;

import base.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.RetryAnalyzer;

@Listeners(utils.TestListener.class)
public class HomePageTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void checkoutOrder() throws InterruptedException {
        HomePage homePage = new HomePage();
        homePage.selectMultipleTimes(3);
        homePage.checkoutOrder();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void parkOrder() throws InterruptedException {
        HomePage homePage = new HomePage();
        homePage.selectMultipleTimes(3);
        homePage.parkOrder("Test note for parking order");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void voidOrder() throws InterruptedException {
        HomePage homePage = new HomePage();
        homePage.selectMultipleTimes(3);
        homePage.voidOrder();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void splitByValueOrder() throws InterruptedException {
        HomePage homePage = new HomePage();
        homePage.selectMultipleTimes(3);
        homePage.splitByValue();
    }
}
