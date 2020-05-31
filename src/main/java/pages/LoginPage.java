package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ExtentReport;
import utils.TestUtil;

import static pages.LibraryPage.getLibraryPage;
import static utils.DriverFactory.getDriver;

public class LoginPage {
    private final WebDriver driver ;

    //-------LOCATORS-------
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
    private final String blankEmailError_XPATH = "//p[contains(text(),'Please enter your email')]";
    //--------------

    //Constructor
    private LoginPage() {
        driver = getDriver();
    }

    public static LoginPage getLoginPage() {
        return new LoginPage();
    }
    //--------------


    //-----------GETTERS
    public WebElement email_field() {
        return driver.findElement(By.id(emailField_ID));
    }

    public WebElement pass_field() {
        return driver.findElement(By.id(passField_ID));
    }

    public WebElement signIn_BTN() {
        return driver.findElement(By.id(signInBTN_ID));
    }

    public WebElement invalidEmailError_Msg() {
        return driver.findElement(By.xpath(invalidEmailError_XPATH));
    }

    public WebElement invalidPassError_Msg() {
        return driver.findElement(By.xpath(invalidPassError_XPATH));
    }

    public WebElement incorrectPassError_Msg() {
        return driver.findElement(By.xpath(incorrectPassError_XPATH));
    }

    public WebElement nonExistingAccount_Msg() {
        return driver.findElement(By.xpath(nonExistingAccountError_XPATH));
    }

    public WebElement blankEmailError_Msg() {return driver.findElement(By.xpath(blankEmailError_XPATH));}

    public WebElement signUp_link(){return driver.findElement(By.id(signUpLink_ID));}
    public WebElement forgotPass_link() {return driver.findElement(By.id(forgotPasswordLink_ID));}
    //-----------


    //-----------SETTERS
    public LoginPage setEmail(String email) {
        TestUtil.waitForElementToLoad(email_field());
        email_field().clear();
        email_field().sendKeys(email);
        ExtentReport.addTestCaseStep("Entered the following email: " + email);
        return this;
    }

    public LoginPage setPass(String pass) {
        TestUtil.waitForElementToLoad(pass_field());
        pass_field().clear();
        pass_field().sendKeys(pass);
        ExtentReport.addTestCaseStep("Entered the following password: " + pass);
        return this;
    }
    //-----------

    //-----------Actions
    public void clickOn_signUpLink() {
        TestUtil.waitForElementToLoad(signUp_link());
        signUp_link().click();
        ExtentReport.addTestCaseStep("Clicked on SignUp link");
    }

    public void clickOn_forgotPasswordLink() {
        TestUtil.waitForElementToLoad(forgotPass_link());
        forgotPass_link().click();
        ExtentReport.addTestCaseStep("Clicked on Forgot Password link");
    }

    public void clickOn_signInBTN() {
        TestUtil.waitForElementToLoad(signIn_BTN());
        signIn_BTN().click();
        ExtentReport.addTestCaseStep("Clicked on SignIn Button");
    }

    public LoginPage clearField(WebElement webElement) {
        webElement.clear();
        return this;
    }

    public LoginPage verifyIfPageLoaded(){
        TestUtil.waitForElementToLoad(signIn_BTN());
        ExtentReport.addTestCaseStep("Navigated to the Login page");
        return this;
    }

    public void checkErrMsgIsDisplayed(WebElement error_Msg) {
        TestUtil.waitForElementToLoad(error_Msg);
        ExtentReport.addTestCaseStep("Error message is displayed: " + error_Msg.getText());
    }

    public LibraryPage login(String email,String pass){
        setEmail(email)
                .setPass(pass)
                .clickOn_signInBTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded();
        return libraryPage;
    }

}
