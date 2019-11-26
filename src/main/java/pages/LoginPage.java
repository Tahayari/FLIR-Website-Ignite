package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends TestBase {

    @FindBy(id = "logonIdentifier")
    @CacheLookup
    public WebElement email_field;

    @FindBy(id = "password")
    @CacheLookup
    public WebElement pass_field;

    @FindBy(xpath = "//button[@id='next']")
    @CacheLookup
    public WebElement signIn_BTN;

    @FindBy(id = "createAccount")
    @CacheLookup
    private WebElement signUpLink;

    @FindBy(id = "forgotPassword")
    @CacheLookup
    private WebElement forgotPasswordLink;


    //Initialize the Page Objects
    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    //Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public SignUpPage signUpLink_click() {
        signUpLink.click();
        return new SignUpPage();
    }

    public RecoverPasswordPage forgotPasswordLink_click(){
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
}
