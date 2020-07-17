package testCases;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;
import utils.ExtentReport;

import static pages.LandingPage.getLandingPage;
import static pages.LibraryPage.getLibraryPage;
import static pages.LoginPage.getLoginPage;

public class SmokeTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;

    @Test
    public void smokeTest1() {

        executeSetup("SmokeTest_1","Upload image,change measurements,");

        loginPage.setEmail(testData.getEmailOfExistingAcc())
                .setPass(testData.getPassOfExistingAcc())
                .clickOn_signInBTN();

        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded();
    }

    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded()
                .clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

    private void executeSetup(String testCaseTitle, String testCaseDescription) {
        log.info("----Begin to test " + testCaseTitle + "----");
        landingPage = getLandingPage();
        loginPage = getLoginPage();
        ExtentReport.createTestCase(testCaseTitle, testCaseDescription);
        ExtentReport.assignCategory(testCasesInfo.loginPageInfo().getCategory());
        goToLoginPage();
    }
}
