package utils.javautils;

import com.aventstack.extentreports.ExtentReports;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.BrowserManager.BrowserManager;
import org.testng.ITestContext;


import java.lang.reflect.Method;
import java.time.Duration;

import static utils.javautils.Reporter.extent;

public class BaseTest extends BrowserManager
{
    TakeScreenshot tss = new TakeScreenshot();
    private static boolean isSuiteInitialized = false;

    @BeforeSuite
    public void setUpSuite(ITestContext context) throws Exception {
        logStep("Current working directory: " + System.getProperty("user.dir"));
        Reporter.extent = new ExtentReports();

        if (!isSuiteInitialized) {
            String suiteName = context.getSuite().getName();  // Get the suite name
            Reporter.setupReport(suiteName);
            isSuiteInitialized = true;
        }
        browserRun();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpLog(Method method) throws Exception {
        Reporter.test = extent.createTest(method.getName());
        LoggerUtil.setLogFileName(method.getName());

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws Exception {
        logResultStatus(result);

    }

    private void logResultStatus(ITestResult result) throws Exception {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                Reporter.logPass("**** " + result.getName() + " has PASSED ****");
                break;
            case ITestResult.FAILURE:
                Reporter.logFail("**** " + result.getName() + " has FAILED ****", driver);
                break;
            case ITestResult.SKIP:
                logStep("**** " + result.getName() + " has been SKIPPED ****");
                Reporter.logStep("**** " + result.getName() + " has been SKIPPED ****");
                break;
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        Reporter.flushReport();
    }
    @AfterSuite
    public void closeBrowser()
    {
        driver.quit();
    }

}
