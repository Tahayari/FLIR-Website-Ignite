package testCases;

import base.TestBase;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;
import utils.TestUtil;

public class LibraryPageTest extends TestBase {
    private LandingPage landingPage;
    private LoginPage loginPage;
    private LibraryPage libraryPage;
    private TestUtil testUtil;

    public LibraryPageTest() {
        super();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            initialization();
            landingPage = new LandingPage();
            loginPage = new LoginPage();
            libraryPage = new LibraryPage();
            testUtil = new TestUtil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadLandingPage() {
        try {
            waitForElementToBeClickable(landingPage.signup_BTN());
        } catch (Exception e) {
            System.out.println("------------Page timed out. Refreshing...");
            driver.navigate().refresh();
            waitForElementToBeClickable(landingPage.signup_BTN());
        }
    }

    public void goToLoginPage() {
        loadLandingPage();
        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        addTestCaseStep("Navigated to Landing page");

        loginPage = landingPage.clickOn_loginBTN();
        testUtil.waitForElementToLoad(loginPage.signIn_BTN());
        addTestCaseStep("Navigated to Login page");
    }

    public void goToLibraryPage() {
        loadLandingPage();
        goToLoginPage();
        String email = "flirtest7@mailinator.com";
        String pass = "QAZxsw123";

        loginPage.email_field().click();
        loginPage.email_field().sendKeys(email);
        loginPage.pass_field().click();
        loginPage.pass_field().sendKeys(pass);
        addTestCaseStep("Entered email: " + email + " and password: " + pass + " then clicked on the Sign In button");

        loginPage.signIn_BTN().click();
        testUtil.waitForElementToLoad(libraryPage.newFolder_BTN());
        Assert.assertTrue(libraryPage.newFolder_BTN().isDisplayed());
        addTestCaseStep("Logged in successfully. Library page is displayed");
    }

    @Test
    public void createNewFolderWithBlankName_Test() {
        String testCaseTitle = "LIBRARY PAGE - createNewFolderWithBlankName_Test";
        String testCaseDescription = "Error message is displayed when the user creates a new folder that has a blank name";
        createTestCase(testCaseTitle, testCaseDescription);

        goToLibraryPage();

        libraryPage.clickOn_createNewFolder_BTN();

        testUtil.waitForElementToLoad(libraryPage.newFolderCancel_BTN());
        libraryPage.folderName_field().sendKeys("testFolder");
        libraryPage.folderName_field().sendKeys(Keys.chord(Keys.CONTROL, "a"));
        libraryPage.folderName_field().sendKeys(Keys.DELETE);

        testUtil.waitForElementToLoad(libraryPage.folderNameError_Msg());
        Assert.assertEquals(libraryPage.folderNameError_Msg().getText(), "* Folder name is a required field.");
        addTestCaseStep("Error message is displayed: " + libraryPage.folderNameError_Msg().getText());
    }

    @Test
    public void createNewFolderWithReallyLongName_Test() {
        String testCaseTitle = "LIBRARY PAGE - createNewFolderWithReallyLongName_Test";
        String testCaseDescription = "Error message is displayed when the user creates a new folder that has a more than 256 characters";
        String longName = "hC6H7PZCo3f1RAYfK3LnI6SwUOSijzKLVP1QZMjcRrOZkTcW5svoK4YXISKZi5qfaJY8Xnncog474CrSJ3qqRdiPN1BFmBHPw1YA3xDdN2uYIrDlmeLyokClsXHB9rk3aALfPSN8syosaikiEruYAH9uss6Kxcq7yTeumGdQDUKrMuCGx6m3X04jcxZOHTki1srbpLp6iJuc9RhmEdPhU1Jj29ObtaXyQnvCWUXvsZEcPW6HLnfPd4sxJItM72aQ";
        createTestCase(testCaseTitle, testCaseDescription);

        goToLibraryPage();

        libraryPage.clickOn_createNewFolder_BTN();

        testUtil.waitForElementToLoad(libraryPage.newFolderCancel_BTN());
        libraryPage.folderName_field().sendKeys(longName);

        testUtil.waitForElementToLoad(libraryPage.folderNameError_Msg());
        Assert.assertEquals(libraryPage.folderNameError_Msg().getText(), "* Folder name cannot be longer than 255 characters.");
        addTestCaseStep("Error message is displayed: " + libraryPage.folderNameError_Msg().getText());
    }

    @Test
    public void createNewFolder_Test() {
        String testCaseTitle = "LIBRARY PAGE - createNewFolder_Test";
        String testCaseDescription = "Successfully create a new folder";
        createTestCase(testCaseTitle, testCaseDescription);

        goToLibraryPage();

        libraryPage.createNewFolder("newFolder");
    }

    @Test
    public void uploadValidFile_Test() {
        String testCaseTitle = "LIBRARY PAGE - uploadValidFile_Test";
        String testCaseDescription = "Successfully upload a valid file";
        String fileLocation = System.getProperty("user.dir") + "\\src\\main\\java\\testData\\Images\\validImage.jpg";
        createTestCase(testCaseTitle, testCaseDescription);

        goToLibraryPage();

        libraryPage.uploadFile(fileLocation);
    }
}
