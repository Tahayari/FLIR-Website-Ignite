package testCases.libraryPage;

import base.TestBase;
import org.testng.annotations.Test;
import pages.LibraryPage;
import pages.LoginPage;
import utils.TestUtil;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseDesc;

import static pages.LoginPage.getLoginPage;

public class LibraryPageNegativeTests extends TestBase {
    LoginPage loginPage;
    LibraryPage libraryPage;

    @Test(enabled = false)
    public void testing() {
        executeSetup(TestCaseDesc.LIBRARYPAGE_CREATENEWFOLDER);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.logout();
    }

    @Test
    public void createNewFolderWithBlankName_Test() {
        executeSetup(TestCaseDesc.LIBRARYPAGE_CREATENEWFOLDERWITHBLANKNAME);

        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getValidAccountPasswd());

        libraryPage.clickOn_newFolder_BTN()
                .setFolderName("test")
                .clearField(libraryPage.folderName_field())
                .checkErrMsgIsDisplayed(libraryPage.folderNameError_Msg());

        libraryPage.clickOn_newFolderCancel_BTN()
                .logout();
    }

    @Test
    public void createNewFolderWith256Characters_Test() {
        executeSetup(TestCaseDesc.LIBRARYPAGE_CREATENEWFOLDERWITHLONGNAME);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.clickOn_newFolder_BTN()
                .setFolderName(TestUtil.getRandomString(256))
                .checkErrMsgIsDisplayed(libraryPage.folderNameError_Msg());

        libraryPage.clickOn_newFolderCancel_BTN()
                .logout();
    }

    @Test
    public void createFolderWithIllegalCharacters_Test() {
        executeSetup(TestCaseDesc.LIBRARYPAGE_CreateFolderWithIllegalCharacters);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.clickOn_newFolder_BTN();
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
    public void cancelButtonClosesCreateNewFolderModal_Test() {
        executeSetup(TestCaseDesc.LIBRARYPAGE_CancelButtonClosesCreateNewFolderModal);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        libraryPage.clickOn_newFolder_BTN()
                .setFolderName(TestUtil.getRandomString(6))
                .clickOn_newFolderCancel_BTN()
                .logout();
    }

    @Test
    public void createFolderWithFileSuffix_Test() {
        executeSetup(TestCaseDesc.LIBRARYPAGE_CreateFolderWithFileSuffix);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        String folderName = TestUtil.getRandomString(6) + ".jpg";
        libraryPage.clickOn_newFolder_BTN()
                .setFolderName(folderName)
                .clickOn_newFolderCreate_BTN();
        libraryPage.checkIfFileWasCreated(folderName);
        libraryPage.logout();
    }

    @Test
    public void createFolderWhoseNameIsTakenByAnotherFolder_Test() {
        executeSetup(TestCaseDesc.LIBRARYPAGE_CreateFolderWhoseNameIsTakenByAnotherFolder);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        String folderName = TestUtil.getRandomString(6);

        libraryPage.createNewFolder(folderName)
                .closeToastMessage();

        libraryPage.clickOn_newFolder_BTN()
                .setFolderName(folderName)
                .clickOn_newFolderCreate_BTN();

        libraryPage.checkIfElementHasLoaded(libraryPage.folderAlreadyExistsError_Msg(),"Toast error message is displayed");
        libraryPage.deleteFolder(folderName);
        libraryPage.logout();
    }

    @Test
    public void createFolderWhoseNameIsTakenByAnotherFile_Test() {
        executeSetup(TestCaseDesc.LIBRARYPAGE_CreateFolderWhoseNameIsTakenByAnotherFile);
        libraryPage = loginPage.login(testData.getTestAccountEmail(), testData.getPassOfExistingAcc());

        String folderName = TestUtil.getRandomString(6);
        String fileLocation = System.getProperty("user.dir") + "\\src\\main\\java\\testData\\Images\\validImage.jpg";
        String fileName = fileLocation.substring(fileLocation.lastIndexOf('\\') + 1);

        libraryPage.createNewFolder(folderName)
                .closeToastMessage()
                .openFolder(folderName);

        libraryPage.uploadFile(fileLocation); // de verificat daca merge si fara Systemn.getProperty
        libraryPage.closeToastMessage();

        libraryPage.createNewFolder(fileName);

        libraryPage.checkIfElementHasLoaded(libraryPage.folderAlreadyExistsError_Msg(),"Toast error message is displayed");

        libraryPage.clickOn_All_link()
                .deleteFolder(folderName)
                .logout();
    }
    //----

    private void executeSetup(TestCaseDesc testCaseDesc) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("************************Begin test " + parentMethodName + "*********************************");
        String testCaseDescription = String.valueOf(testCaseDesc.description);
        String testCaseCategory = String.valueOf(TestCaseCategory.LIBRARY_PAGE);
        createTestCase(parentMethodName, testCaseDescription, testCaseCategory);

        loginPage = getLoginPage();
        goToLoginPage();
    }

    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded()
                .clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

}
