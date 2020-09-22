package base;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import setup.Backend;
import testData.TestData;
import utils.ExtentReport;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static setup.ReadProperties.loadProperties;
import static utils.DriverFactory.getDriver;
import static utils.DriverFactory.quitDriver;

public class TestBase {
    protected WebDriver driver;
    public static Properties prop;
    public ExtentReport extentReport = new ExtentReport();
    protected TestData testData = new TestData();
    protected static final Logger log = LogManager.getLogger(TestBase.class);
    public static String browser;

    @BeforeMethod
    @Parameters({"browserName", "webrellaEnv", "ssoEnv"})
    public void goToLandingPage(@Optional("chrome") String browserName, @Optional("PROD") String webrellaEnv,
                                @Optional("PROD") String ssoEnv) throws IOException {
        log.info("Begin @BeforeMethod");
        startUpBrowser(browserName);

        prop = loadProperties();
        driver.get(prop.getProperty("url"));

        Backend backend = new Backend(driver);
        backend.setAPI(webrellaEnv, ssoEnv);

        log.info("End @BeforeMethod");
    }

    @AfterMethod(alwaysRun = true)
    public void updateExtentReport(ITestResult result) throws IOException {
        log.info("Begin @AfterMethod");
        if (result.getStatus() == ITestResult.FAILURE) {
            extentReport.logFailure(result);
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentReport.logSkip(result);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentReport.logSuccess(result);
        }
        closeDriver();
        log.info("End @AfterMethod");
    }

    @AfterTest(alwaysRun = true)
    public void endReport() {
        log.info("Begin @AfterTest");
        extentReport.endReport();
        log.info("End @AfterTest");
    }

    public void startUpBrowser(String browser) {
        driver = getDriver(browser);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestData.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestData.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    public void closeDriver() {
        log.info("Begin @AfterClass");
        if (driver != null) {
            driver = quitDriver();
            log.info("Closing the browser...");
        }
        log.info("End @AfterClass");
    }
}