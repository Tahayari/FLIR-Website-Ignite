package utils.testCasesInfo;

public class LoginPageInfo {
    private String blankPassword_Test_title;
    private String blankPassword_Test_desc;
    private String blankEmail_Test_title;
    private String blankEmail_Test_desc;
    private String invalidEmail_Test_title;
    private String invalidEmail_Test_desc;
    private String incorrectPassword_Test_title;
    private String incorrectPassword_Test_desc;
    private String loginWithNonExistingAccount_Test_title;
    private String loginWithNonExistingAccount_Test_desc;
    private String loginWithValidCredentials_Test_title;
    private String loginWithValidCredentials_Test_desc;
    private String clickSignUpLink_Test_title;
    private String clickSignUpLink_Test_desc;
    private String clickForgotPasswordLink_Test_title;
    private String clickForgotPasswordLink_Test_Test_desc;
    private String category;

    public LoginPageInfo() {
        category = "LOGIN_PAGE";
        blankPassword_Test_title = "LOGIN PAGE - blankPassword_Test";
        blankPassword_Test_desc = "Error message is displayed if an incorrect password is entered";
        blankEmail_Test_title ="LOGIN PAGE - blankEmail_Test";
        blankEmail_Test_desc = "Error message is displayed if the user logs in with a blank email field";
        invalidEmail_Test_title = "LOGIN PAGE - invalidEmail_Test";
        invalidEmail_Test_desc = "Error message is displayed if an invalid email address is entered";
        incorrectPassword_Test_title = "LOGIN PAGE - incorrectPassword_Test";
        incorrectPassword_Test_desc = "Error message is displayed if an incorrect password is entered";
        loginWithNonExistingAccount_Test_title = "LOGIN PAGE - loginWithNonExistingAccount_Test";
        loginWithNonExistingAccount_Test_desc = "Error message is displayed when logging in with a email account who doesn't have an account associated";
        loginWithValidCredentials_Test_title = "LOGIN PAGE - loginWithValidCredentials_Test";
        loginWithValidCredentials_Test_desc = "Login with valid credentials";
        clickSignUpLink_Test_title = "LOGIN PAGE - clickSignUpLink_Test";
        clickSignUpLink_Test_desc = "Clicking on the SignUp link redirects to Sign Up page";
        clickForgotPasswordLink_Test_title = "LOGIN PAGE - clickSignUpLink_Test";
        clickForgotPasswordLink_Test_Test_desc = "Clicking on the Forgot Password link redirects to Recover password page";
    }

    public String getBlankPassword_Test_title() {
        return blankPassword_Test_title;
    }

    public String getBlankPassword_Test_desc() {
        return blankPassword_Test_desc;
    }

    public String getInvalidEmail_Test_title() {
        return invalidEmail_Test_title;
    }

    public String getInvalidEmail_Test_desc() {
        return invalidEmail_Test_desc;
    }

    public String getIncorrectPassword_Test_title() {
        return incorrectPassword_Test_title;
    }

    public String getIncorrectPassword_Test_desc() {
        return incorrectPassword_Test_desc;
    }

    public String getLoginWithNonExistingAccount_Test_title() {
        return loginWithNonExistingAccount_Test_title;
    }

    public String getLoginWithNonExistingAccount_Test_desc() {
        return loginWithNonExistingAccount_Test_desc;
    }

    public String getLoginWithValidCredentials_Test_title() {
        return loginWithValidCredentials_Test_title;
    }

    public String getLoginWithValidCredentials_Test_desc() {
        return loginWithValidCredentials_Test_desc;
    }

    public String getClickSignUpLink_Test_title() {
        return clickSignUpLink_Test_title;
    }

    public String getClickSignUpLink_Test_desc() {
        return clickSignUpLink_Test_desc;
    }

    public String getClickForgotPasswordLink_Test_title() {
        return clickForgotPasswordLink_Test_title;
    }

    public String getClickForgotPasswordLink_Test_Test_desc() {
        return clickForgotPasswordLink_Test_Test_desc;
    }

    public String getCategory(){return category;}
}
