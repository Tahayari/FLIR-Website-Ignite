package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends TestBase {

    //-------PATHS-------
    private final String loginButton_xpath = "//span[contains(text(),'LOG IN')]";
    private final String signUpButton_xpath = "//span[contains(text(),'SIGN UP')]";
    //--------------


    //-------Locators-------
    @FindBy(xpath = loginButton_xpath)
    @CacheLookup
    private WebElement login_BTN;
    @FindBy(xpath = signUpButton_xpath)
    @CacheLookup
    private WebElement signup_BTN;
    //--------------

    //Constructor
    public LandingPage() {
        PageFactory.initElements(driver, this); // "this" points to the current class object(s)
    }


    //-----------GETTERS
    public WebElement getLogin_BTN() {
        return login_BTN;
    }

    public WebElement getSignup_BTN() {
        return signup_BTN;
    }
    //-----------


    //-----------SETTERS
    //-----------


    //-----------Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public LoginPage login_btn_click() {
        login_BTN.click();
        return new LoginPage();
    }

    public SignUpPage signUp_btn_click() {
        signup_BTN.click();
        return new SignUpPage();
    }
    //-----------
}
