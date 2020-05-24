package testCases;

import base.TestBase;
import org.testng.annotations.Test;
import pages.*;
import utils.ExtentReport;

import static pages.LandingPage.getLandingPage;
import static pages.LibraryPage.getLibraryPage;
import static pages.LoginPage.getLoginPage;
import static pages.RecoverPasswordPage.getRecoverPasswordPage;
import static pages.SignUpPage.getSignUpPage;
import static utils.TestUtil.getDataFromExcel;

public class LoginPageTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;

    // Test cases begin here------------------------------------------------------------
    @Test(enabled = false) /*For testing purposes*/
    public void title_Test() {
        executeSetup("title", "description");

        loginPage.setEmail("flirtest2@mailinator.com")
                .setPass("QAZxsw123")
                .clickOn_signInBTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.logout();
    }

    @Test
    public void invalidEmail_Test() {
//        https://jiracommercial.flir.com/browse/THAL-2555
        executeSetup(testCasesInfo.loginPageInfo().getInvalidEmail_Test_title(),
                testCasesInfo.loginPageInfo().getInvalidEmail_Test_desc());

        verifyListOfInvalidEmails();
    }

    @Test
    public void blankPassword_Test() {
        executeSetup(testCasesInfo.loginPageInfo().getBlankPassword_Test_title(),
                testCasesInfo.loginPageInfo().getBlankPassword_Test_desc());

        loginPage.setEmail(testData.getRandomEmail())
                .setPass("")
                .clickOn_signInBTN();

        loginPage.invalidPassError_Msg();
    }

    @Test
    public void incorrectPassword_Test() {
        executeSetup(testCasesInfo.loginPageInfo().getIncorrectPassword_Test_title(),
                testCasesInfo.loginPageInfo().getIncorrectPassword_Test_desc());

        loginPage.setEmail(testData.getEmailOfExistingAcc())
                .setPass(testData.getIncorrectPass())
                .clickOn_signInBTN();
        loginPage.incorrectPassError_Msg();
    }

    @Test(groups = {"smoke"})
    public void loginWithNonExistingAccount_Test() {
        executeSetup(testCasesInfo.loginPageInfo().getLoginWithNonExistingAccount_Test_title(),
                testCasesInfo.loginPageInfo().getLoginWithNonExistingAccount_Test_desc());

        loginPage.setEmail(testData.getRandomEmail())
                .setPass(testData.getIncorrectPass())
                .clickOn_signInBTN();

        loginPage.nonExistingAccount_Msg();
    }

    @Test(groups = {"smoke"}, priority = 100) /*execute this TestCase last*/
    public void loginWithValidCredentials_Test() {
        executeSetup(testCasesInfo.loginPageInfo().getLoginWithValidCredentials_Test_title(),
                testCasesInfo.loginPageInfo().getLoginWithValidCredentials_Test_desc());

        loginPage.setEmail(testData.getEmailOfExistingAcc())
                .setPass(testData.getPassOfExistingAcc())
                .clickOn_signInBTN();

        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded();
    }

    @Test(groups = {"smoke"})
    public void clickSignUpLink_Test() {
        executeSetup(testCasesInfo.loginPageInfo().getClickSignUpLink_Test_title(),
                testCasesInfo.loginPageInfo().getClickSignUpLink_Test_desc());

        loginPage.clickOn_signUpLink();

        SignUpPage signUpPage = getSignUpPage();
        signUpPage.verifyIfPageLoaded();
    }

    @Test(groups = {"smoke"})
    public void clickForgotPasswordLink_Test() {
        executeSetup(testCasesInfo.loginPageInfo().getClickForgotPasswordLink_Test_title(),
                testCasesInfo.loginPageInfo().getClickForgotPasswordLink_Test_Test_desc());

        loginPage.clickOn_forgotPasswordLink();
        RecoverPasswordPage recoverPasswordPage = getRecoverPasswordPage();
        recoverPasswordPage.verifyIfPageLoaded();
    }

    //---
    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded()
                .clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

    private void executeSetup(String testCaseTitle, String testCaseDescription) {
        landingPage = getLandingPage();
        loginPage = getLoginPage();
        ExtentReport.createTestCase(testCaseTitle, testCaseDescription);
        ExtentReport.assignCategory(testCasesInfo.loginPageInfo().getCategory());
        goToLoginPage();
    }

    private void verifyListOfInvalidEmails() {
        Object[][] invalidEmailsList = getDataFromExcel(testData.getNameOfInvalidEmailsFile(), testData.getNameOfFirstSheet());
        for (int i = 1; i < invalidEmailsList.length; i++) {
            loginPage.clearField(loginPage.email_field())
                    .setEmail(invalidEmailsList[i][0].toString())
                    .clickOn_signInBTN();
            loginPage.invalidEmailError_Msg();
        }
    }

}
