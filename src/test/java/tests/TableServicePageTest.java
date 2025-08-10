package tests;

import base.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.TableServicePage;
import utils.RetryAnalyzer;

@Listeners(utils.TestListener.class)
public class TableServicePageTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void checkTables() throws Exception {
        TableServicePage tableServicePage = new TableServicePage();
//        tableServicePage.checkButtonsStatus();
        tableServicePage.reserveTable();
    }
}
