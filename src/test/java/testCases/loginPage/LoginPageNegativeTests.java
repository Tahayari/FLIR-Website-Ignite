package testCases.loginPage;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LibraryPage;
import pages.LoginPage;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseDesc;

import static pages.LibraryPage.getLibraryPage;
import static pages.LoginPage.getLoginPage;
import static utils.TestUtil.getDataFromExcel;

public class LoginPageNegativeTests extends TestBase {
    LoginPage loginPage;

    @Test(enabled = false) /*For testing purposes*/
    public void title_Test() {
        executeSetup(TestCaseDesc.LOGINPAGE_BlankPassword);

        loginPage.setEmail("flirtest2@mailinator.com")
                .setPass("QAZxsw123")
                .clickOn_signInBTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.logout();
    }

    @Test
    public void blankEmail_Test() {
        executeSetup(TestCaseDesc.LOGINPAGE_BlankEmail);

        loginPage.setEmail("")
                .clickOn_signInBTN();
        loginPage.checkErrMsgIsDisplayed(loginPage.blankEmailError_Msg());
    }

    @Test
    public void invalidEmail_Test() {
//        https://jiracommercial.flir.com/browse/THAL-2555
        executeSetup(TestCaseDesc.LOGINPAGE_InvalidEmail);

        verifyListOfInvalidEmails();
    }

    @Test
    public void blankPassword_Test() {
        executeSetup(TestCaseDesc.LOGINPAGE_BlankPassword);

        loginPage.setEmail(testData.getRandomEmail())
                .setPass("")
                .clickOn_signInBTN();
        loginPage.checkErrMsgIsDisplayed(loginPage.invalidPassError_Msg());
    }

    @Test
    public void incorrectPassword_Test() {
        executeSetup(TestCaseDesc.LOGINPAGE_IncorrectPass);

        loginPage.setEmail(testData.getEmailOfExistingAcc())
                .setPass(testData.getIncorrectPass())
                .clickOn_signInBTN();
        loginPage.checkErrMsgIsDisplayed(loginPage.incorrectPassError_Msg());
    }

    @Test(groups = {"smoke"})
    public void loginWithNonExistingAccount_Test() {
        executeSetup(TestCaseDesc.LOGINPAGE_LoginWithNonExistingAccount);

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

    private void executeSetup(TestCaseDesc testCaseDesc){
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        String testCaseDescription = String.valueOf(testCaseDesc.description);
        String testCaseCategory = String.valueOf(TestCaseCategory.LOGIN_PAGE);
        createTestCase(parentMethodName,testCaseDescription,testCaseCategory);

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
