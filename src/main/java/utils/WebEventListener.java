/*************************************** PURPOSE **********************************
 - This class implements the WebDriverEventListener, which is included under events.
 The purpose of implementing this interface is to override all the methods and define certain useful  Log statements
 which would be displayed/logged as the application under test is being run.
 Do not call any of these methods, instead these methods will be invoked automatically
 as an when the action done (click, findBy etc).
 */

package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.io.IOException;

public class WebEventListener implements WebDriverEventListener {

    private static final Logger log = LogManager.getLogger(WebEventListener.class);

    public void beforeNavigateTo(String url, WebDriver driver) {
        log.info(" Navigating to URL: '" + url + "'");
    }

    public void afterNavigateTo(String url, WebDriver driver) {
        log.info(" Navigated to:'" + url + "'");
    }

    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        log.info(" Value of the:" + trimToMakeItReadable(element.toString()) + " before any changes made");
    }

    public void afterChangeValueOf(WebElement element, WebDriver driver) {
        log.info(" Element value changed to: " + trimToMakeItReadable(element.toString()));
    }

    public void beforeClickOn(WebElement element, WebDriver driver) {
        log.info(" Trying to click on: " + trimToMakeItReadable(element.toString()));
    }

    public void afterClickOn(WebElement element, WebDriver driver) {
        log.info(" Clicked on: " + trimToMakeItReadable(element.toString()));
    }

    public void beforeNavigateBack(WebDriver driver) {
        log.info(" Navigating back to previous page");
    }

    public void afterNavigateBack(WebDriver driver) {
        log.info(" Navigated back to previous page");
    }

    public void beforeNavigateForward(WebDriver driver) {
        log.info(" Navigating forward to next page");
    }

    public void afterNavigateForward(WebDriver driver) {
        log.info(" Navigated forward to next page");
    }

    public void onException(Throwable error, WebDriver driver) {
        log.info(" Exception occurred: " + error);
        try {
            TestUtil.getScreenshot(driver, "ScreenshotName");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        log.info(" Trying to find Element by : " + by.toString());
    }

    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        log.info(" Found Element by : " + by.toString());
    }

    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        log.info(" Entered a string to WebElement " + trimToMakeItReadable(element.toString()));
    }

    /*
     * non overridden methods of WebListener class
     */
    public void beforeScript(String script, WebDriver driver) {
    }

    public void afterScript(String script, WebDriver driver) {
    }

    public void beforeAlertAccept(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    public void afterAlertAccept(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    public void afterAlertDismiss(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    public void beforeAlertDismiss(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    public void beforeNavigateRefresh(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    public void afterNavigateRefresh(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        // TODO Auto-generated method stub

    }

    public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
        // TODO Auto-generated method stub

    }

    public void afterGetText(WebElement arg0, WebDriver arg1, String arg2) {
        // TODO Auto-generated method stub

    }

    public void afterSwitchToWindow(String arg0, WebDriver arg1) {
        // TODO Auto-generated method stub

    }

    public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
        // TODO Auto-generated method stub

    }

    public void beforeGetText(WebElement arg0, WebDriver arg1) {
        // TODO Auto-generated method stub

    }

    public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
        // TODO Auto-generated method stub
    }

    private String trimToMakeItReadable(String inputString) {
        String trimmedString;
        trimmedString = inputString.substring(inputString.lastIndexOf(">") + 1);
        trimmedString = trimmedString.substring(0,trimmedString.length()-1);
        trimmedString = trimmedString.replace("]","");
        return trimmedString;
    }
}
