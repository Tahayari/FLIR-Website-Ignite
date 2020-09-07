package testData;

import utils.TestUtil;

public class TestData {
    private final String randomEmail = "randomEmail@mail.com";
    private final String nameOfInvalidEmailsFile = "InvalidEmails";
    private final String nameOfFirstSheet = "Sheet1";
    private final String emailOfExistingAcc = "flirtest1@mailinator.com";
    private final String testAccountEmail = "flirtest10@mailinator.com";
    private final String smokeTestAccount = "flirsmoketest@mailinator.com";
    private final String passOfExistingAcc = "QAZxsw123";
    private final String incorrectPass = "thisIsNotTheCorrectPass";
    private final String projectPath = System.getProperty("user.dir");
    private final String excelFilePath = projectPath + "\\src\\main\\java\\testData\\";
    private final String gmailURL = "https://mail.google.com/mail/u/0/#label/FLIR" ;
    private final String gmailEmail = "flirAutomationTest@gmail.com";
    private final String gmailPass = "Pa$$word1!";
    private final String firstName = "firstName" ;
    private final String lastName = "lastName" ;
    private final String validAccountPasswd = "QAZxsw123"; //redundant, maybe remove it
    private final String invalidToken = "000000";
    private final int minForTokenToExpire = 5;
    public static long PAGE_LOAD_TIMEOUT = 20;
    public static long IMPLICIT_WAIT = 10;
    public static long WAIT_FOR_ELEMENT_TIMEOUT = 15;

    public String getRandomEmail(){ return randomEmail;}

    public String getNameOfInvalidEmailsFile() {
        return nameOfInvalidEmailsFile;
    }

    public String getNameOfFirstSheet() {
        return nameOfFirstSheet;
    }

    public String getEmailOfExistingAcc() {
        return emailOfExistingAcc;
    }

    public String getPassOfExistingAcc() {
        return passOfExistingAcc;
    }

    public String getIncorrectPass() {
        return incorrectPass;
    }

    public String getExcelFilePath() {return excelFilePath;}

    public String getGmailURL() {return gmailURL;}

    public String getGmailEmail() {
        return gmailEmail;
    }

    public String getGmailPass() {
        return gmailPass;
    }

    public String getInvalidToken(){return invalidToken;}

    public int getMinForTokenToExpire(){return minForTokenToExpire;}

    public String getValidAccountPasswd(){return validAccountPasswd;}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLongName() {
        return TestUtil.getRandomString(256);
    }

    public String getTestAccountEmail(){
        return testAccountEmail;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public String getSmokeTestAccount(){
        return smokeTestAccount;
    }
}
