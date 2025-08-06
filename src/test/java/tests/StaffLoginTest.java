package tests;

import base.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.StaffLoginPage;
import utils.RetryAnalyzer;

import static utils.SharedMethods.*;

@Listeners(utils.TestListener.class)
public class StaffLoginTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void StaffLoginSuccess() {
        /*RegisterStoreTest registerStoreTest = new RegisterStoreTest();
        registerStoreTest.testLoginSuccess();
        *//*SelectBranchTest selectBranchTest = new SelectBranchTest();
        selectBranchTest.selectBranchSuccess();*//*
        SelectBranchPage selectBranchPage = new SelectBranchPage();
        selectBranchPage.Select();*/
        StaffLoginPage staffLoginPage = new StaffLoginPage();
        staffLoginPage.Enter("0000");
    }
}
