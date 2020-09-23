package testCases.recoverPassPage;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;
import pages.RecoverPasswordPage;
import utils.ExtentReport;
import utils.TestUtil;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseHeader;

import static pages.LandingPage.getLandingPage;
import static pages.LibraryPage.getLibraryPage;
import static pages.LoginPage.getLoginPage;
import static pages.RecoverPasswordPage.getRecoverPasswordPage;

public class RecoverPassPagePositiveTests extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;
    RecoverPasswordPage recoverPasswordPage;


    @Test
    public void changePassword_Test() {
        executeSetup(TestCaseHeader.RECOVERPASSPAGE_CHANGEPASSWORD);

        goToChangePassScreen();

        recoverPasswordPage.setNewPassword(testData.getPassOfExistingAcc())
                .setConfirmNewPassword(testData.getPassOfExistingAcc())
                .clickOn_continue_BTN();
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

    private void executeSetup(TestCaseHeader testCaseHeader) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("----Begin to test " + parentMethodName + "----");
        ExtentReport.createTestCase(parentMethodName, testCaseHeader.description);
        ExtentReport.assignCategory(String.valueOf(TestCaseCategory.RECOVERPASSWORD_PAGE));

        landingPage = getLandingPage();

        loginPage = getLoginPage();
        recoverPasswordPage = getRecoverPasswordPage();
        goToRecoverPassPage();
    }

    private void goToChangePassScreen() {
        verifyEmail();
        recoverPasswordPage.clickOn_continue_BTN()
                .verifyIfChangePassScreenLoaded();
    }

    private void verifyEmail() {
        recoverPasswordPage.sendTokenToEmail(testData.getGmailEmail())
                .setVerificationCode_field(TestUtil.getTokenFromGmail())
                .clickOn_verifyCode_BTN()
                .changeEmail_BTN();
    }

}
