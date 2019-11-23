package testCases;

import base.TestBase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.SignUpPage;
import utils.TestUtil;

import java.io.IOException;

public class SignUpPageTest extends TestBase {
    LandingPage landingPage;
    TestUtil testUtil;
    SignUpPage signUpPage;


    public SignUpPageTest() {
        super();
    }

    @BeforeTest
    public void setExtent() {
        extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
        extent.addSystemInfo("Host Name", "Dan's Laptop");
        extent.addSystemInfo("User Name", "Dan Hosman");
        extent.addSystemInfo("Environment", "DEV");

    }

    @AfterTest
    public void endReport() {
        extent.flush();
        extent.close();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
        testUtil = new TestUtil();
    }

    @Test
    public void title_Test() {
        extentTest = extent.startTest("SIGN UP PAGE - title_test");
        signUpPage = landingPage.signUp_btn_click();
        testUtil.waitForElementToLoad(driver, signUpPage.create_btn);
        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName()); //to add name in extent report
            extentTest.log(LogStatus.FAIL, "REASON : " + result.getThrowable()); //to add error/exception in extent report

            String screenshotPath = TestUtil.getScreenshot(driver, result.getName());
            extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); //to add screenshot in extent report

        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());
        }

        extent.endTest(extentTest); //ending test and ends the current test and prepare to create html report
        driver.quit();

    }
}
