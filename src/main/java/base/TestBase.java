package base;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import setup.Backend;
import testData.TestData;
import utils.DriverFactory;
import utils.ExtentReport;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static setup.ReadProperties.loadProperties;

public class TestBase {
    protected DriverFactory factory;
    protected WebDriver driver;
    public static Properties prop;
    public static ExtentReport extentReport;
    protected TestData testData = new TestData();
    protected static final Logger log = LogManager.getLogger(TestBase.class);

    @BeforeTest
    @Parameters({"browserName", "webrellaEnv", "ssoEnv"})
    public void initExtentReports(@Optional("chrome") String browserName, @Optional("DEV") String webrellaEnv,
                                  @Optional("LAB") String ssoEnv) throws IOException{
        extentReport = new ExtentReport(browserName);

        factory=DriverFactory.getInstance();
        startUpBrowser(browserName);

        prop = loadProperties();
        driver.get(prop.getProperty("url"));

        //set the Backend ENV
        Backend backend = new Backend(factory.getDriver());
        backend.setAPI(webrellaEnv, ssoEnv);

        //Append backend URL used into the Extent Report
        extentReport.setBackend(backend.getWebrellaAPI(),backend.getSsoAPI());
    }

    @BeforeMethod
    @Parameters({"browserName", "webrellaEnv", "ssoEnv"})
    public void goToLandingPage(@Optional("chrome") String browserName, @Optional("DEV") String webrellaEnv,
                                @Optional("LAB") String ssoEnv) throws IOException {
        log.info("Begin @BeforeMethod");

        factory=DriverFactory.getInstance();
        startUpBrowser(browserName);

        prop = loadProperties();
        driver.get(prop.getProperty("url"));

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
        factory.createDriver(browser);
        driver = factory.getDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestData.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestData.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    public void closeDriver() {
        log.info("Begin @AfterClass");
        if (driver != null) {
            driver = factory.quitDriver();
            log.info("Closing the browser...");
        }
        log.info("End @AfterClass");
    }
}