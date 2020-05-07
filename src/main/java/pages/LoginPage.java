package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.ExtentReport;
import utils.TestUtil;

import static utils.DriverFactory.getDriver;

public class LoginPage {
    private WebDriver driver ;

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
        driver = getDriver();
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
        TestUtil.waitForElementToLoad(invalidEmailError_Msg);
        Assert.assertTrue(invalidEmailError_Msg.isDisplayed());
        ExtentReport.addTestCaseStep("Error message is displayed: "+invalidEmailError_Msg.getText());
        return invalidEmailError_Msg;
    }

    public WebElement invalidPassError_Msg() {
        TestUtil.waitForElementToLoad(invalidPassError_Msg);
        Assert.assertTrue(invalidPassError_Msg.isDisplayed());
        ExtentReport.addTestCaseStep("Error message is displayed: "+invalidPassError_Msg.getText());
        return invalidPassError_Msg;
    }

    public WebElement incorrectPassError_Msg() {
        TestUtil.waitForElementToLoad(incorrectPassError_Msg);
        Assert.assertTrue(incorrectPassError_Msg.isDisplayed());
        ExtentReport.addTestCaseStep("Error message is displayed: "+incorrectPassError_Msg.getText());
        return incorrectPassError_Msg;
    }

    public WebElement nonExistingAccount_Msg() {
        TestUtil.waitForElementToLoad(nonExistingAccount_Msg);
        Assert.assertTrue(nonExistingAccount_Msg.isDisplayed());
        ExtentReport.addTestCaseStep("Error message is displayed: "+nonExistingAccount_Msg.getText());
        return nonExistingAccount_Msg;
    }
    //-----------


    //-----------SETTERS
    public LoginPage setEmail(String email) {
        TestUtil.waitForElementToLoad(email_field);
        email_field.clear();
        email_field.sendKeys(email);
        ExtentReport.addTestCaseStep("Entered the following email: " + email);
        return this;
    }

    public LoginPage setPass(String pass) {
        TestUtil.waitForElementToLoad(pass_field);
        pass_field.clear();
        pass_field.sendKeys(pass);
        ExtentReport.addTestCaseStep("Entered the following password: " + pass);
        return this;
    }
    //-----------

    //-----------Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickOn_signUpLink() {
        TestUtil.waitForElementToLoad(signUp_link);
        signUp_link.click();
        ExtentReport.addTestCaseStep("Clicked on SignUp link");
    }

    public void clickOn_forgotPasswordLink() {
        TestUtil.waitForElementToLoad(forgotPass_link);
        forgotPass_link.click();
        ExtentReport.addTestCaseStep("Clicked on Forgot Password link");
    }

    public void clickOn_signInBTN() {
        TestUtil.waitForElementToLoad(signIn_BTN);
        signIn_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on SignIn Button");
    }

    public LoginPage clearField(WebElement webElement) {
        webElement.clear();
        return this;
    }

    public LoginPage verifyIfPageLoaded(){
        TestUtil.waitForElementToLoad(signIn_BTN);
        ExtentReport.addTestCaseStep("Navigated to the Login page");
        return this;
    }

}
