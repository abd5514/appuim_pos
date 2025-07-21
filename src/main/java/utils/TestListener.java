package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import drivers.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // 📌 Base report folder (must match the one used in ExtentManager)
    private static final String REPORT_FOLDER = "reports/";
    private static final String SCREENSHOT_FOLDER = REPORT_FOLDER + "screenshots/";

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest currentTest = test.get();
        if (currentTest == null) {
            currentTest = extent.createTest(result.getMethod().getMethodName());
            test.set(currentTest);
        }

        currentTest.log(Status.FAIL, "Test failed: " + result.getThrowable());

        try {
            // 🖼️ Create screenshot folder if not exists
            new File(SCREENSHOT_FOLDER).mkdirs();

            // 📷 Generate screenshot file
            File srcFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotName = result.getMethod().getMethodName() + "_" + timestamp + ".png";
            String fullPath = SCREENSHOT_FOLDER + screenshotName;

            FileUtils.copyFile(srcFile, new File(fullPath));

            // ✅ Add relative path to the report (so it links correctly)
            currentTest.addScreenCaptureFromPath("screenshots/" + screenshotName);

        } catch (IOException e) {
            currentTest.log(Status.WARNING, "Screenshot failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
