package testCases.libraryPage;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;
import utils.ExtentReport;
import utils.TestUtil;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseHeader;

import static pages.LandingPage.getLandingPage;
import static pages.LoginPage.getLoginPage;

public class LibraryPagePositiveTests extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;
    LibraryPage libraryPage;

    @Test
    public void createNewFolder_Test() {
        executeSetup(TestCaseHeader.LIBRARYPAGE_CREATENEWFOLDER);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.createNewFolder(TestUtil.getRandomString(7));

        libraryPage.logout();
    }
//
//    @Test
//    public void uploadValidFile_Test() {
//        String testCaseTitle = "LIBRARY PAGE - uploadValidFile_Test";
//        String testCaseDescription = "Successfully upload a valid file";
//        String fileLocation = System.getProperty("user.dir") + "\\src\\main\\java\\testData\\Images\\validImage.jpg";
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToLibraryPage();
//
//        libraryPage.uploadFile(fileLocation);
//    }

    //----

    private void executeSetup(TestCaseHeader testCaseHeader) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("************************Begin test " + parentMethodName +"*********************************");
        ExtentReport.createTestCase(parentMethodName, testCaseHeader.description);
        ExtentReport.assignCategory(String.valueOf(TestCaseCategory.LIBRARY_PAGE));

        landingPage = getLandingPage();

        loginPage = getLoginPage();
        goToLoginPage();
        log.info("************************End test " + parentMethodName +"*********************************");
    }

    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded()
                .clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

}
