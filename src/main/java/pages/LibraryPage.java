package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.ExtentReport;
import utils.TestUtil;

import static org.testng.Assert.assertTrue;
import static utils.DriverFactory.getDriver;

public class LibraryPage {
    private final WebDriver driver;
    //-------PATHS-------
    //---Input fields
    private final String folderNameInput_XPATH = "//input[@placeholder='Name']";

    //---Buttons
    private final String newFolderBTN_XPATH = "//button[@class='flir-button secondary has-icon create-folder']";
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
//    @FindBy(xpath = folderNameInput_XPATH)
//    private WebElement folderName_field;

    //---Buttons
//    @FindBy(xpath = newFolderBTN_XPATH)
//    @CacheLookup
//    private WebElement newFolder_BTN;
//    @FindBy(xpath = uploadFilesBTN_XPATH)
//    @CacheLookup
//    private WebElement uploadFiles_BTN;
//    @FindBy(xpath = myFiles_XPATH)
//    @CacheLookup
//    private WebElement myFiles_LINK;
//    @FindBy(xpath = sharedWithMe_XPATH)
//    private WebElement sharedWithMe_BTN;
//    @FindBy(xpath = libraryBTN_XPATH)
//    private WebElement library_BTN;
//    @FindBy(xpath = termsAndCondAccept_XPATH)
//    private WebElement termsAndCondAccept_BTN;
//    @FindBy(xpath = termsAndCondDecline_XPATH)
//    private WebElement termsAndCondDecline_BTN;
//    @FindBy(xpath = welcomeScreenNext_XPATH)
//    private WebElement welcomeScreenNext_BTN;
//    @FindBy(xpath = welcomeScreenSkip_XPATH)
//    private WebElement welcomeScreenSkip_BTN;
//    @FindBy(xpath = newFolderCancelBTN_XPATH)
//    private WebElement newFolderCancel_BTN;
//    @FindBy(xpath = newFolderCreateBTN_XPATH)
//    private WebElement newFolderCreate_BTN;
    //--Errors
//    @FindBy(xpath = folderNameErrorMsg_XPATH)
//    private WebElement folderNameError_Msg;

    //---Others
//    @FindBy(xpath = termsAndConditions_XPATH)
//    private WebElement termsAndConditions;
//    @FindBy(xpath = termsAndCondCheckbox_XPATH)
//    private WebElement termsAndCond_checkbox;
//    @FindBy(className = userMenu_ClassName)
//    private WebElement userMenu;
//    @FindBy(xpath = signoutBTN_XPATH)
//    private WebElement signOut_BTN;
    //--------------

    //Constructor
    private LibraryPage() {
        driver = getDriver();
//        PageFactory.initElements(getDriver(), this);
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

    public WebElement signOut_BTN() {
        return driver.findElement(By.xpath(signoutBTN_XPATH));
    }

    //--------------

    //-----------SETTERS
    public LibraryPage setFolderName(String folderName) {
        TestUtil.waitForElementToLoad(folderName_field());
        folderName_field().sendKeys(folderName);
        ExtentReport.addTestCaseStep("Entered the following name: " + folderName);
        return this;
    }
    //--------------

    //Actions
    public LibraryPage acceptTermsConditions() {
        TestUtil.waitForElementToLoad(termsAndCondCheckbox());
        termsAndCondCheckbox().click();
        assertTrue(termsAndCondAccept_BTN().isEnabled());
        ExtentReport.addTestCaseStep("Ticked the T&C checkbox and the Accept button is now enabled");

        TestUtil.waitForElementToLoad(termsAndCondAccept_BTN());
        termsAndCondAccept_BTN().click();
        ExtentReport.addTestCaseStep("Clicked on the Accept button");
        return this;
    }

    public LibraryPage skipWelcomeScreen() {
        TestUtil.waitForElementToLoad(welcomeScreenSkip_BTN());
        welcomeScreenSkip_BTN().click();
        ExtentReport.addTestCaseStep("Clicked on SKIP button from the Welcome screen, Library page is displayed");
        verifyIfPageLoaded();
        return this;
    }

    public LibraryPage clickOn_createNewFolder_BTN() {
        TestUtil.waitForElementToBeClickable(newFolder_BTN());
        newFolder_BTN().click();
        TestUtil.waitForElementToLoad(folderName_field());
        ExtentReport.addTestCaseStep("Clicked on the New Folder button");
        return this;
    }

    public void createNewFolder(String folderName) {
        clickOn_createNewFolder_BTN();
        folderName_field().sendKeys(folderName);
        Assert.assertTrue(newFolderCreate_BTN().isEnabled());
        ExtentReport.addTestCaseStep("Entered the name: " + folderName + ". Create button is enabled");

        clickOn_newFolderCreate_BTN();

        String temp_XPATH = "//a[contains(text(),'" + folderName + "')]";
        TestUtil.waitForElementToLoad(driver.findElement(By.xpath(temp_XPATH)));
        ExtentReport.addTestCaseStep("Folder created successfully");
    }

    public void uploadFile(String fileLocation) {
        String fileName = fileLocation.substring(fileLocation.lastIndexOf("\\") + 1);
        String temp_XPATH = "//a[contains(text(),'" + fileName + "')]";
//        WebElement upload = driver.findElement(By.xpath("//input[@type='file']//following::input[1]"));

        uploadFiles_BTN().sendKeys(fileLocation);
        TestUtil.waitForElementToLoad(driver.findElement(By.xpath(temp_XPATH)));
        ExtentReport.addTestCaseStep("File " + fileName + " uploaded successfully");
    }

    public LibraryPage clickOn_userMenu() {
        TestUtil.waitForElementToLoad(userMenu());
        TestUtil.waitForElementToBeClickable(userMenu());
        userMenu().click();
        ExtentReport.addTestCaseStep("Clicked on User Menu");
        return this;
    }

    public void clickOn_logout_BTN() {
        TestUtil.waitForElementToLoad(signOut_BTN());
        TestUtil.waitForElementToBeClickable(signOut_BTN());
        signOut_BTN().click();
        ExtentReport.addTestCaseStep("Clicked on Logout button");
    }

    public void clickOn_newFolderCreate_BTN(){
        TestUtil.waitForElementToLoad(newFolderCreate_BTN());
        newFolderCreate_BTN().click();
        ExtentReport.addTestCaseStep("Clicked on Create button");
    }

    public LibraryPage clickOn_newFolderCancel_BTN(){
        TestUtil.waitForElementToLoad(newFolderCancel_BTN());
        newFolderCancel_BTN().click();
        ExtentReport.addTestCaseStep("Clicked on Cancel button");
        return this;
    }

    public LibraryPage verifyIfPageLoaded() {
        TestUtil.waitForElementToLoad(uploadFiles_BTN());
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

    public LibraryPage clearField(WebElement webElement){
        TestUtil.waitForElementToLoad(webElement);
        webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        webElement.sendKeys(Keys.DELETE);
        ExtentReport.addTestCaseStep("Cleared the field");
        return this;
    }

    public void checkErrMsgIsDisplayed(WebElement error_Msg) {
        TestUtil.waitForElementToLoad(error_Msg);
        ExtentReport.addTestCaseStep("Error message is displayed: " + error_Msg.getText());
    }
    //--------------
}
