package utils;

public enum ElementManager {

    //BUTTON("xpath","id","CssSelector"),

    //LANDING PAGE
    LANDINGPAGE_SIGNIN_BTN("//span[contains(text(),'Sign in')]"),
    LANDINGPAGE_LOGIN_BTN("//div[@class='button-bar']//span[@class='text'][contains(text(),'Sign up')]"),

    //LIBRARY PAGE
    LIBRARYPAGE_FOLDERNAME_INPUT("//input[@placeholder='Name']"),
    LIBRARYPAGE_NEWFOLDER_BTN("//button[@title='New folder']"),
    LIBRARYPAGE_UPLOADFILES_BTN("//button[@title='Upload files']//span[2]//div//input"),
    LIBRARYPAGE_MYFILES_LINK("//h5[contains(text(),'My Files')]"),
    LIBRARYPAGE_SHAREDWITHME_LINK("//h5[contains(text(),'Shared with me')]"),
    LIBRARYPAGE_LIBRARY_LINK("//span[contains(text(),'Library')]"),
    LIBRARYPAGE_TERMSANDCOND_ACCEPT_BTN("//span[contains(text(),'Accept')]"),
    LIBRARYPAGE_TERMSANDCOND_DECLINE_BTN("//span[contains(text(),'Decline')]"),
    LIBRARYPAGE_TERMSANDCOND_HEADER("//h1[contains(text(),'Terms and Conditions')]"),
    LIBRARYPAGE_TERMSANDCOND_CHECKBOX("//button[@class='checkbox']"),
    LIBRARYPAGE_WELCOMESCREEN_NEXT_BTN("//button[@class='flir-icon-button is-inverted is-text first-time-use-forward']"),
    LIBRARYPAGE_WELCOMESCREEN_SKIP_BTN("//button[@class='flir-icon-button is-inverted is-text first-time-use-back']"),
    LIBRARYPAGE_NEWFOLDER_CANCEL_BTN("//div[@class='button-bar']//descendant::button[@type='button']"),
    LIBRARYPAGE_NEWFOLDER_CREATE_BTN("//div[@class='button-bar']//descendant::button[@type='submit']"),
    LIBRARYPAGE_NEWFOLDER_FOLDERNAME_ERR("//div[@class='flir-input-error']"),
    LIBRARYPAGE_USERMENU_BTN("//div[@class='user-menu']"),
    LIBRARYPAGE_USERMENU_LOGOUT_BTN("//ul[@class='flir-dropdown-list upper-right']//button[@class='sign-out'][contains(text(),'Log out')]"),

    //LOGIN PAGE
    LOGINPAGE_EMAIL_FIELD("", "logonIdentifier"),
    LOGINPAGE_PASSWORD_FIELD("", "password"),
    LOGINPAGE_SIGNIN_BTN("", "next"),
    LOGINPAGE_SIGNUP_LINK("", "createAccount"),
    LOGINPAGE_FORGOTPASS_LINK("", "forgotPassword"),
    LOGINPAGE_INVALIDEMAIL_ERR("//p[contains(text(),'Please enter a valid email address')]"),
    LOGINPAGE_INVALIDPASS_ERR("//p[contains(text(),'Please enter your password')]"),
    LOGINPAGE_INCORRECTPASS_ERR("//p[contains(text(),'Your password is incorrect.')]"),
    LOGINPAGE_NONEXISTINGACC_ERR("//p[contains(text(),'seem to find your account')]"),
    LOGINPAGE_BLANKEMAIL_ERR("//p[contains(text(),'Please enter your email')]"),

    //RECOVER PASS PAGE
    RECOVERPASSPAGE_EMAIL_FIELD("", "email"),
    RECOVERPASSPAGE_VERIFICATIONCODE_FIELD("", "email_ver_input"),
    RECOVERPASSPAGE_NEWPASSWORD_FIELD("", "newPassword"),
    RECOVERPASSPAGE_CONFIRMPASSWORD_FIELD("", "reenterPassword"),
    RECOVERPASSPAGE_SENDVERIFICATIONCODE_BTN("", "email_ver_but_send"),
    RECOVERPASSPAGE_VERIFYCODE_BTN("", "email_ver_but_verify"),
    RECOVERPASSPAGE_SENDNEWCODE_BTN("", "email_ver_but_resend"),
    RECOVERPASSPAGE_CHANGEEMAIL_BTN("", "email_ver_but_edit"),
    RECOVERPASSPAGE_CONTINUE_BTN("", "continue"),
    RECOVERPASSPAGE_CANCEL_BTN("", "cancel"),
    RECOVERPASSPAGE_INCORRECTVERCODE_ERR("", "email_fail_retry"),
    RECOVERPASSPAGE_EXPIREDVERCODE_ERR("", "email_fail_code_expired"),
    RECOVERPASSPAGE_INVALIDEMAIL_ERR("//p[contains(text(),'Please enter a valid email address.')]"),
    RECOVERPASSPAGE_TOOMANYINCORRECTATTEMPTS_ERR("", "email_fail_no_retry"),
    RECOVERPASSPAGE_REQUIREDFIELDMISSING_ERR("", "requiredFieldMissing"),
    RECOVERPASSPAGE_INVALIDPASS_ERR("//input[@id='newPassword']//preceding::p[contains(text(),'8-16 characters')]"),
    RECOVERPASSPAGE_INVALIDCONFIRMPASS_ERR("//input[@id='newPassword']//following::p[contains(text(),'8-16 characters')]"),
    RECOVERPASSPAGE_PASSWORDMISSMATCH_ERR("", "passwordEntryMismatch"),

    //SIGN UP PAGE
    SIGNUPPAGE_EMAIL_FIELD("", "email"),
    SIGNUPPAGE_VERIFICATIONCODE_FIELD("", "email_ver_input"),
    SIGNUPPAGE_NEWPASSWORD_FIELD("", "newPassword"),
    SIGNUPPAGE_CONFIRMPASSWORD_FIELD("", "reenterPassword"),
    SIGNUPPAGE_FIRSTNAME_FIELD("", "givenName"),
    SIGNUPPAGE_LASTNAME_FIELD("", "surname"),
    SIGNUPPAGE_SENDVERIFICATIONCODE_BTN("", "email_ver_but_send"),
    SIGNUPPAGE_VERIFYCODE_BTN("", "email_ver_but_verify"),
    SIGNUPPAGE_CHANGEEMAIL_BTN("", "email_ver_but_edit"),
    SIGNUPPAGE_SENDNEWCODE_BTN("", "email_ver_but_resend"),
    SIGNUPPAGE_CREATE_BTN("", "continue"),
    SIGNUPPAGE_CANCEL_BTN("", "cancel"),
    SIGNUPPAGE_COUNTRY_DROPDOWN("", "country"),
    SIGNUPPAGE_CONSENTYES_BTN("", "extension_Consent_1"),
    SIGNUPPAGE_CONSENTNO_BTN("", "extension_Consent_2"),
    SIGNUPPAGE_INCORRECTVERCODE_ERR("", "email_fail_retry"),
    SIGNUPPAGE_EXPIREDVERCODE_ERR("", "email_fail_code_expired"),
    SIGNUPPAGE_INVALIDEMAIL_ERR("//div[@id='attributeList']//*[contains(text(),'Please enter a valid email address.')]"),
    SIGNUPPAGE_INVALIDPASSWORD_ERR("//li[2]//descendant::div[1]//descendant::div[1]"),
    SIGNUPPAGE_INVALIDCONFIRMATIONPASSWORD_ERR("//li[3]//descendant::div[1]//descendant::div[1]"),
    SIGNUPPAGE_BLANKCOUNTRY_ERR("", "claimVerificationServerError"),
    SIGNUPPAGE_REQUIREDFIELD_ERR("", "requiredFieldMissing"),
    SIGNUPPAGE_TOOMANYATTEMPTS_ERR("", "email_fail_no_retry"),
    SIGNUPPAGE_PASSWORDMISSMATCH_ERR("", "passwordEntryMismatch"),
    SIGNUPPAGE_EXISTINGUSER_ERR("//div[@id='claimVerificationServerError'][contains(text(),'user with the specified ID already exists')]");

    public final String xpath;
    public final String id;
    public final String cssSelector;

//    ElementManager(String xpath, String id, String cssSelector) {
//        this.xpath = xpath;
//        this.id = id;
//        this.cssSelector = cssSelector;
//    }

    ElementManager(String xpath, String id) {
        this.xpath = xpath;
        this.id = id;
        this.cssSelector = "";
    }

    ElementManager(String xpath) {
        this.xpath = xpath;
        this.id = "";
        this.cssSelector = "";
    }

    ElementManager() {
        this.xpath = "";
        this.id = "";
        this.cssSelector = "";
    }

    }
