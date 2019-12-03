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
    private final String signIn_BTN_ID = "next";
    private final String signUpLink_ID = "createAccount";
    private final String forgotPasswordLink_ID = "forgotPassword";
    //--------------

    //-------Locators-------
    @FindBy(id = emailField_ID)    @CacheLookup    public WebElement email_field;
    @FindBy(id = passField_ID)    @CacheLookup    public WebElement pass_field;
    @FindBy(id = signIn_BTN_ID)    @CacheLookup    public WebElement signIn_BTN;
    @FindBy(id = signUpLink_ID)    @CacheLookup    private WebElement signUpLink;
    @FindBy(id = forgotPasswordLink_ID)    @CacheLookup    private WebElement forgotPasswordLink;
    //--------------


    //Constructor
    public LoginPage() {
        PageFactory.initElements(driver, this);
    }
    //--------------


    //-----------GETTERS
    //-----------


    //-----------SETTERS
    //-----------


    //-----------Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public SignUpPage signUpLink_click() {
        signUpLink.click();
        return new SignUpPage();
    }

    public RecoverPasswordPage forgotPasswordLink_click() {
        forgotPasswordLink.click();
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
