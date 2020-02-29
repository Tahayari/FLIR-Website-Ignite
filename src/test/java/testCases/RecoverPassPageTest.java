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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.TestUtil.getTestaData;

public class RecoverPassPageTest extends TestBase {
    private LandingPage landingPage;
    private LoginPage loginPage;
    private RecoverPasswordPage recoverPasswordPage;
    private String sheetName = "Sheet1";
    private String fileName = "InvalidEmails";

    public RecoverPassPageTest() {
        super();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
        recoverPasswordPage = new RecoverPasswordPage();
    }

    public void verifyInvalidEmails(Object[][] invalidEmailsList){
        String error_msg = "Please enter a valid email address.";

        for (Object[] objects : invalidEmailsList) {
            recoverPasswordPage.email_field().clear();
            recoverPasswordPage.setInvalidEmail(objects[0].toString());
            addTestCaseStep("Entered the following invalid email: " + objects[0].toString());
            waitForElementToLoad(recoverPasswordPage.invalidEmailErrorMsg());
            checkIfCorrectErrMsg(recoverPasswordPage.invalidEmailErrorMsg(), error_msg);
            addTestCaseStep("Error message is displayed: " + error_msg);
        }
    }

    public void loadLandingPage(){
        try {
            waitForElementToBeClickable(landingPage.signup_BTN());
        } catch (Exception e) {
            System.out.println("------------Page timed out. Refreshing...");
            driver.navigate().refresh();
            waitForElementToBeClickable(landingPage.signup_BTN());
        }
    }

    public void goToRecoverPassPage() {

        loadLandingPage();
        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        addTestCaseStep("Navigated to Landing page");

        loginPage = landingPage.clickOn_loginBTN();
        waitForElementToLoad(loginPage.signIn_BTN());
        addTestCaseStep("Navigated to Login page");

        recoverPasswordPage = loginPage.clickOn_forgotPasswordLink();
        waitForElementToLoad(recoverPasswordPage.email_field());
        addTestCaseStep("Clicked on Forgot your password link");

        Assert.assertEquals(recoverPasswordPage.getPageTitle(),"FLIR Reset Password");
        addTestCaseStep("Navigated to Recover Pass Page");
    }

    //-------Test Cases-------
    @Test
    public void title_Test() {
        String testCaseTitle = "RECOVER PASS PAGE - title_Test";
        String testCaseDescription = "Verify the page title";

        createTestCase(testCaseTitle,testCaseDescription);
        goToRecoverPassPage();

        Assert.assertEquals(recoverPasswordPage.getPageTitle(), "FLIR Reset Password");
        addTestCaseStep("Page title is: " + recoverPasswordPage.getPageTitle());
    }

    @Test
    public void invalidEmail_Test(){
        String testCaseTitle = "RECOVER PASS PAGE - invalidEmail_Test";
        String testCaseDescription = "Error message is displayed after entering an invalid email";
        Object[][] invalidEmailsList = getTestaData(fileName,sheetName);

        createTestCase(testCaseTitle,testCaseDescription);

        goToRecoverPassPage();

        verifyInvalidEmails(invalidEmailsList);
    }

    @Test
    public void blankToken_Test(){
        String testCaseTitle = "RECOVER PASS PAGE - blankToken_Test";
        String testCaseDescription = "Error message is displayed when the verification code field is left blank";
        String error_msg = "That code is incorrect. Please try again.";
        String email = "someEmailForTesting@test.com" ;

        createTestCase(testCaseTitle,testCaseDescription);

        goToRecoverPassPage();

        recoverPasswordPage.sendTokenToEmail(email);

        recoverPasswordPage.sendInvalidToken("");

        Assert.assertEquals(recoverPasswordPage.incorrectVerCode_err().getText(),error_msg);
        addTestCaseStep("Error message is displayed: "+error_msg);
    }

    @Test
    public void invalidToken_Test(){
        String testCaseTitle = "RECOVER PASS PAGE - invalidToken_Test";
        String testCaseDescription = "Error message is displayed when the user enters an invalid/incorrect token";
        String error_msg = "That code is incorrect. Please try again.";
        String invalidToken = "000000";
        String email = "someEmailForTesting@test.com" ;

        createTestCase(testCaseTitle,testCaseDescription);

        goToRecoverPassPage();

        recoverPasswordPage.sendTokenToEmail(email);

        recoverPasswordPage.sendInvalidToken(invalidToken);

        Assert.assertEquals(recoverPasswordPage.incorrectVerCode_err().getText(),error_msg);
        addTestCaseStep("Error message is displayed: "+error_msg);
    }

    @Test
    public void expiredToken_Test(){
        String testCaseTitle = "RECOVER PASS PAGE - expiredToken_Test";
        String testCaseDescription = "Error message is displayed when the user enters an expired token";
        String error_msg = "That code is expired. Please request a new code.";
        String email = prop.getProperty("gmail") ;
        int minutesToWait = 5; // Number of MINUTES until the token expires

        createTestCase(testCaseTitle,testCaseDescription);

        goToRecoverPassPage();

        testUtil.prepareGmail();

        recoverPasswordPage.sendTokenToEmail(email);

        recoverPasswordPage.waitForTokenToExpire(minutesToWait);

        recoverPasswordPage.enterTokenFromEmail();
        waitForElementToLoad(recoverPasswordPage.expiredVerCode());
        Assert.assertEquals(recoverPasswordPage.expiredVerCode().getText(),error_msg);
        addTestCaseStep("Error message is displayed: "+error_msg);

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
