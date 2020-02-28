package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RecoverPasswordPage extends TestBase {

    //-------PATHS-------

    //---Input fields
    private final String emailAddressField_ID = "email";
    private final String verificationCodeField_ID = "email_ver_input";

    //---Buttons
    private final String sendVerificationCodeBTN_ID = "email_ver_but_send";
    private final String verifyCodeBTN_ID = "email_ver_but_verify";
    private final String sendNewCodeBTN_ID = "email_ver_but_resend";
    private final String changeEmailBTN_ID = "email_ver_but_edit";
    private final String continueBTN_ID = "continue";
    private final String cancelBTN_ID = "cancel";

    //---Errors
    private final String incorrectVerificationCode_ID = "email_fail_retry";
    private final String expiredVerificationCode_ID ="email_fail_code_expired";
    private final String invalidEmailError_XPATH = "//p[contains(text(),'Please enter a valid email address.')]";
    //--------------

    //-------Locators-------
    @FindBy(id = emailAddressField_ID)
    private WebElement email_field;
    @FindBy(id = sendVerificationCodeBTN_ID)
    private WebElement sendVerCode_BTN;
    @FindBy(id = verificationCodeField_ID)
    private WebElement verificationCode_field;
    @FindBy(id = verifyCodeBTN_ID)
    private WebElement verifyCode_BTN;
    @FindBy(id = sendNewCodeBTN_ID)
    private WebElement sendNewCode_BTN;
    @FindBy(id = changeEmailBTN_ID)
    private WebElement changeEmail_BTN;
    @FindBy(id = continueBTN_ID)
    private WebElement continue_BTN;
    @FindBy(id = cancelBTN_ID)
    private WebElement cancel_BTN;
    @FindBy(id = incorrectVerificationCode_ID)
    private WebElement incorrectVerCode;
    @FindBy(xpath = invalidEmailError_XPATH)
    private WebElement invalidEmailErrorMsg;
    @FindBy(id = expiredVerificationCode_ID)
    private WebElement expiredVerCode;
    //--------------

    //Constructor
    public RecoverPasswordPage() {
        PageFactory.initElements(driver, this);
    }
    //-----------

    //-----------GETTERS
    public WebElement email_field() {
        return email_field;
    }

    public WebElement sendVerCode_BTN() {
        return sendVerCode_BTN;
    }

    public WebElement verificationCode_field() {
        return verificationCode_field;
    }

    public WebElement verifyCode_BTN() {
        return verifyCode_BTN;
    }

    public WebElement sendNewCode_BTN() {
        return sendNewCode_BTN;
    }

    public WebElement changeEmail_BTN() {
        return changeEmail_BTN;
    }

    public WebElement continue_BTN() {
        return continue_BTN;
    }

    public WebElement cancel_BTN() {
        return cancel_BTN;
    }

    public WebElement incorrectVerCode_err() {
        return incorrectVerCode;
    }

    public WebElement invalidEmailErrorMsg() {
        return invalidEmailErrorMsg;
    }

    //--------------

    //-----------SETTERS
    public void setVerificationCode_field(String text) {
        verificationCode_field.sendKeys(text);
    }

    public void setInvalidEmail(String invalidEmail) {
        email_field.clear();
        email_field.sendKeys(invalidEmail);
        sendVerCode_BTN.click();
    }
    //--------------

    //Actions
    public String getPageTitle() {
        return driver.getTitle();
    }
//    public String getErrorMessageOf(WebElement element){
//        return element.getText();
//    }

    public void sendTokenToEmail(String email){
        email_field.click();
        email_field.sendKeys(email);
        sendVerCode_BTN.click();
        waitForElementToLoad(verifyCode_BTN);
        addTestCaseStep("Entered email: "+email+" and clicked on Send Verification code button");
    }

    public void sendInvalidToken(String invalidToken){
        verificationCode_field.click();
        verificationCode_field.sendKeys(invalidToken);
        verifyCode_BTN.click();
        addTestCaseStep("Entered the following token: "+invalidToken+" and clicked on the Verify code");
        waitForElementToLoad(incorrectVerCode);
    }

    public void waitForTokenToExpire(int minutesToWait){
        int milisecToWait = minutesToWait * 60 * 1000 ;
        try {
            Thread.sleep( milisecToWait );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addTestCaseStep("Waited "+milisecToWait/60+" seconds for the Verification code to expire");
    }

    public void enterTokenFromEmail(){
        verificationCode_field.click();
        String token = testUtil.getTokenFromGmail();
        verificationCode_field.sendKeys(token);
        verifyCode_BTN.click();
        addTestCaseStep("Entered the following token: "+token+" and clicked on the Verify code");
        waitForElementToLoad(changeEmail_BTN);
    }
    //--------------

//TODO : Rest of the elements from this page

}
