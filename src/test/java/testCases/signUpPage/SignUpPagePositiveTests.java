package testCases.signUpPage;

import base.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.SignUpPage;
import utils.ExtentReport;
import utils.TestUtil;
import utils.emailManager.Mailinator;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseHeader;

import static pages.LandingPage.getLandingPage;
import static pages.LibraryPage.getLibraryPage;
import static pages.SignUpPage.getSignUpPage;
import static utils.TestUtil.getDataFromExcel;

public class SignUpPagePositiveTests extends TestBase {
    LandingPage landingPage;
    SignUpPage signUpPage;

    /*
    For SSO-LAB API there consent field is NOT displayed in the SignUP page
    For SSO-PROD API the field IS displayed
    */

    @DataProvider
    public Object[][] getTestData() {
        String fileName = "Accounts";
        return getDataFromExcel(fileName, testData.getNameOfFirstSheet());
    }

    @Test(dataProvider = "getTestData", groups = {"smoke", "regression"}, priority = 100, enabled = false)
    public void registerNewAccount_Test(String email, String firstName, String lastName) {
        executeSetup(TestCaseHeader.SIGNUPPAGE_REGISTERNEWACCOUNT);

        signUpPage.sendTokenToEmail(email + "@mailinator.com")
                .setVerificationCode_field(Mailinator.getToken(email))
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
        executeSetup(TestCaseHeader.SIGNUPPAGE_REGISTERNEWRANDOMACCOUNT);
        String email = TestUtil.getRandomString(8);
        String firstName = TestUtil.getRandomString(5);
        String lastName = TestUtil.getRandomString(5);

        signUpPage.sendTokenToEmail(email + "@mailinator.com")
                .setVerificationCode_field(Mailinator.getToken(email))
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

    private void executeSetup(TestCaseHeader testCaseHeader) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("************************Begin test " + parentMethodName +"*********************************");
        ExtentReport.createTestCase(parentMethodName, testCaseHeader.description);
        ExtentReport.assignCategory(String.valueOf(TestCaseCategory.SIGNUP_PAGE));

        landingPage = getLandingPage();

        signUpPage = getSignUpPage();
        goToSignUpPage();
        log.info("************************End test " + parentMethodName +"*********************************");
    }
}