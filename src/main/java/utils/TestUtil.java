package utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import testData.TestData;
import utils.emailManager.Gmail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import static utils.DriverFactory.getDriver;

public class TestUtil {

    public static WebDriver driver = getDriver();
    public static TestData testData = new TestData();

    static Workbook book;
    static Sheet sheet;

    public static void waitForElementToLoad(WebElement webElementToWaitFor) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, TestData.WAIT_FOR_ELEMENT_TIMEOUT);
        try {
            wait.until(ExpectedConditions.visibilityOf(webElementToWaitFor));
        } catch (TimeoutException timeoutException) {
            Assert.assertTrue(webElementToWaitFor.isDisplayed(), "Element " + trimToMakeItReadable(webElementToWaitFor.toString()) + " was not loaded!");
            timeoutException.printStackTrace();
        }
    }

    public static void waitForSomeMinutes(long minutesToWait) {
        long milisecToWait = minutesToWait * 60 * 1000;
        try {
            Thread.sleep(milisecToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExtentReport.addTestCaseStep("+++++[TestUtilDebug] : Waited " + milisecToWait / 1000 + " seconds");
    }

    public static String getTokenFromGmail() {
        Gmail gmail = new Gmail(testData.getGmailEmail(), testData.getGmailPass());
        gmail.deleteAllMessages();
        gmail.waitForNewMessages();
        String token = gmail.read();
        System.out.println("+++++[TestUtilDebug] : Token is: " + token.substring(3833, 3839));
        gmail.deleteAllMessages();
        return token.substring(3833, 3839);
    }

    public static Object[][] getDataFromExcel(String filename, String sheetName) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(testData.getExcelFilePath() + "\\" + filename + ".xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert file != null;
            book = WorkbookFactory.create(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = book.getSheet(sheetName);
        Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
            }
        }
        return data;
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String current_time_str = time_formatter.format(System.currentTimeMillis());

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String destination = System.getProperty("user.dir") + "\\test-output\\screenshots\\" + screenshotName + "_"
                + current_time_str + "_" + ".jpeg";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    public static void waitForElementToBeClickable(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, TestData.WAIT_FOR_ELEMENT_TIMEOUT);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (TimeoutException timeoutException) {
            Assert.assertTrue(webElement.isEnabled(), "Element " + trimToMakeItReadable(webElement.toString()) + " is not clickable!");
            timeoutException.printStackTrace();
        }
    }

    public static String getRandomString(int stringLength) {
        if (stringLength > 0) {
            return RandomStringUtils.randomAlphanumeric(stringLength);
        } else System.out.println("Enter a number that's larger than zero");
        return null;
    }

    public static void deleteFilesFromFolder(File dir) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory())
                deleteFilesFromFolder(file);
            file.delete();
        }
    }

    private static String trimToMakeItReadable(String inputString) {
        String trimmedString;
        trimmedString = inputString.substring(inputString.lastIndexOf(">") + 1);
        trimmedString = trimmedString.substring(0,trimmedString.length()-1);
        return trimmedString;
    }

}
