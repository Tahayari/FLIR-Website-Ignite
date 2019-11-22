package utils;

import base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil extends TestBase {

    public static long PAGE_LOAD_TIMEOUT = 20;
    public static long IMPLICIT_WAIT = 10;
    public static long WAIT_TIMEOUT = 10;


    public void waitForElementToLoad(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void takeScreenshotAtEndOfTest() throws IOException {
        SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String current_time_str = time_formatter.format(System.currentTimeMillis());

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        if (System.getProperty("os.name").contains("Windows")) {
            FileUtils.copyFile(scrFile, new File(currentDir + "\\screenshots\\" + current_time_str + ".png"));
        }
        else {
            FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + current_time_str + ".png"));
        }
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException{
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
         File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // after execution, you could see a folder "FailedTestsScreenshots"
        // under src folder
        String destination = System.getProperty("user.dir") + "/screenshots/" + screenshotName + dateName + ".jpeg";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

}
