package testCases;

import base.TestBase;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.SignUpPage;
import utils.TestUtil;

import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SignUpPageTest extends TestBase {
    private LandingPage landingPage;
    private TestUtil testUtil;
    private SignUpPage signUpPage;
    private String sheetName = "Sheet1";
    private String fileName = "Accounts";

    public SignUpPageTest() {
        super();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            initialization();
            landingPage = new LandingPage();
            testUtil = new TestUtil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToSignUpPage() {

        try {
            testUtil.waitForElementToLoad(driver, landingPage.signup_BTN());
        }
        catch(Exception e){
            System.out.println("------------Page timed out. Refreshing...");
            driver.navigate().refresh();
            testUtil.waitForElementToLoad(driver, landingPage.signup_BTN());
        }

        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Navigated to Landing page");

        signUpPage = landingPage.clickOn_signUpBTN();
        testUtil.waitForElementToLoad(driver, signUpPage.create_BTN());
        extentTestChild.log(Status.PASS, "Clicked on the SIGN UP button");
    }

    @Test(groups = {"smoke"})
    public void title_Test() {
        extentTest = extent.createTest("SIGNUP PAGE - Verify the page title");
        extentTestChild = extentTest.createNode("Verify the page title");

        goToSignUpPage();

        testUtil.waitForElementToLoad(driver, signUpPage.email_field());
        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
        extentTestChild.log(Status.PASS, "Page title is " + signUpPage.getPageTitle());
    }

    @DataProvider
    public Object[][] getTestData() {
        return TestUtil.getTestaData(fileName,sheetName);
    }

    @Test(dataProvider = "getTestData", groups = {"smoke", "regression"},enabled = false)
    public void registerNewAccount_Test(String email, String password, String firstName, String lastName) {

        extentTest = extent.createTest("SIGNUP PAGE - registerNewAccount_Test");
        extentTestChild = extentTest.createNode("Create new account(s)");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email + "@mailinator.com");
        Assert.assertEquals(signUpPage.email_field().getAttribute("value"), email + "@mailinator.com");
        extentTestChild.log(Status.PASS, "Entered the following email address: "+email+ "@mailinator.com");

        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromMailinator(email));
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setNewPassword(password);
        extentTestChild.log(Status.PASS, "Entered a valid password: "+password);

        signUpPage.setConfirmNewPassword(password);
        extentTestChild.log(Status.PASS, "Re-entered the same password in the Confirm New Password field");

        signUpPage.setFirstName(firstName);
        extentTestChild.log(Status.PASS, "Entered a first name: "+firstName);

        signUpPage.setLastName(lastName);
        extentTestChild.log(Status.PASS, "Entered a last name: "+lastName);

        Select country_select = new Select(signUpPage.country_dropdown());
        Random random = new Random();
        country_select.selectByIndex(random.nextInt(country_select.getOptions().size()));
        extentTestChild.log(Status.PASS, "Selected a random country from the dropdown list");

        if (random.nextInt(1) % 2 == 0) signUpPage.consentNo().click();
        else signUpPage.consentYes().click();
        extentTestChild.log(Status.PASS, "Selected randomly if I consented or not");

        LibraryPage libraryPage = signUpPage.createButton_click();
        extentTestChild.log(Status.PASS, "Clicked on the CREATE button");

        testUtil.waitForElementToLoad(driver, libraryPage.termsAndCondCheckbox());
        assertTrue(driver.getCurrentUrl().contains("terms"));
        extentTestChild.log(Status.PASS, "Terms and conditions page is displayed");

        libraryPage.termsAndCondCheckbox().click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
//        wait.until(ExpectedConditions.elementToBeClickable(libraryPage.termsAndCondAccept_BTN()));
        testUtil.waitForElementToLoad(driver,libraryPage.termsAndCondAccept_BTN());
        assertTrue(libraryPage.termsAndCondAccept_BTN().isEnabled());
        extentTestChild.log(Status.PASS, "Ticked the T&C checkbox and the Accept button is now enabled");

        libraryPage.termsAndCondAccept_BTN().click();
        wait.until(ExpectedConditions.urlContains("library"));
        extentTestChild.log(Status.PASS, "Clicked on the Accept button and was redirected to the Library page");

        libraryPage.welcomeScreenSkip_BTN().click();
        testUtil.waitForElementToLoad(driver, libraryPage.getNewFolder_btn());
        extentTestChild.log(Status.PASS, "Clicked on SKIP button from the Welcome screen, Library page is displayed");
    }

    @Test()
    public void blankEmail_Test() {
        String error_msg = "Please enter a valid email address.";
        extentTest = extent.createTest("SIGNUP PAGE - blankEmail_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if the email field is left blank");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Left the email field blank and clicked on the Send Verification code button");


        testUtil.waitForElementToLoad(driver, signUpPage.invalidEmail_err());
        Assert.assertEquals(signUpPage.invalidEmail_err().getText(), error_msg);
        extentTestChild.log(Status.PASS, "Error message is displayed: "+ error_msg);
    }

    @Test(groups = {"smoke"})
    public void invalidEmail_Test() {
        String error_msg = "Please enter a valid email address.";

        extentTest = extent.createTest("SIGNUP PAGE - invalidEmail_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if the users enters an email that has an invalid format");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress("test123@");
        extentTestChild.log(Status.PASS, "Entered an invalid email address");

        testUtil.waitForElementToLoad(driver, signUpPage.invalidEmail_err());
        Assert.assertEquals(signUpPage.invalidEmail_err().getText(), error_msg);
        extentTestChild.log(Status.PASS, "Invalid email address error message is displayed: "+error_msg);
    }

    @Test(groups = {"smoke"})
    public void invalidToken_Test() {
        String email = "testemail@mailinator.com";
        String error_msg = "That code is incorrect. Please try again.";

        extentTest = extent.createTest("SIGNUP PAGE - invalidToken_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if the user enters an invalid token");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        extentTestChild.log(Status.PASS, "Entered an email address");

        signUpPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Verify code button is displayed");

        signUpPage.setVerificationCode_field("123456");
        signUpPage.verifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Entered an invalid Verification code");

        testUtil.waitForElementToLoad(driver, signUpPage.incorrectVerCode_err());
        Assert.assertEquals(signUpPage.incorrectVerCode_err().getText(), error_msg);
        extentTestChild.log(Status.PASS, "Error message is displayed: "+error_msg);
    }

    @Test(enabled = false)
    public void expiredToken_Test() {
        String error_msg = "That code is expired. Please request a new code.";
        int waitTime = 5; // Number of MINUTES until the token expires

        extentTest = extent.createTest("SIGNUP PAGE - expiredToken_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if the user enters an expired token. Token is set to expire after +"+waitTime+" minutes");

        goToSignUpPage();
        testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(prop.getProperty("gmail"));
        extentTestChild.log(Status.PASS, "Entered an email address: "+prop.getProperty("gmail"));

        signUpPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Clicked on the Send Verification code button");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        try {
            Thread.sleep(waitTime * 60 * 1000); // wait for waitTime minutes + 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        extentTestChild.log(Status.PASS, "Entered the code received via email and waited for the Verification code to expire");


        signUpPage.verifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on the Verify Code button after the token expired");


        testUtil.waitForElementToLoad(driver, signUpPage.expiredVerCode_err());
        Assert.assertEquals(signUpPage.expiredVerCode_err().getText(), error_msg);
        extentTestChild.log(Status.PASS, "Error message is displayed :"+error_msg );
    }

    @Test()
    public void tooManyIncorrectAttemptsToken_Test() {
        int retry = 3;
        String email = "anytestmail123123xs@gmail.com";
        String error_msg = "You've made too many incorrect attempts. Please try again later.";

        extentTest = extent.createTest("SIGNUP PAGE - tooManyIncorrectAttemptsToken_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if an incorrect token is submitted more than "+retry+" times");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        extentTestChild.log(Status.PASS, "Entered an email address");

        signUpPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Clicked on the Send Verification code button");

        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        for(int i=0;i<retry;i++) {
            testUtil.waitForElementToLoad(driver,signUpPage.verifyCode_BTN());
            signUpPage.verificationCode_field().clear();
            signUpPage.setVerificationCode_field("12345");
            signUpPage.verifyCode_BTN().click();
        }
        extentTestChild.log(Status.PASS, "Entered the wrong token "+retry+" times");

        testUtil.waitForElementToLoad(driver,signUpPage.tooManyAttempts_err());
        assertEquals(signUpPage.tooManyAttempts_err().getText(),error_msg);
        extentTestChild.log(Status.PASS, "Too many incorrect attempts error message is displayed :"+error_msg);
    }

    @Test(groups = {"smoke"})
    public void sendNewCode_Test() {
        String email = "flirAutomationTest@gmail.com";
        String error_msg = "That code is incorrect. Please try again.";

        extentTest = extent.createTest("SIGNUP PAGE - sendNewCode_Test");
        extentTestChild = extentTest.createNode("Resend the token and validate the new one");

        goToSignUpPage();
        testUtil.getTokenFromGmail(); // setup the gmail mail so it would be stored in a secondary tab
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        extentTestChild.log(Status.PASS, "Entered an email address: "+email);

        signUpPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Clicked on the Send Verification code button");

        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        String oldToken = signUpPage.verificationCode_field().getAttribute("value"); //save this token as it will be reset soon
        extentTestChild.log(Status.PASS, "Saved the token received via email");

        testUtil.waitForElementToLoad(driver,signUpPage.sendNewCode_BTN());
        signUpPage.sendNewCode_BTN().click();
        String newToken = testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Clicked on Send new code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        Assert.assertEquals(signUpPage.verificationCode_field().getText(), "");
        extentTestChild.log(Status.PASS, "New code was generated");

        signUpPage.verificationCode_field().sendKeys(oldToken);
        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.incorrectVerCode_err());
        Assert.assertEquals(signUpPage.incorrectVerCode_err().getText(), error_msg);
        extentTestChild.log(Status.PASS, "Entered the previous token and it no longer works");

        signUpPage.verificationCode_field().clear();
        signUpPage.setVerificationCode_field(newToken);
        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.changeEmail_BTN());
        assertTrue(signUpPage.changeEmail_BTN().isDisplayed());
        extentTestChild.log(Status.PASS, "Entered the latest token received via email and it is validated");
    }

    @Test
    public void resendEmail_Test() {
        String email = "flirautomationtest@gmail.com";

        extentTest = extent.createTest("SIGNUP PAGE - resendEmail_Test");
        extentTestChild = extentTest.createNode("Change the email after validating the token");

        goToSignUpPage();
        testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        extentTestChild.log(Status.PASS, "Entered an email address");

        signUpPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Verified that the Send code button is displayed");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.changeEmail_BTN());
        assertTrue(signUpPage.changeEmail_BTN().isDisplayed());
        extentTestChild.log(Status.PASS, "Token was verified. Change e-mail button is displayed");

        signUpPage.changeEmail_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.sendVerCode_BTN());
        assertTrue(signUpPage.sendVerCode_BTN().isDisplayed());
        assertTrue(signUpPage.sendVerCode_BTN().getAttribute("value").isEmpty());
        extentTestChild.log(Status.PASS, "Clicked on he Change e-mail button. Email field is now empty");
    }

    @Test
    public void incorrectPasswordFormat_Test() {
        String email = "flirautomationtest@gmail.com";
        String invalidPass1 = "passwordd";
        String invalidPass2 = "Passwordd";
        String invalidPass3 = "passwordd!!";
        String invalidPass4 = "ThisIsAReallyReallyLongPassword1!";

        extentTest = extent.createTest("SIGNUP PAGE - incorrectPasswordFormat_Test ");
        extentTestChild = extentTest.createNode("Error message is displayed if an invalid password is entered");

        goToSignUpPage();
        testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        Assert.assertEquals(signUpPage.email_field().getAttribute("value"), email);
        extentTestChild.log(Status.PASS, "Entered the email address");

        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setNewPassword(invalidPass1);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass1);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidPass_err());
        assertTrue(signUpPage.invalidPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.newPassword_field().clear();
        signUpPage.setNewPassword(invalidPass2);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass2);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidPass_err());
        assertTrue(signUpPage.invalidPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.newPassword_field().clear();
        signUpPage.setNewPassword(invalidPass3);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass3);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidPass_err());
        assertTrue(signUpPage.invalidPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.newPassword_field().clear();
        signUpPage.setNewPassword(invalidPass4);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass4);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidPass_err());
        assertTrue(signUpPage.invalidPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.newPassword_field().clear();
        signUpPage.setNewPassword("QAZxsw123");
        extentTestChild.log(Status.PASS, "Entered the following valid password: QAZxsw123");

        Assert.assertFalse(signUpPage.invalidPass_err().isDisplayed());
        extentTestChild.log(Status.PASS, "Error message is no longer displayed if a valid password is entered");

    }

    @Test
    public void incorrectPasswordFormatConfirmPassField_Test() {
        String email = "flirautomationtest@gmail.com";
        String invalidPass1 = "passwordd";
        String invalidPass2 = "Passwordd";
        String invalidPass3 = "passwordd!!";
        String invalidPass4 = "ThisIsAReallyReallyLongPassword1!";

        extentTest = extent.createTest("SIGNUP PAGE - incorrectPasswordFormatConfirmPassField_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if an invalid password is entered (Confirm password field)");

        goToSignUpPage();
        testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        Assert.assertEquals(signUpPage.email_field().getAttribute("value"), email);
        extentTestChild.log(Status.PASS, "Entered the email address");

        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setConfirmNewPassword(invalidPass1);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass1);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidConfirmPass_err());
        assertTrue(signUpPage.invalidConfirmPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.confNewPassword_field().clear();
        signUpPage.setConfirmNewPassword(invalidPass2);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass2);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidConfirmPass_err());
        assertTrue(signUpPage.invalidConfirmPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.confNewPassword_field().clear();
        signUpPage.setConfirmNewPassword(invalidPass3);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass3);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidConfirmPass_err());
        assertTrue(signUpPage.invalidConfirmPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.confNewPassword_field().clear();
        signUpPage.setConfirmNewPassword(invalidPass4);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass4);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidConfirmPass_err());
        assertTrue(signUpPage.invalidConfirmPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.confNewPassword_field().clear();
        signUpPage.setConfirmNewPassword("QAZxsw123");
        extentTestChild.log(Status.PASS, "Entered the following valid password: QAZxsw123");

        Assert.assertFalse(signUpPage.invalidConfirmPass_err().isDisplayed());
        extentTestChild.log(Status.PASS, "Error message is no longer displayed if a valid password is entered");
    }

    @Test(groups = {"smoke"})
    public void mismatchingPasswords_Test() {
        String email = "flirautomationtest@gmail.com";
        String pass1 = "PASSWORD123!";
        String pass2 = "Password123!";
        String firstName = "firstName";
        String lastName = "lastName";

        extentTest = extent.createTest("SIGNUP PAGE - mismatchingPasswords_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if the passwords are not identical");

        goToSignUpPage();
        testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Entered the email address and clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.changeEmail_BTN());
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setFirstName(firstName);
        extentTestChild.log(Status.PASS, "Entered a first name");

        signUpPage.setLastName(lastName);
        extentTestChild.log(Status.PASS, "Entered a last name");

        Select country_select = new Select(signUpPage.country_dropdown());
        Random random = new Random();
        country_select.selectByIndex(random.nextInt(country_select.getOptions().size()));
        extentTestChild.log(Status.PASS, "Selected a country from the dropdown list");

        if (random.nextInt(1) % 2 == 0) signUpPage.consentNo().click();
        else signUpPage.consentYes().click();
        extentTestChild.log(Status.PASS, "Selected randomly if I consented or not");

        signUpPage.setNewPassword(pass1);
        signUpPage.setConfirmNewPassword(pass2);
        extentTestChild.log(Status.PASS, "Entered mismatching passwords: "+pass1+" and "+pass2);

        signUpPage.createButton_click();
        testUtil.waitForElementToLoad(driver, signUpPage.passMismatch_err());
        assertTrue(signUpPage.passMismatch_err().getText().contains("match"));
        extentTestChild.log(Status.PASS, "Password mismatch error is displayed");
    }

    @Test
    public void noCountrySelected_Test() {
        String email = "flirautomationtest@gmail.com";
        String pass = "PASSWORD123!";
        String firstName = "firstName";
        String lastName = "lastName";

        extentTest = extent.createTest("SIGNUP PAGE - noCountrySelected_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if no country is selected from the dropdown list");

        goToSignUpPage();
        testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Entered the email address and clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.changeEmail_BTN());
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setFirstName(firstName);
        extentTestChild.log(Status.PASS, "Entered a first name");

        signUpPage.setLastName(lastName);
        extentTestChild.log(Status.PASS, "Entered a last name");

        Random random = new Random();
        if (random.nextInt(1) % 2 == 0) signUpPage.consentNo().click();
        else signUpPage.consentYes().click();
        extentTestChild.log(Status.PASS, "Selected randomly if I consented or not");

        signUpPage.setNewPassword(pass);
        signUpPage.setConfirmNewPassword(pass);
        extentTestChild.log(Status.PASS, "Entered matching passwords");

        signUpPage.createButton_click();
        testUtil.waitForElementToLoad(driver, signUpPage.blankCountry_err());
        assertTrue(signUpPage.blankCountry_err().getText().contains("Missing required element: Country/Region"));
        extentTestChild.log(Status.PASS, "No country was selected error message is displayed");
    }

    @Test(groups = {"smoke"})
    public void noConsent_Test() {
        String email = "flirautomationtest@gmail.com";
        String pass = "PASSWORD123!";
        String firstName = "firstName";
        String lastName = "lastName";
        String error_msg = "A required field is missing. Please fill out all required fields and try again.";

        extentTest = extent.createTest("SIGNUP PAGE - noConsent_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if no consent option is selected");

        goToSignUpPage();
        testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Entered the email address and clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.changeEmail_BTN());
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setFirstName(firstName);
        extentTestChild.log(Status.PASS, "Entered a first name");

        signUpPage.setLastName(lastName);
        extentTestChild.log(Status.PASS, "Entered a last name");

        signUpPage.setNewPassword(pass);
        signUpPage.setConfirmNewPassword(pass);
        extentTestChild.log(Status.PASS, "Entered matching passwords");

        Select country_select = new Select(signUpPage.country_dropdown());
        Random random = new Random();
        country_select.selectByIndex(random.nextInt(country_select.getOptions().size()));
        extentTestChild.log(Status.PASS, "Selected a country from the dropdown list");

        signUpPage.createButton_click();
        testUtil.waitForElementToLoad(driver, signUpPage.requiredFieldMissing_err());
        assertTrue(signUpPage.requiredFieldMissing_err().getText().contains(error_msg));
        extentTestChild.log(Status.PASS, "Mandatory field is missing error message is displayed");
    }

    @Test
    public void noFirstName_Test() {
        String email = "flirautomationtest@gmail.com";
        String pass = "PASSWORD123!";
        String lastName = "lastName";
        String error_msg = "A required field is missing. Please fill out all required fields and try again.";

        extentTest = extent.createTest("SIGNUP PAGE - noFirstName_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if the first Name field is left blank");

        goToSignUpPage();
        testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Entered the email address and clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.changeEmail_BTN());
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setLastName(lastName);
        extentTestChild.log(Status.PASS, "Entered a last name");

        signUpPage.setNewPassword(pass);
        signUpPage.setConfirmNewPassword(pass);
        extentTestChild.log(Status.PASS, "Entered matching passwords");

        Select country_select = new Select(signUpPage.country_dropdown());
        Random random = new Random();
        country_select.selectByIndex(random.nextInt(country_select.getOptions().size()));
        extentTestChild.log(Status.PASS, "Selected a country from the dropdown list");

        if (random.nextInt(1) % 2 == 0) signUpPage.consentNo().click();
        else signUpPage.consentYes().click();
        extentTestChild.log(Status.PASS, "Selected randomly if I consented or not");

        signUpPage.createButton_click();
        testUtil.waitForElementToLoad(driver, signUpPage.requiredFieldMissing_err());
        assertTrue(signUpPage.requiredFieldMissing_err().getText().contains(error_msg));
        extentTestChild.log(Status.PASS, "Mandatory field is missing error message is displayed: "+error_msg);
    }

    @Test
    public void noLastName_Test() {
        String email = "flirautomationtest@gmail.com";
        String pass = "PASSWORD123!";
        String firstName = "firstName";
        String error_msg = "A required field is missing. Please fill out all required fields and try again.";

        extentTest = extent.createTest("SIGNUP PAGE - noLastName_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if the last Name field is left blank");

        goToSignUpPage();
        testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Entered the email address and clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.changeEmail_BTN());
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setFirstName(firstName);
        extentTestChild.log(Status.PASS, "Entered a last name");

        signUpPage.setNewPassword(pass);
        signUpPage.setConfirmNewPassword(pass);
        extentTestChild.log(Status.PASS, "Entered matching passwords");

        Select country_select = new Select(signUpPage.country_dropdown());
        Random random = new Random();
        country_select.selectByIndex(random.nextInt(country_select.getOptions().size()));
        extentTestChild.log(Status.PASS, "Selected a country from the dropdown list");

        if (random.nextInt(1) % 2 == 0) signUpPage.consentNo().click();
        else signUpPage.consentYes().click();
        extentTestChild.log(Status.PASS, "Selected randomly if I consented or not");

        signUpPage.createButton_click();
        testUtil.waitForElementToLoad(driver, signUpPage.requiredFieldMissing_err());
        assertTrue(signUpPage.requiredFieldMissing_err().getText().contains(error_msg));
        extentTestChild.log(Status.PASS, "Mandatory field is missing error message is displayed: "+error_msg);
    }

    @Test
    public void cancelRegistration_Test() {
        String email = "flirautomationtest@gmail.com";
        String pass = "PASSWORD123!";
        String firstName = "firstName";
        String lastName = "lastName";

        extentTest = extent.createTest("SIGNUP PAGE - cancelRegistration_Test");
        extentTestChild = extentTest.createNode("SignUp Flow is cancelled and user is redirected to the landing page");

        goToSignUpPage();
        testUtil.getTokenFromGmail();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email);
        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Entered the email address and clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(testUtil.getTokenFromGmail());
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.changeEmail_BTN());
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setFirstName(firstName);
        extentTestChild.log(Status.PASS, "Entered a last name");

        signUpPage.setLastName(lastName);
        extentTestChild.log(Status.PASS, "Entered a last name");

        signUpPage.setNewPassword(pass);
        signUpPage.setConfirmNewPassword(pass);
        extentTestChild.log(Status.PASS, "Entered matching passwords");

        Select country_select = new Select(signUpPage.country_dropdown());
        Random random = new Random();
        country_select.selectByIndex(random.nextInt(country_select.getOptions().size()));
        extentTestChild.log(Status.PASS, "Selected a country from the dropdown list");

        if (random.nextInt(1) % 2 == 0) signUpPage.consentNo().click();
        else signUpPage.consentYes().click();
        extentTestChild.log(Status.PASS, "Selected randomly if I consented or not");

        signUpPage.cancel_BTN().click();
        testUtil.waitForElementToLoad(driver, landingPage.login_BTN());
        extentTestChild.log(Status.PASS, "Clicked and the Cancel button and was redirected to Landing page");
    }

}