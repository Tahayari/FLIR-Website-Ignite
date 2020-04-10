package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends TestBase {

    //-------PATHS-------
    private final String loginBTN_XPATH = "//span[contains(text(),'LOG IN')]";
    private final String signUpBTN_XPATH = "//span[contains(text(),'SIGN UP')]";
    //--------------


    //-------Locators-------
    @FindBy(xpath = loginBTN_XPATH)
    @CacheLookup
    private WebElement login_BTN;
    @FindBy(xpath = signUpBTN_XPATH)
    @CacheLookup
    private WebElement signup_BTN;
    //--------------

    //Constructor
    public LandingPage() {
        PageFactory.initElements(driver, this); // "this" points to the current class object(s)
    }


    //-----------GETTERS
    public WebElement login_BTN() {
        return login_BTN;
    }

    public WebElement signup_BTN() {
        return signup_BTN;
    }
    //-----------


    //-----------SETTERS
    //-----------


    //-----------Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public LoginPage clickOn_loginBTN() {
        login_BTN.click();
        return new LoginPage();
    }

    public SignUpPage clickOn_signUpBTN() {
        signup_BTN.click();
        return new SignUpPage();
    }
    //-----------
}
