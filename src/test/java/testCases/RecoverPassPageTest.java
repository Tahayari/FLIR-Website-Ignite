package testCases;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;
import pages.RecoverPasswordPage;
import utils.ExtentReport;
import utils.TestUtil;

import static pages.LandingPage.getLandingPage;
import static pages.LibraryPage.getLibraryPage;
import static pages.LoginPage.getLoginPage;
import static pages.RecoverPasswordPage.getRecoverPasswordPage;
import static utils.TestUtil.getDataFromExcel;

public class RecoverPassPageTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;
    RecoverPasswordPage recoverPasswordPage;

    // Test cases begin here------------------------------------------------------------
    @Test(enabled = false)
    public void forTesting() {
        executeSetup("title", "desc");
        recoverPasswordPage.setEmail("blabla@mail.com");
        String token = TestUtil.getTokenFromGmail();
        System.out.println(token);
    }


    @Test
    public void invalidEmail_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getInvalidEmail_Test_title()
                , testCasesInfo.recoverPassPageInfo().getInvalidEmail_Test_desc());

        verifyListOfInvalidEmails();
    }


    @Test
    public void invalidToken_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getInvalidToken_Test_title(),
                testCasesInfo.recoverPassPageInfo().getInvalidToken_Test_desc());

        recoverPasswordPage.sendTokenToEmail(testData.getRandomEmail())
                .setVerificationCode_field(testData.getInvalidToken())
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.incorrectVerCode_Msg());
    }

    @Test(enabled = true)
    public void expiredToken_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getExpiredToken_Test_title(),
                testCasesInfo.recoverPassPageInfo().getExpiredToken_Test_desc());

        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail());
        String token = TestUtil.getTokenFromGmail();
        TestUtil.waitForSomeMinutes(testData.getMinForTokenToExpire());
        recoverPasswordPage.setVerificationCode_field(token)
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.expiredVerCode_Msg());
    }

    @Test
    public void tooManyIncorrectAttemptsToken_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getTooManyIncorrectAttemptsToken_Test_title(),
                testCasesInfo.recoverPassPageInfo().getTooManyIncorrectAttemptsToken_Test_desc());
        verifyInvalidTokenTooManyTimes();
    }

    @Test
    public void sendNewCode_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getSendNewCode_Test_title(),
                testCasesInfo.recoverPassPageInfo().getSendNewCode_Test_desc());

        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail());
        String firstToken = TestUtil.getTokenFromGmail();
        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail());
        String secondToken = TestUtil.getTokenFromGmail();

        recoverPasswordPage.setVerificationCode_field(firstToken)
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.incorrectVerCode_Msg());

        recoverPasswordPage.setVerificationCode_field(secondToken)
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
    }

    @Test
    public void resendToken_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getResendToken_Test_title(),
                testCasesInfo.recoverPassPageInfo().getResendToken_Test_desc());

        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail());
        String firstToken = TestUtil.getTokenFromGmail();
        recoverPasswordPage.clickOn_sendNewCode_BTN();
        String secondToken = TestUtil.getTokenFromGmail();
        recoverPasswordPage.setVerificationCode_field(firstToken)
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.incorrectVerCode_Msg());

        recoverPasswordPage.setVerificationCode_field(secondToken)
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
    }

    @Test
    public void changeEmail_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getChangeEmail_Test_title(),
                testCasesInfo.recoverPassPageInfo().getChangeEmail_Test_desc());

        verifyEmail();
        recoverPasswordPage.clickOn_changeEmail_BTN();
    }

    @Test
    public void cancelRecoverPass_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getCancelRecoverPass_Test_title(),
                testCasesInfo.recoverPassPageInfo().getCancelRecoverPass_Test_desc());

        verifyEmail();
        recoverPasswordPage.clickOn_cancel_BTN();
        landingPage.verifyIfPageLoaded();
    }


    @Test
    public void incorrectPasswordFormat_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getIncorrectPasswordFormat_Test_title(),
                testCasesInfo.recoverPassPageInfo().getIncorrectPasswordFormat_Test_desc());

        goToChangePassScreen();

        recoverPasswordPage.tryIncorrectPasswords(recoverPasswordPage.newPassword_field());
    }

    @Test
    public void incorrectPasswordFormatConfirmPassField_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getIncorrectPasswordFormatConfirmPassField_Test_title(),
                testCasesInfo.recoverPassPageInfo().getIncorrectPasswordFormatConfirmPassField_Test_desc());

        goToChangePassScreen();

        recoverPasswordPage.tryIncorrectPasswords(recoverPasswordPage.confNewPassword_field());
    }

    @Test
    public void blankPass_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getBlankPass_Test_title(),
                testCasesInfo.recoverPassPageInfo().getBlankPass_Test_desc());

        goToChangePassScreen();

        recoverPasswordPage.setNewPassword(" ")
                .clickOn_continue_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.requiredFieldMissing_Msg());
    }

    @Test
    public void mismatchingPasswords_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getMismatchingPasswords_Test_title(),
                testCasesInfo.recoverPassPageInfo().getMismatchingPasswords_Test_desc());

        goToChangePassScreen();

        recoverPasswordPage.setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword("")
                .clickOn_continue_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.passMismatch_Msg());
    }

    @Test
    public void changePassword_Test() {
//        https://jiracommercial.flir.com/browse/THAL-2544
        executeSetup(testCasesInfo.recoverPassPageInfo().getChangePassword_Test_title(),
                testCasesInfo.recoverPassPageInfo().getChangePassword_Test_desc());

        goToChangePassScreen();

        recoverPasswordPage.setNewPassword(testData.getPassOfExistingAcc())
                .setConfirmNewPassword(testData.getPassOfExistingAcc())
                .clickOn_continue_BTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded();
        libraryPage.logout();
        landingPage.verifyIfPageLoaded();
    }

    @Test
    public void cancelUpdatingPassword_Test() {
        executeSetup(testCasesInfo.recoverPassPageInfo().getCancelUpdatingPassword_Test_title(),
                testCasesInfo.recoverPassPageInfo().getCancelUpdatingPassword_Test_desc());

        goToChangePassScreen();
        String newPass = testData.getPassOfExistingAcc() + "123";
        recoverPasswordPage.setNewPassword(newPass)
                .setConfirmNewPassword(newPass)
                .clickOn_cancel_BTN();
        landingPage.verifyIfPageLoaded();
        landingPage.clickOn_loginBTN();
        loginPage.setEmail(testData.getGmailEmail())
                .setPass(testData.getPassOfExistingAcc())
                .clickOn_signInBTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded();
        libraryPage.logout();
        landingPage.verifyIfPageLoaded();
    }


    //---
    private void goToRecoverPassPage() {
        landingPage.verifyIfPageLoaded();
        landingPage.clickOn_loginBTN();
        loginPage.clickOn_forgotPasswordLink();
        recoverPasswordPage.verifyIfPageLoaded();
    }

    private void executeSetup(String testCaseTitle, String testCaseDescription) {
        landingPage = getLandingPage();
        loginPage = getLoginPage();
        recoverPasswordPage = getRecoverPasswordPage();
        ExtentReport.createTestCase(testCaseTitle, testCaseDescription);
        ExtentReport.assignCategory(testCasesInfo.recoverPassPageInfo().getCategory());
        goToRecoverPassPage();
    }

    private void goToChangePassScreen() {
        verifyEmail();
        recoverPasswordPage.clickOn_continue_BTN()
                .verifyIfChangePassScreenLoaded();
    }

    private void verifyListOfInvalidEmails() {
        Object[][] invalidEmailsList = getDataFromExcel(testData.getNameOfInvalidEmailsFile(), testData.getNameOfFirstSheet());
        for (int i = 1; i < invalidEmailsList.length; i++) {
            recoverPasswordPage.clearField(recoverPasswordPage.emailAddress_field())
                    .setEmail(invalidEmailsList[i][0].toString())
                    .checkErrMsgIsDisplayed(recoverPasswordPage.invalidEmail_Msg());
        }
    }

    private void verifyInvalidTokenTooManyTimes() {
        int timesToRetry = 2;
        recoverPasswordPage.sendTokenToEmail(testData.getRandomEmail());
        for (int i = 0; i < timesToRetry; i++) {
            recoverPasswordPage.setVerificationCode_field(String.valueOf(i + 10000))
                    .clickOn_verifyCode_BTN()
                    .checkErrMsgIsDisplayed(recoverPasswordPage.incorrectVerCode_Msg());
        }
        recoverPasswordPage.setVerificationCode_field("10002")
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(recoverPasswordPage.tooManyAttempts_Msg());
    }

    private void verifyEmail() {
        //maybe put this in SignUpPage class instead?
        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail())
                .setVerificationCode_field(TestUtil.getTokenFromGmail())
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
    }

}
