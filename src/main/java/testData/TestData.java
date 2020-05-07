package testData;

public class TestData {
    private final String randomEmail = "randomEmail@mail.com";
    private final String nameOfInvalidEmailsFile = "InvalidEmails";
    private final String nameOfFirstSheet = "Sheet1";
    private final String emailOfExistingAcc = "flirtest1@mailinator.com";
    private final String passOfExistingAcc = "QAZxsw123";
    private final String incorrectPass = "thisIsNotTheCorrectPass";

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
}
