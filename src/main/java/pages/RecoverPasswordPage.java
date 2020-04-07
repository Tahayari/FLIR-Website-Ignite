package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.TestUtil;

import java.util.NoSuchElementException;

import static org.testng.Assert.assertTrue;

public class RecoverPasswordPage extends TestBase {
    private TestUtil testUtil;
    //-------PATHS-------

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
    private final String invalidEmailError_XPATH = "//p[contains(text(),'Please enter a valid email address.')]";
    private final String tooManyIncorrectAttemptsError_ID = "email_fail_no_retry";
    private final String requiredFieldMissing_ID = "requiredFieldMissing";
    private final String invalidPassError_XPATH = "//input[@id='newPassword']//preceding::p[contains(text(),'8-16 characters')]";
    private final String invalidConfirmPassError_XPATH = "//input[@id='newPassword']//following::p[contains(text(),'8-16 characters')]";
    private final String passwordEntryMismatch_ID = "passwordEntryMismatch";
    //--------------

    //-------Locators-------
    @FindBy(id = emailAddressField_ID)
    private WebElement email_field;
    @FindBy(id = sendVerificationCodeBTN_ID)
    private WebElement sendVerCode_BTN;
    @FindBy(id = verificationCodeField_ID)
    private WebElement verificationCode_field;
    @FindBy(id = verifyCodeBTN_ID)
    private WebElement verifyCode_BTN;
    @FindBy(id = sendNewCodeBTN_ID)
    private WebElement sendNewCode_BTN;
    @FindBy(id = changeEmailBTN_ID)
    private WebElement changeEmail_BTN;
    @FindBy(id = continueBTN_ID)
    private WebElement continue_BTN;
    @FindBy(id = cancelBTN_ID)
    private WebElement cancel_BTN;
    @FindBy(id = incorrectVerificationCode_ID)
    private WebElement incorrectVerCode;
    @FindBy(xpath = invalidEmailError_XPATH)
    private WebElement invalidEmailErrorMsg;
    @FindBy(id = expiredVerificationCode_ID)
    private WebElement expiredVerCode;
    @FindBy(id = tooManyIncorrectAttemptsError_ID)
    private WebElement tooManyIncorrectAttemptsError;
    @FindBy(id = newPassword_ID)
    private WebElement newPassword_field;
    @FindBy(id = reenterPassword_ID)
    private WebElement reEnterPassword_field;
    @FindBy(id = requiredFieldMissing_ID)
    private WebElement requiredFieldMissingMsg;
    @FindBy(xpath = invalidPassError_XPATH)
    private WebElement invalidPassErrorMsg;
    @FindBy(xpath = invalidConfirmPassError_XPATH)
    private WebElement invalidConfirmPassErrorMsg;
    @FindBy(id = passwordEntryMismatch_ID)
    private WebElement passwordEntryMismatchMsg;
    //--------------

    //Constructor
    public RecoverPasswordPage() {
        PageFactory.initElements(driver, this);
        testUtil = new TestUtil();
    }
    //-----------

    //-----------GETTERS
    public WebElement email_field() {
        return email_field;
    }

    public WebElement sendVerCode_BTN() {
        return sendVerCode_BTN;
    }

    public WebElement verificationCode_field() {
        return verificationCode_field;
    }

    public WebElement verifyCode_BTN() {
        return verifyCode_BTN;
    }

    public WebElement sendNewCode_BTN() {
        return sendNewCode_BTN;
    }

    public WebElement changeEmail_BTN() {
        return changeEmail_BTN;
    }

    public WebElement continue_BTN() {
        return continue_BTN;
    }

    public WebElement cancel_BTN() {
        return cancel_BTN;
    }

    public WebElement incorrectVerCodeMsg() {
        return incorrectVerCode;
    }

    public WebElement invalidEmailErrorMsg() {
        return invalidEmailErrorMsg;
    }

    public WebElement expiredVerCode() {
        return expiredVerCode;
    }

    public WebElement tooManyIncorrectAttemptsError() {
        return tooManyIncorrectAttemptsError;
    }

    public WebElement newPassword_field() {
        return newPassword_field;
    }

    public WebElement reEnterPassword_field() {
        return reEnterPassword_field;
    }

    public WebElement requiredFieldMissingMsg() {
        return requiredFieldMissingMsg;
    }

    public WebElement invalidPassErrorMsg() {
        return invalidPassErrorMsg;
    }

    public WebElement invalidConfirmPassErrorMsg() {
        return invalidConfirmPassErrorMsg;
    }
    //--------------

    //-----------SETTERS
    public void setVerificationCode_field(String text) {
        verificationCode_field.sendKeys(text);
    }

    public void setInvalidEmail(String invalidEmail) {
        email_field.clear();
        email_field.sendKeys(invalidEmail);
        sendVerCode_BTN.click();
    }
    //--------------

    //Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public void sendTokenToEmail(String email) {
        email_field.click();
        email_field.clear();
        email_field.sendKeys(email);
        sendVerCode_BTN.click();
        testUtil.waitForElementToLoad(verifyCode_BTN);
        Assert.assertTrue(verifyCode_BTN().isDisplayed());
        addTestCaseStep("Entered email: " + email + " and clicked on Send Verification code button");
    }

    public void sendInvalidToken(String invalidToken) {
        verificationCode_field.click();
        verificationCode_field.sendKeys(invalidToken);
        verifyCode_BTN.click();
        testUtil.waitForElementToLoad(incorrectVerCode);
        Assert.assertTrue(incorrectVerCodeMsg().isDisplayed());
        addTestCaseStep("Entered the following token: " + invalidToken + " and clicked on the Verify code button");
    }

    public void verifyInvalidTokenErrorMsg() {
        String error_msg = "That code is incorrect. Please try again.";

        testUtil.waitForElementToLoad(incorrectVerCode);
        Assert.assertEquals(incorrectVerCode.getText(), error_msg);
        addTestCaseStep("Error message is displayed: " + error_msg);
    }

    public void waitForTokenToExpire(int minutesToWait) {
        int milisecToWait = minutesToWait * 60 * 1000;
        try {
            Thread.sleep(milisecToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addTestCaseStep("Waited " + milisecToWait / 1000 + " seconds for the Verification code to expire");
    }

    public void enterTokenFromGmail() {
        verificationCode_field.click();
        verificationCode_field.clear();
        String token = testUtil.getTokenFromGmail();
        verificationCode_field.sendKeys(token);
        verifyCode_BTN.click();
        testUtil.waitForElementToLoad(changeEmail_BTN);
        Assert.assertTrue(changeEmail_BTN.isDisplayed());
        addTestCaseStep("Entered the following token: " + token + " and clicked on the Verify code");
    }

    public void enterTokenFromMailinator(String email) {
        verificationCode_field.click();
        verificationCode_field.clear();
        String token = testUtil.getTokenFromMailinator(email);
        verificationCode_field.sendKeys(token);
        verifyCode_BTN.click();
        testUtil.waitForElementToLoad(changeEmail_BTN);
        Assert.assertTrue(changeEmail_BTN.isDisplayed());
        addTestCaseStep("Entered the following token: " + token + " and clicked on the Verify code");
    }

    public void verifyIfTokenExpired() {
        String error_msg = "That code is expired. Please request a new code.";

        verificationCode_field.click();
        verificationCode_field.clear();
        String token = testUtil.getTokenFromGmail();
        verificationCode_field.sendKeys(token);
        verifyCode_BTN.click();
        addTestCaseStep("Entered the following token: " + token + " and clicked on the Verify code");

        testUtil.waitForElementToLoad(expiredVerCode());
        Assert.assertEquals(expiredVerCode().getText(), error_msg);
        addTestCaseStep("Error message is displayed: " + error_msg);
    }

    public void enterInvalidTokenMultipleTimes(int timesToRetry) {
        for (int i = 0; i < timesToRetry; i++) {
            testUtil.waitForElementToLoad(verifyCode_BTN());
            verificationCode_field().clear();
            setVerificationCode_field(String.valueOf((i * 10000)));
            verifyCode_BTN().click();
        }

        addTestCaseStep("Entered the wrong token " + timesToRetry + " times");
    }

    public void generateAnotherToken() {
        sendNewCode_BTN().click();
        testUtil.waitForElementToLoad(verifyCode_BTN);
        Assert.assertTrue(verifyCode_BTN.isDisplayed());
        addTestCaseStep("Generated another token");
    }

    public void clickOn_changeEmail_BTN() {
        changeEmail_BTN().click();
        testUtil.waitForElementToLoad(sendVerCode_BTN());
        assertTrue(sendVerCode_BTN().isDisplayed());
        assertTrue(sendVerCode_BTN().getAttribute("value").isEmpty());
        addTestCaseStep("Clicked on the Change e-mail button. Email field is now empty");
    }

    public void clickOn_continue_BTN() {
        continue_BTN.click();
        testUtil.waitForElementToLoad(newPassword_field);
        addTestCaseStep("Enter new password page is displayed");
    }

    public void tryIncorrectPasswords(WebElement passField) {
        String invalidPass[] = {" ", "passwordd", "Passwordd", "passwordd!!", "ThisIsAReallyReallyLongPassword1!"};

        for (int i = 0; i < invalidPass.length; i++) {
            passField.clear();
            passField.sendKeys(invalidPass[i]);
            addTestCaseStep("Entered the following password: " + invalidPass[i]);

            if (passField == newPassword_field) {
                testUtil.waitForElementToLoad(invalidPassErrorMsg);
                Assert.assertTrue(invalidPassErrorMsg.getText().contains("8-16 characters"));
                addTestCaseStep("Error message is displayed: " + invalidPassErrorMsg.getText());
            } else if (passField == reEnterPassword_field) {
                testUtil.waitForElementToLoad(invalidConfirmPassErrorMsg);
                Assert.assertTrue(invalidConfirmPassErrorMsg.getText().contains("8-16 characters"));
                addTestCaseStep("Error message is displayed: " + invalidConfirmPassErrorMsg.getText());
            } else throw new NoSuchElementException();
        }
    }


    public void submitBlankPassword() {
        continue_BTN.click();
        addTestCaseStep("Left the password field blank and clicked on Continue button");

        testUtil.waitForElementToLoad(requiredFieldMissingMsg);
        Assert.assertTrue(requiredFieldMissingMsg.isDisplayed());
        addTestCaseStep("Error message is displayed: " + requiredFieldMissingMsg.toString());
    }

    public void enterMismatchingPasswords() {
        String pass1 = "PASSWORD123!";
        String pass2 = "Password123!";

        newPassword_field.clear();
        newPassword_field.sendKeys(pass1);
        addTestCaseStep("Entered the following password in the password field: " + pass1);
        reEnterPassword_field.clear();
        reEnterPassword_field.sendKeys(pass2);
        addTestCaseStep("Entered the following password in the confirm password field: " + pass2);

        continue_BTN.click();
        addTestCaseStep("Clicked on the Create button");
        testUtil.waitForElementToLoad(passwordEntryMismatchMsg);
        Assert.assertTrue(passwordEntryMismatchMsg.getText().contains("The password entry fields do not match"));
        addTestCaseStep("Error message is displayed: " + passwordEntryMismatchMsg.getText());
    }

    public void enterNewPassword(String newPassword) {
        newPassword_field.sendKeys(newPassword);
        reEnterPassword_field.sendKeys(newPassword);
        addTestCaseStep("Entered the password " + newPassword +" into the password fields");
    }

    public LibraryPage createNewPassword(){
        continue_BTN.click();
        LibraryPage libraryPage = new LibraryPage();
        testUtil.waitForElementToLoad(libraryPage.newFolder_BTN());
        Assert.assertTrue(libraryPage.newFolder_BTN().isDisplayed());
        addTestCaseStep("Clicked on the Continue button. New Password is set and Library page is displayed");
        return libraryPage;
    }
//--------------

//TODO : Rest of the elements from this page
}