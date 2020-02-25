package testCases;

import base.TestBase;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestUtil;

public class LoginPageTest extends TestBase {
    private LandingPage landingPage;
    private LoginPage loginPage;
    private RecoverPasswordPage recoverPasswordPage;
    private TestUtil testUtil;

    public LoginPageTest() {
        super();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
        recoverPasswordPage = new RecoverPasswordPage();
        testUtil = new TestUtil();
    }


    public void goToLoginPage() {
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

        loginPage = landingPage.login_btn_click();
        extentTestChild.log(Status.PASS, "Navigated to Login page");
    }

    @Test
    public void title_Test() {
        extentTest = extent.createTest("LOGIN PAGE - title_Test");
        extentTestChild = extentTest.createNode("Verify the page title");

        goToLoginPage();
        testUtil.waitForElementToLoad(driver, loginPage.signIn_BTN());
        Assert.assertEquals(loginPage.getPageTitle(), "FLIR Log in");
        extentTestChild.log(Status.PASS, "Page title is " + loginPage.getPageTitle());
    }

    @Test
    public void blankEmail_Test() {
        String error_XPATH = "//p[contains(text(),'Please enter your email')]";
        String error_msg = "Please enter your email";

        extentTest = extent.createTest("LOGIN PAGE - blankEmail_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if email filed is blank");

        goToLoginPage();

        loginPage.email_field().clear();
        extentTestChild.log(Status.PASS, "Left the email field blank");

        loginPage.signIn_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on the Login button");

        testUtil.waitForElementToLoad(driver, driver.findElement(By.xpath(error_XPATH)));
        Assert.assertEquals(driver.findElement(By.xpath(error_XPATH)).getText(), error_msg);
        extentTestChild.log(Status.PASS, "Error message is displayed: " + driver.findElement(By.xpath(error_XPATH)).getText());
    }

    @Test
    public void invalidEmail_Test() {
        String invalidEmail = "test@";
        String error_XPATH = "//p[contains(text(),'Please enter a valid email address')]";
        String error_msg = "Please enter a valid email address";

        extentTest = extent.createTest("LOGIN PAGE - invalidEmail_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if an invalid email address is entered");

        goToLoginPage();

        loginPage.email_field().click();
        loginPage.email_field().sendKeys(invalidEmail);
        loginPage.signIn_BTN().click();
        extentTestChild.log(Status.PASS, "Entered an invalid email and clicked on Sign in button");

        testUtil.waitForElementToLoad(driver, driver.findElement(By.xpath(error_XPATH)));
        Assert.assertEquals(driver.findElement(By.xpath(error_XPATH)).getText(), error_msg);
        extentTestChild.log(Status.PASS, "Error message is displayed: " + driver.findElement(By.xpath(error_XPATH)).getText());
    }

    @Test
    public void blankPassword_Test() {
        String email = "test@test.com";
        String error_XPATH = "//p[contains(text(),'Please enter your password')]";
        String error_msg = "Please enter your password";

        extentTest = extent.createTest("LOGIN PAGE - blankPassword_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if an incorrect password is entered");

        goToLoginPage();

        loginPage.email_field().sendKeys(email);
        loginPage.signIn_BTN().click();
        extentTestChild.log(Status.PASS, "Entered a valid email, left the password field blank and clicked on the SignIN button");

        testUtil.waitForElementToLoad(driver, driver.findElement(By.xpath(error_XPATH)));
        Assert.assertEquals(driver.findElement(By.xpath(error_XPATH)).getText(), error_msg);
        extentTestChild.log(Status.PASS, "Error message is displayed: " + driver.findElement(By.xpath(error_XPATH)).getText());
    }

    @Test
    public void incorrectPassword_Test() {
        String email = "flirtest1@mailinator.com";
        String incorrectPass = "thisIsNotTheCorrectPass";
        String error_XPATH = "//p[contains(text(),'Your password is incorrect.')]";
        String error_msg = "Your password is incorrect.";

        extentTest = extent.createTest("LOGIN PAGE - incorrectPassword_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if an incorrect password is entered");

        goToLoginPage();

        loginPage.email_field().sendKeys(email);
        loginPage.pass_field().sendKeys(incorrectPass);
        loginPage.signIn_BTN().click();
        extentTestChild.log(Status.PASS, "Entered an incorrect password for an existing account");

        testUtil.waitForElementToLoad(driver, driver.findElement(By.xpath(error_XPATH)));
        Assert.assertEquals(driver.findElement(By.xpath(error_XPATH)).getText(), error_msg);
        extentTestChild.log(Status.PASS, "Error message is displayed: " + driver.findElement(By.xpath(error_XPATH)).getText());
    }

    @Test(groups = {"smoke"})
    public void loginWithNonExistingAccount_Test() {
        String email = "throwawayaccount@mail.com";
        String pass = "password";
        String error_XPATH = "//p[contains(text(),'seem to find your account')]";
        String error_msg = "We can't seem to find your account.";

        extentTest = extent.createTest("LOGIN PAGE - loginWithNonExistingAccount_Test");
        extentTestChild = extentTest.createNode("Error message is displayed when logging in with a email account who doesn't have an account associated");

        goToLoginPage();

        loginPage.email_field().sendKeys(email);
        loginPage.pass_field().sendKeys(pass);
        loginPage.signIn_BTN().click();
        extentTestChild.log(Status.PASS, "Entered the credentials for a non existing account and clicked on Sign In Button");

        testUtil.waitForElementToLoad(driver, driver.findElement(By.xpath(error_XPATH)));
        Assert.assertEquals(driver.findElement(By.xpath(error_XPATH)).getText(), error_msg);
        extentTestChild.log(Status.PASS, "Error message is displayed: " + driver.findElement(By.xpath(error_XPATH)).getText());
    }

    @Test(groups = {"smoke"})
    public void loginWithValidCredentials_Test() {
        String email = "flirtest1@mailinator.com";
        String pass = "QAZxsw123";

        extentTest = extent.createTest("LOGIN PAGE - loginWithValidCredentials_Test");
        extentTestChild = extentTest.createNode("Login with valid credentials");

        goToLoginPage();

        LibraryPage libraryPage = loginPage.login(email, pass);
        extentTestChild.log(Status.PASS, "Entered a valid email and pass then clicked on Sign In button");

        testUtil.waitForElementToLoad(driver, libraryPage.myFiles_LINK());
        Assert.assertEquals(libraryPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Library Page is displayed/ user is logged in successfully");
    }

    @Test(groups = {"smoke"})
    public void clickSignUpLink_Test() {
        extentTest = extent.createTest("LOGIN PAGE - clickSignUpLink_Test");
        extentTestChild = extentTest.createNode("Clicking on the SignUp link redirects to Sign Up page");

        goToLoginPage();

        SignUpPage signUpPage = loginPage.signUpLink_click();
        extentTestChild.log(Status.PASS, "Clicked on the Sign Up button");

        testUtil.waitForElementToLoad(driver, signUpPage.create_BTN());
        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
        extentTestChild.log(Status.PASS, "Sign Up Page is displayed");
    }

    @Test(groups = {"smoke"})
    public void clickForgotPasswordLink_Test() {
        extentTest = extent.createTest("LOGIN PAGE - clickForgotPasswordLink_Test");
        extentTestChild = extentTest.createNode("Clicking on the Forgot Password link redirects to Recover password page");

        goToLoginPage();

        recoverPasswordPage = loginPage.forgotPasswordLink_click();
        extentTestChild.log(Status.PASS, "Clicked on Forgot your password link");

        testUtil.waitForElementToLoad(driver, recoverPasswordPage.email_field());
        Assert.assertEquals(recoverPasswordPage.getPageTitle(), "FLIR Reset Password");
        extentTestChild.log(Status.PASS, "Recover password page is displayed");
    }

}
