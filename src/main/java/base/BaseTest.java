package base;

import drivers.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

public class BaseTest {

    @BeforeMethod
    public void setUp() {
        boolean noReset = Boolean.parseBoolean(System.getProperty("noReset", "true"));
        boolean fullReset = Boolean.parseBoolean(System.getProperty("fullReset", "false"));
        DriverManager.initDriver(noReset, fullReset);
        DriverManager.getDriver().activateApp(ConfigReader.get("appPackage"));
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
