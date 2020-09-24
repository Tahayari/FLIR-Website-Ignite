package testCases.loginPage;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;
import utils.ExtentReport;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseHeader;

import static pages.LandingPage.getLandingPage;
import static pages.LibraryPage.getLibraryPage;
import static pages.LoginPage.getLoginPage;
import static utils.TestUtil.getDataFromExcel;

public class LoginPageNegativeTests extends TestBase {
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
