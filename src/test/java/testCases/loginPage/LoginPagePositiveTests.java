package testCases.loginPage;

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

public class LoginPagePositiveTests extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;

    @Test(groups = {"smoke"})
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
        log.info("************************Begin test " + parentMethodName +"*********************************");

        ExtentReport.createTestCase(parentMethodName, testCaseHeader.description);
        ExtentReport.assignCategory(String.valueOf(TestCaseCategory.LOGIN_PAGE));

        landingPage = getLandingPage();

        loginPage = getLoginPage();
        goToLoginPage();
        log.info("************************End test " + parentMethodName +"*********************************");
    }

}
