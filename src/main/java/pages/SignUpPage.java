package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage extends TestBase {

    @FindBy(xpath = "//input[@id='email']")
    private WebElement email_field;
    @FindBy(xpath = "//input[@id='email_ver_input']")
    private WebElement verificationCode_field;
    @FindBy(xpath = "//button[@id='email_ver_but_send']")
    private WebElement sendVerCode_BTN;
    @FindBy(xpath="//button[@id='email_ver_but_verify']")
    private WebElement verifyCode_BTN;
    @FindBy (xpath = "//button[@id='email_ver_but_edit']")
    private WebElement changeEmail_BTN;
    @FindBy(xpath = "//button[@id='email_ver_but_resend']")
    WebElement sendNewCode_BTN;
    @FindBy(xpath = "//input[@id='newPassword']")
    WebElement newPassword_field;
    @FindBy(xpath = "//input[@id='reenterPassword']")
    WebElement confNewPassword_field;
    @FindBy(xpath = "//input[@id='givenName']")
    WebElement firstName_field;
    @FindBy(xpath = "//input[@id='surname']")
    WebElement lastName_field;
    @FindBy(xpath = "//select[@id='country']")
    WebElement country_dropdown;
    @FindBy(xpath = "//input[@id='extension_Consent_1']")
    WebElement consentYes;
    @FindBy(xpath = "//input[@id='extension_Consent_2']")
    WebElement consentNo;
    @FindBy(xpath = "//button[@id='continue']")
    @CacheLookup
    WebElement create_BTN;
    @FindBy(xpath = "//button[@id='cancel']")
    WebElement cancel_BTN;

    //locator By.ID
    private String blabbla = "email_ver_input";
    private String emailtext = "PlugMeIN";

    //Constructor
    SignUpPage() {
        PageFactory.initElements(driver, this);
    }

    //Actions
    public void setEmail_field2(){
        WebElement emailfield = (new WebDriverWait(driver, 10)).until(ExpectedConditions
                .visibilityOfElementLocated(By.id(blabbla)));
        emailfield.sendKeys(emailtext);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    //-----------START of SETTERS
    public void setEmailAddress(String text) {
        email_field.sendKeys(text);
    }

    public void setVerificationCode_field(String text){
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
    //-----------END of SETTERS


    //-----------START of GETTERS
    public WebElement getEmail_field() {
        return email_field;
    }

    public WebElement getVerificationCode_field(){
        return verificationCode_field;
    }

    public WebElement getSendVerCode_BTN() {
        return sendVerCode_BTN;
    }

    public WebElement getVerifyCode_BTN(){
        return verifyCode_BTN;
    }

    public WebElement getChangeEmail_BTN(){
        return changeEmail_BTN;
    }

    public WebElement getCreate_BTN(){
        return create_BTN;
    }


    //-----------END of GETTERS


}
