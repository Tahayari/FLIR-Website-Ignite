package testCases;

import base.TestBase;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
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
    private LandingPage landingPage;
    private LoginPage loginPage;
    private LibraryPage libraryPage;
    private SignUpPage signUpPage;
    private TestUtil testUtil;


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
        extentTestChild = extentTest.createNode("Verify the page title");

        loginPage = landingPage.login_btn_click();
        extentTestChild.log(Status.PASS, "Navigated to Login page");

        testUtil.waitForElementToLoad(driver, loginPage.login_btn);
        String title = loginPage.getPageTitle();
        Assert.assertEquals(title, "FLIR Log in");
        extentTestChild.log(Status.PASS, "Page title is " + signUpPage.getPageTitle());
    }

    @Test
    public void blank_email_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Create an account with a blank email");
        extentTestChild = extentTest.createNode("Verify the title of the page");

        loginPage = landingPage.login_btn_click();
        extentTestChild.log(Status.PASS, "Navigated to Login page");

        loginPage.email_field.clear();
        extentTestChild.log(Status.PASS, "Leave the email field blank");

        loginPage.login_btn.click();
        extentTestChild.log(Status.PASS, "Click on Login button");

        boolean errorMsg = driver.findElement(By.xpath("//p[contains(text(),'Please enter your email')]")).isDisplayed();
        Assert.assertTrue(errorMsg, "true");
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }

    @Test
    public void valid_credentials_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Login with valid credentials");
        extentTestChild = extentTest.createNode("Login with valid credentials");

        loginPage = landingPage.login_btn_click();
        extentTestChild.log(Status.PASS, "Navigated to Login page");

        libraryPage = loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
        extentTestChild.log(Status.PASS, "Enter valid email and pass then login");

        testUtil.waitForElementToLoad(driver, libraryPage.newFolder_btn);
        Assert.assertEquals(loginPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Library Page is displayed/ user is logged in successfully");
    }

    @Test
    public void click_SignUpLink_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Click on Sign Up Hyperlink");
        extentTestChild = extentTest.createNode("Redirect to Sign Up page");

        loginPage = landingPage.login_btn_click();
        extentTestChild.log(Status.PASS, "Navigated to Login page");

        signUpPage = loginPage.click_SignUp();
        extentTestChild.log(Status.PASS, "Click on Sign Up button");

        testUtil.waitForElementToLoad(driver, signUpPage.create_btn);
        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
        extentTestChild.log(Status.PASS, "Sign Up Page is displayed");
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            extentTestChild.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            extentTestChild.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));

            String screenshotPath = TestUtil.getScreenshot(driver, result.getName());
            System.out.println(screenshotPath);
            extentTestChild.fail("Snapshot below : ").addScreenCaptureFromPath(screenshotPath);

        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTestChild.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTestChild.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case PASSED", ExtentColor.GREEN));
        }

        driver.quit();

    }
}
