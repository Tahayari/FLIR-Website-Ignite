package testCases;

import base.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.SignUpPage;
import utils.ExtentReport;
import utils.TestUtil;
import utils.emailManager.Mailinator;

import static pages.LandingPage.getLandingPage;
import static pages.LibraryPage.getLibraryPage;
import static pages.SignUpPage.getSignUpPage;
import static utils.TestUtil.getDataFromExcel;

public class SignUpPageTest extends TestBase {
    LandingPage landingPage = getLandingPage();
    SignUpPage signUpPage = getSignUpPage();

    // Test cases begin here------------------------------------------------------------
    @Test(enabled = false)
    public void forTesting() {
        executeSetup("title", "description");
        String dummyEmail = "asdajh292wja";
        signUpPage.sendTokenToEmail(dummyEmail + "@mailinator.com")
                .setVerificationCode_field(Mailinator.getToken(dummyEmail))
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
    }

    @DataProvider
    public Object[][] getTestData() {
        String fileName = "Accounts";
        return getDataFromExcel(fileName, testData.getNameOfFirstSheet());
    }

    @Test(dataProvider = "getTestData", groups = {"smoke", "regression"}, priority = 100, enabled = false)
    public void registerNewAccount_Test(String email, String firstName, String lastName) {
        executeSetup(testCasesInfo.signUpPageInfo().getRegisterNewAccount_Test_title()
                , testCasesInfo.signUpPageInfo().getRegisterNewAccount_Test_desc());

        signUpPage.sendTokenToEmail(email + "@mailinator.com")
                .setVerificationCode_field(Mailinator.getToken(email))
                .clickOn_verifyCode_BTN()
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .setFirstName(firstName)
                .setLastName(lastName)
                .selectRandomCountry()
                .selectRandomConsent()
                .clickOn_create_BTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.acceptTermsConditions()
                .skipWelcomeScreen()
                .logout();
    }

    @Test(groups = {"smoke"})
    public void invalidEmail_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getInvalidEmail_Test_title()
                , testCasesInfo.signUpPageInfo().getInvalidEmail_Test_desc());

        verifyListOfInvalidEmails();
    }

    @Test(groups = {"smoke"})
    public void invalidToken_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getInvalidToken_Test_title()
                , testCasesInfo.signUpPageInfo().getInvalidToken_Test_desc());

        signUpPage.sendTokenToEmail(testData.getRandomEmail())
                .setVerificationCode_field(testData.getInvalidToken())
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(signUpPage.incorrectVerCode_Msg());
    }

    @Test(enabled = false)
    public void expiredToken_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getExpiredToken_Test_title()
                , testCasesInfo.signUpPageInfo().getExpiredToken_Test_desc());

        signUpPage.sendTokenToEmail(testData.getGmailEmail());
        String token = TestUtil.getTokenFromGmail();
        TestUtil.waitForSomeMinutes(testData.getMinForTokenToExpire());
        signUpPage.setVerificationCode_field(token)
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(signUpPage.expiredVerCode_Msg());
    }

    @Test()
    public void tooManyIncorrectAttemptsToken_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getTooManyIncorrectAttemptsToken_Test_title()
                , testCasesInfo.signUpPageInfo().getTooManyIncorrectAttemptsToken_Test_desc());

        verifyInvalidTokenTooManyTimes();
    }

    @Test(groups = {"smoke"})
    public void sendNewCode_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getSendNewCode_Test_title()
                , testCasesInfo.signUpPageInfo().getSendNewCode_Test_desc());

        signUpPage.sendTokenToEmail(testData.getGmailEmail());
        String firstToken = TestUtil.getTokenFromGmail();
        signUpPage.sendTokenToEmail(testData.getGmailEmail());
        String secondToken = TestUtil.getTokenFromGmail();

        signUpPage.setVerificationCode_field(firstToken)
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(signUpPage.incorrectVerCode_Msg());

        signUpPage.setVerificationCode_field(secondToken)
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
    }

    @Test
    public void resendEmail_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getResendEmail_Test_title()
                , testCasesInfo.signUpPageInfo().getResendEmail_Test_desc());

        verifyEmail();
        signUpPage.clickOn_changeEmail_BTN();
    }

    @Test
    public void incorrectPasswordFormat_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getIncorrectPasswordFormat_Test_title()
                , testCasesInfo.signUpPageInfo().getIncorrectPasswordFormat_Test_desc());

        signUpPage.tryIncorrectPasswords(signUpPage.newPassword_field());
    }

    @Test
    public void incorrectPasswordFormatConfirmPassField_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getIncorrectPasswordFormatConfirmPassField_Test_title()
                , testCasesInfo.signUpPageInfo().getIncorrectPasswordFormatConfirmPassField_Test_desc());

        signUpPage.tryIncorrectPasswords(signUpPage.confNewPassword_field());
    }

    @Test(groups = {"smoke"})
    public void mismatchingPasswords_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getMismatchingPasswords_Test_title(),
                testCasesInfo.signUpPageInfo().getMismatchingPasswords_Test_desc());

        verifyEmail();

        signUpPage.setFirstName(testData.getFirstName())
                .setLastName(testData.getLastName())
                .selectRandomCountry()
                .selectRandomConsent()

                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd() + "extra")
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.mismatchingPass_Msg());
    }

    @Test
    public void noCountrySelected_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getNoCountrySelected_Test_title(),
                testCasesInfo.signUpPageInfo().getNoCountrySelected_Test_desc());

        verifyEmail();
        signUpPage.setFirstName(testData.getFirstName())
                .setLastName(testData.getLastName())
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .selectRandomConsent()
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.blankCountry_Msg());
    }

    @Test(groups = {"smoke"})
    public void noConsent_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getNoConsent_Test_title(),
                testCasesInfo.signUpPageInfo().getNoConsent_Test_desc());

        verifyEmail();
        signUpPage.setFirstName(testData.getFirstName())
                .setLastName(testData.getLastName())
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .selectRandomCountry()
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.requiredFieldMissing_Msg());
    }

    @Test
    public void noFirstName_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getNoFirstName_Test_title(),
                testCasesInfo.signUpPageInfo().getNoFirstName_Test_desc());

        verifyEmail();
        signUpPage.setLastName(testData.getLastName())
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .selectRandomCountry()
                .selectRandomConsent()
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.requiredFieldMissing_Msg());
    }

    @Test
    public void noLastName_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getNoLastName_Test_title(),
                testCasesInfo.signUpPageInfo().getNoLastName_Test_desc());

        verifyEmail();
        signUpPage.setFirstName(testData.getFirstName())
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .selectRandomCountry()
                .selectRandomConsent()
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.requiredFieldMissing_Msg());
    }

    @Test
    public void cancelRegistration_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getCancelRegistration_Test_title(),
                testCasesInfo.signUpPageInfo().getCancelRegistration_Test_desc());

        verifyEmail();
        signUpPage.setFirstName(testData.getFirstName())
                .setLastName(testData.getLastName())
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .selectRandomCountry()
                .selectRandomConsent()
                .clickOn_cancel_BTN();
        landingPage.verifyIfPageLoaded();
    }

    @Test
    public void registerWithExistingEmail_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getRegisterWithExistingEmail_Test_title()
                , testCasesInfo.signUpPageInfo().getRegisterWithExistingEmail_Test_desc());

        signUpPage.sendTokenToEmail(testData.getEmailOfExistingAcc())
                .setVerificationCode_field(Mailinator.getToken(testData.getEmailOfExistingAcc().replaceAll("@.*", "")))
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
        signUpPage.setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .setFirstName(testData.getFirstName())
                .setLastName(testData.getLastName())
                .selectRandomCountry()
                .selectRandomConsent()
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.existingUserErr_Msg());
    }

    //---

    private void goToSignUpPage() {
        landingPage.verifyIfPageLoaded();
        landingPage.clickOn_signUpBTN();
        signUpPage.verifyIfPageLoaded();
    }

    private void executeSetup(String testCaseTitle, String testCaseDescription) {
        ExtentReport.createTestCase(testCaseTitle, testCaseDescription);
        ExtentReport.assignCategory(testCasesInfo.signUpPageInfo().getCategory());
        goToSignUpPage();
    }

    private void verifyListOfInvalidEmails() {
        Object[][] invalidEmailsList = getDataFromExcel(testData.getNameOfInvalidEmailsFile(), testData.getNameOfFirstSheet());
        for (int i = 1; i < invalidEmailsList.length; i++) {
            signUpPage.clearField(signUpPage.email_field())
                    .setEmail(invalidEmailsList[i][0].toString())
                    .checkErrMsgIsDisplayed(signUpPage.invalidEmail_Msg());
        }
    }

    private void verifyInvalidTokenTooManyTimes() {
        int timesToRetry = 2;
        signUpPage.sendTokenToEmail(testData.getRandomEmail());
        for (int i = 0; i < timesToRetry; i++) {
            signUpPage.setVerificationCode_field(String.valueOf(i + 10000))
                    .clickOn_verifyCode_BTN()
                    .checkErrMsgIsDisplayed(signUpPage.incorrectVerCode_Msg());
        }
        signUpPage.setVerificationCode_field("10002")
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(signUpPage.tooManyAttempts_Msg());
    }

    private void verifyEmail() {
        //maybe put this in SignUpPage class instead?
        signUpPage.sendTokenToEmail(testData.getGmailEmail())
                .setVerificationCode_field(TestUtil.getTokenFromGmail())
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
    }
}