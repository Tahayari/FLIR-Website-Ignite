package utils.testCasesInfo;

public class RecoverPassPageInfo {

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
    private String resendToken_Test_title;
    private String resendToken_Test_desc;
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
    private String cancelUpdatingPassword_Test_title;
    private String cancelUpdatingPassword_Test_desc;
    private String category;

    public RecoverPassPageInfo() {
        category = "RECOVER_PASS_PAGE";
        invalidEmail_Test_title = "RECOVER PASS PAGE - invalidEmail_Test";
        invalidEmail_Test_desc = "Error message is displayed after entering an invalid email";
        invalidToken_Test_title = "RECOVER PASS PAGE - invalidToken_Test";
        invalidToken_Test_desc = "Error message is displayed when the user enters an invalid/incorrect token";
        expiredToken_Test_title = "RECOVER PASS PAGE - expiredToken_Test";
        expiredToken_Test_desc = "Error message is displayed when the user enters an expired token";
        tooManyIncorrectAttemptsToken_Test_title = "RECOVER PASS PAGE - tooManyIncorrectAttemptsToken_Test";
        tooManyIncorrectAttemptsToken_Test_desc = "Error message is displayed if an incorrect token is submitted more than 3 times";
        sendNewCode_Test_title = "RECOVER PASS PAGE - sendNewCode_Test";
        sendNewCode_Test_desc = "Resend the token and validate the new one";
        resendToken_Test_title = "RECOVER PASS PAGE - resendEmail_Test";
        resendToken_Test_desc = "Clicking on the Send new token button will generate a new token. The old token is not valid anymore";
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
        cancelUpdatingPassword_Test_title = category+ " - cancelUpdatingPassword_Test";
        cancelUpdatingPassword_Test_desc = "Old password is still functional if the user cancels the recover password flow in the last step";
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

    public String getResendToken_Test_title() {
        return resendToken_Test_title;
    }

    public String getResendToken_Test_desc() {
        return resendToken_Test_desc;
    }

    public String getCancelRecoverPass_Test_title() {
        return cancelRecoverPass_Test_title;
    }

    public String getCancelRecoverPass_Test_desc() {
        return cancelRecoverPass_Test_desc;
    }

    public String getChangeEmail_Test_title() {
        return changeEmail_Test_title;
    }

    public String getChangeEmail_Test_desc() {
        return changeEmail_Test_desc;
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

    public String getBlankPass_Test_title() {
        return blankPass_Test_title;
    }

    public String getBlankPass_Test_desc() {
        return blankPass_Test_desc;
    }

    public String getMismatchingPasswords_Test_title() {
        return mismatchingPasswords_Test_title;
    }

    public String getMismatchingPasswords_Test_desc() {
        return mismatchingPasswords_Test_desc;
    }

    public String getChangePassword_Test_title() {
        return changePassword_Test_title;
    }

    public String getChangePassword_Test_desc() {
        return changePassword_Test_desc;
    }

    public String getCancelUpdatingPassword_Test_title() {
        return cancelUpdatingPassword_Test_title;
    }

    public String getCancelUpdatingPassword_Test_desc() {
        return cancelUpdatingPassword_Test_desc;
    }

    public String getCategory() {
        return category;
    }
}
