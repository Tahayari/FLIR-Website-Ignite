package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ElementManager;

import static utils.DriverFactory.getDriver;

public class LibraryPage extends FlirWebPage {
    private final WebDriver driver;

    //Constructor
    private LibraryPage() {
        driver = getDriver();
    }

    public static LibraryPage getLibraryPage() {
        return new LibraryPage();
    }
    //--------------

    //-----------GETTERS
    public WebElement termsAndConditions() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_TERMSANDCOND_HEADER);
    }

    public WebElement uploadFiles_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_UPLOADFILES_BTN);
    }

    public WebElement myFiles_LINK() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_MYFILES_LINK);
    }

    public WebElement sharedWithMe_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_SHAREDWITHME_LINK);
    }

    public WebElement newFolder_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_NEWFOLDER_BTN);
    }

    public WebElement library_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_LIBRARY_LINK);
    }

    public WebElement termsAndCondCheckbox() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_TERMSANDCOND_CHECKBOX);
    }

    public WebElement termsAndCondAccept_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_TERMSANDCOND_ACCEPT_BTN);
    }

    public WebElement termsAndCondDecline_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_TERMSANDCOND_DECLINE_BTN);
    }

    public WebElement welcomeScreenNext_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_WELCOMESCREEN_NEXT_BTN);
    }

    public WebElement welcomeScreenSkip_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_WELCOMESCREEN_SKIP_BTN);
    }

    public WebElement newFolderCancel_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_NEWFOLDER_CANCEL_BTN);
    }

    public WebElement newFolderCreate_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_NEWFOLDER_CREATE_BTN);
    }

    public WebElement folderName_field() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_FOLDERNAME_INPUT);
    }

    public WebElement folderNameError_Msg() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_NEWFOLDER_FOLDERNAME_ERR);
    }

    public WebElement userMenu() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_USERMENU_BTN);
    }

    public WebElement logOut_BTN() {
        return getWebElement(driver, ElementManager.LIBRARYPAGE_USERMENU_LOGOUT_BTN);
    }

    //--------------

    //-----------SETTERS
    public LibraryPage setFolderName(String folderName) {
        setField(folderName_field(), folderName, "Entered the following name: " + folderName);
        return this;
    }
    //--------------

    //Actions
    public LibraryPage acceptTermsConditions() {
        clickAction(termsAndCondCheckbox(), "Ticked the T&C checkbox");
        assertElementIsEnabled(termsAndCondAccept_BTN(), "The Accept button is now enabled");
        clickAction(termsAndCondAccept_BTN(), "Clicked on the Accept button");
        return this;
    }

    public LibraryPage skipWelcomeScreen() {
        clickAction(welcomeScreenSkip_BTN(), "Clicked on SKIP button from the Welcome screen, Library page is displayed");
        verifyIfPageLoaded();
        return this;
    }

    public LibraryPage clickOn_createNewFolder_BTN() {
        clickAction(newFolder_BTN(), "Clicked on the New Folder button");
        return this;
    }

    public void createNewFolder(String folderName) {
        clickOn_createNewFolder_BTN();

        setField(folderName_field(), folderName, "Entered the name: " + folderName);

        assertElementIsEnabled(newFolderCreate_BTN(), "Create button is enabled");

        clickOn_newFolderCreate_BTN();

        String temp_XPATH = "//a[contains(text(),'" + folderName + "')]";
        checkIfElementHasLoaded(driver.findElement(By.xpath(temp_XPATH)), "Folder created successfully");
    }

    public void uploadFile(String fileLocation) {
        String fileName = fileLocation.substring(fileLocation.lastIndexOf("\\") + 1);
        String temp_XPATH = "//a[contains(text(),'" + fileName + "')]";

        uploadFiles_BTN().sendKeys(fileLocation);
        checkIfElementHasLoaded(driver.findElement(By.xpath(temp_XPATH)), "File " + fileName + " uploaded successfully");
    }

    public LibraryPage clickOn_userMenu() {
        clickAction(userMenu(), "Clicked on User Menu");
        return this;
    }

    public void clickOn_logout_BTN() {
        clickAction(logOut_BTN(), "Clicked on Logout button");
    }

    public void clickOn_newFolderCreate_BTN() {
        clickAction(newFolderCreate_BTN(), "Clicked on Create button");
    }

    public LibraryPage clickOn_newFolderCancel_BTN() {
        clickAction(newFolderCancel_BTN(), "Clicked on Cancel button");
        return this;
    }

    public LibraryPage verifyIfPageLoaded() {
        checkIfElementHasLoaded(newFolder_BTN(),"Navigated to the Library page");
        return this;
    }

    public void logout() {
        clickOn_userMenu();
        clickOn_logout_BTN();
        //avoid bug where the user is still logged in after logging out - THAL-2731
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LibraryPage clearField(WebElement webElement) {
        super.clearWebElement(webElement);
        return this;
    }

    public void checkErrMsgIsDisplayed(WebElement error_Msg) {
        waitForErrorMsgToLoad(error_Msg);
    }
    //--------------
}
