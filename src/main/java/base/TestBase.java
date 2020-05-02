package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReport;
import utils.TestUtil;
import utils.WebEventListener;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static setup.ReadProperties.loadProperties;
import static utils.DriverFactory.getDriver;

public class TestBase {

    WebDriver driver;
    public static Properties prop;
    public ExtentReport extentReport;
    public static ExtentReports extent;
    public static ExtentTest extentTest;
    public static ExtentTest extentTestChild;

    @BeforeSuite
    public void beforeSuite() throws IOException {
        prop = loadProperties();
        extentReport = new ExtentReport();
    }

    @BeforeClass
    public void startUpBrowser() {
        driver = getDriver();

        EventFiringWebDriver e_driver = new EventFiringWebDriver(driver);
        WebEventListener eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

    }

    @BeforeMethod
    public void goToLandingPage() {
        driver.get(prop.getProperty("url"));
    }

    protected void addTestCaseStep(String testCaseStep) {
        extentTestChild.log(Status.PASS, testCaseStep);
    }

    protected void createTestCaseDescription(String testCaseDescription) {
        extentTestChild = extentTest.createNode(testCaseDescription);
    }

    protected void createTestCaseTitle(String testCaseTitle) {
        extentTest = extent.createTest(testCaseTitle);
    }

    protected void createTestCase(String testCaseTitle, String testCaseDescription) {
        createTestCaseTitle(testCaseTitle);
        createTestCaseDescription(testCaseDescription);
        Assert.assertEquals(driver.getTitle(), "FLIR Ignite");
        addTestCaseStep("Navigated to Landing page");
    }

    public void checkIfCorrectErrMsg(WebElement element, String error_msg) {
        Assert.assertEquals(element.getText(), error_msg);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {
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
        driver.quit();
    }

    @AfterTest(alwaysRun = true)
    public void endReport() {
//        extent.flush();
        extentReport.endReport();
    }

    //something Else
}