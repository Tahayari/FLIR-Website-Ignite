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

    @BeforeMethod
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
        recoverPasswordPage = new RecoverPasswordPage();
        testUtil = new TestUtil();
    }


    public void goToLoginPage() {

        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Navigated to Landing page");

        loginPage = landingPage.login_btn_click();
        extentTestChild.log(Status.PASS, "Navigated to Login page");
    }

    @Test
    public void title_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Verify the page title");
        extentTestChild = extentTest.createNode("Verify the page title");

        goToLoginPage();
        testUtil.waitForElementToLoad(driver, loginPage.signIn_BTN);
        Assert.assertEquals(loginPage.getPageTitle(), "FLIR Log in");
        extentTestChild.log(Status.PASS, "Page title is " + loginPage.getPageTitle());
    }

    @Test
    public void blankEmail_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Create an account with a blank email");
        extentTestChild = extentTest.createNode("Error message is displayed if email filed is blank");

        goToLoginPage();

        loginPage.email_field.clear();
        extentTestChild.log(Status.PASS, "Leave the email field blank");

        loginPage.signIn_BTN.click();
        extentTestChild.log(Status.PASS, "Click on Login button");

        boolean errorMsg = driver.findElement(By.xpath("//p[contains(text(),'Please enter your email')]")).isDisplayed();
        Assert.assertTrue(errorMsg, "true");
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }

    @Test
    public void invalidEmail_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Enter an invalid email address");
        extentTestChild = extentTest.createNode("Error message is displayed if an invalid email address is entered");

        goToLoginPage();

        loginPage.email_field.click();
        loginPage.email_field.sendKeys("dan@");

        loginPage.signIn_BTN.click();
        extentTestChild.log(Status.PASS, "Entered an invalid email and clicked on Sign in button");

        boolean errorMsg = driver.findElement(By.xpath("//p[contains(text(),'Please enter a valid email address')]")).isDisplayed();
        Assert.assertTrue(errorMsg);
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }

    @Test
    public void blankPassword_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Enter an incorrect password");
        extentTestChild = extentTest.createNode("Error message is displayed if an incorrect password is entered");

        goToLoginPage();

        loginPage.email_field.sendKeys(prop.getProperty("email"));
        loginPage.signIn_BTN.click();
        extentTestChild.log(Status.PASS, "Entered an incorrect password for an existing account");

        boolean errorMsg = driver.findElement(By.xpath("//p[contains(text(),'Please enter your password')]")).isDisplayed();
        Assert.assertTrue(errorMsg);
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }

    @Test
    public void incorrectPassword_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Enter an incorrect password");
        extentTestChild = extentTest.createNode("Error message is displayed if an incorrect password is entered");

        goToLoginPage();

        loginPage.email_field.sendKeys(prop.getProperty("email"));
        loginPage.pass_field.sendKeys(prop.getProperty("password") + "TEST"); //Incorrect password addendum
        loginPage.signIn_BTN.click();
        extentTestChild.log(Status.PASS, "Entered an incorrect password for an existing account");

        boolean errorMsg = driver.findElement(By.xpath("//p[contains(text(),'Your password is incorrect.')]")).isDisplayed();
        Assert.assertTrue(errorMsg);
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }

    @Test
    public void loginWithNonExistingAccount_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Login with a email account who doesn't have an account associated");
        extentTestChild = extentTest.createNode("Error message is displayed when logging in with a email account who doesn't have an account associated");

        goToLoginPage();

        loginPage.email_field.sendKeys(prop.getProperty("email") + "TEST"); //Incorrect password addendum
        loginPage.pass_field.sendKeys(prop.getProperty("password") + "TEST"); //Incorrect password addendum
        loginPage.signIn_BTN.click();
        extentTestChild.log(Status.PASS, "Entered an incorrect password for an existing account");

        boolean errorMsg = driver.findElement(By.xpath("//p[contains(text(),'seem to find your account')]")).isDisplayed();
        Assert.assertTrue(errorMsg);
        extentTestChild.log(Status.PASS, "Error message is displayed");
    }


    @Test
    public void loginWithValidCredentials_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Login with valid credentials");
        extentTestChild = extentTest.createNode("Login with valid credentials");

        goToLoginPage();

        LibraryPage libraryPage = loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
        extentTestChild.log(Status.PASS, "Enter valid email and pass then login");

        testUtil.waitForElementToLoad(driver, libraryPage.getMyFiles_BTN());
        Assert.assertEquals(libraryPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Library Page is displayed/ user is logged in successfully");
    }

    @Test
    public void clickSignUpLink_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Click on Sign Up Hyperlink");
        extentTestChild = extentTest.createNode("Redirect to Sign Up page");

        goToLoginPage();

        SignUpPage signUpPage = loginPage.signUpLink_click();
        extentTestChild.log(Status.PASS, "Click on Sign Up button");

        testUtil.waitForElementToLoad(driver, signUpPage.create_BTN());
        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
        extentTestChild.log(Status.PASS, "Sign Up Page is displayed");
    }

    @Test
    public void clickForgotPasswordLink_Test() {
        extentTest = extent.createTest("LOGIN PAGE - Click on Sign Up Hyperlink");
        extentTestChild = extentTest.createNode("Redirect to Sign Up page");

        goToLoginPage();

        recoverPasswordPage = loginPage.forgotPasswordLink_click();
        extentTestChild.log(Status.PASS, "Clicked on Forgot your password link");

        testUtil.waitForElementToLoad(driver, recoverPasswordPage.getEmail_field());
        Assert.assertEquals(recoverPasswordPage.getPageTitle(), "FLIR Reset Password");
        extentTestChild.log(Status.PASS, "Recover password page is displayed");
    }

}
