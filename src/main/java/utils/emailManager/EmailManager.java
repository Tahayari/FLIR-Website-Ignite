package utils.emailManager;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.gmail.GmailPage;
import utils.TestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import static pages.gmail.GmailPage.getGmailPage;
import static setup.ReadProperties.loadProperties;
import static utils.DriverFactory.getDriver;

public class EmailManager {
    WebDriver driver = getDriver();
    public static boolean gmailReady = false;
    Properties prop;

    {
        try {
            prop = loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private EmailManager navigateToGmail(){
        String a = "window.open('','_blank');";
        String URL = "https://mail.google.com/mail/u/0/#label/FLIR";

        ((JavascriptExecutor) driver).executeScript(a);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(URL);
        return this;
    }

    private EmailManager deleteExistingEmails(){ //TODO this
        String emailBody_XPATH = "//span[contains(@id,'UserVerificationEmailBodySentence2')]";
        String email_XPATH = "//span[@class='bog']";
        String deleteEmailBTN_XPATH = "//div[@class='iH bzn']//div[@class='T-I J-J5-Ji nX T-I-ax7 T-I-Js-Gs mA']//div[@class='asa']";

        if (driver.findElements(By.xpath(email_XPATH)).size() > 0) {
            driver.findElements(By.xpath(email_XPATH)).get(0).click();
            TestUtil.waitForElementToLoad(driver.findElement(By.xpath(emailBody_XPATH)));
            driver.findElement(By.xpath(deleteEmailBTN_XPATH)).click();

            try {
                WebDriverWait wait = new WebDriverWait(driver, TestUtil.WAIT_FOR_ELEMENT_TIMEOUT);
                wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(email_XPATH), 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    private EmailManager enterGmailCredentials(){
        GmailPage gmailPage = getGmailPage();

        gmailPage.setEmail(prop.getProperty("gmail"))
                .clickOn_nextEmail_BTN()
                .setPass(prop.getProperty("password"))
                .clickOn_nextEmail_BTN();

        return this;
    }

    public void prepareGmail(){
        navigateToGmail();
        if (!gmailReady) {
            enterGmailCredentials();
            gmailReady = true;
        }
        deleteExistingEmails();
//                .navigateToFirstTab();
    }
}
