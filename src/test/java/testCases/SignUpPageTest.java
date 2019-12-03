package testCases;

import base.TestBase;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.LibraryPage;
import pages.SignUpPage;
import utils.TestUtil;

import java.io.IOException;
import java.util.Random;

public class SignUpPageTest extends TestBase {
    private LandingPage landingPage;
    private TestUtil testUtil;
    private SignUpPage signUpPage;
    private LibraryPage libraryPage;


    public SignUpPageTest() {
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
        extentTest = extent.createTest("SIGN UP PAGE - title_test");
        extentTestChild = extentTest.createNode("Verify the title of the page");

        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Navigated to landing page");

        signUpPage = landingPage.signUp_btn_click();
        extentTestChild.log(Status.PASS, "Clicked on Sign Up button in Index page");

        testUtil.waitForElementToLoad(driver, signUpPage.getCreate_BTN());
        extentTestChild.log(Status.PASS, "Sign Up page has loaded");

        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
        extentTestChild.log(Status.PASS, "Page title is " + signUpPage.getPageTitle());
    }

    @Test
    public void registerNewAccount_test() {
        String email = "testaaaaa";
        String password = "QAZxsw123";
        String firstName = "FirstName";
        String lastName = "LastName";

        extentTest = extent.createTest("SIGN UP PAGE - create a new user");
        extentTestChild = extentTest.createNode("Create a new user");

        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Navigated to landing page");

        signUpPage = landingPage.signUp_btn_click();
        extentTestChild.log(Status.PASS, "Clicked on Sign Up button from Index page");

        testUtil.waitForElementToLoad(driver, signUpPage.getCreate_BTN());
        Assert.assertTrue(signUpPage.getCreate_BTN().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Sign Up page has loaded");

        signUpPage.setEmailAddress(email + "@mailinator.com");
        Assert.assertEquals(signUpPage.getEmail_field().getAttribute("value"), email + "@mailinator.com");
        extentTestChild.log(Status.PASS, "Entered the email address");

        signUpPage.getSendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.getVerificationCode_field());
        Assert.assertTrue(signUpPage.getVerificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(TestUtil.getTokenFromMailinator(email));
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.getVerifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setNewPassword(password);
        extentTestChild.log(Status.PASS, "Entered a password");

        signUpPage.setConfirmNewPassword(password);
        extentTestChild.log(Status.PASS, "Re-entered the same password in the Confirm New Password field");

        signUpPage.setFirstName(firstName);
        extentTestChild.log(Status.PASS, "Entered a first name");

        signUpPage.setLastName(lastName);
        extentTestChild.log(Status.PASS, "Entered a last name");

        Select country_select = new Select(signUpPage.getCountry_dropdown());
//        System.out.println("################# Number of countries from the dropdown list is: " + country_select.getOptions().size());
        Random random = new Random();
        country_select.selectByIndex(random.nextInt(country_select.getOptions().size()));
        extentTestChild.log(Status.PASS, "Selected a country from the dropdown list");

        if (random.nextInt(1) % 2 == 0) signUpPage.getConsentNo().click();
        else signUpPage.getConsentYes().click();
        extentTestChild.log(Status.PASS, "Selected randomly if I consented or not");

        libraryPage = signUpPage.createButton_click();
        testUtil.waitForElementToLoad(driver,libraryPage.getTermsAndConditions());
        Assert.assertTrue(libraryPage.getTermsAndConditions().isDisplayed(),"true");
        extentTestChild.log(Status.PASS,"Created a new account, Terms and conditions page is displayed");
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            extentTestChild.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            extentTestChild.log(Status.FAIL, result.getThrowable());

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
