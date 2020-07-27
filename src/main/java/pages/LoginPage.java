package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.TestUtil;

import static pages.LibraryPage.getLibraryPage;
import static utils.DriverFactory.getDriver;

public class LoginPage extends FlirWebPage{
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
        setField(email_field(),email,"Entered the following email: " + email);
        return this;
    }

    public LoginPage setPass(String pass) {
        TestUtil.waitForElementToLoad(pass_field());
        setField(pass_field(),pass,"Entered the following password: " + pass);
        return this;
    }
    //-----------

    //-----------Actions
    public void clickOn_signUpLink() {
        clickAction(signUp_link(),"Clicked on SignUp link");
    }

    public void clickOn_forgotPasswordLink() {
        clickAction(forgotPass_link(),"Clicked on Forgot Password link");
    }

    public void clickOn_signInBTN() {
        clickAction(signIn_BTN(),"Clicked on SignIn Button");
    }

    public LoginPage clearField(WebElement webElement) {
        webElement.clear();
        return this;
    }

    public LoginPage verifyIfPageLoaded(){
        checkIfElementHasLoaded(signIn_BTN(),"Navigated to the Login page");
        return this;
    }

//    public void checkErrMsgIsDisplayed(WebElement error_Msg) {
//        TestUtil.waitForElementToLoad(error_Msg);
//        ExtentReport.addTestCaseStep("Error message is displayed: " + error_Msg.getText());
//    }

    public LibraryPage login(String email,String pass){
        setEmail(email)
                .setPass(pass)
                .clickOn_signInBTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded();
        return libraryPage;
    }

}
