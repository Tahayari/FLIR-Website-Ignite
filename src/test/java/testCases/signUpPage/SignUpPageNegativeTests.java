package testCases.signUpPage;

import base.TestBase;
import org.testng.annotations.Test;
import pages.SignUpPage;
import utils.TestUtil;
import utils.emailManager.Mailinator;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseDesc;

import static pages.SignUpPage.getSignUpPage;
import static utils.TestUtil.getDataFromExcel;

public class SignUpPageNegativeTests extends TestBase {
    SignUpPage signUpPage;

    /*
    For SSO-LAB API there consent field is NOT displayed in the SignUP page
    For SSO-PROD API the field IS displayed
    */

    @Test(groups = {"smoke"})
    public void invalidEmail_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_INVALIDEMAIL);

        verifyListOfInvalidEmails();
    }

    @Test(groups = {"smoke"})
    public void invalidToken_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_INVALIDTOKEN);

        signUpPage.sendTokenToEmail(testData.getRandomEmail())
                .setVerificationCode_field(testData.getInvalidToken())
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(signUpPage.incorrectVerCode_Msg());
    }

    @Test()
    public void expiredToken_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_EXPIREDTOKEN);

        signUpPage.sendTokenToEmail(testData.getGmailEmail());
        String token = TestUtil.getTokenFromGmail();
        TestUtil.waitForSomeMinutes(testData.getMinForTokenToExpire());
        signUpPage.setVerificationCode_field(token)
                .clickOn_verifyCode_BTN()
                .checkErrMsgIsDisplayed(signUpPage.expiredVerCode_Msg());
    }

    @Test()
    public void tooManyIncorrectAttemptsToken_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_TOOMANYINCORRECTTOKENATTEMPTS);

        verifyInvalidTokenTooManyTimes();
    }

    @Test(groups = {"smoke"})
    public void sendNewCode_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_SENDNEWCODE);

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
    public void resendToken_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_RESENDTOKEN);

        signUpPage.sendTokenToEmail(testData.getGmailEmail());
        String firstToken = TestUtil.getTokenFromGmail();
        signUpPage.clickOn_sendNewCode_BTN();
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
        executeSetup(TestCaseDesc.SIGNUPPAGE_RESENDEMAIL);

        verifyEmail();
        signUpPage.clickOn_changeEmail_BTN();
    }

    @Test
    public void incorrectPasswordFormat_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_INCORRECTPASSFORMAT);

        signUpPage.tryIncorrectPasswords(signUpPage.newPassword_field());
    }

    @Test
    public void incorrectPasswordFormatConfirmPassField_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_INCORRECTCONFIRMPASSFORMAT);

        signUpPage.tryIncorrectPasswords(signUpPage.confNewPassword_field());
    }

    @Test(groups = {"smoke"})
    public void mismatchingPasswords_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_MISSMATCHINGPASSWORDS);

        verifyEmail();

        signUpPage.setFirstName(testData.getFirstName())
                .setLastName(testData.getLastName())
                .selectRandomCountry()
//                .selectRandomConsent()
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd() + "extra")
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.mismatchingPass_Msg());
    }

    @Test
    public void noCountrySelected_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_NOCOUNTRYSELECTED);

        verifyEmail();
        signUpPage.setFirstName(testData.getFirstName())
                .setLastName(testData.getLastName())
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
//                .selectRandomConsent()
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.blankCountry_Msg());
    }

    @Test(groups = {"smoke"})
    public void noConsent_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_NOCONSENT);

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
        executeSetup(TestCaseDesc.SIGNUPPAGE_NOFIRSTNAME);

        verifyEmail();
        signUpPage.setLastName(testData.getLastName())
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .selectRandomCountry()
//                .selectRandomConsent()
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.requiredFieldMissing_Msg());
    }

    @Test
    public void noLastName_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_NOLASTNAME);

        verifyEmail();
        signUpPage.setFirstName(testData.getFirstName())
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .selectRandomCountry()
//                .selectRandomConsent()
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.requiredFieldMissing_Msg());
    }

    @Test
    public void cancelRegistration_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_CANCELREGISTRATION);

        verifyEmail();
        signUpPage.setFirstName(testData.getFirstName())
                .setLastName(testData.getLastName())
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .selectRandomCountry()
//                .selectRandomConsent()
                .clickOn_cancel_BTN();
        landingPage.verifyIfPageLoaded();
    }

    @Test
    public void registerWithExistingEmail_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_REGISTERWITHEXISTINGMAIL);

        signUpPage.sendTokenToEmail(testData.getEmailOfExistingAcc())
                .setVerificationCode_field(Mailinator.getToken(testData.getEmailOfExistingAcc().replaceAll("@.*", "")))
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
        signUpPage.setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .setFirstName(testData.getFirstName())
                .setLastName(testData.getLastName())
                .selectRandomCountry()
//                .selectRandomConsent()
                .clickOn_create_BTN()
                .checkErrMsgIsDisplayed(signUpPage.existingUserErr_Msg());
    }

    //---

    private void goToSignUpPage() {
        landingPage.verifyIfPageLoaded();
        landingPage.clickOn_signUpBTN();
        signUpPage.verifyIfPageLoaded();
    }

    private void executeSetup(TestCaseDesc testCaseDesc) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("************************Begin test " + parentMethodName +"*********************************");
        String testCaseDescription = String.valueOf(testCaseDesc.description);
        String testCaseCategory = String.valueOf(TestCaseCategory.SIGNUP_PAGE);
        createTestCase(parentMethodName,testCaseDescription,testCaseCategory);

        signUpPage = getSignUpPage();
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
        signUpPage.sendTokenToEmail(testData.getGmailEmail())
                .setVerificationCode_field(TestUtil.getTokenFromGmail())
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
    }
}