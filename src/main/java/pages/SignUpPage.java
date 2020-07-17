package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.ExtentReport;
import utils.TestUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.testng.Assert.assertTrue;
import static utils.DriverFactory.getDriver;

public class SignUpPage {
    private final WebDriver driver;

    //-------LOCATORS-------
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
    private final String invalidEmailError_XPATH = "//div[@id='attributeList']" +
            "//*[contains(text(),'Please enter a valid email address.')]";
    private final String invalidPassError_XPATH = "//li[2]//descendant::div[1]//descendant::div[1]";
    private final String invalidConfirmPassError_XPATH = "//li[3]//descendant::div[1]//descendant::div[1]";
    private final String blankCountryError_ID = "claimVerificationServerError";
    private final String requiredFieldError_ID = "requiredFieldMissing";
    private final String tooManyAttemptsError_ID = "email_fail_no_retry";
    private final String mismatchingPassError_ID = "passwordEntryMismatch";
    private final String existingUserError_XPATH = "//div[@id='claimVerificationServerError']" +
            "[contains(text(),'user with the specified ID already exists')]";
    //--------------

    //-------Locators-------
    //---Input fields
//    @FindBy(id = emailField_ID)
//    private WebElement emailAddress_field;
//    @FindBy(id = verificationCodeField_ID)
//    private WebElement verificationCode_field;
//    @FindBy(id = newPasswordField_ID)
//    private WebElement newPassword_field;
//    @FindBy(id = confirmNewPasswordField_ID)
//    private WebElement confNewPassword_field;
//    @FindBy(id = firstNameField_ID)
//    private WebElement firstName_field;
//    @FindBy(id = lastNameField_ID)
//    private WebElement lastName_field;

    //---Buttons
//    @FindBy(id = sendVerificationCodeBTN_ID)
//    private WebElement sendVerificationCode_BTN;
//    @FindBy(id = verifyCodeBTN_ID)
//    private WebElement verifyCode_BTN;
//    @FindBy(id = changeEmailBTN_ID)
//    private WebElement changeEmail_BTN;
//    @FindBy(id = sendNewCodeBTN_ID)
//    private WebElement sendNewCode_BTN;
//    @FindBy(id = createBTN_ID)
//    private WebElement create_BTN;
//    @FindBy(id = cancelBTN_ID)
//    private WebElement cancel_BTN;

    //---Dropdown items or radio buttons
//    @FindBy(id = countrySelector_ID)
//    private WebElement country_dropdown;
//    @FindBy(id = consentYes_ID)
//    private WebElement consentYes;
//    @FindBy(id = consentNo_ID)
//    private WebElement consentNo;

    //---Errors
//    @FindBy(id = incorrectVerificationCodeError_ID)
//    private WebElement incorrectVerCode_Msg;
//    @FindBy(id = expiredVerificationCodeError_ID)
//    private WebElement expiredVerCode_Msg;
//    @FindBy(xpath = invalidEmailError_XPATH)
//    private WebElement invalidEmail_Msg;
//    @FindBy(xpath = invalidPassError_XPATH)
//    private WebElement invalidPass_Msg;
//    @FindBy(xpath = invalidConfirmPassError_XPATH)
//    private WebElement invalidConfirmPass_Msg;
//    @FindBy(id = blankCountryError_ID)
//    private WebElement blankCountry_Msg;
//    @FindBy(id = requiredFieldError_ID)
//    private WebElement requiredFieldMissing_Msg;
//    @FindBy(id = tooManyAttemptsError_ID)
//    private WebElement tooManyAttempts_Msg;
//    @FindBy(id = mismatchingPassError_ID)
//    private WebElement mismatchingPass_Msg;
//    @FindBy(xpath = existingUserError_XPATH)
//    private WebElement existingUserErr_Msg;
    //--------------

    //Constructor
    private SignUpPage() {
//        PageFactory.initElements(getDriver(), this);
        driver = getDriver();
    }

    public static SignUpPage getSignUpPage() {
        return new SignUpPage();
    }
    //-----------


    //-----------GETTERS
    public WebElement email_field() {
        return driver.findElement(By.id(emailField_ID));
    }

    public WebElement verificationCode_field() {
        return driver.findElement(By.id(verificationCodeField_ID));
    }

    public WebElement sendVerCode_BTN() {
        return driver.findElement(By.id(sendVerificationCodeBTN_ID));
    }

    public WebElement verifyCode_BTN() {
        return driver.findElement(By.id(verifyCodeBTN_ID));
    }

    public WebElement changeEmail_BTN() {
        TestUtil.waitForElementToLoad(driver.findElement(By.id(changeEmailBTN_ID)));
        ExtentReport.addTestCaseStep("Token from email is validated; change E-mail button is displayed");
        return driver.findElement(By.id(changeEmailBTN_ID));
    }

    public WebElement sendNewCode_BTN() {
        return driver.findElement(By.id(sendNewCodeBTN_ID));
    }

    public WebElement create_BTN() {
        return driver.findElement(By.id(createBTN_ID));
    }

    public WebElement cancel_BTN() {
        return driver.findElement(By.id(cancelBTN_ID));
    }

    public WebElement incorrectVerCode_Msg() {
        return driver.findElement(By.id(incorrectVerificationCodeError_ID));
    }

    public WebElement expiredVerCode_Msg() {
        return driver.findElement(By.id(expiredVerificationCodeError_ID));
    }

    public WebElement invalidEmail_Msg() {
        return driver.findElement(By.xpath(invalidEmailError_XPATH));
    }

    public WebElement invalidPass_Msg() {
        return driver.findElement(By.xpath(invalidPassError_XPATH));
    }

    public WebElement invalidConfirmPass_Msg() {
        return driver.findElement(By.xpath(invalidConfirmPassError_XPATH));
    }

    public WebElement existingUserErr_Msg() {
        return driver.findElement(By.xpath(existingUserError_XPATH));
    }

    public WebElement newPassword_field() {
        return driver.findElement(By.id(newPasswordField_ID));
    }

    public WebElement confNewPassword_field() {
        return driver.findElement(By.id(confirmNewPasswordField_ID));
    }

    public WebElement firstName_field() {
        return driver.findElement(By.id(firstNameField_ID));
    }

    public WebElement lastName_field() {
        return driver.findElement(By.id(lastNameField_ID));
    }

    public WebElement country_dropdown() {
        return driver.findElement(By.id(countrySelector_ID));
    }

    public WebElement consentYes() {
        return driver.findElement(By.id(consentYes_ID));
    }

    public WebElement consentNo() {
        return driver.findElement(By.id(consentNo_ID));
    }

    public WebElement requiredFieldMissing_Msg() {
        return driver.findElement(By.id(requiredFieldError_ID));
    }

    public WebElement tooManyAttempts_Msg() {
        return driver.findElement(By.id(tooManyAttemptsError_ID));
    }

    public WebElement mismatchingPass_Msg() {
        return driver.findElement(By.id(mismatchingPassError_ID));
    }

    public WebElement blankCountry_Msg() {
        return driver.findElement(By.id(blankCountryError_ID));
    }

    //-----------

    //-----------SETTERS

    public SignUpPage setEmail(String email) {
        TestUtil.waitForElementToLoad(email_field());
        email_field().clear();
        email_field().sendKeys(email);
        ExtentReport.addTestCaseStep("Entered the following email: " + email);
        return this;
    }

    public SignUpPage setVerificationCode_field(String code) {
        TestUtil.waitForElementToLoad(verificationCode_field());
        verificationCode_field().clear();
        verificationCode_field().sendKeys(code);
        ExtentReport.addTestCaseStep("Entered the following Verification Code: " + code);
        return this;
    }

    public SignUpPage setNewPassword(String password) {
        TestUtil.waitForElementToLoad(newPassword_field());
        newPassword_field().clear();
        newPassword_field().sendKeys(password);
        ExtentReport.addTestCaseStep("Entered the following password: " + password);
        return this;
    }

    public SignUpPage setConfirmNewPassword(String confirmNewPassword) {
        TestUtil.waitForElementToLoad(confNewPassword_field());
        confNewPassword_field().clear();
        confNewPassword_field().sendKeys(confirmNewPassword);
        ExtentReport.addTestCaseStep("Entered the following password in the Confirm password field: " + confirmNewPassword);
        return this;
    }

    public SignUpPage setFirstName(String firstName) {
        TestUtil.waitForElementToLoad(firstName_field());
        firstName_field().clear();
        firstName_field().sendKeys(firstName);
        ExtentReport.addTestCaseStep("Entered a first name: " + firstName);
        return this;
    }

    public SignUpPage setLastName(String lastName) {
        TestUtil.waitForElementToLoad(lastName_field());
        lastName_field().clear();
        lastName_field().sendKeys(lastName);
        ExtentReport.addTestCaseStep("Entered a last name: " + lastName);
        return this;
    }

    //-----------

    //Actions

    public SignUpPage clearField(WebElement webElement) {
        webElement.clear();
        return this;
    }


    public SignUpPage sendTokenToEmail(String email) {
        setEmail(email)
                .clickOn_sendVerificationCode_BTN();
        //TODO maybe verify here if the email has been used too many times...
        return this;
    }

    public SignUpPage clickOn_sendVerificationCode_BTN() {
        TestUtil.waitForElementToLoad(sendVerCode_BTN());
        sendVerCode_BTN().click();
        ExtentReport.addTestCaseStep("Clicked on Send Verification Code button");
        return this;
    }

    public SignUpPage selectRandomCountry() {
        Select country_select = new Select(country_dropdown());
        Random random = new Random();
        int index = random.nextInt(country_select.getOptions().size());
        country_select.selectByIndex(index);
        List<WebElement> countries = country_select.getOptions();
        ExtentReport.addTestCaseStep("Selected a random country from the dropdown list: " + countries.get(index).getText());
        return this;
    }

    public SignUpPage selectRandomConsent() {
        Random random = new Random();
        if (random.nextInt(1) % 2 == 0) {
            consentNo().click();
            ExtentReport.addTestCaseStep("Selected randomly if I consented or not. Selected NO");
        } else {
            consentYes().click();
            ExtentReport.addTestCaseStep("Selected randomly if I consented or not. Selected YES");
        }
        return this;
    }

    public void clickOn_changeEmail_BTN() {
        changeEmail_BTN().click();
        assertTrue(sendVerCode_BTN().isDisplayed());
        assertTrue(sendVerCode_BTN().getAttribute("value").isEmpty());
        ExtentReport.addTestCaseStep("Clicked on the Change e-mail button. Email field is now empty");
    }

    public void tryIncorrectPasswords(WebElement passField) {
        String[] invalidPass = {"passwordd", "Passwordd", "passwordd!!", "ThisIsAReallyReallyLongPassword"};

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

    public SignUpPage clickOn_verifyCode_BTN() {
        TestUtil.waitForElementToLoad(verifyCode_BTN());
        verifyCode_BTN().click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExtentReport.addTestCaseStep("Clicked on Verify Code button");
        return this;
    }

    public void verifyIfPageLoaded() {
        TestUtil.waitForElementToLoad(email_field());
        ExtentReport.addTestCaseStep("Navigated to the Login page");
    }

    public SignUpPage clickOn_create_BTN() {
        TestUtil.waitForElementToLoad(create_BTN());
        create_BTN().click();
        ExtentReport.addTestCaseStep("Clicked on the Create button");
        return this;
    }

    public void clickOn_cancel_BTN() {
        TestUtil.waitForElementToLoad(cancel_BTN());
        cancel_BTN().click();
        ExtentReport.addTestCaseStep("Clicked on the Cancel button");
    }

    public SignUpPage clickOn_sendNewCode_BTN() {
        TestUtil.waitForElementToLoad(sendNewCode_BTN());
        sendNewCode_BTN().click();
        ExtentReport.addTestCaseStep("Clicked on the Send new code button");
        return this;
    }

    public void checkErrMsgIsDisplayed(WebElement error_Msg) {
        TestUtil.waitForElementToLoad(error_Msg);
        ExtentReport.addTestCaseStep("Error message is displayed: " + error_Msg.getText());
    }
}