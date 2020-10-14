package testCases.signUpPage;

import base.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LibraryPage;
import pages.SignUpPage;
import utils.TestUtil;
import utils.emailManager.NewMailinator;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseDesc;

import static pages.LibraryPage.getLibraryPage;
import static pages.SignUpPage.getSignUpPage;
import static utils.TestUtil.getDataFromExcel;

public class SignUpPagePositiveTests extends TestBase {
    SignUpPage signUpPage;
    NewMailinator mailinator = new NewMailinator();

    /*
    For SSO-LAB API there consent field is NOT displayed in the SignUP page
    For SSO-PROD API the field IS displayed
    */

    @DataProvider
    public Object[][] getTestData() {
        String fileName = "Accounts";
        return getDataFromExcel(fileName, testData.getNameOfFirstSheet());
    }

    @Test(dataProvider = "getTestData", groups = {"smoke", "regression"}, priority = 100, enabled = true)
    public void registerNewAccount_Test(String email, String firstName, String lastName) {
        executeSetup(TestCaseDesc.SIGNUPPAGE_REGISTERNEWACCOUNT);

        signUpPage.sendTokenToEmail(email + "@mailinator.com")
                .setVerificationCode_field(mailinator.getToken(email))
                .clickOn_verifyCode_BTN()
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .setFirstName(firstName)
                .setLastName(lastName)
                .selectRandomCountry()
//                .selectRandomConsent();
                .clickOn_create_BTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.acceptTermsConditions()
                .skipWelcomeScreen()
                .logout();
    }

    @Test
    public void registerNewRandomAccount_Test() {
        executeSetup(TestCaseDesc.SIGNUPPAGE_REGISTERNEWRANDOMACCOUNT);
        String email = TestUtil.getRandomString(8);
        String firstName = TestUtil.getRandomString(5);
        String lastName = TestUtil.getRandomString(5);

        signUpPage.sendTokenToEmail(email + "@mailinator.com")
                .setVerificationCode_field(mailinator.getToken(email))
                .clickOn_verifyCode_BTN()
                .setNewPassword(testData.getValidAccountPasswd())
                .setConfirmNewPassword(testData.getValidAccountPasswd())
                .setFirstName(firstName)
                .setLastName(lastName)
                .selectRandomCountry()
//                .selectRandomConsent();
                .clickOn_create_BTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.acceptTermsConditions()
                .skipWelcomeScreen()
                .logout();
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
}