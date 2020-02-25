package testCases;

import base.TestBase;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LoginPage;
import pages.RecoverPasswordPage;
import utils.TestUtil;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RecoverPassPageTest extends TestBase {
    private LandingPage landingPage;
    private LoginPage loginPage;
    private RecoverPasswordPage recoverPasswordPage;
    private TestUtil testUtil;

    public RecoverPassPageTest() {
        super();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
        recoverPasswordPage = new RecoverPasswordPage();
        testUtil = new TestUtil();
    }


    public void goToRecoverPassPage() {
        try {
            testUtil.waitForElementToLoad(driver, landingPage.signup_BTN());
        } catch (Exception e) {
            System.out.println("------------Page timed out. Refreshing...");
            driver.navigate().refresh();
            testUtil.waitForElementToLoad(driver, landingPage.signup_BTN());
        }

        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Navigated to Landing page");

        loginPage = landingPage.login_btn_click();
        extentTestChild.log(Status.PASS, "Navigated to Login page");

        testUtil.waitForElementToLoad(driver, loginPage.forgotPass_link());
        recoverPasswordPage = loginPage.forgotPasswordLink_click();
        extentTestChild.log(Status.PASS, "Clicked on Forgot your password link");

        testUtil.waitForElementToLoad(driver, recoverPasswordPage.email_field());
        extentTestChild.log(Status.PASS, "Navigated to Recover Pass Page");
    }

    @Test
    public void title_Test() {
        extentTest = extent.createTest("RECOVER PASS PAGE - title_Test");
        extentTestChild = extentTest.createNode("Verify the page title");

        goToRecoverPassPage();
        Assert.assertEquals(recoverPasswordPage.getPageTitle(), "FLIR Reset Password");
        extentTestChild.log(Status.PASS, "Page title is: " + recoverPasswordPage.getPageTitle());
    }

    @Test
    public void blankEmail_Test() {
        String error_XPATH = "//p[contains(text(),'Please enter a valid email address.')]";
        String error_msg = "Please enter a valid email address.";

        extentTest = extent.createTest("RECOVER PASS PAGE - blankEmail_Test");
        extentTestChild = extentTest.createNode("Error message is displayed when email address field is left blank");

        goToRecoverPassPage();

        recoverPasswordPage.email_field().clear();
        recoverPasswordPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, driver.findElement(By.xpath(error_XPATH)));
        Assert.assertEquals(driver.findElement(By.xpath(error_XPATH)).getText(), error_msg);
        extentTestChild.log(Status.PASS,"Error message is displayed: " + error_msg);
    }

    @Test
    public void invalidEmail_Test(){
        String error_XPATH = "//p[contains(text(),'Please enter a valid email address.')]";
        String error_msg = "Please enter a valid email address.";
        String invalidMail = "test@";

        extentTest = extent.createTest("RECOVER PASS PAGE - invalidEmail_Test");
        extentTestChild = extentTest.createNode("Error message is displayed when email address field is left blank");

        goToRecoverPassPage();

        recoverPasswordPage.email_field().sendKeys(invalidMail);
        recoverPasswordPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, driver.findElement(By.xpath(error_XPATH)));
        Assert.assertEquals(driver.findElement(By.xpath(error_XPATH)).getText(), error_msg);
        extentTestChild.log(Status.PASS,"Error message is displayed: " + error_msg);
    }

    @Test
    public void blankToken_Test(){
        String error_ID = "email_fail_retry";
        String error_msg = "That code is incorrect. Please try again.";

        extentTest = extent.createTest("RECOVER PASS PAGE - blankToken_Test");
        extentTestChild = extentTest.createNode("Error message is displayed when the verification code field is left blank");

        goToRecoverPassPage();

        recoverPasswordPage.email_field().sendKeys(prop.getProperty("gmail"));
        recoverPasswordPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.verifyCode_BTN());
        Assert.assertTrue(recoverPasswordPage.verifyCode_BTN().isDisplayed());
        extentTestChild.log(Status.PASS,"Entered email: "+prop.getProperty("gmail")+" and clicked on Send Verification code button");

        recoverPasswordPage.verificationCode_field().clear();
        recoverPasswordPage.verifyCode_BTN().click();
        extentTestChild.log(Status.PASS,"Left the Verification code blank and clicked on the Verify code");

        testUtil.waitForElementToLoad(driver,driver.findElement(By.id(error_ID)));
        Assert.assertEquals(driver.findElement(By.id(error_ID)).getText(),error_msg);
        extentTestChild.log(Status.PASS,"Error message is displayed: "+error_msg);
    }

    @Test
    public void invalidToken_Test(){
        String error_ID = "email_fail_retry";
        String error_msg = "That code is incorrect. Please try again.";
        String invalidToken = "123456";
        String email = "throwAwayEmail@mailinator.com";

        extentTest = extent.createTest("RECOVER PASS PAGE - invalidToken_Test");
        extentTestChild = extentTest.createNode("Error message is displayed when the user enters an invalid/incorrect token");

        goToRecoverPassPage();

        recoverPasswordPage.email_field().sendKeys(email);
        recoverPasswordPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.verifyCode_BTN());
        Assert.assertTrue(recoverPasswordPage.verifyCode_BTN().isDisplayed());
        extentTestChild.log(Status.PASS,"Entered email: "+email+" and clicked on Send Verification code button");

        recoverPasswordPage.setVerificationCode_field(invalidToken);
        recoverPasswordPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver,driver.findElement(By.id(error_ID)));
        Assert.assertEquals(driver.findElement(By.id(error_ID)).getText(),error_msg);
        extentTestChild.log(Status.PASS,"Error message is displayed: "+error_msg);
    }

    @Test
    public void expiredToken_Test(){
        String error_ID = "email_fail_code_expired";
        String error_msg = "That code is expired. Please request a new code.";
        int waitTime = 5; // Number of MINUTES until the token expires

        extentTest = extent.createTest("RECOVER PASS PAGE - expiredToken_Test");
        extentTestChild = extentTest.createNode("Error message is displayed when the user enters an expired token");

        goToRecoverPassPage();
        testUtil.getTokenFromGmail(true);

        recoverPasswordPage.email_field().sendKeys(prop.getProperty("gmail"));
        recoverPasswordPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.verifyCode_BTN());
        Assert.assertTrue(recoverPasswordPage.verifyCode_BTN().isDisplayed());
        extentTestChild.log(Status.PASS,"Entered email: "+prop.getProperty("gmail")+" and clicked on Send Verification code button");

        recoverPasswordPage.setVerificationCode_field(testUtil.getTokenFromGmail(false));
        try {
            Thread.sleep(waitTime * 60 * 1000); // wait for waitTime minutes + 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        extentTestChild.log(Status.PASS,"Entered the code received via email and waited for the Verification code to expire");

        recoverPasswordPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver,driver.findElement(By.id(error_ID)));
        Assert.assertEquals(driver.findElement(By.id(error_ID)).getText(),error_msg);
        extentTestChild.log(Status.PASS,"Error message is displayed: "+error_msg);
    }

    @Test
    public void tooManyIncorrectAttemptsToken_Test(){
        String error_ID = "email_fail_no_retry";
        String error_msg = "You've made too many incorrect attempts. Please try again later.";
        String invalidToken = "123456";
        String email = "throwAwayEmail@mailinator.com";
        int retry = 3;

        extentTest = extent.createTest("RECOVER PASS PAGE - tooManyIncorrectAttemptsToken_Test");
        extentTestChild = extentTest.createNode("Error message is displayed if an incorrect token is submitted more than "+retry+" times");

        goToRecoverPassPage();

        recoverPasswordPage.email_field().sendKeys(email);
        recoverPasswordPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.verifyCode_BTN());
        Assert.assertTrue(recoverPasswordPage.verifyCode_BTN().isDisplayed());
        extentTestChild.log(Status.PASS,"Entered email: "+email+" and clicked on Send Verification code button");

        for(int i=0;i<retry;i++) {
            testUtil.waitForElementToLoad(driver,recoverPasswordPage.verifyCode_BTN());
            recoverPasswordPage.verificationCode_field().clear();
            recoverPasswordPage.setVerificationCode_field(invalidToken);
            recoverPasswordPage.verifyCode_BTN().click();
        }
        extentTestChild.log(Status.PASS, "Entered the wrong token "+retry+" times");
        testUtil.waitForElementToLoad(driver,driver.findElement(By.id(error_ID)));
        Assert.assertEquals(driver.findElement(By.id(error_ID)).getText(),error_msg);
        extentTestChild.log(Status.PASS,"Error message is displayed: "+error_msg);
    }

    @Test
    public void sendNewCode_Test() {
        String error_msg = "That code is incorrect. Please try again.";

        extentTest = extent.createTest("RECOVER PASS PAGE - sendNewCode_Test");
        extentTestChild = extentTest.createNode("Resend the token and validate the new one");

        goToRecoverPassPage();
        testUtil.getTokenFromGmail(true);

        recoverPasswordPage.email_field().sendKeys(prop.getProperty("gmail"));
        extentTestChild.log(Status.PASS, "Entered an email address: "+prop.getProperty("gmail"));

        recoverPasswordPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Clicked on the Send Verification code button");

        assertTrue(recoverPasswordPage.verificationCode_field().isDisplayed(), "true");
        extentTestChild.log(Status.PASS, "Verification code field is displayed. Token was sent via email");

        recoverPasswordPage.setVerificationCode_field(testUtil.getTokenFromGmail(false));
        String oldToken = recoverPasswordPage.verificationCode_field().getAttribute("value"); //save this token as it will be reset soon
        extentTestChild.log(Status.PASS, "Saved the token received via email");

        testUtil.waitForElementToLoad(driver,recoverPasswordPage.sendNewCode_BTN());
        recoverPasswordPage.sendNewCode_BTN().click();
        String newToken = testUtil.getTokenFromGmail(false);
        extentTestChild.log(Status.PASS, "Clicked on Send new code button");

        testUtil.waitForElementToLoad(driver, recoverPasswordPage.verificationCode_field());
        Assert.assertEquals(recoverPasswordPage.verificationCode_field().getText(), "");
        extentTestChild.log(Status.PASS, "New code was generated");

        recoverPasswordPage.verificationCode_field().sendKeys(oldToken);
        recoverPasswordPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.incorrectVerCode_err());
        Assert.assertEquals(recoverPasswordPage.incorrectVerCode_err().getText(), error_msg);
        extentTestChild.log(Status.PASS, "Entered the previous token and it no longer works");

        recoverPasswordPage.verificationCode_field().clear();
        recoverPasswordPage.setVerificationCode_field(newToken);
        recoverPasswordPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.changeEmail_BTN());
        assertTrue(recoverPasswordPage.changeEmail_BTN().isDisplayed());
        extentTestChild.log(Status.PASS, "Entered the latest token received via email and it works");
    }

    @Test
    public void resendEmail_Test() {

        extentTest = extent.createTest("RECOVER PASS PAGE - resendEmail_Test");
        extentTestChild = extentTest.createNode("Change the email after validating the token");

        goToRecoverPassPage();
        testUtil.getTokenFromGmail(true);

        recoverPasswordPage.email_field().sendKeys(prop.getProperty("gmail"));
        extentTestChild.log(Status.PASS, "Entered an email address");

        recoverPasswordPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Verified that the Send code button is displayed");

        recoverPasswordPage.setVerificationCode_field(testUtil.getTokenFromGmail(false));
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        recoverPasswordPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.changeEmail_BTN());
        assertTrue(recoverPasswordPage.changeEmail_BTN().isDisplayed());
        extentTestChild.log(Status.PASS, "Token was verified. Change e-mail button is displayed");

        recoverPasswordPage.changeEmail_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.sendVerCode_BTN());
        assertTrue(recoverPasswordPage.sendVerCode_BTN().isDisplayed());
        assertTrue(recoverPasswordPage.sendVerCode_BTN().getAttribute("value").isEmpty());
        extentTestChild.log(Status.PASS, "Clicked on he Change e-mail button. Email field is now empty");
    }

    @Test
    public void cancelRecoverPass_Test() {

        extentTest = extent.createTest("RECOVER PASS PAGE - cancelRecoverPass_Test");
        extentTestChild = extentTest.createNode("Clicking on the Cancel button redirects the user to the index page");

        goToRecoverPassPage();
        testUtil.getTokenFromGmail(true);

        recoverPasswordPage.email_field().sendKeys(prop.getProperty("gmail"));
        extentTestChild.log(Status.PASS, "Entered an email address");

        recoverPasswordPage.sendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.verifyCode_BTN());
        extentTestChild.log(Status.PASS, "Verified that the Send code button is displayed");

        recoverPasswordPage.setVerificationCode_field(testUtil.getTokenFromGmail(false));
        extentTestChild.log(Status.PASS, "Entered the token received via email");

        recoverPasswordPage.verifyCode_BTN().click();
        testUtil.waitForElementToLoad(driver, recoverPasswordPage.changeEmail_BTN());
        assertTrue(recoverPasswordPage.changeEmail_BTN().isDisplayed());
        extentTestChild.log(Status.PASS, "Token was verified. Change e-mail button is displayed");

        recoverPasswordPage.cancel_BTN().click();
        extentTestChild.log(Status.PASS, "Clicked on the Cancel button");

        testUtil.waitForElementToLoad(driver,landingPage.signup_BTN());
        assertEquals(landingPage.getPageTitle(),"FLIR Tools");
        extentTestChild.log(Status.PASS, "Landing page is displayed");
    }


}
