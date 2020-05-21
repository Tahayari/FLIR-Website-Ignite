//package pages.gmail;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import testData.TestData;
//import utils.ExtentReport;
//import utils.TestUtil;
//
//public class GmailPage extends GmailPageElements{
//    private GmailPage(){
//        super();
//    }
//
//    public static GmailPage getGmailPage(){
//        return new GmailPage();
//    }
//
//    //Setters
//    public GmailPage setEmail(String email) {
//        TestUtil.waitForElementToLoad(email_field());
//        email_field().sendKeys(email);
//        return this;
//    }
//
//    public GmailPage setPass(String pass) {
//        TestUtil.waitForElementToLoad(password_field());
//        password_field().sendKeys(pass);
//        return this;
//    }
//
//    //Actions
//    public GmailPage clickOn_nextEmail_BTN() {
//        TestUtil.waitForElementToLoad(nextEmail_BTN());
//        nextEmail_BTN().click();
//        return this;
//    }
//
//    public void clickOn_nextPass_BTN() {
//        TestUtil.waitForElementToLoad(nextPass_BTN());
//        nextPass_BTN().click();
//    }
//
//    public GmailPage verifyIfPageLoaded(){
//        TestUtil.waitForElementToLoad(avatar_icon());
//        ExtentReport.addTestCaseStep("Gmail page has loaded");
//        return this;
//    }
//
//    public void waitForEmailsToBeDeleted(){
//        int number = 0 ;
//        TestData testData = new TestData();
//        WebDriverWait wait = new WebDriverWait(driver, testData.WAIT_FOR_ELEMENT_TIMEOUT);
//        TestUtil.waitForElementsToBeMoreThanZero(email_XPATH);
////        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(email_XPATH), number));
//    }
//
//    public void clickOn_firstEmail(){
//        WebElement listOfEmails = driver.findElement(By.xpath(email_XPATH));
//        System.out.println("++++++ clickOn_firstEmail(): waiting for the email to be received...");
//        TestUtil.waitForElementToLoad(listOfEmails);
//        System.out.println("++++++ clickOn_firstEmail(): received a new email...");
//        System.out.println("++++++ clickOn_firstEmail(): trying to click on the first email...");
//        listOfEmails.click();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("++++++ clickOn_firstEmail(): clicked on the first email...");
//        WebElement emailBody = driver.findElement(By.xpath(emailBody_XPATH));
//        System.out.println("++++++ clickOn_firstEmail(): will begin waiting for the email body to load...");
//        TestUtil.waitForElementToLoad(emailBody);
//        System.out.println("++++++ clickOn_firstEmail(): email body has loaded...");
//    }
//
//    public void clickOn_deleteEmail_BTN(){
//        TestUtil.waitForElementToLoad(deleteEmail_BTN());
//        deleteEmail_BTN().click();
//        TestUtil.waitForElementToLoad(avatar_icon());
//    }
//
//    public void clickOn_newDeleteEmail_BTN(){
//        newDeleteEmail_BTN().click();
//        //Check for element to NOT be visible
//    }
//
//}
