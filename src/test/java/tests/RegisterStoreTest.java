package tests;

import base.BaseTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.RegisterStorePage;
import utils.RetryAnalyzer;

@Listeners(utils.TestListener.class)
public class RegisterStoreTest extends BaseTest {

    @BeforeTest
    public void setDriverParams() {
        System.setProperty("noReset", "true");
        System.setProperty("fullReset", "false");
    }
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testLoginSuccess() {
        RegisterStorePage registerStorePage = new RegisterStorePage();
        registerStorePage.StoreCode("591807338700", "Auto_SM-x700");
    }
}
