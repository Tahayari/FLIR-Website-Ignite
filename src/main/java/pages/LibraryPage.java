package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.ExtentReport;
import utils.TestUtil;

import static org.testng.Assert.assertTrue;
import static utils.DriverFactory.getDriver;

public class LibraryPage {

    //-------PATHS-------
    //---Input fields
    private final String folderNameInput_XPATH = "//input[@placeholder='Name']";

    //---Buttons
    private final String newFolderBTN_XPATH = "//button[@title='New Folder']";
    private final String uploadFilesBTN_XPATH = "//button[@title='Upload files']";
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
    private final String signoutBTN_XPATH = "//ul[@class='flir-dropdown-list upper-right']//button[@class='sign-out'][contains(text(),'Sign out')]";
    //--------------


    //-------Locators-------
    //---Input fields
    @FindBy(xpath = folderNameInput_XPATH)
    private WebElement folderName_field;


    //---Buttons
    @FindBy(xpath = newFolderBTN_XPATH)
    @CacheLookup
    private WebElement newFolder_BTN;
    @FindBy(xpath = uploadFilesBTN_XPATH)
    @CacheLookup
    private WebElement uploadFiles_BTN;
    @FindBy(xpath = myFiles_XPATH)
    @CacheLookup
    private WebElement myFiles_LINK;
    @FindBy(xpath = sharedWithMe_XPATH)
    private WebElement sharedWithMe_BTN;
    @FindBy(xpath = libraryBTN_XPATH)
    private WebElement library_BTN;
    @FindBy(xpath = termsAndCondAccept_XPATH)
    private WebElement termsAndCondAccept_BTN;
    @FindBy(xpath = termsAndCondDecline_XPATH)
    private WebElement termsAndCondDecline_BTN;
    @FindBy(xpath = welcomeScreenNext_XPATH)
    private WebElement welcomeScreenNext_BTN;
    @FindBy(xpath = welcomeScreenSkip_XPATH)
    private WebElement welcomeScreenSkip_BTN;
    @FindBy(xpath = newFolderCancelBTN_XPATH)
    private WebElement newFolderCancel_BTN;
    @FindBy(xpath = newFolderCreateBTN_XPATH)
    private WebElement newFolderCreate_BTN;
    //--Errors
    @FindBy(xpath = folderNameErrorMsg_XPATH)
    private WebElement folderNameError_Msg;

    //---Others
    @FindBy(xpath = termsAndConditions_XPATH)
    private WebElement termsAndConditions;
    @FindBy(xpath = termsAndCondCheckbox_XPATH)
    private WebElement termsAndCond_checkbox;
    @FindBy(className = userMenu_ClassName)
    private WebElement userMenu;
    @FindBy(xpath = signoutBTN_XPATH)
    private WebElement signOut_BTN;
    //--------------

    //Constructor
    private LibraryPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public static LibraryPage getLibraryPage() {
        return new LibraryPage();
    }
    //--------------

    //-----------GETTERS
    public WebElement getTermsAndConditions() {
        return termsAndConditions;
    }

    public WebElement getUploadFiles_BTN() {
        return uploadFiles_BTN;
    }

    public WebElement myFiles_LINK() {
        return myFiles_LINK;
    }

    public WebElement sharedWithMe_BTN() {
        return sharedWithMe_BTN;
    }

    public WebElement newFolder_BTN() {
        return newFolder_BTN;
    }

    public WebElement library_BTN() {
        return library_BTN;
    }

    public WebElement termsAndCondCheckbox() {
        return termsAndCond_checkbox;
    }

    public WebElement termsAndCondAccept_BTN() {
        return termsAndCondAccept_BTN;
    }

    public WebElement termsAndCondDecline_BTN() {
        return termsAndCondDecline_BTN;
    }

    public WebElement welcomeScreenNext_BTN() {
        return welcomeScreenNext_BTN;
    }

    public WebElement welcomeScreenSkip_BTN() {
        return welcomeScreenSkip_BTN;
    }

    public WebElement newFolderCancel_BTN() {
        return newFolderCancel_BTN;
    }

    public WebElement newFolderCreate_BTN() {
        return newFolderCreate_BTN;
    }

    public WebElement folderName_field() {
        return folderName_field;
    }

    public WebElement folderNameError_Msg() {
        return folderNameError_Msg;
    }

    public WebElement userMenu_BTN() {
        return userMenu;
    }

    public WebElement signOut_BTN() {
        return signOut_BTN;
    }

    //--------------

    //-----------SETTERS
    //--------------

    //Actions
    public LibraryPage acceptTermsConditions() {
        TestUtil.waitForElementToLoad(termsAndCondCheckbox());
//        TestUtil.waitForElementToBeClickable(termsAndCondAccept_BTN);
        termsAndCondCheckbox().click();
        assertTrue(termsAndCondAccept_BTN().isEnabled());
        ExtentReport.addTestCaseStep("Ticked the T&C checkbox and the Accept button is now enabled");

        TestUtil.waitForElementToLoad(termsAndCondAccept_BTN);
        termsAndCondAccept_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on the Accept button");
        return this;
    }

    public LibraryPage skipWelcomeScreen() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        TestUtil.waitForElementToLoad(welcomeScreenSkip_BTN);
        welcomeScreenSkip_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on SKIP button from the Welcome screen, Library page is displayed");

        verifyIfPageLoaded();
//        TestUtil.waitForElementToLoad(newFolder_BTN);
        return this;
    }

    public void clickOn_createNewFolder_BTN() {
        newFolder_BTN.click();
        TestUtil.waitForElementToLoad(folderName_field);
        ExtentReport.addTestCaseStep("Clicked on the New Folder button");
    }

    public void createNewFolder(String folderName) {
        clickOn_createNewFolder_BTN();

        folderName_field.sendKeys(folderName);
        Assert.assertTrue(newFolderCreate_BTN.isEnabled());
        ExtentReport.addTestCaseStep("Entered the name: " + folderName + ". Create button is enabled");

        newFolderCreate_BTN.click();
        String temp_XPATH = "//a[contains(text(),'" + folderName + "')]";
//        TestUtil.waitForElementToLoad(driver.findElement(By.xpath(temp_XPATH)));
        ExtentReport.addTestCaseStep("Folder created successfully");
    }

    public void uploadFile(String fileLocation) {
        String fileName = fileLocation.substring(fileLocation.lastIndexOf("\\") + 1);
        String temp_XPATH = "//a[contains(text(),'" + fileName + "')]";
//        WebElement upload = driver.findElement(By.xpath("//input[@type='file']//following::input[1]"));

//        upload.sendKeys(fileLocation);
//        TestUtil.waitForElementToLoad(driver.findElement(By.xpath(temp_XPATH)));
//        ExtentReport.addTestCaseStep("File " + fileName + " uploaded successfully");
    }

    public LibraryPage clickOn_userMenu() {
        TestUtil.waitForElementToLoad(userMenu);
        userMenu.click();
        ExtentReport.addTestCaseStep("Clicked on User Menu");
        return this;
    }

    public void clickOn_logout_BTN() {
        TestUtil.waitForElementToLoad(signOut_BTN);
        signOut_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on Logout button");
    }

    public LibraryPage verifyIfPageLoaded() {
        TestUtil.waitForElementToLoad(uploadFiles_BTN);
        ExtentReport.addTestCaseStep("Navigated to the Library page");
        return this;
    }

    public void logout() {
        clickOn_userMenu()
                .clickOn_logout_BTN();
        //avoid bug where the user is still logged in after logging out - THAL-2731
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //--------------
}
