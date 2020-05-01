package pages;

import base.TestBase;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utils.TestUtil;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.testng.Assert.assertTrue;
import static utils.DriverFactory.getDriver;

public class SignUpPage extends TestBase {
    private TestUtil testUtil;
    private WebDriver driver = getDriver();

    //-------PATHS-------
    //---Input fields
    private final String emailField_ID = "email";
    private final String verificationCodeField_ID = "email_ver_input";
    private final String newPasswordField_ID = "newPassword";
    private final String confirmNewPasswordField_ID = "reenterPassword";
    private final String firstNameField_ID = "givenName";
    private final String lastNameField_ID = "surname";

    //---Buttons
    private final String sendVerificationCodeBTN_ID = "email_ver_but_send";
    private final String verifyCodeBTN_ID = "email_ver_but_verify";
    private final String changeEmailBTN_ID = "email_ver_but_edit";
    private final String sendNewCodeBTN_ID = "email_ver_but_resend";
    private final String createBTN_ID = "continue";
    private final String cancelBTN_ID = "cancel";

    //---Dropdown items or radio buttons
    private final String countrySelector_ID = "country";
    private final String consentYes_ID = "extension_Consent_1";
    private final String consentNo_ID = "extension_Consent_2";

    //---Errors
    private final String incorrectVerificationCodeError_ID = "email_fail_retry";
    private final String expiredVerificationCodeError_ID = "email_fail_code_expired";
    private final String invalidEmailError_XPATH = "//div[@id='attributeList']//*[contains(text(),'Please enter a valid email address.')]";
    private final String invalidPassError_XPATH = "//li[2]//descendant::div[1]//descendant::div[1]";
    private final String invalidConfirmPassError_XPATH = "//li[3]//descendant::div[1]//descendant::div[1]";
    private final String passMismatchError_ID = "passwordEntryMismatch";
    private final String blankCountryError_ID = "claimVerificationServerError";
    private final String requiredFieldError_ID = "requiredFieldMissing";
    private final String tooManyAttemptsError_ID = "email_fail_no_retry";
    private final String mismatchingPassError_ID = "passwordEntryMismatch";
    //--------------

    //-------Locators-------
    //---Input fields
    @FindBy(id = emailField_ID)
    private WebElement emailAddress_field;
    @FindBy(id = verificationCodeField_ID)
    private WebElement verificationCode_field;
    @FindBy(id = newPasswordField_ID)
    private WebElement newPassword_field;
    @FindBy(id = confirmNewPasswordField_ID)
    private WebElement confNewPassword_field;
    @FindBy(id = firstNameField_ID)
    private WebElement firstName_field;
    @FindBy(id = lastNameField_ID)
    private WebElement lastName_field;

    //---Buttons
    @FindBy(id = sendVerificationCodeBTN_ID)
    private WebElement sendVerificationCode_BTN;
    @FindBy(id = verifyCodeBTN_ID)
    private WebElement verifyCode_BTN;
    @FindBy(id = changeEmailBTN_ID)
    private WebElement changeEmail_BTN;
    @FindBy(id = sendNewCodeBTN_ID)
    private WebElement sendNewCode_BTN;
    @FindBy(id = createBTN_ID)
    @CacheLookup
    private WebElement create_BTN;
    @FindBy(id = cancelBTN_ID)
    private WebElement cancel_BTN;

    //---Dropdown items or radio buttons
    @FindBy(id = countrySelector_ID)
    private WebElement country_dropdown;
    @FindBy(id = consentYes_ID)
    private WebElement consentYes;
    @FindBy(id = consentNo_ID)
    private WebElement consentNo;

    //---Errors
    @FindBy(id = incorrectVerificationCodeError_ID)
    private WebElement incorrectVerCode_Msg;
    @FindBy(id = expiredVerificationCodeError_ID)
    private WebElement expiredVerCode_Msg;
    @FindBy(xpath = invalidEmailError_XPATH)
    private WebElement invalidEmail_Msg;
    @FindBy(xpath = invalidPassError_XPATH)
    private WebElement invalidPass_Msg;
    @FindBy(xpath = invalidConfirmPassError_XPATH)
    private WebElement invalidConfirmPass_Msg;
    @FindBy(id = passMismatchError_ID)
    private WebElement passMismatch_Msg;
    @FindBy(id = blankCountryError_ID)
    private WebElement blankCountry_Msg;
    @FindBy(id = requiredFieldError_ID)
    private WebElement requiredFieldMissing_Msg;
    @FindBy(id = tooManyAttemptsError_ID)
    private WebElement tooManyAttempts_Msg;
    @FindBy(id = mismatchingPassError_ID)
    private WebElement mismatchingPass_Msg;
    //--------------

    //Constructor
    SignUpPage() {
        PageFactory.initElements(driver, this);
        testUtil = new TestUtil();
    }
    //-----------


    //-----------GETTERS
    public WebElement email_field() {
        return emailAddress_field;
    }

    public WebElement verificationCode_field() {
        return verificationCode_field;
    }

    public WebElement sendVerCode_BTN() {
        return sendVerificationCode_BTN;
    }

    public WebElement verifyCode_BTN() {
        return verifyCode_BTN;
    }

    public WebElement changeEmail_BTN() {
        return changeEmail_BTN;
    }

    public WebElement sendNewCode_BTN() {
        return sendNewCode_BTN;
    }

    public WebElement create_BTN() {
        return create_BTN;
    }

    public WebElement incorrectVerCode_Msg() {
        return incorrectVerCode_Msg;
    }

    public WebElement expiredVerCode_Msg() {
        return expiredVerCode_Msg;
    }

    public WebElement invalidEmail_Msg() {
        return invalidEmail_Msg;
    }

    public WebElement newPassword_field() {
        return newPassword_field;
    }

    public WebElement confNewPassword_field() {
        return confNewPassword_field;
    }

    public WebElement blankCountry_Msg() {
        return blankCountry_Msg;
    }

    public WebElement requiredFieldMissing_Msg() {
        return requiredFieldMissing_Msg;
    }

    public WebElement tooManyAttempts_Msg() {
        return tooManyAttempts_Msg;
    }

    //-----------

    //-----------SETTERS

    public void setInvalidEmail(String invalidEmail) {
        emailAddress_field.clear();
        emailAddress_field.sendKeys(invalidEmail);
        sendVerificationCode_BTN.click();
    }

    public void setVerificationCode_field(String text) {
        verificationCode_field.sendKeys(text);
    }

    public void setNewPassword(String password) {
        newPassword_field.sendKeys(password);
        addTestCaseStep("Entered the following password: " + password);
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        confNewPassword_field.sendKeys(confirmNewPassword);
        addTestCaseStep("Entered the following password in the Confirm password field: " + confirmNewPassword);
    }

    public void setFirstName(String firstName) {
        firstName_field.sendKeys(firstName);
        addTestCaseStep("Entered a first name: " + firstName);
    }

    public void setLastName(String lastName) {
        lastName_field.sendKeys(lastName);
        addTestCaseStep("Entered a last name: " + lastName);
    }

    //-----------

    //Actions

    public String getPageTitle() {
        return driver.getTitle();
    }

    public LibraryPage createNewAccount() {
        create_BTN.click();
        LibraryPage libraryPage = new LibraryPage();
        testUtil.waitForElementToLoad(libraryPage.termsAndCondCheckbox());
        Assert.assertTrue(libraryPage.termsAndCondCheckbox().isDisplayed());
        addTestCaseStep("Clicked on the Create account button. Terms and Conditions page is displayed");
        return libraryPage;
    }

    public void sendTokenToEmail(String email) {
        emailAddress_field.click();
        emailAddress_field.clear();
        emailAddress_field.sendKeys(email);
        sendVerificationCode_BTN.click();
        testUtil.waitForElementToLoad(verifyCode_BTN);
        Assert.assertTrue(verifyCode_BTN().isDisplayed());
        addTestCaseStep("Entered email: " + email + " and clicked on Send Verification code button");
    }

    public void verifyIfTokenExpired() {
        String error_msg = "That code is expired. Please request a new code.";

        verificationCode_field.click();
        verificationCode_field.clear();
        String token = testUtil.getTokenFromGmail();
        verificationCode_field.sendKeys(token);
        verifyCode_BTN.click();
        addTestCaseStep("Entered the following token: " + token + " and clicked on the Verify code");

        testUtil.waitForElementToLoad(expiredVerCode_Msg());
        Assert.assertEquals(expiredVerCode_Msg().getText(), error_msg);
        addTestCaseStep("Error message is displayed: " + error_msg);
    }

    public void sendInvalidToken(String invalidToken) {
        verificationCode_field.click();
        verificationCode_field.sendKeys(invalidToken);
        verifyCode_BTN.click();
        testUtil.waitForElementToLoad(incorrectVerCode_Msg);
        Assert.assertTrue(incorrectVerCode_Msg.isDisplayed());
        addTestCaseStep("Entered the following token: " + invalidToken + " and clicked on the Verify code button");
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

    public void enterInvalidTokenMultipleTimes(int timesToRetry) {
        for (int i = 0; i < timesToRetry; i++) {
            testUtil.waitForElementToLoad(verifyCode_BTN());
            verificationCode_field().clear();
            setVerificationCode_field(String.valueOf((i * 10000)));
            verifyCode_BTN().click();
        }

        addTestCaseStep("Entered the wrong token " + timesToRetry + " times");
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

    public void waitForTokenToExpire(int minutesToWait) {
        int milisecToWait = minutesToWait * 60 * 1000;
        try {
            Thread.sleep(milisecToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addTestCaseStep("Waited " + milisecToWait / 1000 + " seconds for the Verification code to expire");
    }

    public void selectRandomCountry() {
        Select country_select = new Select(country_dropdown);
        Random random = new Random();
        int index = random.nextInt(country_select.getOptions().size());
        country_select.selectByIndex(index);
        //TODO: countryName = GET THE NAME OF THE SELECTED COUNTRY;
        addTestCaseStep("Selected a random country from the dropdown list: ");
        extentTestChild.log(Status.PASS, "Selected a random country from the dropdown list");
    }

    public void selectRandomConsent() {
        Random random = new Random();
        if (random.nextInt(1) % 2 == 0) consentNo.click();
        else consentYes.click();
        addTestCaseStep("Selected randomly if I consented or not");
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

    public void tryIncorrectPasswords(WebElement passField) {
        String invalidPass[] = {"passwordd", "Passwordd", "passwordd!!", "ThisIsAReallyReallyLongPassword1!"};

        for (int i = 0; i < invalidPass.length; i++) {
            passField.clear();
            passField.sendKeys(invalidPass[i]);
            addTestCaseStep("Entered the following password: " + invalidPass[i]);

            if (passField == newPassword_field) {
                testUtil.waitForElementToLoad(invalidPass_Msg);
                Assert.assertTrue(invalidPass_Msg.getText().contains("8-16 characters"));
                addTestCaseStep("Error message is displayed: " + invalidPass_Msg.getText());
            } else if (passField == confNewPassword_field) {
                testUtil.waitForElementToLoad(invalidConfirmPass_Msg);
                Assert.assertTrue(invalidConfirmPass_Msg.getText().contains("8-16 characters"));
                addTestCaseStep("Error message is displayed: " + invalidConfirmPass_Msg.getText());
            } else throw new NoSuchElementException();
        }
    }

    public void enterMismatchingPasswords() {
        String pass1 = "PASSWORD123!";
        String pass2 = "Password123!";

        newPassword_field.clear();
        newPassword_field.sendKeys(pass1);
        addTestCaseStep("Entered the following password in the password field: " + pass1);
        confNewPassword_field.clear();
        confNewPassword_field.sendKeys(pass2);
        addTestCaseStep("Entered the following password in the confirm password field: " + pass2);

        create_BTN.click();
        addTestCaseStep("Clicked on the Create button");
        testUtil.waitForElementToLoad(mismatchingPass_Msg);
        Assert.assertTrue(mismatchingPass_Msg.getText().contains("The password entry fields do not match"));
        addTestCaseStep("Error message is displayed: "+ mismatchingPass_Msg.getText());
    }

    public void createUserWithoutCountry(){
        create_BTN.click();
        testUtil.waitForElementToLoad(blankCountry_Msg);
        assertTrue(blankCountry_Msg().getText().contains("Missing required element: Country/Region"));
        addTestCaseStep("No country was selected error message is displayed");
    }

    public void createUserWithoutMandatoryField(){
        String error_msg = "A required field is missing. Please fill out all required fields and try again.";

        create_BTN.click();
        testUtil.waitForElementToLoad(requiredFieldMissing_Msg());
        assertTrue(requiredFieldMissing_Msg().getText().contains(error_msg));
        addTestCaseStep("Mandatory field is missing error message is displayed");
    }

    public void cancelRegistration(){
        cancel_BTN.click();
        LandingPage landingPage = LandingPage.getLandingPage();
        testUtil.waitForElementToLoad(landingPage.login_BTN());
        addTestCaseStep("Clicked and the Cancel button and was redirected to Landing page");
    }

}