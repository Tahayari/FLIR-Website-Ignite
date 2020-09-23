package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ElementManager;
import utils.ExtentReport;

import java.util.NoSuchElementException;

import static org.testng.Assert.assertTrue;

public class RecoverPasswordPage extends FlirWebPage {
    private WebDriver driver;

    //Constructor
    private RecoverPasswordPage() {
        driver = factory.getDriver();
    }

    public static RecoverPasswordPage getRecoverPasswordPage() {
        return new RecoverPasswordPage();
    }
    //-----------

    //-----------GETTERS
    public WebElement emailAddress_field() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_EMAIL_FIELD);
    }

    public WebElement sendVerCode_BTN() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_SENDVERIFICATIONCODE_BTN);
    }

    public WebElement verificationCode_field() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_VERIFICATIONCODE_FIELD);
    }

    public WebElement verifyCode_BTN() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_VERIFYCODE_BTN);
    }

    public WebElement sendNewCode_BTN() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_SENDNEWCODE_BTN);
    }

    public WebElement changeEmail_BTN() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_CHANGEEMAIL_BTN);
    }

    public WebElement cancel_BTN() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_CANCEL_BTN);
    }

    public WebElement continue_BTN() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_CONTINUE_BTN);
    }

    public WebElement incorrectVerCode_Msg() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_INCORRECTVERCODE_ERR);
    }

    public WebElement invalidEmail_Msg() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_INVALIDEMAIL_ERR);
    }

    public WebElement expiredVerCode_Msg() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_EXPIREDVERCODE_ERR);
    }

    public WebElement tooManyAttempts_Msg() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_TOOMANYINCORRECTATTEMPTS_ERR);
    }

    public WebElement newPassword_field() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_NEWPASSWORD_FIELD);
    }

    public WebElement confNewPassword_field() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_CONFIRMPASSWORD_FIELD);
    }

    public WebElement requiredFieldMissing_Msg() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_REQUIREDFIELDMISSING_ERR);
    }

    public WebElement passMismatch_Msg() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_PASSWORDMISSMATCH_ERR);
    }

    public WebElement invalidPass_Msg() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_INVALIDPASS_ERR);
    }

    public WebElement invalidConfirmPass_Msg() {
        return getWebElement(driver, ElementManager.RECOVERPASSPAGE_INVALIDCONFIRMPASS_ERR);
    }

    //--------------

    //-----------SETTERS
    public RecoverPasswordPage setVerificationCode_field(String code) {
        setField(verificationCode_field(), code, "Entered the following Verification Code: " + code);
        return this;
    }

    public RecoverPasswordPage setEmail(String email) {
        setField(emailAddress_field(), email, "Entered the following email: " + email);
        return this;
    }

    public RecoverPasswordPage setNewPassword(String password) {
        setField(newPassword_field(), password, "Entered the following password: " + password);
        return this;
    }

    public RecoverPasswordPage setConfirmNewPassword(String confirmNewPassword) {
        setField(confNewPassword_field(), confirmNewPassword,
                "Entered the following password in the Confirm password field: " + confirmNewPassword);
        return this;
    }
    //--------------

    //Actions
    public RecoverPasswordPage clearField(WebElement webElement) {
        webElement.clear();
        return this;
    }

    public RecoverPasswordPage sendTokenToEmail(String email) {
        setEmail(email)
                .clickOn_sendVerificationCode_BTN();
        //TODO maybe verify here if the email has been used too many times...
        return this;
    }

    public void clickOn_sendVerificationCode_BTN() {
        clickAction(sendVerCode_BTN(),"Clicked on Send Verification Code button");
    }

    public void clickOn_changeEmail_BTN() {
        clickAction(changeEmail_BTN(),"Clicked on the Change e-mail button");

        assertTrue(sendVerCode_BTN().isDisplayed());
        assertTrue(sendVerCode_BTN().getAttribute("value").isEmpty());
        ExtentReport.addTestCaseStep("Email field is now empty");
    }

    public void clickOn_cancel_BTN() {
        clickAction(cancel_BTN(),"Clicked on the Cancel button");
    }

    public void clickOn_sendNewCode_BTN() {
        clickAction(sendNewCode_BTN(),"Clicked on the Send new code button");
    }

    public RecoverPasswordPage clickOn_verifyCode_BTN() {
        clickAction(verifyCode_BTN(),"Clicked on Verify Code button");
        return this;
    }

    public RecoverPasswordPage clickOn_continue_BTN() {
        clickAction(continue_BTN(),"Clicked on the Continue button");
        return this;
    }

    public void verifyIfPageLoaded() {
        checkIfElementHasLoaded(emailAddress_field(),"Navigated to the Recover Password page");
    }

    public void verifyIfChangePassScreenLoaded() {
        checkIfElementHasLoaded(newPassword_field(),"Navigated to the Set new Password page");
    }

    public void tryIncorrectPasswords(WebElement passField) {
        String[] invalidPass = {"passwordd", "Passwordd", "passwordd!!", "ThisIsAReallyReallyLongPassword"};

        for (String pass : invalidPass) {
            clearWebElement(passField);
            setField(passField,pass,"Entered the following password: " + pass);

            if (passField.hashCode() == newPassword_field().hashCode()) {
                checkIfElementHasLoaded(invalidPass_Msg(),"Error message is displayed: " + invalidPass_Msg().getText());
            } else if (passField.hashCode() == confNewPassword_field().hashCode()) {
                checkIfElementHasLoaded(invalidConfirmPass_Msg(),"Error message is displayed: " + invalidConfirmPass_Msg().getText());
            } else throw new NoSuchElementException();
        }
    }

}