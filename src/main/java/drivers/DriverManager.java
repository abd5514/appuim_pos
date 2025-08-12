package drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverManager {

    private static final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    public static void initDriver(boolean noReset, boolean fullReset) {
        String gridUrl = ConfigReader.get("gridUrl");
        String deviceName = ConfigReader.get("deviceName");
        String platformVersion = ConfigReader.get("platformVersion");
        String appPackage = ConfigReader.get("appPackage");
        String appActivity = ConfigReader.get("appActivity");
//        String appPath = ConfigReader.get("appPath"); // optional
        boolean autoLaunch = Boolean.parseBoolean(ConfigReader.get("autoLaunch"));
        boolean enableMultiWindows = Boolean.parseBoolean(ConfigReader.get("enableMultiWindows"));

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setDeviceName(deviceName);
        options.setPlatformVersion(platformVersion);
        options.setAppPackage(appPackage);
        options.setAppActivity(appActivity);
        options.setAutomationName("UiAutomator2");


        options.setCapability("appium:autoLaunch", autoLaunch);
        options.setCapability("appium:noReset", noReset);
        options.setCapability("appium:fullReset", fullReset);
        options.setCapability("appium:enableMultiWindows", enableMultiWindows);

        try {
            driver.set(new AndroidDriver(new URL(gridUrl), options));
            System.out.println("🚀 Driver is initializing with noReset=" + noReset + ", fullReset=" + fullReset);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL", e);
        }
    }

    public static AndroidDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}