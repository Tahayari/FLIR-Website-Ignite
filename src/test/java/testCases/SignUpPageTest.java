package testCases;

import base.TestBase;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.SignUpPage;
import utils.TestUtil;

import java.util.Random;

public class SignUpPageTest extends TestBase {
    private LandingPage landingPage;
    private TestUtil testUtil;
    private SignUpPage signUpPage;

    String sheetName = "Sheet1";


    public SignUpPageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
        testUtil = new TestUtil();
    }

    public void goToSignUpPage() {
        extentTest = extent.createTest("SIGN UP PAGE - title_test");
        extentTestChild = extentTest.createNode("Verify the title of the page");

        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Navigated to landing page");

        signUpPage = landingPage.signUp_btn_click();
        extentTestChild.log(Status.PASS, "Clicked on Sign Up button in Index page");

        testUtil.waitForElementToLoad(driver, signUpPage.getCreate_BTN());
        extentTestChild.log(Status.PASS, "Sign Up page has loaded");
    }

    @Test
    public void title_Test() {
        goToSignUpPage();

        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
        extentTestChild.log(Status.PASS, "Page title is " + signUpPage.getPageTitle());
    }

    @DataProvider
    public Object[][] getTestData() {
        return TestUtil.getTestaData(sheetName);
    }

    @Test(dataProvider = "getTestData")
    public void registerNewAccount_Test(String email, String password, String firstName, String lastName) {

        goToSignUpPage();

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
        Random random = new Random();
        country_select.selectByIndex(random.nextInt(country_select.getOptions().size()));
        extentTestChild.log(Status.PASS, "Selected a country from the dropdown list");

        if (random.nextInt(1) % 2 == 0) signUpPage.getConsentNo().click();
        else signUpPage.getConsentYes().click();
        extentTestChild.log(Status.PASS, "Selected randomly if I consented or not");

        LibraryPage libraryPage = signUpPage.createButton_click();
        testUtil.waitForElementToLoad(driver, libraryPage.getTermsAndConditions());
        Assert.assertTrue(libraryPage.getTermsAndConditions().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Created a new account, Terms and conditions page is displayed");
    }

    @Test
    public void blankEmail_Test() {
        String invalidEmail = "invalid.email@email";
        String error_xpath = "//div[@class='helpText show']";

        goToSignUpPage();

        signUpPage.setEmailAddress(invalidEmail);
        extentTestChild.log(Status.PASS, "Entered an invalid email address");

        signUpPage.getSendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, driver.findElement(By.xpath(error_xpath)));
        Assert.assertEquals(driver.findElement(By.xpath(error_xpath)).getText(), "Please enter a valid email address.");
        extentTestChild.log(Status.PASS, "Clicked on send verification code and an error message is displayed");
    }

    @Test
    public void invalidEmail_Test() {
        String expectedMsg = "Please enter a valid email address.";
        goToSignUpPage();

        signUpPage.setEmailAddress("test123");
        extentTestChild.log(Status.PASS, "Entered an invalid email address");

        testUtil.waitForElementToLoad(driver, signUpPage.getInvalidEmailWebelement());
        Assert.assertEquals(signUpPage.getInvalidEmailMsg(), expectedMsg);
        extentTestChild.log(Status.PASS, "Invalid email address error message is displayed");
    }

    @Test
    public void invalidToken_Test() {
        String email = "testemail@mailinator.com";
        String error_id = "That code is incorrect. Please try again.";

        goToSignUpPage();

        signUpPage.setEmailAddress(email);
        extentTestChild.log(Status.PASS, "Entered an email address");

        signUpPage.getSendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.getVerifyCode_BTN());
        extentTestChild.log(Status.PASS, "Verify code button is displayed");

        signUpPage.setVerificationCode_field("123456");
        signUpPage.getVerifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Entered an invalid Verification code");

        Assert.assertEquals(signUpPage.getIncorrectVerCode(), error_id);
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }

    @Test
    public void expiredToken_Test() throws InterruptedException {
        String email = "testemail@mailinator.com";
        String expiredError = "That code is expired. Please request a new code.";
        long waitTime = 5; // expressed in minutes

        goToSignUpPage();

        signUpPage.setEmailAddress(email);
        extentTestChild.log(Status.PASS, "Entered an email address");

        signUpPage.getSendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.getVerifyCode_BTN());
        extentTestChild.log(Status.PASS, "Verify code button is displayed");

        Thread.sleep(waitTime * 60 * 1000); // wait for waitTime minutes + 3 seconds
        extentTestChild.log(Status.PASS, "Waited for the Verification code to expire");

        signUpPage.setVerificationCode_field("123456");
        signUpPage.getVerifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Entered the Verification code after it expired");


        testUtil.waitForElementToLoad(driver, signUpPage.getExpiredVerCodeWebelement());
        Assert.assertEquals(signUpPage.getExpiredVerCode(), expiredError);
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }

    //TODO: incorrectPasswordFormat_Test 3-4 types of incorrect passwords
    //TODO: incorrectPasswordFormatConfirmPassField_Test 3-4 types of incorrect passwords
    //TODO: mismatchingPasswords_Test

}
