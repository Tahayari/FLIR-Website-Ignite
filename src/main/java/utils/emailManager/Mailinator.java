package utils.emailManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testData.TestData;

import java.util.ArrayList;

import static utils.DriverFactory.getDriver;

public class Mailinator {
    static WebDriver driver = getDriver();

    public static String getToken(String email){
        String firstReceived_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[5]";
        String firstFrom_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[3]";
        String token_xpath = "//span[@id='BodyPlaceholder_UserVerificationEmailBodySentence2']";
        String token;

        navigateToMailinator(email);

        WebElement received = driver.findElement(By.xpath(firstReceived_xpath));
        WebDriverWait wait = new WebDriverWait(driver, TestData.WAIT_FOR_ELEMENT_TIMEOUT);
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.visibilityOf(received),
                        ExpectedConditions.visibilityOf(driver.findElement(By.xpath(firstReceived_xpath + "[contains(text(),'ago')]")))
                )
        );

        driver.findElement(By.xpath(firstFrom_xpath)).click();
        driver.switchTo().frame("msg_body"); //switched to the Email body iFrame

        WebElement tokenString = driver.findElement(By.xpath(token_xpath));
        token = tokenString.getText().substring(tokenString.getText().length() - 6);
        System.out.println("----------------------------Token is: " + token);

        deleteEmail();
        closeCurrentTab() ;
        return token;
    }

    private static void closeCurrentTab() {
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

    private static void navigateToMailinator(String email) {
        String a = "window.open('','_blank');";
        String URL = "https://www.mailinator.com/v3/index.jsp?zone=public&query=" + email + "#/#inboxpane";

        ((JavascriptExecutor) driver).executeScript(a);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size()-1));
        driver.get(URL);
    }

    private static void deleteEmail(){
//        String emails_xpath = "//table//tbody//tr[contains(@class,'even')]";
//        List<WebElement> emails = driver.findElements(By.xpath(emails_xpath));
        driver.switchTo().parentFrame();
        driver.findElement(By.xpath("//button[@id='trash_but']")).click();
    }
}
