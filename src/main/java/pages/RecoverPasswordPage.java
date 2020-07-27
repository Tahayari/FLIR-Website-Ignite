package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ExtentReport;
import utils.TestUtil;

import java.util.NoSuchElementException;

import static org.testng.Assert.assertTrue;
import static utils.DriverFactory.getDriver;

public class RecoverPasswordPage extends FlirWebPage {
    private final WebDriver driver;

    //-------LOCATORS-------
    //---Input fields
    private final String emailAddressField_ID = "email";
    private final String verificationCodeField_ID = "email_ver_input";
    private final String newPassword_ID = "newPassword";
    private final String reenterPassword_ID = "reenterPassword";

    //---Buttons
    private final String sendVerificationCodeBTN_ID = "email_ver_but_send";
    private final String verifyCodeBTN_ID = "email_ver_but_verify";
    private final String sendNewCodeBTN_ID = "email_ver_but_resend";
    private final String changeEmailBTN_ID = "email_ver_but_edit";
    private final String continueBTN_ID = "continue";
    private final String cancelBTN_ID = "cancel";

    //---Errors
    private final String incorrectVerificationCode_ID = "email_fail_retry";
    private final String expiredVerificationCode_ID = "email_fail_code_expired";
    private final String invalidEmailMsg_XPATH = "//p[contains(text(),'Please enter a valid email address.')]";
    private final String tooManyIncorrectAttemptsError_ID = "email_fail_no_retry";
    private final String requiredFieldMissing_ID = "requiredFieldMissing";
    private final String invalidPassError_XPATH = "//input[@id='newPassword']//preceding::p[contains(text(),'8-16 characters')]";
    private final String invalidConfirmPassError_XPATH = "//input[@id='newPassword']//following::p[contains(text(),'8-16 characters')]";
    private final String passwordEntryMismatch_ID = "passwordEntryMismatch";
    //--------------

    //Constructor
    private RecoverPasswordPage() {
        driver = getDriver();
    }

    public static RecoverPasswordPage getRecoverPasswordPage() {
        return new RecoverPasswordPage();
    }
    //-----------

    //-----------GETTERS
    public WebElement emailAddress_field() {
        return driver.findElement(By.id(emailAddressField_ID));
    }

    public WebElement sendVerCode_BTN() {
        return driver.findElement(By.id(sendVerificationCodeBTN_ID));
    }

    public WebElement verificationCode_field() {
        return driver.findElement(By.id(verificationCodeField_ID));
    }

    public WebElement verifyCode_BTN() {
        return driver.findElement(By.id(verifyCodeBTN_ID));
    }

    public WebElement sendNewCode_BTN() {
        return driver.findElement(By.id(sendNewCodeBTN_ID));
    }

    public WebElement changeEmail_BTN() {
        return driver.findElement(By.id(changeEmailBTN_ID));
    }

    public WebElement cancel_BTN() {
        return driver.findElement(By.id(cancelBTN_ID));
    }

    public WebElement continue_BTN() {
        return driver.findElement(By.id(continueBTN_ID));
    }

    public WebElement incorrectVerCode_Msg() {
        return driver.findElement(By.id(incorrectVerificationCode_ID));
    }

    public WebElement invalidEmail_Msg() {
        return driver.findElement(By.xpath(invalidEmailMsg_XPATH));
    }

    public WebElement expiredVerCode_Msg() {
        return driver.findElement(By.id(expiredVerificationCode_ID));
    }

    public WebElement tooManyAttempts_Msg() {
        return driver.findElement(By.id(tooManyIncorrectAttemptsError_ID));
    }

    public WebElement newPassword_field() {
        return driver.findElement(By.id(newPassword_ID));
    }

    public WebElement confNewPassword_field() {
        return driver.findElement(By.id(reenterPassword_ID));
    }

    public WebElement requiredFieldMissing_Msg() {
        return driver.findElement(By.id(requiredFieldMissing_ID));
    }

    public WebElement passMismatch_Msg() {
        return driver.findElement(By.id(passwordEntryMismatch_ID));
    }

    public WebElement invalidPass_Msg() {
        return driver.findElement(By.xpath(invalidPassError_XPATH));
    }

    public WebElement invalidConfirmPass_Msg() {
        return driver.findElement(By.xpath(invalidConfirmPassError_XPATH));
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
//        TestUtil.waitForElementToLoad(verifyCode_BTN());
//        verifyCode_BTN().click();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ExtentReport.addTestCaseStep("Clicked on Verify Code button");
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

//    public void checkErrMsgIsDisplayed(WebElement error_Msg) {
//        TestUtil.waitForElementToLoad(error_Msg);
//        ExtentReport.addTestCaseStep("Error message is displayed: " + error_Msg.getText());
//    }

    public void tryIncorrectPasswords(WebElement passField) {
        String[] invalidPass = {"passwordd", "Passwordd", "passwordd!!", "ThisIsAReallyReallyLongPassword1!"};

        for (String pass : invalidPass) {
            passField.clear();
            passField.sendKeys(pass);
            ExtentReport.addTestCaseStep("Entered the following password: " + pass);

            if (passField.hashCode() == newPassword_field().hashCode()) {
                TestUtil.waitForElementToLoad(invalidPass_Msg());
                ExtentReport.addTestCaseStep("Error message is displayed: " + invalidPass_Msg().getText());
            } else if (passField.hashCode() == confNewPassword_field().hashCode()) {
                TestUtil.waitForElementToLoad(invalidConfirmPass_Msg());
                ExtentReport.addTestCaseStep("Error message is displayed: " + invalidConfirmPass_Msg().getText());
            } else throw new NoSuchElementException();
        }
    }

}