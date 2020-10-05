package utils.emailManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testData.TestData;
import utils.DriverFactory;

import java.util.ArrayList;

public class NewMailinator {
    DriverFactory driverFactory;
    WebDriver driver;
    String firstReceived_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[5]";
    String firstFrom_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[3]";
    String token_id = "BodyPlaceholder_UserVerificationEmailBodySentence2";

    public NewMailinator() {
        driverFactory = DriverFactory.getInstance();
    }

    public String getToken(String email) {
        driver = driverFactory.getDriver();
        String token;

        navigateToMailinator(driver, email);
        waitForEmail(driver);
        token = extractTokenFromEmail(driver);
        deleteEmail(driver);
        closeCurrentTab(driver);
        return token;
    }

    private void navigateToMailinator(WebDriver driver, String email) {
        String a = "window.open('','_blank');";
        String URL = "https://www.mailinator.com/v3/index.jsp?zone=public&query=" + email + "#/#inboxpane";

        ((JavascriptExecutor) driver).executeScript(a);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        driver.get(URL);
    }

    private void waitForEmail(WebDriver driver) {
        WebElement received = driver.findElement(By.xpath(firstReceived_xpath));
        WebDriverWait wait = new WebDriverWait(driver, TestData.WAIT_FOR_ELEMENT_TIMEOUT);
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.visibilityOf(received),
                        ExpectedConditions.visibilityOf(driver.findElement(By.xpath(firstReceived_xpath + "[contains(text(),'moments ago')]")))
                )
        );
    }

    private String extractTokenFromEmail(WebDriver driver) {
        String token;
        driver.findElement(By.xpath(firstFrom_xpath)).click();
        driver.switchTo().frame("msg_body"); //switched to the Email body iFrame

        WebElement tokenString = driver.findElement(By.id(token_id));
        token = tokenString.getAttribute("textContent");
        token = token.substring(token.length() - 6);
        System.out.println("----------------------------Token from Mailinator is: " + token);
        return token;
    }

    private void deleteEmail(WebDriver driver) {
        driver.switchTo().parentFrame();
        driver.findElement(By.xpath("//button[@id='trash_but']")).click();
    }

    private void closeCurrentTab(WebDriver driver) {
        String currentTab = driver.getWindowHandle();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.indexOf(currentTab) == 0) {
            driver.close();
            driver.switchTo().window(tabs.get(tabs.indexOf(currentTab) - 1));
        } else {
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }
    }
}
