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

        String destination = System.getProperty("user.dir") + "\\screenshots\\" + screenshotName +"_"
                            +current_time_str+ "_"+".jpeg";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    public static String getTokenFromYahoo(boolean firstLogin) {
        String a = "window.open('','_blank');";
        String URL = "https://mail.yahoo.com/d/folders/26?guce_referrer=aHR0cHM6Ly9sb2dpbi55YWhvby5jb20v&guce_referrer_sig=AQAAAKbPsmKwuDgurqBba5jUt9pBnXqR-XqE-qkSM0wgT42S4RxSkEK57d_SJ9f0ypNnhRaTdEvRq1xXQyJsuj21wLAQPKGaBYjyT3YFrBgm7Tbd_DqkoUT3r8aa6cBqfmttcSlAXo9eJhH5-Ca5T7gvd1ARtbOOA6BnYsATwt64RbWX";
        String user = "flirAutomationTest@yahoo.com";
        String pass = "Password1!";
        String email_ID = "login-username";
        String nextButton_ID = "login-signin";
        String password_ID = "login-passwd";
        String allEmails_XPATH = "//div[contains(@class,'p_R Z_0 iy_h iz_A W_6D6F H_6D6F k_w em_N c22hqzz_GN')]//ul[contains(@class,'M_0 P_0')]//li";
        String emailBody_XPATH = "//span[contains(@id,'UserVerificationEmailBodySentence2')]";
        String token;
        int WAIT_FOR_EMAIL_TIMEOUT = 120; //number of seconds before the yahoo page timeouts


        ((JavascriptExecutor) driver).executeScript(a);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_EMAIL_TIMEOUT);

        driver.switchTo().window(tabs.get(1));
        driver.get(URL);

        if(firstLogin) {
            driver.findElement(By.id(email_ID)).sendKeys(user);
            wait.until(ExpectedConditions.elementToBeClickable(By.id(nextButton_ID)));
            driver.findElement(By.id(nextButton_ID)).click();

            driver.findElement(By.id(password_ID)).sendKeys(pass);
            wait.until(ExpectedConditions.elementToBeClickable(By.id(nextButton_ID)));
            driver.findElement(By.id(nextButton_ID)).click();

            driver.close();
            driver.switchTo().window(tabs.get(0));
            return "email setup complete";
        }

        int noInitialMails = driver.findElements(By.xpath(allEmails_XPATH)).size();

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(allEmails_XPATH),noInitialMails));

        driver.findElements(By.xpath(allEmails_XPATH)).get(2).click();

        token = driver.findElement(By.xpath(emailBody_XPATH)).getText().substring(14); //get only the token;
        driver.close();
        driver.switchTo().window(tabs.get(0));
        return token;

    }

    public static String getTokenFromMailinator(String text) {
        String a = "window.open('','_blank');";  // replace link with your desired link
        String firstReceived_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[5]";
        String firstFrom_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[3]";
//        String secondReceived_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[2]//descendant::td[5]";
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
