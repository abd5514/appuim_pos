package tests;

import base.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.SelectBranchPage;
import utils.RetryAnalyzer;

@Listeners(utils.TestListener.class)
public class SelectBranchTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void selectBranchSuccess() {
        /*RegisterStoreTest registerStoreTest = new RegisterStoreTest();
        registerStoreTest.testLoginSuccess();*/
        SelectBranchPage selectBranchPage = new SelectBranchPage();
        selectBranchPage.Select();
    }
}
