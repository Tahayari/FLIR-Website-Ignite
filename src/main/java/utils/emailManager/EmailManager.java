//package utils.emailManager;
//
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import pages.gmail.GmailPage;
//import testData.TestData;
//import utils.TestUtil;
//
//import java.util.ArrayList;
//
//import static pages.gmail.GmailPage.getGmailPage;
//import static utils.DriverFactory.getDriver;
//
//public class EmailManager {
//    WebDriver driver = getDriver();
//    private static boolean gmailReady = false;
//    public static TestUtil testUtil;
//    private TestData testData = new TestData();
//    GmailPage gmailPage = getGmailPage();
//
//    private EmailManager openNewTab() {
//        String a = "window.open('','_blank');";
//        ((JavascriptExecutor) driver).executeScript(a);
//        navigateToNextTab(); //switch to the latest opened tab
//        return this;
//    }
//
//    private EmailManager navigateToFirstTab() {
//        String currentTab = driver.getWindowHandle();
//        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
//        if (tabs.indexOf(currentTab) == 0) {
////            driver.close();
//            driver.switchTo().window(tabs.get(tabs.indexOf(currentTab) - 1));
//        } else {
////            driver.close();
//            driver.switchTo().window(tabs.get(0));
//        }
//        return this;
//    }
//
//    private EmailManager navigateToNextTab() {
//        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
//        driver.switchTo().window(tabs.get(tabs.size() - 1));
//        return this;
//    }
//
//    private EmailManager closeCurrentTab() {
//        String currentTab = driver.getWindowHandle();
//        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
//        if (tabs.indexOf(currentTab) == 0) {
//            driver.close();
//            driver.switchTo().window(tabs.get(tabs.indexOf(currentTab) - 1));
//        } else {
//            driver.close();
//            driver.switchTo().window(tabs.get(0));
//        }
//        return this;
//    }
//
//    private EmailManager goToURL(String URL) {
//        driver.get(URL);
//        return this;
//    }
//
//    private EmailManager navigateToGmail() {
//        openNewTab().
//                goToURL(testData.getGmailURL());
//        return this;
//    }
//
//    private void newDeleteExistingEmails() {
//        TestUtil.waitForElementToLoad(gmailPage.avatar_icon());
//        try {
//            TestUtil.waitForElementToLoad(gmailPage.incomingEmail());
//            if (gmailPage.incomingEmail().isDisplayed()) {
//                gmailPage.selectAll_checkbox().click();
//                gmailPage.clickOn_newDeleteEmail_BTN();
//            }
//        } catch (Exception e) {
//            System.out.println("Element not found because there are no emails in the inbox to delete");
//        }
//
////        System.out.println("<----> Token  =" + gmailPage.incomingEmail().getText().substring(108, 114)+"!!!");
//    }
//
//    private EmailManager deleteExistingEmails() {
//        System.out.println("++++++ deleteExistingEmails: will begin to delete existing emails...");
////        GmailPage gmailPage = getGmailPage();
//        TestUtil.waitForElementToLoad(gmailPage.avatar_icon());
//        if (gmailPage.listOfEmails().size() > 0) {
//            gmailPage.listOfEmails().get(0).click();
//            TestUtil.waitForElementToLoad(gmailPage.emailBody());
//            TestUtil.waitForElementToLoad(gmailPage.deleteEmail_BTN());
//            gmailPage.deleteEmail_BTN().click();
//
//            gmailPage.waitForEmailsToBeDeleted();
//        }
//        return this;
//    }
//
//    private EmailManager enterGmailCredentials() {
//        GmailPage gmailPage = getGmailPage();
//
//        gmailPage.setEmail(testData.getGmailEmail())
//                .clickOn_nextEmail_BTN()
//                .setPass(testData.getGmailPass())
//                .clickOn_nextPass_BTN();
//
//        return this;
//    }
//
//    public void prepareGmail() {
//        navigateToGmail();
//        if (!gmailReady) {
//            enterGmailCredentials();
//            gmailReady = true;
//        }
//        newDeleteExistingEmails();
////        deleteExistingEmails();
//        navigateToFirstTab();
//    }
//
//    public String getTokenFromGmail() {
//        navigateToNextTab()
//                .waitForIncomingMail();
////                .clickOnIncomingMail();
//        String token = getTokenFromEmailBody();
////        deleteCurrentEmail();
//        newDeleteExistingEmails();
//        closeCurrentTab();
//        return token;
//    }
//
//    private EmailManager waitForIncomingMail() {
//        TestUtil.waitForElementToLoad(gmailPage.incomingEmail());
////        TestUtil.waitForElementsToBeMoreThanZero(gmailPage.getEmail_XPATH());
//        return this;
//    }
//
//    private void clickOnIncomingMail() {
//        System.out.println("++++++clickOnIncomingMail(): trying to click on the first email...");
////        GmailPage gmailPage = getGmailPage();
//        gmailPage.clickOn_firstEmail();
//        System.out.println("++++++clickOnIncomingMail(): clicked on the first email...");
//    }
//
//    private String getTokenFromEmailBody() {
////        GmailPage gmailPage = getGmailPage();
////        System.out.println("++++getTokenFromEmailBody: getting the token from the email body...");
////        TestUtil.waitForElementToLoad(gmailPage.emailBody());
////        String token = gmailPage.emailBody().getText().substring(14);
////        System.out.println("++++getTokenFromEmailBody: successfully retrieved the token from the email body...");
//        return gmailPage.incomingEmail().getText().substring(108, 114);
//    }
//
//    private void deleteCurrentEmail() {
////        GmailPage gmailPage = getGmailPage();
//        gmailPage.clickOn_deleteEmail_BTN();
//    }
//}
