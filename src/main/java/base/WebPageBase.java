package base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pages.elements.ElementManager;
import reports.ExtentReport;
import utils.DriverFactory;
import utils.TestUtil;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class WebPageBase {
    protected DriverFactory factory = DriverFactory.getInstance();
    protected WebDriver driver;

    public void clickAction(WebElement element, String msg) {
        TestUtil.waitForElementToBeClickable(element);
        element.click();
        ExtentReport.addTestCaseStep(msg);
    }

    public void doubleClickAction(WebElement element, String msg) {
        TestUtil.waitForElementToLoad(element);
        driver = factory.getDriver();
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

    public void checkIfElementHasLoaded(WebElement element, String msg) {
        TestUtil.waitForElementToLoad(element);
        ExtentReport.addTestCaseStep(msg);
    }

    public boolean checkIfElementIsLoaded(WebElement element) {
        try {
            if(element.isDisplayed()) return true;
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    public void assertElementIsEnabled(WebElement webElement, String msg) {
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

    public WebElement getWebElement(ElementManager element) {
        driver = factory.getDriver();
        if (!element.xpath.equals(""))
            return driver.findElement(By.xpath(element.xpath));
        else if (!element.id.equals(""))
            return driver.findElement(By.id(element.id));
        else if (!element.className.equals(""))
            return driver.findElement(By.className(element.className));
        System.out.println("++++No identifier was defined");
        return null;
    }

    public List<WebElement> getDropdownElements(WebElement dropdownList) {
        Select dropdown = new Select(dropdownList);
        return dropdown.getOptions();
    }

    public String getSelectedElementFromDropdown(WebElement dropdownList) {
        WebElement webElement;
        String selectedElement;
        Select dropdown = new Select(dropdownList);
        webElement = dropdown.getFirstSelectedOption();
        selectedElement = webElement.getText();
        return selectedElement;
    }

}
