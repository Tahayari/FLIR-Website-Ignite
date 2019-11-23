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
    public WebElement login_btn;

    @FindBy(id = "createAccount")
    public WebElement signup_link;

    @FindBy(id = "forgotPassword")
    public WebElement forgotPass_link;

    //Initialize the Page Objects
    LoginPage() {
        PageFactory.initElements(driver, this);
    }

    //Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    //TODO : click on SignUp
    public SignUpPage click_SignUp() {
        signup_link.click();
        return new SignUpPage();
    }

    //TODO : Click on Recover Password

    public LibraryPage login(String email, String pass) {
        email_field.click();
        email_field.sendKeys(email);
        pass_field.click();
        pass_field.sendKeys(pass);
        login_btn.click();
        return new LibraryPage();
    }
}
