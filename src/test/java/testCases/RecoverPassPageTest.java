//package testCases;
//
//import base.TestBase;
//import com.aventstack.extentreports.Status;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//import pages.LandingPage;
//import pages.LoginPage;
//import pages.RecoverPasswordPage;
//import utils.TestUtil;
//
//import static org.testng.Assert.assertEquals;
//import static pages.LandingPage.getLandingPage;
//import static pages.LoginPage.getLoginPage;
//import static utils.TestUtil.getTestaData;
//
//public class RecoverPassPageTest extends TestBase {
//    LandingPage landingPage = getLandingPage();
//    LoginPage loginPage = getLoginPage();
//    RecoverPasswordPage recoverPassPage = getRecoverPassPage();
//    private RecoverPasswordPage recoverPasswordPage;
//    private TestUtil testUtil;
//    private String sheetName = "Sheet1";
//    private String fileName = "InvalidEmails";
//
//
//    //-------Test Cases-------
//    @Test
//    public void title_Test() {
//        String testCaseTitle = "RECOVER PASS PAGE - title_Test";
//        String testCaseDescription = "Verify the page title";
//
//        createTestCase(testCaseTitle,testCaseDescription);
//        goToRecoverPassPage();
//
//        Assert.assertEquals(recoverPasswordPage.getPageTitle(), "FLIR Reset Password");
//        addTestCaseStep("Page title is: " + recoverPasswordPage.getPageTitle());
//    }
//
//    @Test
//    public void invalidEmail_Test(){
//        String testCaseTitle = "RECOVER PASS PAGE - invalidEmail_Test";
//        String testCaseDescription = "Error message is displayed after entering an invalid email";
//        Object[][] invalidEmailsList = getTestaData(fileName,sheetName);
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToRecoverPassPage();
//
//        verifyInvalidEmails(invalidEmailsList);
//    }
//
//    @Test
//    public void blankToken_Test(){
//        String testCaseTitle = "RECOVER PASS PAGE - blankToken_Test";
//        String testCaseDescription = "Error message is displayed when the verification code field is left blank";
//        String error_msg = "That code is incorrect. Please try again.";
//        String email = "someEmailForTesting@test.com" ;
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToRecoverPassPage();
//
//        recoverPasswordPage.sendTokenToEmail(email);
//
//        recoverPasswordPage.sendInvalidToken("");
//
//        Assert.assertEquals(recoverPasswordPage.incorrectVerCodeMsg().getText(),error_msg);
//        addTestCaseStep("Error message is displayed: "+error_msg);
//    }
//
//    @Test
//    public void invalidToken_Test(){
//        String testCaseTitle = "RECOVER PASS PAGE - invalidToken_Test";
//        String testCaseDescription = "Error message is displayed when the user enters an invalid/incorrect token";
//        String invalidToken = "000000";
//        String email = "someEmailForTesting@test.com" ;
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToRecoverPassPage();
//
//        recoverPasswordPage.sendTokenToEmail(email);
//
//        recoverPasswordPage.sendInvalidToken(invalidToken);
//
//        recoverPasswordPage.verifyInvalidTokenErrorMsg();
//    }
//
//    @Test
//    public void expiredToken_Test(){
//        String testCaseTitle = "RECOVER PASS PAGE - expiredToken_Test";
//        String testCaseDescription = "Error message is displayed when the user enters an expired token";
//        String email = prop.getProperty("gmail") ;
//        int minutesToWait = 5; // Number of MINUTES until the token expires
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToRecoverPassPage();
//
//        testUtil.prepareGmail();
//
//        recoverPasswordPage.sendTokenToEmail(email);
//
//        recoverPasswordPage.waitForTokenToExpire(minutesToWait);
//
//        recoverPasswordPage.verifyIfTokenExpired();
//    }
//
//    @Test
//    public void tooManyIncorrectAttemptsToken_Test(){
//        int timesToRetry = 3;
//        String testCaseTitle = "RECOVER PASS PAGE - tooManyIncorrectAttemptsToken_Test";
//        String testCaseDescription = "Error message is displayed if an incorrect token is submitted more than " +timesToRetry +" times";
//        String error_msg = "You've made too many incorrect attempts. Please try again later.";
//        String email = "throwAwayEmail@mailinator.com";
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToRecoverPassPage();
//
//        recoverPasswordPage.sendTokenToEmail(email);
//
//        recoverPasswordPage.enterInvalidTokenMultipleTimes(timesToRetry) ;
//
//        testUtil.waitForElementToLoad(recoverPasswordPage.tooManyIncorrectAttemptsError());
//        Assert.assertEquals(recoverPasswordPage.tooManyIncorrectAttemptsError().getText(),error_msg);
//        addTestCaseStep("Error message is displayed: "+error_msg);
//    }
//
//    @Test
//    public void sendNewCode_Test() {
//        String testCaseTitle = "RECOVER PASS PAGE - sendNewCode_Test";
//        String testCaseDescription = "Resend the token and validate the new one";
//        String email = prop.getProperty("gmail");
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToRecoverPassPage();
//
//        testUtil.prepareGmail();
//
//        recoverPasswordPage.sendTokenToEmail(email);
//        String oldToken = testUtil.getTokenFromGmail();
//        addTestCaseStep("Saved the token received via email");
//
//        recoverPasswordPage.generateAnotherToken();
//
//        recoverPasswordPage.sendInvalidToken(oldToken);
//        addTestCaseStep("Entered the previous token and it no longer works");
//
//        recoverPasswordPage.enterTokenFromGmail();
//        extentTestChild.log(Status.PASS, "Entered the latest token received via email and it works");
//    }
//
//    @Test
//    public void resendEmail_Test() {
//        String testCaseTitle = "RECOVER PASS PAGE - resendEmail_Test";
//        String testCaseDescription = "Change the email after validating the token";
//        String email = prop.getProperty("gmail");
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToRecoverPassPage();
//        testUtil.prepareGmail();
//
//        recoverPasswordPage.sendTokenToEmail(email);
//
//        recoverPasswordPage.enterTokenFromGmail();
//
//        recoverPasswordPage.clickOn_changeEmail_BTN();
//    }
//
//    @Test
//    public void cancelRecoverPass_Test() {
//        String testCaseTitle = "RECOVER PASS PAGE - cancelRecoverPass_Test";
//        String testCaseDescription = "Clicking on the Cancel button redirects the user to the index page";
//        String email = prop.getProperty("gmail");
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToRecoverPassPage();
//        testUtil.prepareGmail();
//
//        recoverPasswordPage.sendTokenToEmail(email);
//
//        recoverPasswordPage.enterTokenFromGmail();
//
//        recoverPasswordPage.cancel_BTN().click();
//        addTestCaseStep("Clicked on the Cancel button");
//
//        testUtil.waitForElementToLoad(landingPage.signup_BTN());
//        assertEquals(landingPage.getPageTitle(),"FLIR Tools");
//        addTestCaseStep("Landing page is displayed");
//    }
//
//    @Test
//    public void changeEmail_Test(){
//        String testCaseTitle = "RECOVER PASS PAGE - changeEmail_Test";
//        String testCaseDescription = "Clicking on the Change e-mail button resets the ";
//        String email = "flirtest10";
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToRecoverPassPage();
//
//        testUtil.prepareMailinator(email);
//
//        recoverPasswordPage.sendTokenToEmail(email+"@mailinator.com");
//
//        recoverPasswordPage.enterTokenFromMailinator(email);
//
//        recoverPasswordPage.clickOn_changeEmail_BTN();
//    }
//
//    @Test
//    public void incorrectPasswordFormat_Test(){
//        String testCaseTitle = "RECOVER PASS PAGE - incorrectPasswordFormat_Test";
//        String testCaseDescription = "Error message is displayed if an invalid password is entered";
//        String email = "flirtest10";
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToChangePassPage(email);
//
//        recoverPasswordPage.tryIncorrectPasswords(recoverPasswordPage.newPassword_field());
//    }
//
//    @Test
//    public void incorrectPasswordFormatConfirmPassField_Test(){
//        String testCaseTitle = "RECOVER PASS PAGE - incorrectPasswordFormatConfirmPassField_Test";
//        String testCaseDescription = "Error message is displayed if an invalid password is entered (Confirm password field)";
//        String email = "flirtest10";
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToChangePassPage(email);
//
//        recoverPasswordPage.tryIncorrectPasswords(recoverPasswordPage.reEnterPassword_field());
//    }
//
//    @Test
//    public void blankPass_Test(){
//        String testCaseTitle = "RECOVER PASS PAGE - blankPass_Test";
//        String testCaseDescription = "Error message is displayed if password field is left blank and user clicks on Continue button";
//        String email = "flirtest10";
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToChangePassPage(email);
//
//        recoverPasswordPage.submitBlankPassword();
//    }
//
//    @Test
//    public void mismatchingPasswords_Test(){
//        String testCaseTitle = "RECOVER PASS PAGE - mismatchingPasswords_Test";
//        String testCaseDescription = "Error message is displayed if the passwords are not identical";
//        String email = "flirtest10";
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToChangePassPage(email);
//
//        recoverPasswordPage.enterMismatchingPasswords();
//    }
//
//    @Test
//    public void changePassword_Test(){
////        https://jiracommercial.flir.com/browse/THAL-2544
//        String testCaseTitle = "RECOVER PASS PAGE - changePassword_Test";
//        String testCaseDescription = "Successfully change the password to an existing account";
//        String email = "flirtest10";
//        String newPassword = "QAZxsw123";
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToChangePassPage(email);
//
//        recoverPasswordPage.enterNewPassword(newPassword);
//
//        recoverPasswordPage.createNewPassword() ;
//    }
//
//
//    //---
//    public void verifyInvalidEmails(Object[][] invalidEmailsList){
//        String error_msg = "Please enter a valid email address.";
//
//        for (Object[] objects : invalidEmailsList) {
//            recoverPasswordPage.email_field().clear();
//            recoverPasswordPage.setInvalidEmail(objects[0].toString());
//            addTestCaseStep("Entered the following invalid email: " + objects[0].toString());
//            testUtil.waitForElementToLoad(recoverPasswordPage.invalidEmailErrorMsg());
//            checkIfCorrectErrMsg(recoverPasswordPage.invalidEmailErrorMsg(), error_msg);
//            addTestCaseStep("Error message is displayed: " + error_msg);
//        }
//    }
//
//    public void loadLandingPage(){
//        try {
//            waitForElementToBeClickable(landingPage.signup_BTN());
//        } catch (Exception e) {
//            System.out.println("------------Page timed out. Refreshing...");
//            driver.navigate().refresh();
//            waitForElementToBeClickable(landingPage.signup_BTN());
//        }
//    }
//
//    public void goToRecoverPassPage() {
//
//        loadLandingPage();
//        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
//        addTestCaseStep("Navigated to Landing page");
//
//        loginPage = landingPage.clickOn_loginBTN();
//        testUtil.waitForElementToLoad(loginPage.signIn_BTN());
//        addTestCaseStep("Navigated to Login page");
//
//        recoverPasswordPage = loginPage.clickOn_forgotPasswordLink();
//        testUtil.waitForElementToLoad(recoverPasswordPage.email_field());
//        addTestCaseStep("Clicked on Forgot your password link");
//
//        Assert.assertEquals(recoverPasswordPage.getPageTitle(),"FLIR Reset Password");
//        addTestCaseStep("Navigated to Recover Pass Page");
//    }
//
//    public void goToChangePassPage(String email){
//        goToRecoverPassPage();
//        testUtil.prepareMailinator(email);
//        recoverPasswordPage.sendTokenToEmail(email+"@mailinator.com");
//        recoverPasswordPage.enterTokenFromMailinator(email);
//        recoverPasswordPage.clickOn_continue_BTN();
//    }
//
//}
