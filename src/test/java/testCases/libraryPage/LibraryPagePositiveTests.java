package testCases.libraryPage;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LibraryPage;
import pages.LoginPage;
import utils.TestUtil;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseDesc;

import static pages.LoginPage.getLoginPage;

public class LibraryPagePositiveTests extends TestBase {
    LoginPage loginPage;
    LibraryPage libraryPage;

    @Test
    public void createNewFolder_Test() {
        executeSetup(TestCaseDesc.LIBRARYPAGE_CREATENEWFOLDER);
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

    private void executeSetup(TestCaseDesc testCaseDesc) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("************************Begin test " + parentMethodName +"*********************************");
        String testCaseDescription = String.valueOf(testCaseDesc.description);
        String testCaseCategory = String.valueOf(TestCaseCategory.LIBRARY_PAGE);
        createTestCase(parentMethodName,testCaseDescription,testCaseCategory);

        loginPage = getLoginPage();
        goToLoginPage();
    }

    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded()
                .clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

}
