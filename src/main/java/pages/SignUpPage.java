package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage extends TestBase {

    private final String emailID = "email";
    //TODO : declare the other constants here

    //----------- locators By.ID
    @FindBy(id = emailID)
    private WebElement email_field;
    @FindBy(id = "email_ver_input")
    private WebElement verificationCode_field;
    @FindBy(id = "email_ver_but_send")
    private WebElement sendVerCode_BTN;
    @FindBy(id = "email_ver_but_verify")
    private WebElement verifyCode_BTN;
    @FindBy(id = "email_ver_but_edit")
    private WebElement changeEmail_BTN;
    @FindBy(id = "email_ver_but_resend")
    private WebElement sendNewCode_BTN;
    @FindBy(id = "newPassword")
    private WebElement newPassword_field;
    @FindBy(id = "reenterPassword")
    private WebElement confNewPassword_field;
    @FindBy(id = "givenName")
    private WebElement firstName_field;
    @FindBy(id = "surname")
    private WebElement lastName_field;
    @FindBy(id = "country")
    private WebElement country_dropdown;
    @FindBy(id = "extension_Consent_1")
    private WebElement consentYes;
    @FindBy(id = "extension_Consent_2")
    private WebElement consentNo;
    @FindBy(id = "continue")
    @CacheLookup
    private WebElement create_BTN;
    @FindBy(id = "cancel")
    private WebElement cancel_BTN;
    //-----------

    //Constructor
    SignUpPage() {
        PageFactory.initElements(driver, this);
    }

    //----------- SETTERS
    public void setEmailAddress(String text) {
        email_field.sendKeys(text);
    }

    public void setVerificationCode_field(String text) {
        verificationCode_field.sendKeys(text);
    }

    public void setNewPassword(String text) {
        newPassword_field.sendKeys(text);
    }

    public void setConfirmNewPassword(String text) {
        confNewPassword_field.sendKeys(text);
    }

    public void setFirstName(String text) {
        firstName_field.sendKeys(text);
    }

    public void setLastName(String text) {
        lastName_field.sendKeys(text);
    }

    public void setCountry(String text) {
        //TODO: Select country from dropdown. Either select a random entry from the dropdown or have it send via a parameter
    }

    public void setConsent(String text) {
        //TODO: Select Yes or No based on parameter
    }
    //-----------


    //-----------GETTERS

    public WebElement getEmail_field() {
        return email_field;
    }

    public WebElement getVerificationCode_field() {
        return verificationCode_field;
    }

    public WebElement getSendVerCode_BTN() {
        return sendVerCode_BTN;
    }

    public WebElement getVerifyCode_BTN() {
        return verifyCode_BTN;
    }

    public WebElement getChangeEmail_BTN() {
        return changeEmail_BTN;
    }

    public WebElement getCreate_BTN() {
        return create_BTN;
    }

    public WebElement getCountry_dropdown() {
        return country_dropdown;
    }

    public WebElement getConsentNo() { return consentNo; }

    public WebElement getConsentYes() {return consentYes; }

    //-----------

    //Actions

    public String getPageTitle() {
        return driver.getTitle();
    }

    public LibraryPage createButton_click(){
        create_BTN.click();
        return new LibraryPage();
    }


}
