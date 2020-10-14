package utils.testCaseManager;

public enum TestCaseDesc {
    //******************LOGIN PAGE*********************
    LOGINPAGE_BlankPassword("Error message is displayed if a blank password is entered"),
    LOGINPAGE_BlankEmail("Error message is displayed if the user logs in with a blank email field"),
    LOGINPAGE_InvalidEmail("Error message is displayed if an invalid email address is entered"),
    LOGINPAGE_IncorrectPass("Error message is displayed if an incorrect password is entered"),
    LOGINPAGE_LoginWithNonExistingAccount("Error message is displayed when logging in with a email account " +
            "who doesn't have an account associated"),
    LOGINPAGE_LoginWithValidCredentials("Successfully login with valid credentials"),
    LOGINPAGE_ClickSignUpLink("Clicking on the SignUp link redirects to Sign Up page"),
    LOGINPAGE_ClickForgotPassLink("Clicking on the Forgot Password link redirects to Recover password page"),

    //******************SIGNUP PAGE*********************
    SIGNUPPAGE_REGISTERNEWACCOUNT("Create new account(s)"),
    SIGNUPPAGE_REGISTERNEWRANDOMACCOUNT("Create a new random account"),
    SIGNUPPAGE_INVALIDEMAIL("Error message is displayed if the users enters an email that has an invalid format"),
    SIGNUPPAGE_INVALIDTOKEN("Error message is displayed if the user enters an invalid token"),
    SIGNUPPAGE_EXPIREDTOKEN("Error message is displayed when the user enters an expired token"),
    SIGNUPPAGE_TOOMANYINCORRECTTOKENATTEMPTS("Error message is displayed if an incorrect token is submitted " +
            "more than 3 times"),
    SIGNUPPAGE_SENDNEWCODE("Resend the token and validate the new one"),
    SIGNUPPAGE_RESENDTOKEN("Clicking on the Send new token button will generate a new token. The old token " +
            "is not valid anymore"),
    SIGNUPPAGE_RESENDEMAIL("Change the email after validating the token"),
    SIGNUPPAGE_INCORRECTPASSFORMAT("Error message is displayed if an invalid password is entered"),
    SIGNUPPAGE_INCORRECTCONFIRMPASSFORMAT("Error message is displayed if an invalid password is " +
            "entered (Confirm password field)"),
    SIGNUPPAGE_MISSMATCHINGPASSWORDS("Error message is displayed if the passwords are not identical"),
    SIGNUPPAGE_NOCOUNTRYSELECTED("Error message is displayed if no country is selected from the dropdown list"),
    SIGNUPPAGE_NOCONSENT("Error message is displayed if no consent option is selected"),
    SIGNUPPAGE_NOFIRSTNAME("Error message is displayed if the first Name field is left blank"),
    SIGNUPPAGE_NOLASTNAME("Error message is displayed if the last Name field is left blank"),
    SIGNUPPAGE_CANCELREGISTRATION("SignUp Flow is cancelled and user is redirected to the landing page"),
    SIGNUPPAGE_REGISTERWITHEXISTINGMAIL("Error message is displayed when signing up with an email associated " +
            "to an existing account"),

    //******************RECOVER PASSWORD PAGE*********************
    RECOVERPASSPAGE_INVALIDEMAIL("Error message is displayed after entering an invalid email"),
    RECOVERPASSPAGE_INVALIDTOKEN("Error message is displayed when the user enters an invalid/incorrect token"),
    RECOVERPASSPAGE_EXPIREDTOKEN("Error message is displayed when the user enters an expired token"),
    RECOVERPASSPAGE_TOOMANYINCORRECTTOKENATTEMPTS("Error message is displayed if an incorrect token is " +
            "submitted more than 3 times"),
    RECOVERPASSPAGE_SENDNEWCODE("Resend the token and validate the new one"),
    RECOVERPASSPAGE_RESENDTOKEN("Clicking on the Send new token button will generate a new token. The old " +
            "token is not valid anymore"),
    RECOVERPASSPAGE_CANCELRECOVERPASS("Clicking on the Cancel button redirects the user to the index page"),
    RECOVERPASSPAGE_CHANGEEMAIL("Clicking on the Change e-mail button resets the previously set email"),
    RECOVERPASSPAGE_INCORECTPASSFORMAT("Error message is displayed if an invalid password is entered"),
    RECOVERPASSPAGE_INCORECTCONFIRMPASSFORMAT("Error message is displayed if an invalid password is entered" +
            " (Confirm password field)"),
    RECOVERPASSPAGE_BLANKPASS("Error message is displayed if password field is left blank and user clicks" +
            " on Continue button"),
    RECOVERPASSPAGE_MISSMATCHINGPASS("Error message is displayed if the passwords are not identical"),
    RECOVERPASSPAGE_CHANGEPASSWORD("Successfully change the password to an existing account"),
    RECOVERPASSPAGE_CANCELUPDATINGPASSWORD("Old password is still functional if the user cancels the " +
            "recover password flow in the last step"),

    //******************LIBRARY PAGE*********************
    LIBRARYPAGE_CREATENEWFOLDERWITHBLANKNAME("Error message is displayed when the user creates a new folder " +
            "that has a blank name"),
    LIBRARYPAGE_CREATENEWFOLDERWITHLONGNAME("Error message is displayed when the user creates a new folder " +
            "that has a more than 255 characters"),
    LIBRARYPAGE_CREATENEWFOLDER("Successfully create a new folder"),
    LIBRARYPAGE_CreateFolderWithIllegalCharacters("Error message is displayed if the name of the folder " +
            "contains an illegal character"),
    LIBRARYPAGE_CancelButtonClosesCreateNewFolderModal("Clicking on the Close button closes the create " +
            "new folder modal"),
    LIBRARYPAGE_CreateFolderWithFileSuffix("Create a new folder whose name has a file-like suffix"),
    LIBRARYPAGE_CreateFolderWhoseNameIsTakenByAnotherFolder("Create a folder whose name is already taken by " +
            "another folder"),
    LIBRARYPAGE_CreateFolderWhoseNameIsTakenByAnotherFile("Create a folder whose name is already taken by " +
            "another file"),

    SMOKETEST_TEST1("https://jiracommercial.flir.com/secure/Tests.jspa#/testCase/THAL-T477");

    public final String description;

    TestCaseDesc(String description) {
        this.description = description;
    }
}
