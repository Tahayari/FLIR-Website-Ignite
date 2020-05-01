package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.TestUtil;

import static org.testng.Assert.assertTrue;
import static utils.DriverFactory.getDriver;

public class LibraryPage extends TestBase {
    private TestUtil testUtil;
    private WebDriver driver = getDriver();

    //-------PATHS-------
    //---Input fields
    private final String folderNameInput_XPATH = "//input[@placeholder='Name']";

    //---Buttons
    private final String newFolderBTN_XPATH = "//button[@title='New Folder']";
    private final String uploadFilesBTN_XPATH = "//button[@title='Upload files']";
    private final String myFiles_XPATH = "//h5[contains(text(),'My Files')]";
    private final String sharedWithMe_XPATH = "//h5[contains(text(),'Shared with me')]";
    private final String userArea_XPATH = "//span[contains(text(),'Library')]";
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
    @FindBy(xpath = userArea_XPATH)
    private WebElement userArea;
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
    //--------------

    //Constructor
    public LibraryPage() {
        PageFactory.initElements(driver, this);
        testUtil = new TestUtil();
    }

//    public static LibraryPage(){
//        return new LibraryPage();
//    }
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

    public WebElement userArea() {
        return userArea;
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
    //--------------

    //-----------SETTERS
    //--------------

    //Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public void acceptTermsConditions() {
        termsAndCondCheckbox().click();
        testUtil.waitForElementToBeClickable(termsAndCondAccept_BTN);
        assertTrue(termsAndCondAccept_BTN().isEnabled());
        addTestCaseStep("Ticked the T&C checkbox and the Accept button is now enabled");

        termsAndCondAccept_BTN.click();
        testUtil.waitForElementToLoad(welcomeScreenNext_BTN);
        assertTrue(welcomeScreenNext_BTN.isDisplayed());
        addTestCaseStep("Clicked on the Accept button");
    }

    public void skipWelcomeScreen() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        welcomeScreenSkip_BTN.click();
        testUtil.waitForElementToLoad(newFolder_BTN);
        assertTrue(newFolder_BTN.isDisplayed());
        addTestCaseStep("Clicked on SKIP button from the Welcome screen, Library page is displayed");
    }

    public void clickOn_createNewFolder_BTN() {
        newFolder_BTN.click();
        testUtil.waitForElementToLoad(folderName_field);
        addTestCaseStep("Clicked on the New Folder button");
    }

    public void createNewFolder(String folderName) {
        clickOn_createNewFolder_BTN();

        folderName_field.sendKeys(folderName);
        Assert.assertTrue(newFolderCreate_BTN.isEnabled());
        addTestCaseStep("Entered the name: " + folderName + ". Create button is enabled");

        newFolderCreate_BTN.click();
        String temp_XPATH = "//a[contains(text(),'" + folderName + "')]";
        testUtil.waitForElementToLoad(driver.findElement(By.xpath(temp_XPATH)));
        addTestCaseStep("Folder created successfully");
    }

    public void uploadFile(String fileLocation) {
        String fileName = fileLocation.substring(fileLocation.lastIndexOf("\\") + 1);
        String temp_XPATH = "//a[contains(text(),'" + fileName + "')]";
        WebElement upload = driver.findElement(By.xpath("//input[@type='file']//following::input[1]"));

        upload.sendKeys(fileLocation);
        testUtil.waitForElementToLoad(driver.findElement(By.xpath(temp_XPATH)));
        addTestCaseStep("File " + fileName + " uploaded successfully");
    }
    //--------------
}
