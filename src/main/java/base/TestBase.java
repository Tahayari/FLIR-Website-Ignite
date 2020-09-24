package base;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LandingPage;
import setup.Backend;
import testData.TestData;
import utils.DriverFactory;
import utils.ExtentReport;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static pages.LandingPage.getLandingPage;
import static setup.ReadProperties.loadProperties;

public class TestBase {
    protected DriverFactory factory;
    protected WebDriver driver;
    public static Properties prop;
    public static ExtentReport extentReport;
    protected TestData testData = new TestData();
    protected Logger log = LogManager.getLogger(TestBase.class);
    protected LandingPage landingPage;

    @BeforeTest
    @Parameters({"browserName", "webrellaEnv", "ssoEnv"})
    public void initExtentReports(@Optional("chrome") String browserName, @Optional("DEV") String webrellaEnv,
                                  @Optional("LAB") String ssoEnv) {

        extentReport = new ExtentReport(browserName);
        setupReports(browserName, webrellaEnv, ssoEnv);
    }

    @BeforeMethod
    @Parameters({"browserName", "webrellaEnv", "ssoEnv"})
    public void goToLandingPage(@Optional("chrome") String browserName, @Optional("DEV") String webrellaEnv,
                                @Optional("LAB") String ssoEnv) {
        log.info("************************Begin @BeforeMethod*********************************");

        startUpBrowser(browserName);
        configureBrowser();
        driver.get(getStartingURL());
        landingPage = getLandingPage(); //All of the tests will have this page to start from;

        log.info("************************End @BeforeMethod*********************************");
    }

    @AfterMethod(alwaysRun = true)
    public void updateExtentReport(ITestResult result) throws IOException {
        log.info("************************Begin @AfterMethod*********************************");
        if (result.getStatus() == ITestResult.FAILURE) {
            extentReport.logFailure(result);
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentReport.logSkip(result);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentReport.logSuccess(result);
        }
        closeDriver();
        log.info("************************End @AfterMethod*********************************");
    }

    @AfterTest(alwaysRun = true)
    public void endReport() {
        log.info("************************Begin @AfterTest*********************************");
        extentReport.endReport();
        log.info("************************End @AfterTest*********************************");
    }

    public void startUpBrowser(String browser) {
        factory = DriverFactory.getInstance();
        factory.createDriver(browser);
        driver = factory.getDriver();
    }

    public void closeDriver() {
        log.info("************************Begin @AfterClass*********************************");
        if (driver != null) {
            driver = factory.quitDriver();
            log.info("************************Closed the current browser*********************************");
        }
        log.info("************************End @AfterClass*********************************");
    }

    public void setupReports(String browserName, String webrellaEnv, String ssoEnv) {
        startUpBrowser(browserName);

        driver.get(getStartingURL());

        //set the Backend ENV
        Backend backend = new Backend(factory.getDriver());
        backend.setAPI(webrellaEnv, ssoEnv);

        //Append backend URL used into the Extent Report
        extentReport.setBackend(backend.getWebrellaAPI(), backend.getSsoAPI());
        closeDriver();
    }

    protected String getStartingURL() {
        try {
            prop = loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty("url");
    }

    private void configureBrowser() {
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestData.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestData.IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    protected void createTestCase(String testCaseName,String testCaseDescription,String testCaseCategory){
        ExtentReport.createTestCase(testCaseName, testCaseDescription);
        ExtentReport.assignCategory(testCaseCategory);
    }
}