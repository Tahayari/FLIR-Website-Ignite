package testCases;

import base.TestBase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;
import utils.TestUtil;

import java.io.IOException;

public class LoginPageTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;
    LibraryPage libraryPage;
    TestUtil testUtil;


    public LoginPageTest() {
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

    @Test(priority = 1)
    public void titleTest() {
        extentTest = extent.startTest("title_test");
        loginPage = landingPage.login_btn_click();
        testUtil.waitForElementToLoad(driver, loginPage.login_btn);
        String title = loginPage.getPageTitle();
        Assert.assertEquals(title, "FLIR Log in");
    }

    @Test(priority = 2)
    public void blankEmailTest() {
        extentTest = extent.startTest("blank_email_test");
        loginPage = landingPage.login_btn_click();
        loginPage.email_field.clear();
        loginPage.login_btn.click();
        boolean errorMsg = driver.findElement(By.xpath("//p[contains(text(),'Please enter your email')]")).isDisplayed();
        Assert.assertTrue(errorMsg, "true");
    }

    @Test(priority = 3)
    public void LoginTest() {
        extentTest = extent.startTest("login_test");
        loginPage = landingPage.login_btn_click();
        System.out.println("Am dat click pe butonul de Login");
        libraryPage = loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
        testUtil.waitForElementToLoad(driver,libraryPage.newFolder_btn);
        Assert.assertEquals(loginPage.getPageTitle(),"FLIR Tools");
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName()); //to add name in extent report
            extentTest.log(LogStatus.FAIL, "REASON : " + result.getThrowable()); //to add error/exception in extent report

            String screenshotPath = TestUtil.getScreenshot(driver, result.getName());
            extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); //to add screenshot in extent report
            //extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath)); //to add screencast/video in extent report
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());
        }

        extent.endTest(extentTest); //ending test and ends the current test and prepare to create html report
        driver.quit();

    }
}
