package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ExtentReport;

import static utils.DriverFactory.getDriver;

public class LoginPage {
    private WebDriver driver = getDriver();

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
    private WebElement email_field;
    @FindBy(id = passField_ID)
    private WebElement pass_field;

    //---Buttons
    @FindBy(id = signInBTN_ID)
    private WebElement signIn_BTN;

    //---Links
    @FindBy(id = signUpLink_ID)
    private WebElement signUp_link;
    @FindBy(id = forgotPasswordLink_ID)
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
    private LoginPage() {
        PageFactory.initElements(driver, this);
    }

    public static LoginPage getLoginPage() {
        return new LoginPage();
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
    public LoginPage setEmail(String email) {
        email_field().clear();
        email_field().sendKeys(email);
        ExtentReport.addTestCaseStep("Entered the following email: " + email);
        return this;
    }

    public LoginPage setPass(String pass) {
        pass_field().clear();
        pass_field().sendKeys(pass);
        ExtentReport.addTestCaseStep("Entered the following password: " + pass);
        return this;
    }
    //-----------

    //-----------Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickOn_signUpLink() {
        signUp_link.click();
        ExtentReport.addTestCaseStep("Clicked on SignUp link");
    }

    public void clickOn_forgotPasswordLink() {
        forgotPass_link.click();
        ExtentReport.addTestCaseStep("Clicked on Forgot Password link");
    }

    public void clickOn_signInBTN() {
        signIn_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on SignIn Button");
    }

    public LoginPage clearField(WebElement webElement) {
        webElement.clear();
        return this;
    }

    public void logout() {
    //TODO
    }

}
