package testCases;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;
import utils.ExtentReport;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseHeader;

import static pages.LandingPage.getLandingPage;
import static pages.LoginPage.getLoginPage;

public class SmokeTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;
    LibraryPage libraryPage;

    @Test
    public void smokeTest1() {

        executeSetup(TestCaseHeader.SMOKETEST_TEST1);

        libraryPage = loginPage.login(testData.getEmailOfExistingAcc(),testData.getPassOfExistingAcc());

    }

    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded()
                .clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

    private void executeSetup(TestCaseHeader testCaseHeader) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("----Begin to test " + parentMethodName + "----");
        ExtentReport.createTestCase(parentMethodName, testCaseHeader.description);
        ExtentReport.assignCategory(String.valueOf(TestCaseCategory.LANDING_PAGE));

        landingPage = getLandingPage();

        loginPage = getLoginPage();
        goToLoginPage();
    }
}
