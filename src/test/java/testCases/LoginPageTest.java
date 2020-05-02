package testCases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.CommonVerification;
import utils.ExtentReport;

import static pages.LandingPage.getLandingPage;
import static pages.LoginPage.getLoginPage;
import static utils.CommonVerification.getCommonVerification;
import static utils.TestUtil.getTestaData;

public class LoginPageTest extends TestBase {
    LandingPage landingPage = getLandingPage();
    LoginPage loginPage = getLoginPage();
    CommonVerification commonVerification = getCommonVerification(); //TODO rename this to something more likeable

    private void goToLoginPage() {
        commonVerification.verifyIsDisplayed(landingPage.signup_BTN());
        ExtentReport.addTestCaseStep("Navigated to the Landing page");
        landingPage.clickOn_loginBTN();
        commonVerification.verifyIsDisplayed(loginPage.signIn_BTN());
        ExtentReport.addTestCaseStep("Navigated to Login page");
    }

    // Test cases begin here------------------------------------------------------------
    @Test(enabled = false) /*For testing purposes*/
    public void title_Test() {
        String testCaseTitle = "LOGIN PAGE - title_Test";
        String testCaseDescription = "Verify the page title";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        Assert.assertEquals(loginPage.getPageTitle(), "FLIR Log in");
        ExtentReport.addTestCaseStep("Page title is: " + loginPage.getPageTitle());
    }

    @Test
    public void invalidEmail_Test() {
        String testCaseTitle = "LOGIN PAGE - invalidEmail_Test";
        String testCaseDescription = "Error message is displayed if an invalid email address is entered";
        String fileName = "InvalidEmails";
        String sheetName = "Sheet1";
        Object[][] invalidEmailsList = getTestaData(fileName, sheetName);

        extentReport.createTestCase(testCaseTitle, testCaseDescription);
        goToLoginPage();

        for (int i = 1; i < invalidEmailsList.length; i++) {
            loginPage.clearField(loginPage.email_field())
                    .setEmail(invalidEmailsList[i][0].toString())
                    .clickOn_signInBTN();

            commonVerification.verifyIsDisplayed(loginPage.invalidEmailError_Msg())
                    .getTextFromElement(loginPage.invalidEmailError_Msg());
        }
    }

    @Test
    public void blankPassword_Test() {
        String testCaseTitle = "LOGIN PAGE - blankPassword_Test";
        String testCaseDescription = "Error message is displayed if an incorrect password is entered";
        String email = "email@test.com";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);
        goToLoginPage();

        loginPage.setEmail(email)
                .setPass("")
                .clickOn_signInBTN();

        commonVerification.verifyIsDisplayed(loginPage.invalidPassError_Msg())
                .getTextFromElement(loginPage.invalidPassError_Msg());
    }

    @Test
    public void incorrectPassword_Test() {
        String testCaseTitle = "LOGIN PAGE - incorrectPassword_Test";
        String testCaseDescription = "Error message is displayed if an incorrect password is entered";
        String email = "flirtest1@mailinator.com";
        String incorrectPass = "thisIsNotTheCorrectPass";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);
        goToLoginPage();

        loginPage.setEmail(email)
                .setPass(incorrectPass)
                .clickOn_signInBTN();

        commonVerification.verifyIsDisplayed(loginPage.incorrectPassError_Msg())
                .getTextFromElement(loginPage.incorrectPassError_Msg());
    }

    @Test(groups = {"smoke"})
    public void loginWithNonExistingAccount_Test() {
        String testCaseTitle = "LOGIN PAGE - loginWithNonExistingAccount_Test";
        String testCaseDescription = "Error message is displayed when logging in with a email account who doesn't have an account associated";
        String randomEmail = "randomEmail@mailinator.com";
        String randomPass = "thisIsNotTheCorrectPass";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);
        goToLoginPage();

        loginPage.setEmail(randomEmail)
                .setPass(randomPass)
                .clickOn_signInBTN();

        commonVerification.verifyIsDisplayed(loginPage.nonExistingAccount_Msg())
                .getTextFromElement(loginPage.nonExistingAccount_Msg());
    }

    @Test(groups = {"smoke"}, priority = 100) /*execute this TestCase last*/
    public void loginWithValidCredentials_Test() {
        String testCaseTitle = "LOGIN PAGE - loginWithValidCredentials_Test";
        String testCaseDescription = "Login with valid credentials";
        String email = "flirtest1@mailinator.com";
        String pass = "QAZxsw123";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);
        goToLoginPage();

        loginPage.setEmail(email)
                .setPass(pass)
                .clickOn_signInBTN();

        LibraryPage libraryPage = new LibraryPage();
        commonVerification.verifyIsDisplayed(libraryPage.newFolder_BTN());
    }

    @Test(groups = {"smoke"})
    public void clickSignUpLink_Test() {
        String testCaseTitle = "LOGIN PAGE - clickSignUpLink_Test";
        String testCaseDescription = "Clicking on the SignUp link redirects to Sign Up page";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);
        goToLoginPage();

        loginPage.clickOn_signUpLink();

        SignUpPage signUpPage = SignUpPage.getSignUpPage();
        commonVerification.verifySignUpPageLoaded(signUpPage);
    }

    @Test(groups = {"smoke"})
    public void clickForgotPasswordLink_Test() {
        String testCaseTitle = "LOGIN PAGE - clickForgotPasswordLink_Test";
        String testCaseDescription = "Clicking on the Forgot Password link redirects to Recover password page";

        extentReport.createTestCase(testCaseTitle, testCaseDescription);
        goToLoginPage();

        loginPage.clickOn_forgotPasswordLink();
        RecoverPasswordPage recoverPasswordPage = new RecoverPasswordPage();
        commonVerification.verifyRecoverPassPageLoaded(recoverPasswordPage);
    }

}
