package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends TestBase {

    //-------PATHS-------
    //---Input fields
    private final String emailField_ID = "logonIdentifier";
    private final String passField_ID = "password";

    //---Buttons
    private final String signInBTN_ID = "next";

    //---Links
    private final String signUpLink_ID = "createAccount";
    private final String forgotPasswordLink_ID = "forgotPassword";

    //---Errors
    private final String invalidEmailError_XPATH = "//p[contains(text(),'Please enter a valid email address')]";
    private final String invalidPassError_XPATH = "//p[contains(text(),'Please enter your password')]";
    private final String incorrectPassError_XPATH = "//p[contains(text(),'Your password is incorrect.')]";
    private final String nonExistingAccountError_XPATH = "//p[contains(text(),'seem to find your account')]";
    //--------------

    //-------Locators-------
    //---Input fields
    @FindBy(id = emailField_ID)
    @CacheLookup
    private WebElement email_field;
    @FindBy(id = passField_ID)
    @CacheLookup
    private WebElement pass_field;

    //---Buttons
    @FindBy(id = signInBTN_ID)
    @CacheLookup
    private WebElement signIn_BTN;

    //---Links
    @FindBy(id = signUpLink_ID)
    @CacheLookup
    private WebElement signUp_link;
    @FindBy(id = forgotPasswordLink_ID)
    @CacheLookup
    private WebElement forgotPass_link;

    //---Errors
    @FindBy(xpath = invalidEmailError_XPATH)
    private WebElement invalidEmailError_Msg;
    @FindBy(xpath = invalidPassError_XPATH)
    private WebElement invalidPassError_Msg;
    @FindBy(xpath = incorrectPassError_XPATH)
    private WebElement incorrectPassError_Msg;
    @FindBy(xpath = nonExistingAccountError_XPATH)
    private WebElement nonExistingAccount_Msg;
    //--------------


    //Constructor
    public LoginPage() {
        PageFactory.initElements(driver, this);
    }
    //--------------


    //-----------GETTERS
    public WebElement email_field() {
        return email_field;
    }

    public WebElement pass_field() {
        return pass_field;
    }

    public WebElement signIn_BTN() {
        return signIn_BTN;
    }

    public WebElement invalidEmailError_Msg() {
        return invalidEmailError_Msg;
    }

    public WebElement invalidPassError_Msg() {
        return invalidPassError_Msg;
    }

    public WebElement incorrectPassError_Msg() {
        return incorrectPassError_Msg;
    }

    public WebElement nonExistingAccount_Msg() {
        return nonExistingAccount_Msg;
    }
    //-----------


    //-----------SETTERS
    public void setInvalidEmail(String invalidEmail) {
        email_field.clear();
        email_field.sendKeys(invalidEmail);
        signIn_BTN.click();
    }
    //-----------

    //-----------Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public SignUpPage clickOn_signUpLink() {
        signUp_link.click();
        return new SignUpPage();
    }

    public RecoverPasswordPage clickOn_forgotPasswordLink() {
        forgotPass_link.click();
        return new RecoverPasswordPage();
    }

    public LibraryPage login(String email, String pass) {
        email_field.click();
        email_field.sendKeys(email);
        pass_field.click();
        pass_field.sendKeys(pass);
        signIn_BTN.click();
        return new LibraryPage();
    }

    //-----------
}
