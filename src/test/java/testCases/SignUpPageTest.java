package testCases;

import base.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.SignUpPage;
import utils.ExtentReport;
import utils.TestUtil;
import utils.emailManager.EmailManager;

import java.io.IOException;

import static pages.LandingPage.getLandingPage;
import static pages.SignUpPage.getSignUpPage;
import static utils.TestUtil.getDataFromExcel;

public class SignUpPageTest extends TestBase {
    LandingPage landingPage = getLandingPage();
    SignUpPage signUpPage = getSignUpPage();
    EmailManager emailManager = new EmailManager();

    // Test cases begin here------------------------------------------------------------
    @Test(enabled = true)
    public void newGoogle() {
        executeSetup("title", "description");

        signUpPage.sendTokenToEmail(testData.getGmailEmail())
                .setVerificationCode_field(TestUtil.getToken())
                .clickOn_verifyCode_BTN();
        signUpPage.changeEmail_BTN();
    }

    @DataProvider
    public Object[][] getTestData() {
        String fileName = "Accounts";
        return getDataFromExcel(fileName, testData.getNameOfFirstSheet());
    }

    //TODO
    @Test(dataProvider = "getTestData", groups = {"smoke", "regression"}, enabled = false, priority = 100)
    public void registerNewAccount_Test(String email, String password, String firstName, String lastName) {
        executeSetup(testCasesInfo.signUpPageInfo().getRegisterNewAccount_Test_title()
                , testCasesInfo.signUpPageInfo().getRegisterNewAccount_Test_desc());

        signUpPage.sendTokenToEmail(email + "@mailinator.com");
        signUpPage.enterTokenFromMailinator(email);
        signUpPage.setNewPassword(password);
        signUpPage.setConfirmNewPassword(password);
        signUpPage.setFirstName(firstName);
        signUpPage.setLastName(lastName);
        signUpPage.selectRandomCountry();
        signUpPage.selectRandomConsent();
        LibraryPage libraryPage = signUpPage.createNewAccount();
        libraryPage.acceptTermsConditions();
        libraryPage.skipWelcomeScreen();
    }

    //Done
    @Test(groups = {"smoke"})
    public void invalidEmail_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getInvalidEmail_Test_title()
                , testCasesInfo.signUpPageInfo().getInvalidEmail_Test_desc());

        verifyListOfInvalidEmails();
    }

    //Done
    @Test(groups = {"smoke"})
    public void invalidToken_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getInvalidToken_Test_title()
                , testCasesInfo.signUpPageInfo().getInvalidToken_Test_desc());

        signUpPage.sendTokenToEmail(testData.getRandomEmail())
                .setVerificationCode_field(testData.getInvalidToken())
                .clickOn_verifyCode_BTN();

        signUpPage.incorrectVerCode_Msg();
    }

    //TODO with the new email API
    @Test(enabled = true)
    public void expiredToken_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getExpiredToken_Test_title()
                , testCasesInfo.signUpPageInfo().getExpiredToken_Test_desc());

//        emailManager.prepareGmail();
        signUpPage.sendTokenToEmail(testData.getGmailEmail());
        String token = TestUtil.getToken();
        TestUtil.waitForSomeMinutes(testData.getMinForTokenToExpire());
        signUpPage.setVerificationCode_field(token)
                .clickOn_verifyCode_BTN()
                .expiredVerCode_Msg();
    }

    //Done
    @Test()
    public void tooManyIncorrectAttemptsToken_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getTooManyIncorrectAttemptsToken_Test_title()
                , testCasesInfo.signUpPageInfo().getTooManyIncorrectAttemptsToken_Test_desc());

        verifyInvalidTokenTooManyTimes();
    }

    //Done
    @Test(groups = {"smoke"})
    public void sendNewCode_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getSendNewCode_Test_title()
                , testCasesInfo.signUpPageInfo().getSendNewCode_Test_desc());

        signUpPage.sendTokenToEmail(testData.getGmailEmail());
        String firstToken = TestUtil.getToken();
        signUpPage.sendTokenToEmail(testData.getGmailEmail());
        String secondToken = TestUtil.getToken();

        signUpPage.setVerificationCode_field(firstToken)
                .clickOn_verifyCode_BTN()
                .incorrectVerCode_Msg();
        signUpPage.setVerificationCode_field(secondToken)
                .clickOn_verifyCode_BTN();
        signUpPage.changeEmail_BTN();
    }

    //TODO
    @Test(enabled = false)
    public void resendEmail_Test() throws IOException {

        executeSetup(testCasesInfo.signUpPageInfo().getResendEmail_Test_title()
                , testCasesInfo.signUpPageInfo().getResendEmail_Test_desc());

        String email = prop.getProperty("gmail");
//        testUtil.prepareGmail();

        signUpPage.sendTokenToEmail(email);

        signUpPage.enterTokenFromGmail();

        signUpPage.clickOn_changeEmail_BTN();
    }

    @Test(enabled = false)
    public void incorrectPasswordFormat_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getIncorrectPasswordFormat_Test_title()
                , testCasesInfo.signUpPageInfo().getIncorrectPasswordFormat_Test_desc());

        signUpPage.tryIncorrectPasswords(signUpPage.newPassword_field());
    }

    @Test(enabled = false)
    public void incorrectPasswordFormatConfirmPassField_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getIncorrectPasswordFormatConfirmPassField_Test_title()
                , testCasesInfo.signUpPageInfo().getIncorrectPasswordFormatConfirmPassField_Test_desc());

        signUpPage.tryIncorrectPasswords(signUpPage.confNewPassword_field());
    }

    @Test(groups = {"smoke"}, enabled = false)
    public void mismatchingPasswords_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getMismatchingPasswords_Test_title(),
                testCasesInfo.signUpPageInfo().getMismatchingPasswords_Test_desc());

        String email = prop.getProperty("gmail");
        String firstName = "firstName";
        String lastName = "lastName";

//        testUtil.prepareGmail();
        signUpPage.sendTokenToEmail(email);
        signUpPage.enterTokenFromGmail();
        signUpPage.setFirstName(firstName);
        signUpPage.setLastName(lastName);
        signUpPage.selectRandomCountry();
        signUpPage.selectRandomConsent();

        signUpPage.enterMismatchingPasswords();
    }

    @Test(enabled = false)
    public void noCountrySelected_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getNoCountrySelected_Test_title(),
                testCasesInfo.signUpPageInfo().getNoCountrySelected_Test_desc());
        String email = prop.getProperty("gmail");
        String pass = "Password1!";
        String firstName = "firstName";
        String lastName = "lastName";

//        testUtil.prepareGmail();
        signUpPage.sendTokenToEmail(email);
        signUpPage.enterTokenFromGmail();
        signUpPage.setFirstName(firstName);
        signUpPage.setLastName(lastName);
        signUpPage.setNewPassword(pass);
        signUpPage.setConfirmNewPassword(pass);
        signUpPage.selectRandomConsent();

        signUpPage.createUserWithoutCountry();
    }

    @Test(groups = {"smoke"}, enabled = false)
    public void noConsent_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getNoConsent_Test_title(),
                testCasesInfo.signUpPageInfo().getNoConsent_Test_desc());
        String email = prop.getProperty("gmail");
        String pass = "PASSWORD123!";
        String firstName = "firstName";
        String lastName = "lastName";

//        testUtil.prepareGmail();
        signUpPage.sendTokenToEmail(email);
        signUpPage.enterTokenFromGmail();
        signUpPage.setFirstName(firstName);
        signUpPage.setLastName(lastName);
        signUpPage.setNewPassword(pass);
        signUpPage.setConfirmNewPassword(pass);
        signUpPage.selectRandomCountry();

        signUpPage.createUserWithoutMandatoryField();
    }

    @Test(enabled = false)
    public void noFirstName_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getNoFirstName_Test_title(),
                testCasesInfo.signUpPageInfo().getNoFirstName_Test_desc());
        String email = prop.getProperty("gmail");
        String pass = "PASSWORD123!";
        String lastName = "lastName";

//        testUtil.prepareGmail();
        signUpPage.sendTokenToEmail(email);
        signUpPage.enterTokenFromGmail();
        signUpPage.setLastName(lastName);
        signUpPage.setNewPassword(pass);
        signUpPage.setConfirmNewPassword(pass);
        signUpPage.selectRandomCountry();
        signUpPage.selectRandomConsent();

        signUpPage.createUserWithoutMandatoryField();
    }

    @Test(enabled = false)
    public void noLastName_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getNoLastName_Test_title(),
                testCasesInfo.signUpPageInfo().getNoLastName_Test_desc());
        String email = prop.getProperty("gmail");
        String pass = "PASSWORD123!";
        String firstName = "firstName";

//        testUtil.prepareGmail();
        signUpPage.sendTokenToEmail(email);
        signUpPage.enterTokenFromGmail();
        signUpPage.setFirstName(firstName);
        signUpPage.setNewPassword(pass);
        signUpPage.setConfirmNewPassword(pass);
        signUpPage.selectRandomCountry();
        signUpPage.selectRandomConsent();

        signUpPage.createUserWithoutMandatoryField();
    }

    @Test(enabled = false)
    public void cancelRegistration_Test() {
        executeSetup(testCasesInfo.signUpPageInfo().getCancelRegistration_Test_title(),
                testCasesInfo.signUpPageInfo().getCancelRegistration_Test_desc());
        String email = prop.getProperty("gmail");
        String pass = "PASSWORD123!";
        String firstName = "firstName";
        String lastName = "lastName";

//        testUtil.prepareGmail();
        signUpPage.sendTokenToEmail(email);
        signUpPage.enterTokenFromGmail();
        signUpPage.setFirstName(firstName);
        signUpPage.setLastName(lastName);
        signUpPage.setNewPassword(pass);
        signUpPage.setConfirmNewPassword(pass);
        signUpPage.selectRandomCountry();
        signUpPage.selectRandomConsent();

        signUpPage.cancelRegistration();
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
                    .setEmail(invalidEmailsList[i][0].toString());
            signUpPage.invalidEmail_Msg();
        }
    }

    private void verifyInvalidTokenTooManyTimes() {
        int timesToRetry = 2;
        signUpPage.sendTokenToEmail(testData.getRandomEmail());
        for (int i = 0; i < timesToRetry; i++) {
            signUpPage.setVerificationCode_field(String.valueOf(i + 10000))
                    .clickOn_verifyCode_BTN()
                    .incorrectVerCode_Msg();
        }
        signUpPage.setVerificationCode_field("10002")
                .clickOn_verifyCode_BTN()
                .tooManyAttempts_Msg();
    }

    private void verifyEmailStep() {
        emailManager.prepareGmail();
        signUpPage.sendTokenToEmail(testData.getGmailEmail())
                .setVerificationCode_field(emailManager.getTokenFromGmail())
                .clickOn_verifyCode_BTN();
    }

    private String getToken() {
        emailManager.prepareGmail();
        signUpPage.sendTokenToEmail(testData.getGmailEmail());
        return emailManager.getTokenFromGmail();
    }
}