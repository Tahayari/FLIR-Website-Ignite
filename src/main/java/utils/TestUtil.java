package utils;

import base.TestBase;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TestUtil extends TestBase {

    public static long PAGE_LOAD_TIMEOUT = 20;
    public static long IMPLICIT_WAIT = 10;
    public static long WAIT_FOR_ELEMENT_TIMEOUT = 15;
    public static String EXCEL_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\java\\testData\\Accounts.xlsx";
    static Workbook book;
    static Sheet sheet;

    public void waitForElementToLoad(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static Object[][] getTestaData(String sheetName) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(EXCEL_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            book = WorkbookFactory.create(file);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
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

        String destination = System.getProperty("user.dir") + "\\screenshots\\" + screenshotName + ".jpeg";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    public static String getTokenFromMailinator(String text) {
        String a = "window.open('','_blank');";  // replace link with your desired link
        String firstReceived_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[5]";
        String firstFrom_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[3]";
        String secondReceived_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[2]//descendant::td[5]";
        String token_xpath = "//span[@id='BodyPlaceholder_UserVerificationEmailBodySentence2']";
        String token;

        ((JavascriptExecutor) driver).executeScript(a);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        //switches to new tab
        driver.switchTo().window(tabs.get(1));
        driver.get("https://www.mailinator.com/v3/index.jsp?zone=public&query=" + text + "#/#inboxpane");

        WebElement received = driver.findElement(By.xpath(firstReceived_xpath));
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT);
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.visibilityOf(received),
                        ExpectedConditions.visibilityOf(driver.findElement(By.xpath(firstReceived_xpath + "[contains(text(),'moments ago')]")))
                )
        );
        //-------Experimental block of code if 2 tokens are needed and are generated within a short timeframe
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        WebElement element;
//        try {
//            element = driver.findElement(By.xpath(secondReceived_xpath));
//        } catch (NoSuchElementException n) {
//            element = null;
//            n.printStackTrace();
//        }
//
//        if (element != null) {
//            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(secondReceived_xpath + "[contains(text(),'minute')]"))));
//        }
        //-------End of block code

        driver.findElement(By.xpath(firstFrom_xpath)).click();
        driver.switchTo().frame("msg_body"); //switched to the Email body iFrame

        WebElement tokenString = driver.findElement(By.xpath(token_xpath));
        token = tokenString.getText().substring(tokenString.getText().length() - 6);
        System.out.println("----------------------------Token is: " + token);

        driver.switchTo().window(tabs.get(0));
        return token;
    }

}
