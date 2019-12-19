package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RecoverPasswordPage extends TestBase {
    //-------PATHS-------
    private final String emailAddressField_ID = "email";
    private final String sendVerificationCodeBTN_ID = "email_ver_but_send";
    private final String verificationCodeField_ID = "email_ver_input";
    private final String verifyCodeBTN_ID = "email_ver_but_verify";
    private final String sendNewCodeBTN_ID = "email_ver_but_resend";
    private final String changeEmailBTN_ID = "email_ver_but_edit";
    private final String continueBTN_ID = "continue";
    private final String cancelBTN_ID = "cancel";
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
    //--------------

    //Constructor
    public RecoverPasswordPage() {
        PageFactory.initElements(driver, this);
    }
    //-----------

    //-----------GETTERS
    public WebElement getEmail_field() {
        return email_field;
    }
    //--------------

    //-----------SETTERS
    //--------------

    //Actions
    public String getPageTitle() {
        return driver.getTitle();
    }
    //--------------

//TODO : Rest of the elements from this page

}
