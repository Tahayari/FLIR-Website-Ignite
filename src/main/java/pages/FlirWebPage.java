package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utils.ElementManager;
import utils.ExtentReport;
import utils.TestUtil;

import java.util.List;

import static base.TestBase.browser;
import static org.testng.Assert.assertTrue;
import static utils.DriverFactory.getDriver;

public class FlirWebPage {

    public void clickAction(WebElement element, String msg) {
        TestUtil.waitForElementToBeClickable(element);
        element.click();
        ExtentReport.addTestCaseStep(msg);
    }

    public void doubleClickAction(WebElement element,String msg){
        TestUtil.waitForElementToLoad(element);
        WebDriver driver = getDriver(browser);
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
        ExtentReport.addTestCaseStep(msg);
    }

    public void setField(WebElement element, String text, String msg) {
        TestUtil.waitForElementToLoad(element);
        element.clear();
        element.sendKeys(text);
        ExtentReport.addTestCaseStep(msg);
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

    public WebElement getWebElement(WebDriver driver, ElementManager element) {
        if (!element.xpath.equals(""))
            return driver.findElement(By.xpath(element.xpath));
        else if (!element.id.equals(""))
            return driver.findElement(By.id(element.id));
        else if (!element.className.equals(""))
            return driver.findElement(By.className(element.className));
        System.out.println("++++No identifier was defined");
        return null;
    }

    public List<WebElement> getDropdownElements(WebElement dropdownList){
        Select dropdown = new Select(dropdownList);
        List<WebElement> dropdownElements = dropdown.getOptions();
        return dropdownElements;
    }

    public String getSelectedElementFromDropdown(WebElement dropdownList){
        WebElement webElement;
        String selectedElement;
        Select dropdown = new Select(dropdownList);
        webElement = dropdown.getFirstSelectedOption();
        selectedElement= webElement.getText();
        return selectedElement;
    }

}
