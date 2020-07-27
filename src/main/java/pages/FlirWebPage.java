package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utils.ExtentReport;
import utils.TestUtil;

import static org.testng.Assert.assertTrue;

public class FlirWebPage {

    public void clickAction(WebElement element, String msg) {
        TestUtil.waitForElementToLoad(element);
        element.click();
        ExtentReport.addTestCaseStep(msg);
    }

    public void setField(WebElement element, String text, String msg) {
        TestUtil.waitForElementToLoad(element);
        element.clear();
        element.sendKeys(text);
        ExtentReport.addTestCaseStep("Entered the following email: " + text);
    }

    public void clearWebElement(WebElement webElement) {
        TestUtil.waitForElementToLoad(webElement);
        webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        webElement.sendKeys(Keys.DELETE);
        ExtentReport.addTestCaseStep("Cleared the field");
    }

    public void waitForErrorMsgToLoad(WebElement error_Msg) {
        TestUtil.waitForElementToLoad(error_Msg);
        ExtentReport.addTestCaseStep("Error message is displayed: " + error_Msg.getText());
    }

    public void checkIfElementHasLoaded(WebElement element,String msg){
        TestUtil.waitForElementToLoad(element);
        ExtentReport.addTestCaseStep(msg);
    }

    public void assertElementIsEnabled(WebElement webElement,String msg){
        assertTrue(webElement.isEnabled());
        ExtentReport.addTestCaseStep(msg);
    }

    public void checkErrMsgIsDisplayed(WebElement error_Msg) {
        TestUtil.waitForElementToLoad(error_Msg);
        ExtentReport.addTestCaseStep("Error message is displayed: " + error_Msg.getText());
    }

}
