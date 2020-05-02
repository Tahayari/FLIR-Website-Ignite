package utils;

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
import java.util.Properties;

import static setup.ReadProperties.loadProperties;
import static utils.DriverFactory.getDriver;

public class TestUtil{

    public static long PAGE_LOAD_TIMEOUT = 20;
    public static long IMPLICIT_WAIT = 10;
    public static long WAIT_FOR_ELEMENT_TIMEOUT = 15;
    public static String EXCEL_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\java\\testData\\";
    public static WebDriver driver = getDriver();

    static Workbook book;
    static Sheet sheet;


    public static void waitForElementToLoad(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitForElementToLoad(WebElement webElementToWaitFor) {
        waitForElementToLoad(driver, webElementToWaitFor);
    }

    public static void waiForElementToBeClickable(WebElement webElement){
        try {
            WebDriverWait wait = new WebDriverWait(driver, IMPLICIT_WAIT);
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object[][] getTestaData(String filename, String sheetName) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(EXCEL_FILE_PATH + "\\" + filename + ".xlsx");
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

        String destination = System.getProperty("user.dir") + "\\test-output\\screenshots\\" + screenshotName + "_"
                + current_time_str + "_" + ".jpeg";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    //back-up solution IF google is not working/viable
    public String getTokenFromYahoo(boolean firstLogin) {
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

        if (firstLogin) {
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

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(allEmails_XPATH), noInitialMails));

        driver.findElements(By.xpath(allEmails_XPATH)).get(2).click();

        token = driver.findElement(By.xpath(emailBody_XPATH)).getText().substring(14); //get only the token;
        driver.close();
        driver.switchTo().window(tabs.get(0));
        return token;
    }

    //----Mailinator related functions
    public void prepareMailinator(String email) {
        navigateToMailinator(email);
        navigateToPreviousTab();
    }

    public void navigateToMailinator(String email) {
        String a = "window.open('','_blank');";
        String URL = "https://www.mailinator.com/v3/index.jsp?zone=public&query=" + email + "#/#inboxpane";

        ((JavascriptExecutor) driver).executeScript(a);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(URL);
        //TODO: see if a wait is needed here
    }

    public void enterTokenFromMailinator() {

    }


    public String getTokenFromMailinator(String text) {
        String firstReceived_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[5]";
        String firstFrom_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[3]";
        String token_xpath = "//span[@id='BodyPlaceholder_UserVerificationEmailBodySentence2']";
        String token;

        navigateToNextTab();

        WebElement received = driver.findElement(By.xpath(firstReceived_xpath));
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT);
        wait.until(
                ExpectedConditions.and(
                        ExpectedConditions.visibilityOf(received),
                        ExpectedConditions.visibilityOf(driver.findElement(By.xpath(firstReceived_xpath + "[contains(text(),'moments ago')]")))
                )
        );

        driver.findElement(By.xpath(firstFrom_xpath)).click();
        driver.switchTo().frame("msg_body"); //switched to the Email body iFrame

        WebElement tokenString = driver.findElement(By.xpath(token_xpath));
        token = tokenString.getText().substring(tokenString.getText().length() - 6);
        System.out.println("----------------------------Token is: " + token);

        navigateToPreviousTab();
        return token;
    }

    //----END of Mailinator related functions

    //----Gmail related functions

    public void prepareGmail() throws IOException {
        navigateToGmail();

        enterGmailCredentials();

        deleteExistingMail();

        navigateToPreviousTab();
    }

    public String getTokenFromGmail() {
        String token;
        String emailBody_XPATH = "//span[contains(@id,'UserVerificationEmailBodySentence2')]";
        String deleteEmailBTN_XPATH = "//div[@class='iH bzn']//div[@class='T-I J-J5-Ji nX T-I-ax7 T-I-Js-Gs mA']//div[@class='asa']";

        navigateToNextTab();

        waitForIncomingMail();
        clickOnIncomingMail();

        String emailBodyText = driver.findElement(By.xpath(emailBody_XPATH)).getText();
        token = emailBodyText.substring(14);

        driver.findElement(By.xpath(deleteEmailBTN_XPATH)).click();

        navigateToPreviousTab();
        return token;
    }

    public void navigateToGmail() {
        String a = "window.open('','_blank');";
        String URL = "https://mail.google.com/mail/u/0/#label/FLIR";

        ((JavascriptExecutor) driver).executeScript(a);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(URL);
    }

    public void navigateToPreviousTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    public void navigateToNextTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public static void waitForElementToBeClickable(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void enterGmailCredentials() throws IOException {
        Properties prop;
        prop = loadProperties();
        String email = prop.getProperty("gmail");
        String pass = prop.getProperty("password");
        String email_ID = "identifierId";
        String nextButtonEmail_ID = "identifierNext";
        String passwordField_XPATH = "//input[@name='password']";
        String nextButtonPass_ID = "passwordNext";
        String avatar_XPATH = "//span[@class='gb_Ia gbii']";


        waitForElementToBeClickable(driver.findElement(By.id(email_ID)));
        driver.findElement(By.id(email_ID)).sendKeys(email);
        driver.findElement(By.id(nextButtonEmail_ID)).click();

        waitForElementToBeClickable(driver.findElement(By.xpath(passwordField_XPATH)));
        driver.findElement(By.xpath(passwordField_XPATH)).sendKeys(pass);
        driver.findElement(By.id(nextButtonPass_ID)).click();

        waitForElementToLoad(driver.findElement(By.xpath(avatar_XPATH)));
    }

    public void deleteExistingMail() {
        String emailBody_XPATH = "//span[contains(@id,'UserVerificationEmailBodySentence2')]";
        String email_XPATH = "//span[@class='bog']";
        String deleteEmailBTN_XPATH = "//div[@class='iH bzn']//div[@class='T-I J-J5-Ji nX T-I-ax7 T-I-Js-Gs mA']//div[@class='asa']";

        if (driver.findElements(By.xpath(email_XPATH)).size() > 0) {
            driver.findElements(By.xpath(email_XPATH)).get(0).click();
            waitForElementToLoad(driver.findElement(By.xpath(emailBody_XPATH)));
            driver.findElement(By.xpath(deleteEmailBTN_XPATH)).click();

            try {
                WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT);
                wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(email_XPATH), 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void waitForIncomingMail() {
        String email_XPATH = "//span[@class='bog']";
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT);
        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(email_XPATH), 0));
        } catch (Exception e) {
            System.out.println("----Timeout error. Email did not arrive. Refreshing the page and retrying...");
            try {
                driver.navigate().refresh();
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(email_XPATH), 0));
            } catch (TimeoutException te) {
                System.out.println("----Timeout error. Email did not arrive in the allotted time");
                te.printStackTrace();
                throw new TimeoutException("----Timeout error. Email did not arrive in the allotted time");
            }
        }
    }

    public void clickOnIncomingMail() {
        String email_XPATH = "//span[@class='bog']";
        String emailBody_XPATH = "//span[contains(@id,'UserVerificationEmailBodySentence2')]";
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT);

        driver.findElements(By.xpath(email_XPATH)).get(0).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement element = driver.findElement(By.xpath(emailBody_XPATH));
        wait.until(ExpectedConditions.textToBePresentInElement(element, "code"));
    }
//----END of Gmail related functions

}
