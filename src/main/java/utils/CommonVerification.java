package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static utils.DriverFactory.getDriver;

public class CommonVerification {
    protected WebDriver driver = getDriver();

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

    public void verifyIsNotDisplayed(WebElement webElement) {
        //TODO
    }
}
