package testCases;

import base.TestBase;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;
import pages.SignUpPage;
import utils.TestUtil;

import java.io.IOException;

public class LoginPageTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;
    LibraryPage libraryPage;
    SignUpPage signUpPage;
    TestUtil testUtil;


    public LoginPageTest() {
        super();
    }

    @BeforeTest
    public void setExtent() {
        extentInitialization();
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
        testUtil = new TestUtil();
    }

    @Test
    public void title_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Verify the page title");
        loginPage = landingPage.login_btn_click();
        testUtil.waitForElementToLoad(driver, loginPage.login_btn);
        String title = loginPage.getPageTitle();
        Assert.assertEquals(title, "FLIR Log in");
    }

    @Test
    public void blank_email_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Create an account with a blank email");
        loginPage = landingPage.login_btn_click();
        loginPage.email_field.clear();
        loginPage.login_btn.click();
        boolean errorMsg = driver.findElement(By.xpath("//p[contains(text(),'Please enter your email')]")).isDisplayed();
        Assert.assertTrue(errorMsg, "true");
    }

    @Test
    public void valid_credentials_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Login with valid credentials");
        loginPage = landingPage.login_btn_click();
        libraryPage = loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
        testUtil.waitForElementToLoad(driver, libraryPage.newFolder_btn);
        Assert.assertEquals(loginPage.getPageTitle(), "FLIR Tools");
    }

    @Test
    public void click_SignUpLink_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Click on Sign Up Hyperlink");
        loginPage = landingPage.login_btn_click();
        signUpPage = loginPage.click_SignUp();
        testUtil.waitForElementToLoad(driver, signUpPage.create_btn);
        Assert.assertEquals(signUpPage.getPageTitle(),"FLIR Sign up");
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); //to add name in extent report
            extentTest.log(Status.FAIL, "REASON : " + result.getThrowable()); //to add error/exception in extent report

            String screenshotPath = TestUtil.getScreenshot(driver, result.getName());
            extentTest.addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, "Test Case PASSED IS " + result.getName());
        }

        driver.quit();

    }
}
