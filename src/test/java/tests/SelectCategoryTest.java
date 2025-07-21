package tests;

import base.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.RetryAnalyzer;

@Listeners(utils.TestListener.class)
public class SelectCategoryTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void selectProduct() throws InterruptedException {
        HomePage homePage = new HomePage();
        homePage.selectMultipleTimes(3);
    }
}
