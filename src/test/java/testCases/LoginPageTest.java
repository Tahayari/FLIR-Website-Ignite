package testCases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.CommonVerification;
import utils.TestUtil;

import static pages.LandingPage.getLandingPage;
import static pages.LoginPage.getLoginPage;
import static utils.CommonVerification.getCommonVerification;
import static utils.TestUtil.getTestaData;

public class LoginPageTest extends TestBase {
    LandingPage landingPage = getLandingPage(); //TODO apply builder pattern for the rest of the pages
    LoginPage loginPage = getLoginPage();
    RecoverPasswordPage recoverPasswordPage = new RecoverPasswordPage();
    LibraryPage libraryPage = new LibraryPage();
    CommonVerification commonVerification = getCommonVerification(); //TODO rename this to something more likeable
    TestUtil testUtil = new TestUtil(); //TODO is it really final?
    private String sheetName = "Sheet1";
    private String fileName = "InvalidEmails";

    private void verifyInvalidEmails(Object[][] invalidEmailsList) {

        for (int i=1;i<invalidEmailsList.length;i++) {
            loginPage.email_field().clear();
            loginPage.setInvalidEmail(invalidEmailsList[i][0].toString());
            extentReport.addTestCaseStep("Entered the following invalid email: " + invalidEmailsList[i][0].toString());
            commonVerification.verifyIsDisplayed(loginPage.invalidEmailError_Msg());
            extentReport.addTestCaseStep("Error message is displayed: " + loginPage.invalidEmailError_Msg().getText());
        }
    }

    private void goToLoginPage() {
        landingPage.clickOn_loginBTN();
        commonVerification.verifyIsDisplayed(loginPage.signIn_BTN());
        extentReport.addTestCaseStep("Navigated to Login page");
    }

    private void sendInvalidPassword(String invalidPass) {
        String error_msg = "Your password is incorrect.";

        loginPage.pass_field().clear();
        loginPage.pass_field().sendKeys(invalidPass);
        loginPage.signIn_BTN().click();
        extentReport.addTestCaseStep("Entered an incorrect password: " + invalidPass + " and then clicked on the Sign In button");

        testUtil.waitForElementToLoad(loginPage.incorrectPassError_Msg());
        checkIfCorrectErrMsg(loginPage.incorrectPassError_Msg(), error_msg);
        extentReport.addTestCaseStep("Error message is displayed: " + error_msg);
    }

    private void loginWithNonExistingCredentials(String email, String pass) {
        String error_msg = "We can't seem to find your account.";
        loginPage.email_field().sendKeys(email);
        loginPage.pass_field().sendKeys(pass);
        loginPage.signIn_BTN().click();
        extentReport.addTestCaseStep("Entered credentials for a non existing account and clicked on Sign In button");

        testUtil.waitForElementToLoad(loginPage.nonExistingAccount_Msg());
        checkIfCorrectErrMsg(loginPage.nonExistingAccount_Msg(), error_msg);
        extentReport.addTestCaseStep("Error message is displayed: " + error_msg);
    }

    private void loginWithValidCredentials(String email, String pass) {
        libraryPage = loginPage.login(email, pass);
        extentReport.addTestCaseStep("Entered email: " + email + " and password: " + pass + " then clicked on the Sign In button");

        testUtil.waitForElementToLoad(libraryPage.newFolder_BTN());
        Assert.assertTrue(libraryPage.newFolder_BTN().isDisplayed());
        extentReport.addTestCaseStep("Logged in successfully. Library page is displayed");
    }

    private void goToSignUpPage(){
        SignUpPage signUpPage = loginPage.clickOn_signUpLink();
        extentReport.addTestCaseStep("Clicked on the Sign Up button");

        testUtil.waitForElementToLoad(signUpPage.create_BTN());
        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
        extentReport.addTestCaseStep("Sign Up Page is displayed");
    }

    public void goToRecoverPassPage(){
        recoverPasswordPage = loginPage.clickOn_forgotPasswordLink();
        extentReport.addTestCaseStep("Clicked on Forgot your password link");

        testUtil.waitForElementToLoad(recoverPasswordPage.email_field());
        Assert.assertEquals(recoverPasswordPage.getPageTitle(), "FLIR Reset Password");
        extentReport.addTestCaseStep("Recover password page is displayed");
    }

    // Test cases begin here------------------------------------------------------------
    @Test(enabled = false)
    public void title_Test() {
        String testCaseTitle = "LOGIN PAGE - title_Test";
        String testCaseDescription = "Verify the page title";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        Assert.assertEquals(loginPage.getPageTitle(), "FLIR Log in");
        extentReport.addTestCaseStep("Page title is: " + loginPage.getPageTitle());
    }

    @Test
    public void invalidEmail_Test() {
        String testCaseTitle = "LOGIN PAGE - invalidEmail_Test";
        String testCaseDescription = "Error message is displayed if an invalid email address is entered";
        Object[][] invalidEmailsList = getTestaData(fileName, sheetName);

        extentReport.createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        verifyInvalidEmails(invalidEmailsList);
    }

    @Test
    public void blankPassword_Test() {
        String testCaseTitle = "LOGIN PAGE - blankPassword_Test";
        String testCaseDescription = "Error message is displayed if an incorrect password is entered";
        String email = "email@test.com";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        loginPage.setEmail(email);
        extentReport.addTestCaseStep("Entered a valid email: " + email);

        loginPage.setPass("");
        extentReport.addTestCaseStep("Left the password field blank and clicked on the Sign In button");

        commonVerification.verifyIsDisplayed(loginPage.invalidPassError_Msg());
        Assert.assertTrue(false);
        extentReport.addTestCaseStep("Error message is displayed: " + loginPage.invalidPassError_Msg().getAttribute("value"));
        //TODO getValue does not return the error string message
    }

    @Test
    public void incorrectPassword_Test() {
        String testCaseTitle = "LOGIN PAGE - incorrectPassword_Test";
        String testCaseDescription = "Error message is displayed if an incorrect password is entered";
        String email = "flirtest1@mailinator.com";
        String incorrectPass = "thisIsNotTheCorrectPass";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        loginPage.email_field().sendKeys(email);
        extentReport.addTestCaseStep("Entered a valid email: " + email);

        sendInvalidPassword(incorrectPass);
    }

    @Test(groups = {"smoke"})
    public void loginWithNonExistingAccount_Test() {
        String testCaseTitle = "LOGIN PAGE - loginWithNonExistingAccount_Test";
        String testCaseDescription = "Error message is displayed when logging in with a email account who doesn't have an account associated";
        String randomEmail = "randomEmail@mailinator.com";
        String randomPass = "thisIsNotTheCorrectPass";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        loginWithNonExistingCredentials(randomEmail, randomPass);
    }

    @Test(groups = {"smoke"})
    public void loginWithValidCredentials_Test() {
        String testCaseTitle = "LOGIN PAGE - loginWithValidCredentials_Test";
        String testCaseDescription = "Login with valid credentials";
        String email = "flirtest1@mailinator.com";
        String pass = "QAZxsw123";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        loginWithValidCredentials(email, pass);
//        loginPage.logout();
    }

    @Test(groups = {"smoke"})
    public void clickSignUpLink_Test() {
        String testCaseTitle = "LOGIN PAGE - clickSignUpLink_Test";
        String testCaseDescription = "Clicking on the SignUp link redirects to Sign Up page";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        goToSignUpPage();

    }

    @Test(groups = {"smoke"})
    public void clickForgotPasswordLink_Test() {
        String testCaseTitle = "LOGIN PAGE - clickForgotPasswordLink_Test";
        String testCaseDescription = "Clicking on the Forgot Password link redirects to Recover password page";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        goToRecoverPassPage();
    }

}
