package tests;

import base.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ParkPage;
import utils.RetryAnalyzer;

@Listeners(utils.TestListener.class)
public class ParkPageTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void restoreParkOrder() throws Exception {
        HomePageTest homePageTest = new HomePageTest();
        homePageTest.parkOrder();
        ParkPage parkPage = new ParkPage();
        parkPage.parkBtn.click();
        parkPage.restoreOrVoidParkedOrder(false);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void voidParkOrder() throws Exception {
        HomePageTest homePageTest = new HomePageTest();
        homePageTest.parkOrder();
        ParkPage parkPage = new ParkPage();
        parkPage.parkBtn.click();
        parkPage.restoreOrVoidParkedOrder(true);
    }
}
