package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static utils.TestUtil.WAIT_FOR_ELEMENT_TIMEOUT;

public class GmailPage extends TestBase {
    //-------PATHS-------
    private final String email_ID = "identifierId";
    private final String nextButtonEmail_ID = "identifierNext";
    private final String nextButtonPass_ID = "passwordNext";

    private final String passwordField_XPATH = "//input[@name='password']";
    private final String email_XPATH = "//span[@class='bog']";
    private final String emailBody_XPATH = "//span[contains(@id,'UserVerificationEmailBodySentence2')]";
    private final String deleteEmailBTN_XPATH = "//div[@class='iH bzn']//div[@class='T-I J-J5-Ji nX T-I-ax7 T-I-Js-Gs mA']//div[@class='asa']";
    private final String avatar_XPATH = "//span[@class='gb_Ia gbii']";
    //--------------

    //-------Locators-------
    @FindBy(id = email_ID)
    @CacheLookup
    private WebElement email_field;
    @FindBy(id = nextButtonEmail_ID)
    @CacheLookup
    private WebElement nextButtonEmail_BTN;
    @FindBy(id = nextButtonPass_ID)
    @CacheLookup
    private WebElement nextButtonPass_BTN;
    @FindBy(xpath = passwordField_XPATH)
    @CacheLookup
    private WebElement password_field;
    @FindBy(xpath = email_XPATH)
    @CacheLookup
    private WebElement receivedEmails;
    @FindBy(xpath = email_XPATH)
    @CacheLookup
    private List<WebElement> listOfReceivedEmails;
    @FindBy(xpath = emailBody_XPATH)
    @CacheLookup
    private WebElement emailBody;
    @FindBy(xpath = deleteEmailBTN_XPATH)
    @CacheLookup
    private WebElement deleteEmail_BTN;
    @FindBy(xpath = avatar_XPATH)
    @CacheLookup
    private WebElement avatar;


    //--------------
    //Constructor
    public GmailPage() {
        PageFactory.initElements(driver, this);
    }
    //-----------

    //-----------GETTERS
    public WebElement getEmail_field() {
        return email_field;
    }

    public WebElement getNextButtonEmail_BTN() {
        return nextButtonEmail_BTN;
    }

    public WebElement getNextButtonPass_BTN() {
        return nextButtonPass_BTN;
    }

    public WebElement getPassword_field() {
        return password_field;
    }

    public WebElement getReceivedEmails() {
        return receivedEmails;
    }

    public WebElement getEmailBody() {
        return emailBody;
    }

    public WebElement getDeleteEmail_BTN() {
        return deleteEmail_BTN;
    }

    public WebElement getAvatar() {
        return avatar;
    }
    //-----------

    //-----------SETTERS

    //-----------

    //Actions
    public int numberOfExistingMails() {
        return listOfReceivedEmails.size();
    }

//    public void clickOn_firstEmail() {
//        listOfReceivedEmails.get(0).click();
//        waitForElementToLoad(emailBody);
//    }

//    public void clickOn_deleteEmailBTN() {
//        deleteEmail_BTN.click();
//        waitForElementToLoad(avatar);
//    }

    public void waitForEmailToArrive() {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT);
        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(email_XPATH), 0));
        } catch (Exception e) {
            System.out.println("----Timeout error. Email did not arrive. Refreshing the page and retrying...");
            try {
                driver.navigate().refresh();
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(email_XPATH), 0));
            } catch (TimeoutException te) {
                System.out.println("----Timeout error. Email did not arrive in the allotted time");
                te.printStackTrace();
                throw new TimeoutException("----Timeout error. Email did not arrive in the allotted time");
            }
        }
    }

//    public String getTokenFromEmailBody(){
//        waitForElementToLoad(getEmailBody());
//        String emailBodyText = getEmailBody().getText();
//        return emailBodyText.substring(14);
//    }

}
