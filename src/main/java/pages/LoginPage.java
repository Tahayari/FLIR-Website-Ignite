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
    private WebElement invalidEmailErrorMsg;
    @FindBy(xpath = invalidPassError_XPATH)
    private WebElement invalidPassErrorMsg;
    @FindBy(xpath = incorrectPassError_XPATH)
    private WebElement incorrectPassErrorMsg;
    @FindBy(xpath = nonExistingAccountError_XPATH)
    private WebElement nonExistingAccountMsg;
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

    public WebElement signUp_link() {
        return signUp_link;
    }

    public WebElement forgotPass_link() {
        return forgotPass_link;
    }

    public WebElement invalidEmailErrorMsg() {
        return invalidEmailErrorMsg;
    }

    public WebElement invalidPassErrorMsg() {
        return invalidPassErrorMsg;
    }

    public WebElement incorrectPassErrorMsg() {
        return incorrectPassErrorMsg;
    }

    public WebElement nonExistingAccountMsg() {
        return nonExistingAccountMsg;
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
