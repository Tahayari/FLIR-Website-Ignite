package pages;

import base.WebPageBase;
import org.openqa.selenium.WebElement;
import pages.elements.ElementManager;

import static pages.LibraryPage.getLibraryPage;

public class LoginPage extends WebPageBase {

    private LoginPage() { }

    public static LoginPage getLoginPage() {
        return new LoginPage();
    }

    //-----------GETTERS
    public WebElement email_field() {
        return getWebElement(ElementManager.LOGINPAGE_EMAIL_FIELD);
    }

    public WebElement pass_field() {
        return getWebElement(ElementManager.LOGINPAGE_PASSWORD_FIELD);
    }

    public WebElement signIn_BTN() {
        return getWebElement(ElementManager.LOGINPAGE_SIGNIN_BTN);
    }

    public WebElement invalidEmailError_Msg() {
        return getWebElement(ElementManager.LOGINPAGE_INVALIDEMAIL_ERR);
    }

    public WebElement invalidPassError_Msg() {
        return getWebElement(ElementManager.LOGINPAGE_INVALIDPASS_ERR);
    }

    public WebElement incorrectPassError_Msg() {
        return getWebElement(ElementManager.LOGINPAGE_INCORRECTPASS_ERR);
    }

    public WebElement nonExistingAccount_Msg() {
        return getWebElement(ElementManager.LOGINPAGE_NONEXISTINGACC_ERR);
    }

    public WebElement blankEmailError_Msg() {
        return getWebElement(ElementManager.LOGINPAGE_BLANKEMAIL_ERR);
    }

    public WebElement signUp_link() {
        return getWebElement(ElementManager.LOGINPAGE_SIGNUP_LINK);
    }

    public WebElement forgotPass_link() {
        return getWebElement(ElementManager.LOGINPAGE_FORGOTPASS_LINK);
    }
    //-----------


    //-----------SETTERS
    public LoginPage setEmail(String email) {
        setField(email_field(), email, "Entered the following email: " + email);
        return this;
    }

    public LoginPage setPass(String pass) {
        setField(pass_field(), pass, "Entered the following password: " + pass);
        return this;
    }
    //-----------

    //-----------Actions
    public void clickOn_signUpLink() {
        clickAction(signUp_link(), "Clicked on SignUp link");
    }

    public void clickOn_forgotPasswordLink() {
        clickAction(forgotPass_link(), "Clicked on Forgot Password link");
    }

    public void clickOn_signInBTN() {
        clickAction(signIn_BTN(), "Clicked on SignIn Button");
    }

    public LoginPage clearField(WebElement webElement) {
        webElement.clear();
        return this;
    }

    public LoginPage verifyIfPageLoaded() {
        checkIfElementHasLoaded(signIn_BTN(), "Navigated to the Login page");
        return this;
    }

    public LibraryPage login(String email, String pass) {
        setEmail(email)
                .setPass(pass)
                .clickOn_signInBTN();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded();
        return libraryPage;
    }

}
