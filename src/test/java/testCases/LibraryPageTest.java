package testCases;

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

public class LibraryPageTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;
    LibraryPage libraryPage;

    // Test cases begin here------------------------------------------------------------
    @Test
    public void testing() {
        executeSetup(TestCaseHeader.LIBRARYPAGE_CREATENEWFOLDER);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.logout();
    }


    @Test
    public void createNewFolderWithBlankName_Test() {
        executeSetup(TestCaseHeader.LIBRARYPAGE_CREATENEWFOLDERWITHBLANKNAME);

        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getValidAccountPasswd());

        libraryPage.clickOn_createNewFolder_BTN()
                .setFolderName("test")
                .clearField(libraryPage.folderName_field())
                .checkErrMsgIsDisplayed(libraryPage.folderNameError_Msg());

        libraryPage.clickOn_newFolderCancel_BTN()
                .logout();
    }

    @Test
    public void createNewFolderWithReallyLongName_Test() {
        executeSetup(TestCaseHeader.LIBRARYPAGE_CREATENEWFOLDERWITHLONGNAME);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.clickOn_createNewFolder_BTN()
                .setFolderName(testData.getLongName())
                .checkErrMsgIsDisplayed(libraryPage.folderNameError_Msg());

        libraryPage.clickOn_newFolderCancel_BTN()
                .logout();
    }

    @Test
    public void createFolderWithIllegalCharacters_Test() {
        executeSetup(TestCaseHeader.LIBRARYPAGE_CREATEFOLDERWITHILLEGALCHARACTERS);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.clickOn_createNewFolder_BTN();
        String[] invalidCharacters = {":", "/", "\\", "\"", "?", "<", ">", "|", "*"};

        for (String invalidCharacter : invalidCharacters) {
            libraryPage.setFolderName(TestUtil.getRandomString(5) + invalidCharacter)
                    .checkErrMsgIsDisplayed(libraryPage.folderNameError_Msg());
            libraryPage.clearField(libraryPage.folderName_field());
        }
        libraryPage.clickOn_newFolderCancel_BTN()
                .logout();
    }

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
        log.info("----Begin to test " + parentMethodName + "----");
        ExtentReport.createTestCase(parentMethodName, testCaseHeader.description);
        ExtentReport.assignCategory(String.valueOf(TestCaseCategory.LIBRARY_PAGE));

        landingPage = getLandingPage();

        loginPage = getLoginPage();
        goToLoginPage();
    }

    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded()
                .clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

}
