package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LibraryPage extends TestBase {
    //-------PATHS-------
    private final String newFolderBTN_xpath = "//button[@title='New Folder']";
    private final String uploadFilesBTN_xpath = "//button[@title='Upload files']";
    private final String myFiles_XPATH = "//h5[contains(text(),'My Files')]";
    private final String termsAndConditions_xpath = "//h1[contains(text(),'Terms and Conditions')]";
    private final String sharedWithMe_xpath = "//h5[contains(text(),'Shared with me')]";
    private final String userArea_XPATH = "//span[contains(text(),'Library')]";
    private final String termsAndCondCheckbox_XPATH = "//button[@class='checkbox']";
    private final String termsAndCondAccept_XPATH = "//span[contains(text(),'Accept')]";
    private final String termsAndCondDecline_XPATH = "//span[contains(text(),'Decline')]";
    private final String welcomeScreenNext_XPATH = "//button[@class='flir-icon-button is-inverted is-text first-time-use-forward']";
private final String welcomeScreenSkip_XPATH = "//button[@class='flir-icon-button is-inverted is-text first-time-use-back']";
    //--------------


    //-------Locators-------
    @FindBy(xpath = newFolderBTN_xpath)
    @CacheLookup
    private WebElement newFolder_btn;
    @FindBy(xpath = uploadFilesBTN_xpath)
    @CacheLookup
    private WebElement uploadFiles_btn;
    @FindBy(xpath = myFiles_XPATH)
    @CacheLookup
    private WebElement myFiles_LINK;
    @FindBy(xpath = termsAndConditions_xpath)
    private WebElement termsAndConditions;
    @FindBy(xpath = sharedWithMe_xpath)
    private WebElement sharedwithMe_BTN;
    @FindBy(xpath = userArea_XPATH)
    private WebElement userArea;
    @FindBy(xpath = termsAndCondCheckbox_XPATH)
    private WebElement termsAndCond_checkbox;
    @FindBy(xpath = termsAndCondAccept_XPATH)
    private WebElement termsAndCondAccept_BTN;
    @FindBy(xpath = termsAndCondDecline_XPATH)
    private WebElement termsAndCondDecline_BTN;
    @FindBy(xpath = welcomeScreenNext_XPATH)
    private WebElement welcomeScreenNext_BTN;
    @FindBy(xpath = welcomeScreenSkip_XPATH)
    private WebElement welcomeScreenSkip_BTN;
    //--------------

    //Constructor
    public LibraryPage() {
        PageFactory.initElements(driver, this);
    }
    //--------------

    //-----------GETTERS
    public WebElement getTermsAndConditions() {
        return termsAndConditions;
    }

    public WebElement getUploadFiles_btn() {
        return uploadFiles_btn;
    }
    public WebElement myFiles_LINK(){return myFiles_LINK;}
    public WebElement getSharedwithMe_BTN() {
        return sharedwithMe_BTN;
    }
    public WebElement getNewFolder_btn() {
        return newFolder_btn;
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
    //--------------

    //-----------SETTERS
    //--------------

    //Actions
    public String getPageTitle() {
        return driver.getTitle();
    }
    //--------------
}
