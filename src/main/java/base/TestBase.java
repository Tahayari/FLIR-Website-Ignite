package base;


import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import testData.TestData;
import utils.ExtentReport;
import utils.TestUtil;
import utils.testCasesInfo.TestCasesInfo;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static setup.ReadProperties.loadProperties;
import static utils.DriverFactory.getDriver;

public class TestBase {

    private WebDriver driver;
    public static Properties prop;
    public ExtentReport extentReport;
    protected TestCasesInfo testCasesInfo= new TestCasesInfo();
    protected TestData testData = new TestData();

    @BeforeSuite
    public void beforeSuite() throws IOException {
        prop = loadProperties();
        extentReport = new ExtentReport();
    }

    @BeforeClass
    public void startUpBrowser() {
        driver = getDriver();

//        EventFiringWebDriver e_driver = new EventFiringWebDriver(driver);
//        WebEventListener eventListener = new WebEventListener();
//        e_driver.register(eventListener);
//        driver = e_driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

    }

    @BeforeMethod
    public void goToLandingPage() {
        driver.get(prop.getProperty("url"));
    }

//    protected void addTestCaseStep(String testCaseStep) {
//        extentTestChild.log(Status.PASS, testCaseStep);
//    }
//
//    protected void createTestCaseDescription(String testCaseDescription) {
//        extentTestChild = extentTest.createNode(testCaseDescription);
//    }
//
//    protected void createTestCaseTitle(String testCaseTitle) {
//        extentTest = extent.createTest(testCaseTitle);
//    }
//
//    protected void createTestCase(String testCaseTitle, String testCaseDescription) {
//        createTestCaseTitle(testCaseTitle);
//        createTestCaseDescription(testCaseDescription);
//        Assert.assertEquals(driver.getTitle(), "FLIR Ignite");
//        addTestCaseStep("Navigated to Landing page");
//    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentReport.logFailure(result);
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentReport.logSkip(result);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentReport.logSuccess(result);
        }
    }

    @AfterClass
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterTest(alwaysRun = true)
    public void endReport() {
        extentReport.endReport();
    }

    public void createNewTestCase(String testCaseTitle,String testCaseDescription){
        extentReport.createTestCase(testCaseTitle, testCaseDescription);
    }
}