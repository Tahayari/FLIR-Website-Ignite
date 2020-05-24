package base;


import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testData.TestData;
import utils.ExtentReport;
import utils.testCasesInfo.TestCasesInfo;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static setup.ReadProperties.loadProperties;
import static utils.DriverFactory.getDriver;
import static utils.DriverFactory.quitDriver;

public class TestBase {

    private WebDriver driver;
    public static Properties prop;
    public ExtentReport extentReport = new ExtentReport();
    protected TestCasesInfo testCasesInfo= new TestCasesInfo();
    protected TestData testData = new TestData();

    @BeforeSuite
    public void beforeSuite() throws IOException {
        prop = loadProperties();
    }

    @BeforeClass
    public void startUpBrowser() {
        driver = getDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(testData.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(testData.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void goToLandingPage() {
        driver.get(prop.getProperty("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void updateExtentReport(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentReport.logFailure(result);
        }
        else if (result.getStatus() == ITestResult.SKIP) {
            extentReport.logSkip(result);
        }
        else if (result.getStatus() == ITestResult.SUCCESS) {
            extentReport.logSuccess(result);
        }
    }

    @AfterClass
    public void closeDriver() {
        if (driver != null) {
            driver = quitDriver();
        }
    }

    @AfterTest(alwaysRun = true)
    public void endReport() {
        extentReport.endReport();
    }
}