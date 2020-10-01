package testCases.recoverPassPage;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LibraryPage;
import pages.LoginPage;
import pages.RecoverPasswordPage;
import utils.TestUtil;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseDesc;

import static pages.LibraryPage.getLibraryPage;
import static pages.LoginPage.getLoginPage;
import static pages.RecoverPasswordPage.getRecoverPasswordPage;

public class RecoverPassPagePositiveTests extends TestBase {
    LoginPage loginPage;
    RecoverPasswordPage recoverPasswordPage;


    @Test
    public void changePassword_Test() {
        executeSetup(TestCaseDesc.RECOVERPASSPAGE_CHANGEPASSWORD);

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

    private void executeSetup(TestCaseDesc testCaseDesc) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("************************Begin test " + parentMethodName +"*********************************");
        String testCaseDescription = String.valueOf(testCaseDesc.description);
        String testCaseCategory = String.valueOf(TestCaseCategory.RECOVERPASSWORD_PAGE);
        createTestCase(parentMethodName,testCaseDescription,testCaseCategory);

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
                .setVerificationCode(TestUtil.getTokenFromGmail())
                .clickOn_verifyCode_BTN();
    }

}
