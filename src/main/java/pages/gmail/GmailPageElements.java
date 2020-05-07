package pages.gmail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static utils.DriverFactory.getDriver;

public class GmailPageElements {
    private WebDriver driver = getDriver();
    //-------PATHS-------
    private final String emailField_ID = "identifierId";
    private final String passwordField_XPATH = "//input[@name='password']";
    private final String nextButtonEmail_ID = "identifierNext";
    private final String nextButtonPass_ID = "passwordNext";
    private final String avatar_XPATH = "//span[@class='gb_Ia gbii']";
    //--------------


    //-------Locators-------
    @FindBy(id = emailField_ID)
    private WebElement email_field;
    @FindBy(xpath = passwordField_XPATH)
    private WebElement password_field;
    @FindBy(id = nextButtonEmail_ID)
    private WebElement nextEmail_BTN;
    @FindBy(id = nextButtonPass_ID)
    private WebElement nextPass_BTN;
    @FindBy(xpath = avatar_XPATH)
    private WebElement avatar_icon;
    //--------------

    //Constructor
    public GmailPageElements() {
        PageFactory.initElements(driver, this);
    }

    //--------------

    //Getters
    public WebElement avatar_icon() {
        return avatar_icon;
    }

    public WebElement email_field() {
        return email_field;
    }

    public WebElement password_field() {
        return password_field;
    }

    public WebElement nextEmail_BTN() {
        return nextEmail_BTN;
    }

    public WebElement nextPass_BTN() {
        return nextPass_BTN;
    }
}
