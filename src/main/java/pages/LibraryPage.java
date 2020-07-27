package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.TestUtil;

import static utils.DriverFactory.getDriver;

public class LibraryPage extends FlirWebPage {
    private final WebDriver driver;
    //-------PATHS-------
    //---Input fields
    private final String folderNameInput_XPATH = "//input[@placeholder='Name']";

    //---Buttons
    private final String newFolderBTN_XPATH = "//button[@title='New folder']";
    private final String uploadFilesBTN_XPATH = "//button[@title='Upload files']//span[2]//div//input";
    private final String myFiles_XPATH = "//h5[contains(text(),'My Files')]";
    private final String sharedWithMe_XPATH = "//h5[contains(text(),'Shared with me')]";
    private final String libraryBTN_XPATH = "//span[contains(text(),'Library')]";
    private final String termsAndCondAccept_XPATH = "//span[contains(text(),'Accept')]";
    private final String termsAndCondDecline_XPATH = "//span[contains(text(),'Decline')]";
    private final String welcomeScreenNext_XPATH = "//button[@class='flir-icon-button is-inverted is-text first-time-use-forward']";
    private final String welcomeScreenSkip_XPATH = "//button[@class='flir-icon-button is-inverted is-text first-time-use-back']";
    private final String newFolderCancelBTN_XPATH = "//div[@class='button-bar']//descendant::button[@type='button']";
    private final String newFolderCreateBTN_XPATH = "//div[@class='button-bar']//descendant::button[@type='submit']";

    //--Errors
    private final String folderNameErrorMsg_XPATH = "//div[@class='flir-input-error']";

    //---Others
    private final String termsAndConditions_XPATH = "//h1[contains(text(),'Terms and Conditions')]";
    private final String termsAndCondCheckbox_XPATH = "//button[@class='checkbox']";
    private final String userMenu_ClassName = "user-menu";
    private final String logOutBTN_XPATH = "//ul[@class='flir-dropdown-list upper-right']//button[@class='sign-out'][contains(text(),'Log out')]";
    //--------------

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
        return driver.findElement(By.xpath(termsAndConditions_XPATH));
    }

    public WebElement uploadFiles_BTN() {
        return driver.findElement(By.xpath(uploadFilesBTN_XPATH));
    }

    public WebElement myFiles_LINK() {
        return driver.findElement(By.xpath(myFiles_XPATH));
    }

    public WebElement sharedWithMe_BTN() {
        return driver.findElement(By.xpath(sharedWithMe_XPATH));
    }

    public WebElement newFolder_BTN() {
        return driver.findElement(By.xpath(newFolderBTN_XPATH));
    }

    public WebElement library_BTN() {
        return driver.findElement(By.xpath(libraryBTN_XPATH));
    }

    public WebElement termsAndCondCheckbox() {
        return driver.findElement(By.xpath(termsAndCondCheckbox_XPATH));
    }

    public WebElement termsAndCondAccept_BTN() {
        return driver.findElement(By.xpath(termsAndCondAccept_XPATH));
    }

    public WebElement termsAndCondDecline_BTN() {
        return driver.findElement(By.xpath(termsAndCondDecline_XPATH));
    }

    public WebElement welcomeScreenNext_BTN() {
        return driver.findElement(By.xpath(welcomeScreenNext_XPATH));
    }

    public WebElement welcomeScreenSkip_BTN() {
        return driver.findElement(By.xpath(welcomeScreenSkip_XPATH));
    }

    public WebElement newFolderCancel_BTN() {
        return driver.findElement(By.xpath(newFolderCancelBTN_XPATH));
    }

    public WebElement newFolderCreate_BTN() {
        return driver.findElement(By.xpath(newFolderCreateBTN_XPATH));
    }

    public WebElement folderName_field() {
        return driver.findElement(By.xpath(folderNameInput_XPATH));
    }

    public WebElement folderNameError_Msg() {
        return driver.findElement(By.xpath(folderNameErrorMsg_XPATH));
    }

    public WebElement userMenu() {
        return driver.findElement(By.className(userMenu_ClassName));
    }

    public WebElement logOut_BTN() {
        return driver.findElement(By.xpath(logOutBTN_XPATH));
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
        TestUtil.waitForElementToBeClickable(userMenu());
        clickAction(userMenu(), "Clicked on User Menu");
        return this;
    }

    public void clickOn_logout_BTN() {
        TestUtil.waitForElementToBeClickable(logOut_BTN());
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
