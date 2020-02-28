package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends TestBase {

    //-------PATHS-------
    private final String emailField_ID = "logonIdentifier";
    private final String passField_ID = "password";
    private final String signInBTN_ID = "next";
    private final String signUpLink_ID = "createAccount";
    private final String forgotPasswordLink_ID = "forgotPassword";
    //--------------

    //-------Locators-------
    @FindBy(id = emailField_ID)
    @CacheLookup
    private WebElement email_field;
    @FindBy(id = passField_ID)
    @CacheLookup
    private WebElement pass_field;
    @FindBy(id = signInBTN_ID)
    @CacheLookup
    private WebElement signIn_BTN;
    @FindBy(id = signUpLink_ID)
    @CacheLookup
    private WebElement signUp_link;
    @FindBy(id = forgotPasswordLink_ID)
    @CacheLookup
    private WebElement forgotPass_link;
    //--------------


    //Constructor
    public LoginPage() {
        PageFactory.initElements(driver, this);
    }
    //--------------


    //-----------GETTERS
    public WebElement email_field(){
        return email_field;
    }

    public WebElement pass_field(){
        return pass_field;
    }

    public WebElement signIn_BTN(){
        return signIn_BTN;
    }

    public WebElement signUp_link(){
        return signUp_link;
    }

    public WebElement forgotPass_link(){
        return forgotPass_link;
    }
    //-----------


    //-----------SETTERS
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
