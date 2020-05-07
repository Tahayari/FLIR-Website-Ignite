package utils;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.LibraryPage;
import pages.LoginPage;
import pages.RecoverPasswordPage;
import pages.SignUpPage;
import pages.gmail.GmailPage;

import static pages.gmail.GmailPage.getGmailPage;
import static utils.DriverFactory.getDriver;

public class CommonVerification {
    WebDriver driver = getDriver();

    private CommonVerification() {
        // hide it
    }

    public static CommonVerification getCommonVerification() {
        return new CommonVerification();
    }

    public CommonVerification verifyIsDisplayed(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, TestUtil.IMPLICIT_WAIT);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        Assert.assertTrue(webElement.isDisplayed());
        return this;
    }

    public CommonVerification getTextFromElement(WebElement webElement) {
        ExtentReport.addTestCaseStep("Error message is displayed: " + webElement.getText());
        return this;
    }

    //need to refactor this type of method
    public void verifyLoginPageLoaded(LoginPage loginPage) {
        WebElement webElement = loginPage.signIn_BTN();
        WebDriverWait wait = new WebDriverWait(driver, TestUtil.IMPLICIT_WAIT);
        try {
            wait.until(ExpectedConditions.visibilityOf(webElement));
            ExtentReport.addTestCaseStep("Login Page has loaded");

        } catch (TimeoutException e) {
            ExtentReport.extentTestChild.log(Status.FAIL, "Failed to load Login Page");
            Assert.assertEquals(e.toString(), "Page has loaded");
        }
    }

    //need to refactor this type of method
    public void verifySignUpPageLoaded(SignUpPage signUpPage) {
        WebElement webElement = signUpPage.email_field();
        WebDriverWait wait = new WebDriverWait(driver, TestUtil.IMPLICIT_WAIT);
        try {
            wait.until(ExpectedConditions.visibilityOf(webElement));
            ExtentReport.addTestCaseStep("SignUp Page has loaded");

        } catch (TimeoutException e) {
            ExtentReport.extentTestChild.log(Status.FAIL, "Failed to load SignUp Page");
            Assert.assertEquals(e.toString(), "Page has loaded");
        }
    }

    //need to refactor this type of method
    public void verifyRecoverPassPageLoaded(RecoverPasswordPage recoverPasswordPage) {
        WebElement webElement = recoverPasswordPage.email_field();
        WebDriverWait wait = new WebDriverWait(driver, TestUtil.IMPLICIT_WAIT);
        try {
            wait.until(ExpectedConditions.visibilityOf(webElement));
            ExtentReport.addTestCaseStep("Recover Password Page has loaded");

        } catch (TimeoutException e) {
            ExtentReport.extentTestChild.log(Status.FAIL, "Failed to load Recover Password Page");
            Assert.assertEquals(e.toString(), "Page has loaded");
        }
    }

    //need to refactor this type of method
    public void verifyLibraryPageLoaded(LibraryPage libraryPage) {
        WebElement webElement = libraryPage.newFolder_BTN();
        WebDriverWait wait = new WebDriverWait(driver, TestUtil.IMPLICIT_WAIT);
        try {
            wait.until(ExpectedConditions.visibilityOf(webElement));
            ExtentReport.addTestCaseStep("Library Page has loaded");

        } catch (TimeoutException e) {
            ExtentReport.extentTestChild.log(Status.FAIL, "Failed to load Library Page");
            Assert.assertEquals(e.toString(), "Page has loaded");
        }
    }

    public void verifyGmailPageLoaded(GmailPage gmailPage) {
        gmailPage = getGmailPage();
        WebDriverWait wait = new WebDriverWait(driver, TestUtil.IMPLICIT_WAIT);
        try {
            wait.until(ExpectedConditions.visibilityOf(gmailPage.avatar_icon()));
            ExtentReport.addTestCaseStep("Gmail Page has loaded");

        } catch (TimeoutException e) {
            ExtentReport.extentTestChild.log(Status.FAIL, "Failed to load Gmail Page");
            Assert.assertEquals(e.toString(), "Page has loaded");
        }
    }

    public void verifyIsNotDisplayed(WebElement webElement) {
        //TODO
    }
}
