package base;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testData.TestData;
import utils.ExtentReport;
import utils.TestUtil;

import java.io.File;
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

    @BeforeSuite
    @Parameters({"browserName"})
    public void beforeSuite(String browserName) throws IOException {
        log.info("Begin @BeforeSuite");
        browser=browserName;
        prop = loadProperties();
        TestUtil.deleteFilesFromFolder(new File(testData.getProjectPath() + "\\test-output\\screenshots"));
        log.info("End @BeforeSuite");
    }

    @BeforeClass
    @Parameters({"browserName"})
    public void startUpBrowser() {
        log.info("Begin @BeforeClass");
        driver = getDriver(browser);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestData.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestData.IMPLICIT_WAIT, TimeUnit.SECONDS);
        log.info("End @BeforeClass");
    }

    @BeforeMethod
    @Parameters({"webrellaEnv","ssoEnv"})
    public void goToLandingPage(String webrellaEnv,String ssoEnv) {
        log.info("Begin @BeforeMethod");
        driver.get(prop.getProperty("url"));
//        setAPI(prop.getProperty("webrella"), prop.getProperty("sso"));
        setAPI(webrellaEnv,ssoEnv);
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
        log.info("End @AfterMethod");
    }

    @AfterClass
    public void closeDriver() {
        log.info("Begin @AfterClass");
        if (driver != null) {
            driver = quitDriver();
            log.info("Closing the browser...");
        }
        log.info("End @AfterClass");
    }

    @AfterTest(alwaysRun = true)
    public void endReport() {
        log.info("Begin @AfterTest");
        extentReport.endReport();
        log.info("End @AfterTest");
    }

    void setAPI(String webrellaURL, String ssoURL) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        switch (webrellaURL.trim()) {
            case "DEV":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-develop.azurewebsites.net"));
                break;
            case "DEV-STG":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-develop-stage.azurewebsites.net"));
                break;
            case "FEATURE":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-feature.azurewebsites.net"));
                break;
            case "FEATURE-STG":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-feature-stage.azurewebsites.net"));
                break;
            case "PROD":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella.api-fs.com"));
                break;
            case "PROD-STG":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-stage.azurewebsites.net"));
                break;
            default:
                log.info("Enter a valid Webrella Environment! You've entered : " + webrellaURL);
        }

        switch (ssoURL.trim()) {
            case "LAB":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "flir.sso.env", "flirb2clab"));
                break;
            case "PROD":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "flir.sso.env", "flirb2cprod"));
                break;
            default:
                log.info("Enter a valid Webrella Environment! You've entered : " + ssoURL);
        }
    }

}