package utils.testCasesInfo;

public class SignUpPageInfo {
    private String registerNewAccount_Test_title;
    private String registerNewAccount_Test_desc;
    private String invalidEmail_Test_title;
    private String invalidEmail_Test_desc;
    private String invalidToken_Test_title;
    private String invalidToken_Test_desc;
    private String expiredToken_Test_title;
    private String expiredToken_Test_desc;
    private String tooManyIncorrectAttemptsToken_Test_title;
    private String tooManyIncorrectAttemptsToken_Test_desc;
    private String sendNewCode_Test_title;
    private String sendNewCode_Test_desc;
    private String resendEmail_Test_title;
    private String resendEmail_Test_desc;
    private String incorrectPasswordFormat_Test_title;
    private String incorrectPasswordFormat_Test_desc;
    private String incorrectPasswordFormatConfirmPassField_Test_title;
    private String incorrectPasswordFormatConfirmPassField_Test_desc;
    private String mismatchingPasswords_Test_title;
    private String mismatchingPasswords_Test_desc;
    private String noCountrySelected_Test_title;
    private String noCountrySelected_Test_desc;
    private String noConsent_Test_title;
    private String noConsent_Test_desc;
    private String noFirstName_Test_title;
    private String noFirstName_Test_desc;
    private String noLastName_Test_title;
    private String noLastName_Test_desc;
    private String cancelRegistration_Test_title;
    private String cancelRegistration_Test_desc;
    private String category;


    public SignUpPageInfo() {
        registerNewAccount_Test_title = "SIGNUP PAGE - registerNewAccount_Test";
        registerNewAccount_Test_desc = "Create new account(s)";
        invalidEmail_Test_title = "SIGNUP PAGE - invalidEmail_Test";
        invalidEmail_Test_desc = "Error message is displayed if the users enters an email that has an invalid format";
        invalidToken_Test_title = "SIGNUP PAGE - invalidToken_Test";
        invalidToken_Test_desc = "Error message is displayed if the user enters an invalid token";
        expiredToken_Test_title = "SIGNUP PAGE - expiredToken_Test";
        expiredToken_Test_desc = "Error message is displayed when the user enters an expired token";
        tooManyIncorrectAttemptsToken_Test_title = "SIGNUP PAGE - tooManyIncorrectAttemptsToken_Test";
        tooManyIncorrectAttemptsToken_Test_desc = "Error message is displayed if an incorrect token is submitted more than 3 times";
        sendNewCode_Test_title = "SIGNUP PAGE - sendNewCode_Test";
        sendNewCode_Test_desc = "Resend the token and validate the new one";
        resendEmail_Test_title = "SIGNUP PAGE - resendEmail_Test";
        resendEmail_Test_desc = "Change the email after validating the token";
        incorrectPasswordFormat_Test_title = "SIGNUP PAGE - incorrectPasswordFormat_Test";
        incorrectPasswordFormat_Test_desc = "Error message is displayed if an invalid password is entered";
        incorrectPasswordFormatConfirmPassField_Test_title = "SIGNUP PAGE - incorrectPasswordFormatConfirmPassField_Test";
        incorrectPasswordFormatConfirmPassField_Test_desc = "Error message is displayed if an invalid password is entered (Confirm password field)";
        mismatchingPasswords_Test_title = "SIGNUP PAGE - mismatchingPasswords_Test";
        mismatchingPasswords_Test_desc = "Error message is displayed if the passwords are not identical";
        noCountrySelected_Test_title = "SIGNUP PAGE - noCountrySelected_Test";
        noCountrySelected_Test_desc = "Error message is displayed if no country is selected from the dropdown list";
        noConsent_Test_title = "SIGNUP PAGE - noConsent_Test";
        noConsent_Test_desc = "Error message is displayed if no consent option is selected";
        noFirstName_Test_title = "SIGNUP PAGE - noFirstName_Test";
        noFirstName_Test_desc = "Error message is displayed if the first Name field is left blank";
        noLastName_Test_title = "SIGNUP PAGE - noLastName_Test";
        noLastName_Test_desc = "Error message is displayed if the last Name field is left blank";
        cancelRegistration_Test_title = "SIGNUP PAGE - cancelRegistration_Test";
        cancelRegistration_Test_desc = "SignUp Flow is cancelled and user is redirected to the landing page";
        category = "SIGNUP_PAGE";
    }

    public String getRegisterNewAccount_Test_title() {
        return registerNewAccount_Test_title;
    }

    public String getRegisterNewAccount_Test_desc() {
        return registerNewAccount_Test_desc;
    }

    public String getInvalidEmail_Test_title() {
        return invalidEmail_Test_title;
    }

    public String getInvalidEmail_Test_desc() {
        return invalidEmail_Test_desc;
    }

    public String getInvalidToken_Test_title() {
        return invalidToken_Test_title;
    }

    public String getInvalidToken_Test_desc() {
        return invalidToken_Test_desc;
    }

    public String getExpiredToken_Test_title() {
        return expiredToken_Test_title;
    }

    public String getExpiredToken_Test_desc() {
        return expiredToken_Test_desc;
    }

    public String getTooManyIncorrectAttemptsToken_Test_title() {
        return tooManyIncorrectAttemptsToken_Test_title;
    }

    public String getTooManyIncorrectAttemptsToken_Test_desc() {
        return tooManyIncorrectAttemptsToken_Test_desc;
    }

    public String getSendNewCode_Test_title() {
        return sendNewCode_Test_title;
    }

    public String getSendNewCode_Test_desc() {
        return sendNewCode_Test_desc;
    }

    public String getResendEmail_Test_title() {
        return resendEmail_Test_title;
    }

    public String getResendEmail_Test_desc() {
        return resendEmail_Test_desc;
    }

    public String getIncorrectPasswordFormat_Test_title() {
        return incorrectPasswordFormat_Test_title;
    }

    public String getIncorrectPasswordFormat_Test_desc() {
        return incorrectPasswordFormat_Test_desc;
    }

    public String getIncorrectPasswordFormatConfirmPassField_Test_title() {
        return incorrectPasswordFormatConfirmPassField_Test_title;
    }

    public String getIncorrectPasswordFormatConfirmPassField_Test_desc() {
        return incorrectPasswordFormatConfirmPassField_Test_desc;
    }

    public String getMismatchingPasswords_Test_title() {
        return mismatchingPasswords_Test_title;
    }

    public String getMismatchingPasswords_Test_desc() {
        return mismatchingPasswords_Test_desc;
    }

    public String getNoCountrySelected_Test_title() {
        return noCountrySelected_Test_title;
    }

    public String getNoCountrySelected_Test_desc() {
        return noCountrySelected_Test_desc;
    }

    public String getNoConsent_Test_title() {
        return noConsent_Test_title;
    }

    public String getNoConsent_Test_desc() {
        return noConsent_Test_desc;
    }

    public String getNoFirstName_Test_title() {
        return noFirstName_Test_title;
    }

    public String getNoFirstName_Test_desc() {
        return noFirstName_Test_desc;
    }

    public String getNoLastName_Test_title() {
        return noLastName_Test_title;
    }

    public String getNoLastName_Test_desc() {
        return noLastName_Test_desc;
    }

    public String getCancelRegistration_Test_title() {
        return cancelRegistration_Test_title;
    }

    public String getCancelRegistration_Test_desc() {
        return cancelRegistration_Test_desc;
    }

    public String getCategory() {
        return category;
    }
}
