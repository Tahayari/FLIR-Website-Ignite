package testCases;

import base.TestBase;
import com.aventstack.extentreports.Status;
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

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
        testUtil = new TestUtil();
    }

    public void goToSignUpPage() {

        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Navigated to Landing page");

        signUpPage = landingPage.signUp_btn_click();
        testUtil.waitForElementToLoad(driver, signUpPage.create_BTN());
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
        return TestUtil.getTestaData(sheetName);
    }

    @Test(dataProvider = "getTestData", groups = {"smoke", "regression"})
    public void registerNewAccount_Test(String email, String password, String firstName, String lastName) {

        extentTest = extent.createTest("SIGNUP PAGE - Create account(s)");
        extentTestChild = extentTest.createNode("Create account(s)");

        goToSignUpPage();

        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email + "@mailinator.com");
        Assert.assertEquals(signUpPage.email_field().getAttribute("value"), email + "@mailinator.com");
        extentTestChild.log(Status.PASS, "Entered the email address");

        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        Assert.assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(TestUtil.getTokenFromMailinator(email));
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setNewPassword(password);
        extentTestChild.log(Status.PASS, "Entered a password");

        signUpPage.setConfirmNewPassword(password);
        extentTestChild.log(Status.PASS, "Re-entered the same password in the Confirm New Password field");

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

        LibraryPage libraryPage = signUpPage.createButton_click();
        testUtil.waitForElementToLoad(driver, libraryPage.getTermsAndConditions());
        Assert.assertTrue(libraryPage.getTermsAndConditions().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Created a new account, Terms and conditions page is displayed");

        //TODO: to add accept T&C and skip over the tutorial/welcome screen?
    }

    @Test()
    public void blankEmail_Test() {
        extentTest = extent.createTest("SIGNUP PAGE - Leave the email field blank");
        extentTestChild = extentTest.createNode("Leave the email field blank");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Left the email field blank and clicked on the Send Verification code button");


        testUtil.waitForElementToLoad(driver, signUpPage.invalidEmail_err());
        Assert.assertEquals(signUpPage.invalidEmail_err().getText(), "Please enter a valid email address.");
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }

    @Test(groups = {"smoke"})
    public void invalidEmail_Test() {
        String expectedMsg = "Please enter a valid email address.";

        extentTest = extent.createTest("SIGNUP PAGE - Enter an email that has an invalid format");
        extentTestChild = extentTest.createNode("Enter an email that has an invalid format");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress("test123@");
        extentTestChild.log(Status.PASS, "Entered an invalid email address");

        testUtil.waitForElementToLoad(driver, signUpPage.invalidEmail_err());
        Assert.assertEquals(signUpPage.invalidEmail_err().getText(), expectedMsg);
        extentTestChild.log(Status.PASS, "Invalid email address error message is displayed");
    }

    @Test(groups = {"smoke"})
    public void invalidToken_Test() {
        String email = "testemail@mailinator.com";
        String error_msg = "That code is incorrect. Please try again.";

        extentTest = extent.createTest("SIGNUP PAGE - Enter an invalid token");
        extentTestChild = extentTest.createNode("Enter an invalid token");

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
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }

    @Test
    public void expiredToken_Test() {
        String email = "testemail";
        String error_msg = "That code is expired. Please request a new code.";
        long waitTime = 5; // Number of MINUTES until the token expires

        extentTest = extent.createTest("SIGNUP PAGE - Enter an expired token");
        extentTestChild = extentTest.createNode("Enter an expired token");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email+"@mailinator.com");
        extentTestChild.log(Status.PASS, "Entered an email address");

        signUpPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Verified that the Send code button is displayed");

        signUpPage.setVerificationCode_field(TestUtil.getTokenFromMailinator(email));
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
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }

    @Test(groups = {"smoke"})
    public void sendNewCode_Test() {
        String email = "flirtestemail08";
        String error_msg = "That code is incorrect. Please try again.";

        extentTest = extent.createTest("SIGNUP PAGE - Resend the token and validate the new one");
        extentTestChild = extentTest.createNode("Resend the token and validate the new one");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email + "@mailinator.com");
        extentTestChild.log(Status.PASS, "Entered an email address");

        signUpPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Verified that the Send code button is displayed");

        Assert.assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(TestUtil.getTokenFromMailinator(email));
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        String oldToken = signUpPage.verificationCode_field().getAttribute("value"); //save this token as it will be reset soon
        signUpPage.sendNewCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Send new code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        Assert.assertEquals(signUpPage.verificationCode_field().getText(), "");
        extentTestChild.log(Status.PASS, "New code was generated");

        signUpPage.verificationCode_field().sendKeys(oldToken);
        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.incorrectVerCode_err());
        Assert.assertEquals(signUpPage.incorrectVerCode_err().getText(), error_msg);
        extentTestChild.log(Status.PASS, "Entered the previous token and it no longer works");

        try {
            //lazy solution for waiting for the new token to be resent TODO: improve this implementation in TestUtil class!!
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        signUpPage.verificationCode_field().clear();
        signUpPage.setVerificationCode_field(TestUtil.getTokenFromMailinator(email));
        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.changeEmail_BTN());
        Assert.assertTrue(signUpPage.changeEmail_BTN().isDisplayed());
        extentTestChild.log(Status.PASS, "Entered the latest token received via email");
    }

    @Test
    public void resendEmail_Test() {
        String email = "someRandomEmail";

        extentTest = extent.createTest("SIGNUP PAGE - Change the email after validating the token");
        extentTestChild = extentTest.createNode("Change the email after validating the token");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email + "@mailinator.com");
        extentTestChild.log(Status.PASS, "Entered an email address");

        signUpPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Verified that the Send code button is displayed");

        signUpPage.setVerificationCode_field(TestUtil.getTokenFromMailinator(email));
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.changeEmail_BTN());
        Assert.assertTrue(signUpPage.changeEmail_BTN().isDisplayed());
        extentTestChild.log(Status.PASS, "Token was verified. Change e-mail button is displayed");

        signUpPage.changeEmail_BTN().click();
        testUtil.waitForElementToLoad(driver, signUpPage.sendVerCode_BTN());
        Assert.assertTrue(signUpPage.sendVerCode_BTN().isDisplayed());
        Assert.assertTrue(signUpPage.sendVerCode_BTN().getAttribute("value").isEmpty());
        extentTestChild.log(Status.PASS, "Clicked on he Change e-mail button. Email field is now empty");
    }

    @Test
    public void incorrectPasswordFormat_Test() {
        String email = "dummyEmailForFlir";
        String invalidPass1 = "passwordd";
        String invalidPass2 = "Passwordd";
        String invalidPass3 = "passwordd!!";
        String invalidPass4 = "ThisIsAReallyReallyLongPassword1!";

        extentTest = extent.createTest("SIGNUP PAGE - Enter an invalid password ");
        extentTestChild = extentTest.createNode("Error message is displayed if an invalid password is entered");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email + "@mailinator.com");
        Assert.assertEquals(signUpPage.email_field().getAttribute("value"), email + "@mailinator.com");
        extentTestChild.log(Status.PASS, "Entered the email address");

        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        Assert.assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(TestUtil.getTokenFromMailinator(email));
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setNewPassword(invalidPass1);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass1);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidPass_err());
        Assert.assertTrue(signUpPage.invalidPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.newPassword_field().clear();
        signUpPage.setNewPassword(invalidPass2);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass2);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidPass_err());
        Assert.assertTrue(signUpPage.invalidPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.newPassword_field().clear();
        signUpPage.setNewPassword(invalidPass3);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass3);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidPass_err());
        Assert.assertTrue(signUpPage.invalidPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.newPassword_field().clear();
        signUpPage.setNewPassword(invalidPass4);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass4);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidPass_err());
        Assert.assertTrue(signUpPage.invalidPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.newPassword_field().clear();
        signUpPage.setNewPassword("QAZxsw123");
        extentTestChild.log(Status.PASS, "Entered the following valid password: QAZxsw123");

        Assert.assertFalse(signUpPage.invalidPass_err().isDisplayed());
        extentTestChild.log(Status.PASS, "Error message is no longer displayed if a valid password is entered");

    }

    @Test
    public void incorrectPasswordFormatConfirmPassField_Test() {
        String email = "dummyEmailForFlir";
        String invalidPass1 = "passwordd";
        String invalidPass2 = "Passwordd";
        String invalidPass3 = "passwordd!!";
        String invalidPass4 = "ThisIsAReallyReallyLongPassword1!";

        extentTest = extent.createTest("SIGNUP PAGE - Enter an invalid password (Confirm password field)");
        extentTestChild = extentTest.createNode("Error message is displayed if an invalid password is entered (Confirm password field)");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email + "@mailinator.com");
        Assert.assertEquals(signUpPage.email_field().getAttribute("value"), email + "@mailinator.com");
        extentTestChild.log(Status.PASS, "Entered the email address");

        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        Assert.assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(TestUtil.getTokenFromMailinator(email));
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on Verify code button");

        signUpPage.setConfirmNewPassword(invalidPass1);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass1);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidConfirmPass_err());
        Assert.assertTrue(signUpPage.invalidConfirmPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.confNewPassword_field().clear();
        signUpPage.setConfirmNewPassword(invalidPass2);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass2);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidConfirmPass_err());
        Assert.assertTrue(signUpPage.invalidConfirmPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.confNewPassword_field().clear();
        signUpPage.setConfirmNewPassword(invalidPass3);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass3);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidConfirmPass_err());
        Assert.assertTrue(signUpPage.invalidConfirmPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.confNewPassword_field().clear();
        signUpPage.setConfirmNewPassword(invalidPass4);
        extentTestChild.log(Status.PASS, "Entered the following password: " + invalidPass4);

        testUtil.waitForElementToLoad(driver, signUpPage.invalidConfirmPass_err());
        Assert.assertTrue(signUpPage.invalidConfirmPass_err().getText().contains("8-16 characters"));
        extentTestChild.log(Status.PASS, "Error message is displayed");

        signUpPage.confNewPassword_field().clear();
        signUpPage.setConfirmNewPassword("QAZxsw123");
        extentTestChild.log(Status.PASS, "Entered the following valid password: QAZxsw123");

        Assert.assertFalse(signUpPage.invalidConfirmPass_err().isDisplayed());
        extentTestChild.log(Status.PASS, "Error message is no longer displayed if a valid password is entered");
    }

    @Test
    public void mismatchingPasswords_Test() {
        String email = "dummyEmailForFlir";
        String pass1 = "PASSWORD123!";
        String pass2 = "Password123!";
        String firstName = "firstName";
        String lastName = "lastName";

        extentTest = extent.createTest("SIGNUP PAGE - Enter mismatching passwords");
        extentTestChild = extentTest.createNode("Error message is displayed if the passwords are not identical");

        goToSignUpPage();
        extentTestChild.log(Status.PASS, "Navigated to SignUp Page");

        signUpPage.setEmailAddress(email + "@mailinator.com");
        signUpPage.sendVerCode_BTN().click();
        extentTestChild.log(Status.PASS, "Entered the email address and clicked on Send verification code button");

        testUtil.waitForElementToLoad(driver, signUpPage.verificationCode_field());
        Assert.assertTrue(signUpPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        signUpPage.setVerificationCode_field(TestUtil.getTokenFromMailinator(email));
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        signUpPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver,signUpPage.changeEmail_BTN());
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
        extentTestChild.log(Status.PASS, "Entered mismatching passwords");

        signUpPage.createButton_click();
        testUtil.waitForElementToLoad(driver, signUpPage.passMismatch_err());
        Assert.assertTrue(signUpPage.passMismatch_err().getText().contains("match"));
        extentTestChild.log(Status.PASS, "Password mismatch error is displayed");
    }

    //TODO: noCountrySelected_Test
    //TODO: noConsent_Test
    //TODO: noFirstName_Test
    //TODO: noLastName_Test
    //TODO: declineTOS_Test

}