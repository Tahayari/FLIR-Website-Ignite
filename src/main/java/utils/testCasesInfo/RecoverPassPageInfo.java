package utils.testCasesInfo;

public class RecoverPassPageInfo {

    private String invalidEmail_Test_title;
    private String invalidEmail_Test_desc;
    private String blankToken_Test_title;
    private String blankToken_Test_desc;
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
    private String cancelRecoverPass_Test_title;
    private String cancelRecoverPass_Test_desc;
    private String changeEmail_Test_title;
    private String changeEmail_Test_desc;
    private String incorrectPasswordFormat_Test_title;
    private String incorrectPasswordFormat_Test_desc;
    private String incorrectPasswordFormatConfirmPassField_Test_title;
    private String incorrectPasswordFormatConfirmPassField_Test_desc;
    private String blankPass_Test_title;
    private String blankPass_Test_desc;
    private String mismatchingPasswords_Test_title;
    private String mismatchingPasswords_Test_desc;
    private String changePassword_Test_title;
    private String changePassword_Test_desc;
    private String category;

    public RecoverPassPageInfo() {
        invalidEmail_Test_title = "RECOVER PASS PAGE - invalidEmail_Test";
        invalidEmail_Test_desc = "Error message is displayed after entering an invalid email";
        blankToken_Test_title = "RECOVER PASS PAGE - blankToken_Test";
        blankToken_Test_desc = "Error message is displayed when the verification code field is left blank";
        invalidToken_Test_title = "RECOVER PASS PAGE - invalidToken_Test";
        invalidToken_Test_desc = "Error message is displayed when the user enters an invalid/incorrect token";
        expiredToken_Test_title = "RECOVER PASS PAGE - expiredToken_Test";
        expiredToken_Test_desc = "Error message is displayed when the user enters an expired token";
        tooManyIncorrectAttemptsToken_Test_title = "RECOVER PASS PAGE - tooManyIncorrectAttemptsToken_Test";
        tooManyIncorrectAttemptsToken_Test_desc = "Error message is displayed if an incorrect token is submitted more than 3 times";
        sendNewCode_Test_title = "RECOVER PASS PAGE - sendNewCode_Test";
        sendNewCode_Test_desc = "Resend the token and validate the new one";
        resendEmail_Test_title = "RECOVER PASS PAGE - resendEmail_Test";
        resendEmail_Test_desc = "Change the email after validating the token";
        cancelRecoverPass_Test_title = "RECOVER PASS PAGE - cancelRecoverPass_Test";
        cancelRecoverPass_Test_desc = "Clicking on the Cancel button redirects the user to the index page";
        changeEmail_Test_title = "RECOVER PASS PAGE - changeEmail_Test";
        changeEmail_Test_desc = "Clicking on the Change e-mail button resets the previously set email";
        incorrectPasswordFormat_Test_title = "RECOVER PASS PAGE - incorrectPasswordFormat_Test";
        incorrectPasswordFormat_Test_desc = "Error message is displayed if an invalid password is entered";
        incorrectPasswordFormatConfirmPassField_Test_title = "RECOVER PASS PAGE - incorrectPasswordFormatConfirmPassField_Test";
        incorrectPasswordFormatConfirmPassField_Test_desc = "Error message is displayed if an invalid password is entered (Confirm password field)";
        blankPass_Test_title = "RECOVER PASS PAGE - blankPass_Test";
        blankPass_Test_desc = "Error message is displayed if password field is left blank and user clicks on Continue button";
        mismatchingPasswords_Test_title = "RECOVER PASS PAGE - mismatchingPasswords_Test";
        mismatchingPasswords_Test_desc = "Error message is displayed if the passwords are not identical";
        changePassword_Test_title= "RECOVER PASS PAGE - changePassword_Test";
        changePassword_Test_desc= "Successfully change the password to an existing account";
        category = "RECOVER_PASSWORD_PAGE";
    }
}
