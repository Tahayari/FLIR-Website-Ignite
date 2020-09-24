package testCases.loginPage;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LibraryPage;
import pages.LoginPage;
import pages.RecoverPasswordPage;
import pages.SignUpPage;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseDesc;

import static pages.LibraryPage.getLibraryPage;
import static pages.LoginPage.getLoginPage;
import static pages.RecoverPasswordPage.getRecoverPasswordPage;
import static pages.SignUpPage.getSignUpPage;

public class LoginPagePositiveTests extends TestBase {
    LoginPage loginPage;

    @Test(groups = {"smoke"})
    public void loginWithValidCredentials_Test() {
        executeSetup(TestCaseDesc.LOGINPAGE_LoginWithValidCredentials);

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
        executeSetup(TestCaseDesc.LOGINPAGE_ClickSignUpLink);

        loginPage.clickOn_signUpLink();

        SignUpPage signUpPage = getSignUpPage();
        signUpPage.verifyIfPageLoaded();
    }

    @Test(groups = {"smoke"})
    public void clickForgotPasswordLink_Test() {
        executeSetup(TestCaseDesc.LOGINPAGE_ClickForgotPassLink);

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

    private void executeSetup(TestCaseDesc testCaseDesc){
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("************************Begin test " + parentMethodName +"*********************************");
        String testCaseDescription = String.valueOf(testCaseDesc.description);
        String testCaseCategory = String.valueOf(TestCaseCategory.LOGIN_PAGE);
        createTestCase(parentMethodName,testCaseDescription,testCaseCategory);

        loginPage = getLoginPage();
        goToLoginPage();
    }

}
