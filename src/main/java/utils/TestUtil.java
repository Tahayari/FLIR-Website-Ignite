package utils;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import testData.TestData;
import utils.emailManager.GmailInbox;

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
        wait.until(ExpectedConditions.visibilityOf(webElementToWaitFor));
        Assert.assertTrue(webElementToWaitFor.isDisplayed());
    }

    public static void waitForSomeMinutes(long minutesToWait) {
        long milisecToWait = minutesToWait * 60 * 1000;
        try {
            Thread.sleep(milisecToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExtentReport.addTestCaseStep("Waited " + milisecToWait / 1000 + " seconds");
    }

    public static String getTokenFromGmail() {
        GmailInbox gmailInbox = new GmailInbox(testData.getGmailEmail(), testData.getGmailPass());
        gmailInbox.deleteAllMessages();
        gmailInbox.waitForNewMessages();
        String token = gmailInbox.read();
        System.out.println("Token is: " + token.substring(3833, 3839));
        gmailInbox.deleteAllMessages();
        return token.substring(3833, 3839);
    }

//    public static void closeCurrentTab() {
//        String currentTab = driver.getWindowHandle();
//        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
//        if (tabs.indexOf(currentTab) == 0) {
////            driver.close();
//            driver.switchTo().window(tabs.get(tabs.indexOf(currentTab) - 1));
//        } else {
////            driver.close();
//            driver.switchTo().window(tabs.get(0));
//        }
//    }

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

//    //----Mailinator related functions
//    public void prepareMailinator(String email) {
//        navigateToMailinator(email);
//        navigateToFirstTab();
//    }

//    public void navigateToMailinator(String email) {
//        String a = "window.open('','_blank');";
//        String URL = "https://www.mailinator.com/v3/index.jsp?zone=public&query=" + email + "#/#inboxpane";
//
//        ((JavascriptExecutor) driver).executeScript(a);
//        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
//        driver.switchTo().window(tabs.get(1));
//        driver.get(URL);
//    }


//    public static String getTokenFromMailinator(String text) {
//        String firstReceived_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[5]";
//        String firstFrom_xpath = "//table[@class='table table-striped jambo_table']//tbody//descendant::tr[1]//descendant::td[3]";
//        String token_xpath = "//span[@id='BodyPlaceholder_UserVerificationEmailBodySentence2']";
//        String token;
//
//        navigateToNextTab();
//
//        WebElement received = driver.findElement(By.xpath(firstReceived_xpath));
//        WebDriverWait wait = new WebDriverWait(driver, testData.WAIT_FOR_ELEMENT_TIMEOUT);
//        wait.until(
//                ExpectedConditions.and(
//                        ExpectedConditions.visibilityOf(received),
//                        ExpectedConditions.visibilityOf(driver.findElement(By.xpath(firstReceived_xpath + "[contains(text(),'moments ago')]")))
//                )
//        );
//
//        driver.findElement(By.xpath(firstFrom_xpath)).click();
//        driver.switchTo().frame("msg_body"); //switched to the Email body iFrame
//
//        WebElement tokenString = driver.findElement(By.xpath(token_xpath));
//        token = tokenString.getText().substring(tokenString.getText().length() - 6);
//        System.out.println("----------------------------Token is: " + token);
//
//        navigateToFirstTab();
//        return token;
//    }

    //----END of Mailinator related functions

    //----Gmail related functions

//    public void prepareGmail() throws IOException {
//        navigateToGmail();
//        if (!gmailReady) {
//            enterGmailCredentials();
//            gmailReady = true;
//        }
//        deleteExistingMail()
//                .navigateToFirstTab();
//    }

//    public static String getTokenFromGmail() {
//        String token;
//        String emailBody_XPATH = "//span[contains(@id,'UserVerificationEmailBodySentence2')]";
//        String deleteEmailBTN_XPATH = "//div[@class='iH bzn']//div[@class='T-I J-J5-Ji nX T-I-ax7 T-I-Js-Gs mA']//div[@class='asa']";
//
//        navigateToNextTab();
//
//        waitForIncomingMail();
//        clickOnIncomingMail();
//
//        String emailBodyText = driver.findElement(By.xpath(emailBody_XPATH)).getText();
//        token = emailBodyText.substring(14);
//
//        driver.findElement(By.xpath(deleteEmailBTN_XPATH)).click();
//        closeCurrentTab();
//        return token;
//    }

//    public TestUtil navigateToGmail() {
//        String a = "window.open('','_blank');";
//        String URL = "https://mail.google.com/mail/u/0/#label/FLIR";
//
//        ((JavascriptExecutor) driver).executeScript(a);
//        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
//        driver.switchTo().window(tabs.get(1));
//        driver.get(URL);
//        return this;
//    }

//    public static void navigateToFirstTab() {
//        String currentTab = driver.getWindowHandle();
//        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
//        if (tabs.indexOf(currentTab) == 0) {
////            driver.close();
//            driver.switchTo().window(tabs.get(tabs.indexOf(currentTab) - 1));
//        } else {
////            driver.close();
//            driver.switchTo().window(tabs.get(0));
//        }
//    }

//    public static void navigateToNextTab() {
//        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
//        driver.switchTo().window(tabs.get(1));
//    }

    public static void waitForElementToBeClickable(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, TestData.WAIT_FOR_ELEMENT_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

//    public TestUtil enterGmailCredentials() throws IOException {
//        Properties prop = loadProperties();
//        String email = prop.getProperty("gmail");
//        String pass = prop.getProperty("password");
//        String email_ID = "identifierId";
//        String nextButtonEmail_ID = "identifierNext";
//        String passwordField_XPATH = "//input[@name='password']";
//        String nextButtonPass_ID = "passwordNext";
//        String avatar_XPATH = "//span[@class='gb_Ia gbii']";
//
//        waitForElementToBeClickable(driver.findElement(By.id(email_ID)));
//        driver.findElement(By.id(email_ID)).sendKeys(email);
//        driver.findElement(By.id(nextButtonEmail_ID)).click();
//
//        waitForElementToBeClickable(driver.findElement(By.xpath(passwordField_XPATH)));
//        driver.findElement(By.xpath(passwordField_XPATH)).sendKeys(pass);
//        driver.findElement(By.id(nextButtonPass_ID)).click();
//
//        waitForElementToLoad(driver.findElement(By.xpath(avatar_XPATH)));
//        return this;
//    }
//
//    public TestUtil deleteExistingMail() {
//        String emailBody_XPATH = "//span[contains(@id,'UserVerificationEmailBodySentence2')]";
//        String email_XPATH = "//span[@class='bog']";
//        String deleteEmailBTN_XPATH = "//div[@class='iH bzn']//div[@class='T-I J-J5-Ji nX T-I-ax7 T-I-Js-Gs mA']//div[@class='asa']";
//
//        if (driver.findElements(By.xpath(email_XPATH)).size() > 0) {
//            driver.findElements(By.xpath(email_XPATH)).get(0).click();
//            waitForElementToLoad(driver.findElement(By.xpath(emailBody_XPATH)));
//            driver.findElement(By.xpath(deleteEmailBTN_XPATH)).click();
//
//            try {
//                WebDriverWait wait = new WebDriverWait(driver, testData.WAIT_FOR_ELEMENT_TIMEOUT);
//                wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(email_XPATH), 0));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return this;
//    }
//
//    public static void waitForIncomingMail() {
//        String email_XPATH = "//span[@class='bog']";
//        WebDriverWait wait = new WebDriverWait(driver, testData.WAIT_FOR_ELEMENT_TIMEOUT);
//        try {
//            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(email_XPATH), 0));
//        } catch (Exception e) {
//            System.out.println("----Timeout error. Email did not arrive. Refreshing the page and retrying...");
//            try {
//                driver.navigate().refresh();
//                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(email_XPATH), 0));
//            } catch (TimeoutException te) {
//                System.out.println("----Timeout error. Email did not arrive in the allotted time");
//                te.printStackTrace();
//                throw new TimeoutException("----Timeout error. Email did not arrive in the allotted time");
//            }
//        }
//    }
//
//    public static void clickOnIncomingMail() {
//        String email_XPATH = "//span[@class='bog']";
//        String emailBody_XPATH = "//span[contains(@id,'UserVerificationEmailBodySentence2')]";
//        WebDriverWait wait = new WebDriverWait(driver, testData.WAIT_FOR_ELEMENT_TIMEOUT);
//
//        driver.findElements(By.xpath(email_XPATH)).get(0).click();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        WebElement element = driver.findElement(By.xpath(emailBody_XPATH));
//        wait.until(ExpectedConditions.textToBePresentInElement(element, "code"));
//    }

    //----END of Gmail related functions
}
