package pages;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utils.ExtentReport;
import utils.TestUtil;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.testng.Assert.assertTrue;
import static utils.DriverFactory.getDriver;

public class SignUpPage {
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
    private SignUpPage() {
        driver = getDriver();
        PageFactory.initElements(driver, this);
    }

    public static SignUpPage getSignUpPage() {
        return new SignUpPage();
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
        TestUtil.waitForElementToLoad(changeEmail_BTN);
        ExtentReport.addTestCaseStep("Token from email is validated; change E-mail button is displayed");
        return changeEmail_BTN;
    }

    public WebElement sendNewCode_BTN() {
        return sendNewCode_BTN;
    }

    public WebElement create_BTN() {
        return create_BTN;
    }

    public WebElement incorrectVerCode_Msg() {
        TestUtil.waitForElementToLoad(incorrectVerCode_Msg);
        ExtentReport.addTestCaseStep("Error message is displayed: "+incorrectVerCode_Msg.getText());
        return incorrectVerCode_Msg;
    }

    public WebElement expiredVerCode_Msg() {
        TestUtil.waitForElementToLoad(expiredVerCode_Msg);
        ExtentReport.addTestCaseStep("Error message is displayed: "+expiredVerCode_Msg.getText());
        return expiredVerCode_Msg;
    }

    public WebElement invalidEmail_Msg() {
        TestUtil.waitForElementToLoad(invalidEmail_Msg);
        ExtentReport.addTestCaseStep("Error message is displayed: "+invalidEmail_Msg.getText());
        return invalidEmail_Msg;
    }

    public WebElement newPassword_field() {
        return newPassword_field;
    }

    public WebElement confNewPassword_field() {
        return confNewPassword_field;
    }

    public WebElement firstName_field() {
        return firstName_field;
    }

    public WebElement lastName_field() {
        return lastName_field;
    }

    public WebElement blankCountry_Msg() {
        return blankCountry_Msg;
    }

    public WebElement requiredFieldMissing_Msg() {
        return requiredFieldMissing_Msg;
    }

    public WebElement tooManyAttempts_Msg() {
        TestUtil.waitForElementToLoad(tooManyAttempts_Msg);
        ExtentReport.addTestCaseStep("Error message is displayed: "+tooManyAttempts_Msg.getText());
        return tooManyAttempts_Msg;
    }

    //-----------

    //-----------SETTERS

    public SignUpPage setEmail(String email) {
        TestUtil.waitForElementToLoad(emailAddress_field);
        emailAddress_field.clear();
        emailAddress_field.sendKeys(email);
        ExtentReport.addTestCaseStep("Entered the following email: " + email);
        return this;
    }

    public SignUpPage setVerificationCode_field(String code) {
        TestUtil.waitForElementToLoad(verificationCode_field);
        verificationCode_field.clear();
        verificationCode_field.sendKeys(code);
        ExtentReport.addTestCaseStep("Entered the following Verification Code: " + code);
        return this;
    }

    public SignUpPage setNewPassword(String password) {
        TestUtil.waitForElementToLoad(newPassword_field);
        newPassword_field.clear();
        newPassword_field.sendKeys(password);
        ExtentReport.addTestCaseStep("Entered the following password: " + password);
        return this;
    }

    public SignUpPage setConfirmNewPassword(String confirmNewPassword) {
        TestUtil.waitForElementToLoad(confNewPassword_field);
        confNewPassword_field.clear();
        confNewPassword_field.sendKeys(confirmNewPassword);
        ExtentReport.addTestCaseStep("Entered the following password in the Confirm password field: " + confirmNewPassword);
        return this;
    }

    public SignUpPage setFirstName(String firstName) {
        TestUtil.waitForElementToLoad(firstName_field);
        firstName_field.clear();
        firstName_field.sendKeys(firstName);
        ExtentReport.addTestCaseStep("Entered a first name: " + firstName);
        return this;
    }

    public SignUpPage setLastName(String lastName) {
        TestUtil.waitForElementToLoad(lastName_field);
        lastName_field.clear();
        lastName_field.sendKeys(lastName);
        ExtentReport.addTestCaseStep("Entered a last name: " + lastName);
        return this;
    }

    //-----------

    //Actions

    public String getPageTitle() {
        return driver.getTitle();
    }

    public SignUpPage clearField(WebElement webElement) {
        webElement.clear();
        return this;
    }

    public LibraryPage createNewAccount() {
        create_BTN.click();
        LibraryPage libraryPage = new LibraryPage();
//        testUtil.waitForElementToLoad(libraryPage.termsAndCondCheckbox());
        Assert.assertTrue(libraryPage.termsAndCondCheckbox().isDisplayed());
        ExtentReport.addTestCaseStep("Clicked on the Create account button. Terms and Conditions page is displayed");
        return libraryPage;
    }

    public SignUpPage sendTokenToEmail(String email) {
        setEmail(email)
                .clickOn_sendVerificationCode_BTN();
        return this;
    }

    public SignUpPage clickOn_sendVerificationCode_BTN() {
        TestUtil.waitForElementToLoad(sendVerificationCode_BTN);
        sendVerificationCode_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on Send Verification Code button");
        return this;
    }

    public void enterTokenFromMailinator(String email) {
        verificationCode_field.click();
        verificationCode_field.clear();
        String token = "token";
//                testUtil.getTokenFromMailinator(email);
        verificationCode_field.sendKeys(token);
        verifyCode_BTN.click();
//        testUtil.waitForElementToLoad(changeEmail_BTN);
        Assert.assertTrue(changeEmail_BTN.isDisplayed());
        ExtentReport.addTestCaseStep("Entered the following token: " + token + " and clicked on the Verify code");
    }


    public void enterTokenFromGmail() {
        verificationCode_field.click();
        verificationCode_field.clear();
        String token = TestUtil.getTokenFromGmail();
        setVerificationCode_field(token)
                .clickOn_verifyCode_BTN();
        ExtentReport.addTestCaseStep("Entered the following token: " + token + " and clicked on the Verify code");
    }

    public void selectRandomCountry() {
        Select country_select = new Select(country_dropdown);
        Random random = new Random();
        int index = random.nextInt(country_select.getOptions().size());
        country_select.selectByIndex(index);
        //TODO: countryName = GET THE NAME OF THE SELECTED COUNTRY;
        ExtentReport.addTestCaseStep("Selected a random country from the dropdown list: ");
        ExtentReport.extentTestChild.log(Status.PASS, "Selected a random country from the dropdown list");
    }

    public void selectRandomConsent() {
        Random random = new Random();
        if (random.nextInt(1) % 2 == 0) consentNo.click();
        else consentYes.click();
        ExtentReport.addTestCaseStep("Selected randomly if I consented or not");
    }

    public void generateAnotherToken() {
        sendNewCode_BTN().click();
//        testUtil.waitForElementToLoad(verifyCode_BTN);
        Assert.assertTrue(verifyCode_BTN.isDisplayed());
        ExtentReport.addTestCaseStep("Generated another token");
    }

    public void clickOn_changeEmail_BTN() {
        changeEmail_BTN().click();
//        testUtil.waitForElementToLoad(sendVerCode_BTN());
        assertTrue(sendVerCode_BTN().isDisplayed());
        assertTrue(sendVerCode_BTN().getAttribute("value").isEmpty());
        ExtentReport.addTestCaseStep("Clicked on the Change e-mail button. Email field is now empty");
    }

    public void tryIncorrectPasswords(WebElement passField) {
        String invalidPass[] = {"passwordd", "Passwordd", "passwordd!!", "ThisIsAReallyReallyLongPassword1!"};

        for (int i = 0; i < invalidPass.length; i++) {
            passField.clear();
            passField.sendKeys(invalidPass[i]);
            ExtentReport.addTestCaseStep("Entered the following password: " + invalidPass[i]);

            if (passField == newPassword_field) {
//                testUtil.waitForElementToLoad(invalidPass_Msg);
                Assert.assertTrue(invalidPass_Msg.getText().contains("8-16 characters"));
                ExtentReport.addTestCaseStep("Error message is displayed: " + invalidPass_Msg.getText());
            } else if (passField == confNewPassword_field) {
//                testUtil.waitForElementToLoad(invalidConfirmPass_Msg);
                Assert.assertTrue(invalidConfirmPass_Msg.getText().contains("8-16 characters"));
                ExtentReport.addTestCaseStep("Error message is displayed: " + invalidConfirmPass_Msg.getText());
            } else throw new NoSuchElementException();
        }
    }

    public void enterMismatchingPasswords() {
        String pass1 = "PASSWORD123!";
        String pass2 = "Password123!";

        newPassword_field.clear();
        newPassword_field.sendKeys(pass1);
        ExtentReport.addTestCaseStep("Entered the following password in the password field: " + pass1);
        confNewPassword_field.clear();
        confNewPassword_field.sendKeys(pass2);
        ExtentReport.addTestCaseStep("Entered the following password in the confirm password field: " + pass2);

        create_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on the Create button");
//        testUtil.waitForElementToLoad(mismatchingPass_Msg);
        Assert.assertTrue(mismatchingPass_Msg.getText().contains("The password entry fields do not match"));
        ExtentReport.addTestCaseStep("Error message is displayed: " + mismatchingPass_Msg.getText());
    }

    public void createUserWithoutCountry() {
        create_BTN.click();
//        testUtil.waitForElementToLoad(blankCountry_Msg);
        assertTrue(blankCountry_Msg().getText().contains("Missing required element: Country/Region"));
        ExtentReport.addTestCaseStep("No country was selected error message is displayed");
    }

    public void createUserWithoutMandatoryField() {
        String error_msg = "A required field is missing. Please fill out all required fields and try again.";

        create_BTN.click();
//        testUtil.waitForElementToLoad(requiredFieldMissing_Msg());
        assertTrue(requiredFieldMissing_Msg().getText().contains(error_msg));
        ExtentReport.addTestCaseStep("Mandatory field is missing error message is displayed");
    }

    public void cancelRegistration() {
        cancel_BTN.click();
        LandingPage landingPage = LandingPage.getLandingPage();
//        testUtil.waitForElementToLoad(landingPage.login_BTN());
        ExtentReport.addTestCaseStep("Clicked and the Cancel button and was redirected to Landing page");
    }

    public SignUpPage clickOn_verifyCode_BTN() {
        TestUtil.waitForElementToLoad(verifyCode_BTN);
        verifyCode_BTN.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExtentReport.addTestCaseStep("Clicked on Verify Code button");
        return this;
    }

    public SignUpPage verifyIfPageLoaded(){
        TestUtil.waitForElementToLoad(emailAddress_field);
        ExtentReport.addTestCaseStep("Navigated to the Login page");
        return this;
    }
}