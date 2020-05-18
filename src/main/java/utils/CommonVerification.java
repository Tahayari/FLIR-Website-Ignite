package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import testData.TestData;

import static utils.DriverFactory.getDriver;

public class CommonVerification {
    WebDriver driver = getDriver();
    TestData testData ;

    private CommonVerification() {
        // hide it
    }

    public static CommonVerification getCommonVerification() {
        return new CommonVerification();
    }

    public CommonVerification verifyIsDisplayed(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, testData.IMPLICIT_WAIT);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        Assert.assertTrue(webElement.isDisplayed());
        return this;
    }

    public CommonVerification getTextFromElement(WebElement webElement) {
        ExtentReport.addTestCaseStep("Error message is displayed: " + webElement.getText());
        return this;
    }


    public void verifyIsNotDisplayed(WebElement webElement) {
        //TODO
    }
}
