package testCases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestUtil;

import static utils.TestUtil.getTestaData;

public class LoginPageTest extends TestBase {
    private LandingPage landingPage;
    private LoginPage loginPage;
    private RecoverPasswordPage recoverPasswordPage;
    private LibraryPage libraryPage;
    private TestUtil testUtil;
    private String sheetName = "Sheet1";
    private String fileName = "InvalidEmails";

    public LoginPageTest() {
        super();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
        recoverPasswordPage = new RecoverPasswordPage();
        libraryPage = new LibraryPage();
        testUtil = new TestUtil();
    }

    public void verifyInvalidEmails(Object[][] invalidEmailsList) {
        String error_msg = "Please enter a valid email address";

        for (int i=1;i<invalidEmailsList.length;i++) {
            loginPage.email_field().clear();
            loginPage.setInvalidEmail(invalidEmailsList[i][0].toString());
            addTestCaseStep("Entered the following invalid email: " + invalidEmailsList[i][0].toString());
            testUtil.waitForElementToLoad(loginPage.invalidEmailErrorMsg());
            checkIfCorrectErrMsg(loginPage.invalidEmailErrorMsg(), error_msg);
            addTestCaseStep("Error message is displayed: " + error_msg);
        }
    }

    public void loadLandingPage() {
        try {
            waitForElementToBeClickable(landingPage.signup_BTN());
        } catch (Exception e) {
            System.out.println("------------Page timed out. Refreshing...");
            driver.navigate().refresh();
            waitForElementToBeClickable(landingPage.signup_BTN());
        }
    }

    public void goToLoginPage() {
        loadLandingPage();
        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        addTestCaseStep("Navigated to Landing page");

        loginPage = landingPage.clickOn_loginBTN();
        testUtil.waitForElementToLoad(loginPage.signIn_BTN());
        addTestCaseStep("Navigated to Login page");
    }

    public void sendBlankPassword() {
        String error_msg = "Please enter your password";
        loginPage.pass_field().clear();
        loginPage.pass_field().sendKeys("");
        loginPage.signIn_BTN().click();
        addTestCaseStep("Left the password field blank and clicked on the Sign In button");

        testUtil.waitForElementToLoad(loginPage.invalidPassErrorMsg());
        checkIfCorrectErrMsg(loginPage.invalidPassErrorMsg(), error_msg);
        addTestCaseStep("Error message is displayed: " + error_msg);
    }

    public void sendInvalidPassword(String invalidPass) {
        String error_msg = "Your password is incorrect.";

        loginPage.pass_field().clear();
        loginPage.pass_field().sendKeys(invalidPass);
        loginPage.signIn_BTN().click();
        addTestCaseStep("Entered an incorrect password: " + invalidPass + " and then clicked on the Sign In button");

        testUtil.waitForElementToLoad(loginPage.incorrectPassErrorMsg());
        checkIfCorrectErrMsg(loginPage.incorrectPassErrorMsg(), error_msg);
        addTestCaseStep("Error message is displayed: " + error_msg);
    }

    public void loginWithNonExistingCredentials(String email, String pass) {
        String error_msg = "We can't seem to find your account.";
        loginPage.email_field().sendKeys(email);
        loginPage.pass_field().sendKeys(pass);
        loginPage.signIn_BTN().click();
        addTestCaseStep("Entered credentials for a non existing account and clicked on Sign In button");

        testUtil.waitForElementToLoad(loginPage.nonExistingAccountMsg());
        checkIfCorrectErrMsg(loginPage.nonExistingAccountMsg(), error_msg);
        addTestCaseStep("Error message is displayed: " + error_msg);
    }

    public void loginWithValidCredentials(String email, String pass) {
        libraryPage = loginPage.login(email, pass);
        addTestCaseStep("Entered email: " + email + " and password: " + pass + " then clicked on the Sign In button");

        testUtil.waitForElementToLoad(libraryPage.getNewFolder_btn());
        Assert.assertEquals(libraryPage.getPageTitle(), "FLIR Tools");
        addTestCaseStep("Logged in successfully. Library page is displayed");
    }

    public void goToSignUpPage(){
        SignUpPage signUpPage = loginPage.clickOn_signUpLink();
        addTestCaseStep("Clicked on the Sign Up button");

        testUtil.waitForElementToLoad(signUpPage.create_BTN());
        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
        addTestCaseStep("Sign Up Page is displayed");
    }

    public void goToRecoverPassPage(){
        recoverPasswordPage = loginPage.clickOn_forgotPasswordLink();
        addTestCaseStep("Clicked on Forgot your password link");

        testUtil.waitForElementToLoad(recoverPasswordPage.email_field());
        Assert.assertEquals(recoverPasswordPage.getPageTitle(), "FLIR Reset Password");
        addTestCaseStep("Recover password page is displayed");
    }

    @Test
    public void title_Test() {
        String testCaseTitle = "LOGIN PAGE - title_Test";
        String testCaseDescription = "Verify the page title";

        createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        Assert.assertEquals(loginPage.getPageTitle(), "FLIR Log in");
        addTestCaseStep("Page title is: " + recoverPasswordPage.getPageTitle());
    }

    @Test
    public void invalidEmail_Test() {
        String testCaseTitle = "LOGIN PAGE - invalidEmail_Test";
        String testCaseDescription = "Error message is displayed if an invalid email address is entered";
        Object[][] invalidEmailsList = getTestaData(fileName, sheetName);

        createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        verifyInvalidEmails(invalidEmailsList);
    }

    @Test
    public void blankPassword_Test() {
        String testCaseTitle = "LOGIN PAGE - blankPassword_Test";
        String testCaseDescription = "Error message is displayed if an incorrect password is entered";
        String email = "email@test.com";

        createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        loginPage.email_field().sendKeys(email);
        addTestCaseStep("Entered a valid email: " + email);

        sendBlankPassword();
    }

    @Test
    public void incorrectPassword_Test() {
        String testCaseTitle = "LOGIN PAGE - incorrectPassword_Test";
        String testCaseDescription = "Error message is displayed if an incorrect password is entered";
        String email = "flirtest1@mailinator.com";
        String incorrectPass = "thisIsNotTheCorrectPass";

        createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        loginPage.email_field().sendKeys(email);
        addTestCaseStep("Entered a valid email: " + email);

        sendInvalidPassword(incorrectPass);
    }

    @Test(groups = {"smoke"})
    public void loginWithNonExistingAccount_Test() {
        String testCaseTitle = "LOGIN PAGE - loginWithNonExistingAccount_Test";
        String testCaseDescription = "Error message is displayed when logging in with a email account who doesn't have an account associated";
        String randomEmail = "randomEmail@mailinator.com";
        String randomPass = "thisIsNotTheCorrectPass";

        createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        loginWithNonExistingCredentials(randomEmail, randomPass);
    }

    @Test(groups = {"smoke"})
    public void loginWithValidCredentials_Test() {
        String testCaseTitle = "LOGIN PAGE - loginWithValidCredentials_Test";
        String testCaseDescription = "Login with valid credentials";
        String email = "flirtest1@mailinator.com";
        String pass = "QAZxsw123";

        createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        loginWithValidCredentials(email, pass);
    }

    @Test(groups = {"smoke"})
    public void clickSignUpLink_Test() {
        String testCaseTitle = "LOGIN PAGE - clickSignUpLink_Test";
        String testCaseDescription = "Clicking on the SignUp link redirects to Sign Up page";

        createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        goToSignUpPage();

    }

    @Test(groups = {"smoke"})
    public void clickForgotPasswordLink_Test() {
        String testCaseTitle = "LOGIN PAGE - clickForgotPasswordLink_Test";
        String testCaseDescription = "Clicking on the Forgot Password link redirects to Recover password page";

        createTestCase(testCaseTitle, testCaseDescription);

        goToLoginPage();

        goToRecoverPassPage();
    }

}
