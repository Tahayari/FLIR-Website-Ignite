//package testCases;
//
//import base.TestBase;
//import com.aventstack.extentreports.Status;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import pages.LandingPage;
//import pages.LibraryPage;
//import pages.SignUpPage;
//import utils.TestUtil;
//
//import static utils.TestUtil.getTestaData;
//
//public class SignUpPageTest extends TestBase {
//    private LandingPage landingPage;
//    private TestUtil testUtil;
//    private SignUpPage signUpPage;
//    private String sheetName = "Sheet1";
//
//    public SignUpPageTest() {
//        super();
//    }
//
//    @BeforeMethod(alwaysRun = true)
//    public void setUp() {
//        try {
//            initialization();
//            landingPage = new LandingPage();
//            testUtil = new TestUtil();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void loadLandingPage() {
//        try {
//            waitForElementToBeClickable(landingPage.signup_BTN());
//        } catch (Exception e) {
//            System.out.println("------------Page timed out. Refreshing...");
//            driver.navigate().refresh();
//            waitForElementToBeClickable(landingPage.signup_BTN());
//        }
//    }
//
//    public void goToSignUpPage() {
//
//        loadLandingPage();
//
//        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
//        addTestCaseStep("Navigated to Landing page");
//
//        signUpPage = landingPage.clickOn_signUpBTN();
//        testUtil.waitForElementToLoad(signUpPage.email_field());
//        addTestCaseStep("Navigated to Sign up page");
//    }
//
//    public void verifyInvalidEmails(Object[][] invalidEmailsList) {
//        String error_msg = "Please enter a valid email address.";
//
//        for (Object[] objects : invalidEmailsList) {
//            signUpPage.email_field().clear();
//            signUpPage.setInvalidEmail(objects[0].toString());
//            addTestCaseStep("Entered the following invalid email: " + objects[0].toString());
//            testUtil.waitForElementToLoad(signUpPage.invalidEmail_Msg());
//            checkIfCorrectErrMsg(signUpPage.invalidEmail_Msg(), error_msg);
//            addTestCaseStep("Error message is displayed: " + error_msg);
//        }
//    }
//
//    @Test(groups = {"smoke"})
//    public void title_Test() {
//        String testCaseTitle = "SIGNUP PAGE - Verify the page title";
//        String testCaseDescription = "Verify the page title";
//
//        createTestCase(testCaseTitle, testCaseDescription);
//        goToSignUpPage();
//
//        testUtil.waitForElementToLoad(driver, signUpPage.email_field());
//        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
//        addTestCaseStep("Page title is " + signUpPage.getPageTitle());
//    }
//
//    @DataProvider
//    public Object[][] getTestData() {
//        String fileName = "Accounts";
//        return TestUtil.getTestaData(fileName, sheetName);
//    }
//
//    @Test(dataProvider = "getTestData", groups = {"smoke", "regression"}, enabled = false)
//    public void registerNewAccount_Test(String email, String password, String firstName, String lastName) {
//        String testCaseTitle = "SIGNUP PAGE - registerNewAccount_Test";
//        String testCaseDescription = "Create new account(s)";
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        signUpPage.sendTokenToEmail(email + "@mailinator.com");
//        signUpPage.enterTokenFromMailinator(email);
//        signUpPage.setNewPassword(password);
//        signUpPage.setConfirmNewPassword(password);
//        signUpPage.setFirstName(firstName);
//        signUpPage.setLastName(lastName);
//        signUpPage.selectRandomCountry();
//        signUpPage.selectRandomConsent();
//        LibraryPage libraryPage = signUpPage.createNewAccount();
//        libraryPage.acceptTermsConditions();
//        libraryPage.skipWelcomeScreen();
//    }
//
//    @Test(groups = {"smoke"})
//    public void invalidEmail_Test() {
//        String testCaseTitle = "SIGNUP PAGE - invalidEmail_Test";
//        String testCaseDescription = "Error message is displayed if the users enters an email that has an invalid format";
//        String invalidEmailsFileName = "InvalidEmails";
//        Object[][] invalidEmailsList = getTestaData(invalidEmailsFileName, sheetName);
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        verifyInvalidEmails(invalidEmailsList);
//    }
//
//    @Test(groups = {"smoke"})
//    public void invalidToken_Test() {
//        String testCaseTitle = "SIGNUP PAGE - invalidToken_Test";
//        String testCaseDescription = "Error message is displayed if the user enters an invalid token";
//        String error_msg = "That code is incorrect. Please try again.";
//        String invalidToken = "000000";
//        String email = "someEmailForTesting@test.com";
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        signUpPage.sendTokenToEmail(email);
//
//        signUpPage.sendInvalidToken(invalidToken);
//
//        Assert.assertEquals(signUpPage.incorrectVerCode_Msg().getText(), error_msg);
//        addTestCaseStep("Error message is displayed: " + error_msg);
//    }
//
//    @Test(enabled = false)
//    public void expiredToken_Test() {
//        String testCaseTitle = "SIGNUP PAGE - expiredToken_Test";
//        String testCaseDescription = "Error message is displayed when the user enters an expired token";
//        String email = prop.getProperty("gmail");
//        int minutesToWait = 5; // Number of MINUTES until the token expires
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        testUtil.prepareGmail();
//
//        signUpPage.sendTokenToEmail(email);
//
//        signUpPage.waitForTokenToExpire(minutesToWait);
//
//        signUpPage.verifyIfTokenExpired();
//    }
//
//    @Test()
//    public void tooManyIncorrectAttemptsToken_Test() {
//        int timesToRetry = 3;
//        String testCaseTitle = "SIGNUP PAGE - tooManyIncorrectAttemptsToken_Test";
//        String testCaseDescription = "Error message is displayed if an incorrect token is submitted more than " + timesToRetry + " times";
//        String error_msg = "You've made too many incorrect attempts. Please try again later.";
//        String email = "throwAwayEmail@mailinator.com";
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        signUpPage.sendTokenToEmail(email);
//
//        signUpPage.enterInvalidTokenMultipleTimes(timesToRetry);
//
//        testUtil.waitForElementToLoad(signUpPage.tooManyAttempts_Msg());
//        Assert.assertEquals(signUpPage.tooManyAttempts_Msg().getText(), error_msg);
//        addTestCaseStep("Error message is displayed: " + error_msg);
//
//    }
//
//    @Test(groups = {"smoke"})
//    public void sendNewCode_Test() {
//        String testCaseTitle = "SIGNUP PAGE - sendNewCode_Test";
//        String testCaseDescription = "Resend the token and validate the new one";
//        String email = prop.getProperty("gmail");
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToSignUpPage();
//
//        testUtil.prepareGmail();
//
//        signUpPage.sendTokenToEmail(email);
//        String oldToken = testUtil.getTokenFromGmail();
//        addTestCaseStep("Saved the token received via email");
//
//        signUpPage.generateAnotherToken();
//
//        signUpPage.sendInvalidToken(oldToken);
//        addTestCaseStep("Entered the previous token and it no longer works");
//
//        signUpPage.enterTokenFromGmail();
//        extentTestChild.log(Status.PASS, "Entered the latest token received via email and it works");
//    }
//
//    @Test
//    public void resendEmail_Test() {
//        String testCaseTitle = "SIGNUP PAGE - resendEmail_Test";
//        String testCaseDescription = "Change the email after validating the token";
//        String email = prop.getProperty("gmail");
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToSignUpPage();
//        testUtil.prepareGmail();
//
//        signUpPage.sendTokenToEmail(email);
//
//        signUpPage.enterTokenFromGmail();
//
//        signUpPage.clickOn_changeEmail_BTN();
//    }
//
//    @Test
//    public void incorrectPasswordFormat_Test() {
//        String testCaseTitle = "SIGNUP PAGE - incorrectPasswordFormat_Test";
//        String testCaseDescription = "Error message is displayed if an invalid password is entered";
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToSignUpPage();
//
//        signUpPage.tryIncorrectPasswords(signUpPage.newPassword_field());
//    }
//
//    @Test
//    public void incorrectPasswordFormatConfirmPassField_Test() {
//        String testCaseTitle = "SIGNUP PAGE - incorrectPasswordFormatConfirmPassField_Test";
//        String testCaseDescription = "Error message is displayed if an invalid password is entered (Confirm password field)";
//
//        createTestCase(testCaseTitle,testCaseDescription);
//
//        goToSignUpPage();
//
//        signUpPage.tryIncorrectPasswords(signUpPage.confNewPassword_field());
//    }
//
//    @Test(groups = {"smoke"})
//    public void mismatchingPasswords_Test() {
//        String testCaseTitle = "SIGNUP PAGE - mismatchingPasswords_Test";
//        String testCaseDescription = "Error message is displayed if the passwords are not identical";
//        String email = prop.getProperty("gmail");
//        String firstName = "firstName";
//        String lastName = "lastName";
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        testUtil.prepareGmail();
//        signUpPage.sendTokenToEmail(email);
//        signUpPage.enterTokenFromGmail();
//        signUpPage.setFirstName(firstName);
//        signUpPage.setLastName(lastName);
//        signUpPage.selectRandomCountry();
//        signUpPage.selectRandomConsent();
//
//        signUpPage.enterMismatchingPasswords();
//    }
//
//    @Test
//    public void noCountrySelected_Test() {
//        String testCaseTitle = "SIGNUP PAGE - noCountrySelected_Test";
//        String testCaseDescription = "Error message is displayed if no country is selected from the dropdown list";
//        String email = prop.getProperty("gmail");
//        String pass = "Password1!";
//        String firstName = "firstName";
//        String lastName = "lastName";
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        testUtil.prepareGmail();
//        signUpPage.sendTokenToEmail(email);
//        signUpPage.enterTokenFromGmail();
//        signUpPage.setFirstName(firstName);
//        signUpPage.setLastName(lastName);
//        signUpPage.setNewPassword(pass);
//        signUpPage.setConfirmNewPassword(pass);
//        signUpPage.selectRandomConsent();
//
//        signUpPage.createUserWithoutCountry();
//    }
//
//    @Test(groups = {"smoke"})
//    public void noConsent_Test() {
//        String testCaseTitle = "SIGNUP PAGE - noConsent_Test";
//        String testCaseDescription = "Error message is displayed if no consent option is selected";
//        String email = prop.getProperty("gmail");
//        String pass = "PASSWORD123!";
//        String firstName = "firstName";
//        String lastName = "lastName";
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        testUtil.prepareGmail();
//        signUpPage.sendTokenToEmail(email);
//        signUpPage.enterTokenFromGmail();
//        signUpPage.setFirstName(firstName);
//        signUpPage.setLastName(lastName);
//        signUpPage.setNewPassword(pass);
//        signUpPage.setConfirmNewPassword(pass);
//        signUpPage.selectRandomCountry();
//
//        signUpPage.createUserWithoutMandatoryField();
//    }
//
//    @Test
//    public void noFirstName_Test() {
//        String testCaseTitle = "SIGNUP PAGE - noFirstName_Test";
//        String testCaseDescription = "Error message is displayed if the first Name field is left blank";
//        String email = prop.getProperty("gmail");
//        String pass = "PASSWORD123!";
//        String lastName = "lastName";
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        testUtil.prepareGmail();
//        signUpPage.sendTokenToEmail(email);
//        signUpPage.enterTokenFromGmail();
//        signUpPage.setLastName(lastName);
//        signUpPage.setNewPassword(pass);
//        signUpPage.setConfirmNewPassword(pass);
//        signUpPage.selectRandomCountry();
//        signUpPage.selectRandomConsent();
//
//        signUpPage.createUserWithoutMandatoryField();
//    }
//
//    @Test
//    public void noLastName_Test() {
//        String testCaseTitle = "SIGNUP PAGE - noLastName_Test";
//        String testCaseDescription = "Error message is displayed if the last Name field is left blank";
//        String email = prop.getProperty("gmail");
//        String pass = "PASSWORD123!";
//        String firstName = "firstName";
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        testUtil.prepareGmail();
//        signUpPage.sendTokenToEmail(email);
//        signUpPage.enterTokenFromGmail();
//        signUpPage.setFirstName(firstName);
//        signUpPage.setNewPassword(pass);
//        signUpPage.setConfirmNewPassword(pass);
//        signUpPage.selectRandomCountry();
//        signUpPage.selectRandomConsent();
//
//        signUpPage.createUserWithoutMandatoryField();
//    }
//
//    @Test
//    public void cancelRegistration_Test() {
//        String testCaseTitle = "SIGNUP PAGE - cancelRegistration_Test";
//        String testCaseDescription = "SignUp Flow is cancelled and user is redirected to the landing page";
//        String email = prop.getProperty("gmail");
//        String pass = "PASSWORD123!";
//        String firstName = "firstName";
//        String lastName = "lastName";
//
//        createTestCase(testCaseTitle, testCaseDescription);
//
//        goToSignUpPage();
//
//        testUtil.prepareGmail();
//        signUpPage.sendTokenToEmail(email);
//        signUpPage.enterTokenFromGmail();
//        signUpPage.setFirstName(firstName);
//        signUpPage.setLastName(lastName);
//        signUpPage.setNewPassword(pass);
//        signUpPage.setConfirmNewPassword(pass);
//        signUpPage.selectRandomCountry();
//        signUpPage.selectRandomConsent();
//
//        signUpPage.cancelRegistration();
//    }
//
//}