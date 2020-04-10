package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestUtil;

import static org.testng.Assert.assertTrue;

public class LibraryPage extends TestBase {
    private TestUtil testUtil;
    //-------PATHS-------

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

    //---Others
    private final String termsAndConditions_XPATH = "//h1[contains(text(),'Terms and Conditions')]";
    private final String termsAndCondCheckbox_XPATH = "//button[@class='checkbox']";

    //--------------


    //-------Locators-------
    //---Buttons
    @FindBy(xpath = newFolderBTN_XPATH)
    @CacheLookup
    private WebElement newFolder_btn;
    @FindBy(xpath = uploadFilesBTN_XPATH)
    @CacheLookup
    private WebElement uploadFiles_btn;
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
    //--------------

    //-----------GETTERS
    public WebElement getTermsAndConditions() {
        return termsAndConditions;
    }

    public WebElement getUploadFiles_btn() {
        return uploadFiles_btn;
    }

    public WebElement myFiles_LINK() {
        return myFiles_LINK;
    }

    public WebElement getSharedWithMe_BTN() {
        return sharedWithMe_BTN;
    }

    public WebElement newFolder_BTN() {
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

    public void acceptTermsConditions() {
        termsAndCondCheckbox().click();
        waitForElementToBeClickable(termsAndCondAccept_BTN);
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
        testUtil.waitForElementToLoad(newFolder_btn);
        assertTrue(newFolder_btn.isDisplayed());
        addTestCaseStep("Clicked on SKIP button from the Welcome screen, Library page is displayed");
    }
    //--------------
}
