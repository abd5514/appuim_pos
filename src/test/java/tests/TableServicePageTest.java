package tests;

import base.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.TableServicePage;
import utils.RetryAnalyzer;

@Listeners(utils.TestListener.class)
public class TableServicePageTest extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void EditOrderTable() throws Exception {
        TableServicePage tableServicePage = new TableServicePage();
        tableServicePage.checkTableStatusAndMakeAction("edit order");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void NewOrderTable() throws Exception {
        TableServicePage tableServicePage = new TableServicePage();
        tableServicePage.checkTableStatusAndMakeAction("new order");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void CheckoutOrderTable() throws Exception {
        TableServicePage tableServicePage = new TableServicePage();
        tableServicePage.checkTableStatusAndMakeAction("new order");
        tableServicePage.checkoutOrderTable();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void SplitByValueOrderTable() throws Exception {
        TableServicePage tableServicePage = new TableServicePage();
        tableServicePage.checkTableStatusAndMakeAction("new order");
        tableServicePage.splitByValueOrderTable();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void VoidOrderTable() throws Exception {
        TableServicePage tableServicePage = new TableServicePage();
        tableServicePage.checkTableStatusAndMakeAction("void order");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void TransferItemsOrderTable() throws Exception {
        TableServicePage tableServicePage = new TableServicePage();
        tableServicePage.checkTableStatusAndMakeAction("void order");
    }
}
