package testCases;

import base.TestBase;
import org.testng.annotations.Test;
import pages.*;
import utils.ExtentReport;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseHeader;

import static pages.LandingPage.getLandingPage;
import static pages.LibraryPage.getLibraryPage;
import static pages.LoginPage.getLoginPage;
import static pages.RecoverPasswordPage.getRecoverPasswordPage;
import static pages.SignUpPage.getSignUpPage;
import static utils.TestUtil.getDataFromExcel;

public class LoginPageTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;

    @Test(enabled = false) /*For testing purposes*/
    public void title_Test() {
        executeSetup(TestCaseHeader.LOGINPAGE_BLANKPASSWORD);

        loginPage.setEmail("flirtest2@mailinator.com")
                .setPass("QAZxsw123")
                .clickOn_signInBTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.logout();
    }

    @Test
    public void blankEmail_Test() {
        executeSetup(TestCaseHeader.LOGINPAGE_BLANKEMAIL);

        loginPage.setEmail("")
                .clickOn_signInBTN();
        loginPage.checkErrMsgIsDisplayed(loginPage.blankEmailError_Msg());
    }

    @Test
    public void invalidEmail_Test() {
//        https://jiracommercial.flir.com/browse/THAL-2555
        executeSetup(TestCaseHeader.LOGINPAGE_INVALIDEMAIL);

        verifyListOfInvalidEmails();
    }

    @Test
    public void blankPassword_Test() {
        executeSetup(TestCaseHeader.LOGINPAGE_BLANKPASSWORD);

        loginPage.setEmail(testData.getRandomEmail())
                .setPass("")
                .clickOn_signInBTN();
        loginPage.checkErrMsgIsDisplayed(loginPage.invalidPassError_Msg());
    }

    @Test
    public void incorrectPassword_Test() {
        executeSetup(TestCaseHeader.LOGINPAGE_INCORRECTPASS);

        loginPage.setEmail(testData.getEmailOfExistingAcc())
                .setPass(testData.getIncorrectPass())
                .clickOn_signInBTN();
        loginPage.checkErrMsgIsDisplayed(loginPage.incorrectPassError_Msg());
    }

    @Test(groups = {"smoke"})
    public void loginWithNonExistingAccount_Test() {
        executeSetup(TestCaseHeader.LOGINPAGE_LOGINWITHNONEXISTINGACCOUNT);

        loginPage.setEmail(testData.getRandomEmail())
                .setPass(testData.getIncorrectPass())
                .clickOn_signInBTN();

        loginPage.checkErrMsgIsDisplayed(loginPage.nonExistingAccount_Msg());
    }

    @Test(groups = {"smoke"}) /*execute this TestCase last*/
    public void loginWithValidCredentials_Test() {
        executeSetup(TestCaseHeader.LOGINPAGE_LOGINWITHVALIDCREDENTIALS);

        loginPage.setEmail(testData.getEmailOfExistingAcc())
                .setPass(testData.getPassOfExistingAcc())
                .clickOn_signInBTN();

        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded()
                .logout();
        landingPage.verifyIfPageLoaded();
    }

    @Test(groups = {"smoke"})
    public void clickSignUpLink_Test() {
        executeSetup(TestCaseHeader.LOGINPAGE_CLICKSIGNUPLINK);

        loginPage.clickOn_signUpLink();

        SignUpPage signUpPage = getSignUpPage();
        signUpPage.verifyIfPageLoaded();
    }

    @Test(groups = {"smoke"})
    public void clickForgotPasswordLink_Test() {
        executeSetup(TestCaseHeader.LOGINPAGE_CLICKFORGOTPASSLINK);

        loginPage.clickOn_forgotPasswordLink();
        RecoverPasswordPage recoverPasswordPage = getRecoverPasswordPage();
        recoverPasswordPage.verifyIfPageLoaded();
    }

    //---
    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded();
        landingPage.clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

    private void executeSetup(TestCaseHeader testCaseHeader){
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("----Begin to test " + parentMethodName + "----");
        ExtentReport.createTestCase(parentMethodName, testCaseHeader.description);
        ExtentReport.assignCategory(String.valueOf(TestCaseCategory.LOGIN_PAGE));

        landingPage = getLandingPage();

        loginPage = getLoginPage();
        goToLoginPage();
    }

    private void verifyListOfInvalidEmails() {
        Object[][] invalidEmailsList = getDataFromExcel(testData.getNameOfInvalidEmailsFile(), testData.getNameOfFirstSheet());
        for (int i = 1; i < invalidEmailsList.length; i++) {
            loginPage.clearField(loginPage.email_field())
                    .setEmail(invalidEmailsList[i][0].toString())
                    .clickOn_signInBTN();
            loginPage.checkErrMsgIsDisplayed(loginPage.invalidEmailError_Msg());
        }
    }

}
