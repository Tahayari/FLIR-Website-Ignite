package utils;

import base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TestUtil extends TestBase {

    public static long PAGE_LOAD_TIMEOUT = 20;
    public static long IMPLICIT_WAIT = 10;
    public static long WAIT_TIMEOUT = 10;


    public void waitForElementToLoad(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

//    public static void takeScreenshotAtEndOfTest() throws IOException {
//        SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//        String current_time_str = time_formatter.format(System.currentTimeMillis());
//
//        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        String currentDir = System.getProperty("user.dir");
//        if (System.getProperty("os.name").contains("Windows")) {
//            FileUtils.copyFile(scrFile, new File(currentDir + "\\screenshots\\" + current_time_str + ".png"));
//        } else {
//            FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + current_time_str + ".png"));
//        }
//    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String current_time_str = time_formatter.format(System.currentTimeMillis());

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

//        String destination = System.getProperty("user.dir") + "\\screenshots\\" + screenshotName + "_" + current_time_str + ".jpeg";
        String destination = System.getProperty("user.dir") + "\\screenshots\\" + screenshotName + ".jpeg";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    public static String getTokenFromMailinator(String text) {
        String a = "window.open('','_blank');";  // replace link with your desired link
        String received_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[5]";
        String from_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[3]";
        String token_xpath = "//span[@id='BodyPlaceholder_UserVerificationEmailBodySentence2']" ;
        String token;

        ((JavascriptExecutor) driver).executeScript(a);
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        //switches to new tab
        driver.switchTo().window(tabs.get(1));
        driver.get("https://www.mailinator.com/v3/index.jsp?zone=public&query=" + text + "#/#inboxpane");

        WebElement received = driver.findElement(By.xpath(received_xpath));
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.visibilityOf(received),
                        ExpectedConditions.visibilityOf(driver.findElement(By.xpath(received_xpath + "[contains(text(),'moments ago')]")))
                )
        );

        driver.findElement(By.xpath(from_xpath)).click();
        driver.switchTo().frame("msg_body"); //switched to the Email body iFrame

        WebElement tokenString = driver.findElement(By.xpath(token_xpath));
        token = tokenString.getText().substring(tokenString.getText().length()-6);
        System.out.println("----------------------------Token is: " + token);

        driver.switchTo().window(tabs.get(0));
        return token;
    }

}
