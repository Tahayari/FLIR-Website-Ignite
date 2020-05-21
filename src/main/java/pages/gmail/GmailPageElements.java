//package pages.gmail;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import utils.TestUtil;
//
//import java.util.List;
//
//import static utils.DriverFactory.getDriver;
//
//public class GmailPageElements {
//    protected WebDriver driver = getDriver();
//    //-------PATHS-------
//    private final String emailField_ID = "identifierId";
//    private final String passwordField_XPATH = "//input[@name='password']";
//    private final String nextButtonEmail_ID = "identifierNext";
//    private final String nextButtonPass_ID = "passwordNext";
//    private final String avatar_XPATH = "//span[@class='gb_Ia gbii']";
//    protected final String emailBody_XPATH = "//span[contains(@id,'UserVerificationEmailBodySentence2')]";
//    protected final String email_XPATH = "//span[@class='bog']";
//    protected final String incomingEmail_XPATH = "//span[@class='y2']";
//    private final String deleteEmailBTN_XPATH = "//div[@class='iH bzn']//div[@class='T-I J-J5-Ji nX T-I-ax7 T-I-Js-Gs mA']//div[@class='asa']";
//    private final String newDeleteEmailBTN_XPATH = "(//div[@class='asa'])[3]";
//    private final String selectAllCheckBox_XPATH = "//span[@role='checkbox']";
//    //--------------
//
//
//    //-------Locators-------
//    @FindBy(id = emailField_ID)
//    private WebElement email_field;
//    @FindBy(xpath = passwordField_XPATH)
//    private WebElement password_field;
//    @FindBy(id = nextButtonEmail_ID)
//    private WebElement nextEmail_BTN;
//    @FindBy(id = nextButtonPass_ID)
//    private WebElement nextPass_BTN;
//    @FindBy(xpath = avatar_XPATH)
//    private WebElement avatar_icon;
//    @FindBy(xpath = emailBody_XPATH)
//    private WebElement emailBody;
//    @FindBy(xpath = email_XPATH)
//    private List<WebElement> listOfEmails;
//    @FindBy(xpath = deleteEmailBTN_XPATH)
//    private WebElement deleteEmail_BTN;
//    @FindBy(xpath = newDeleteEmailBTN_XPATH)
//    private WebElement newDeleteEmail_BTN;
//    @FindBy(xpath = incomingEmail_XPATH)
//    private WebElement incomingEmail;
//    @FindBy(xpath = selectAllCheckBox_XPATH)
//    private WebElement selectAll_checkbox;
//    //--------------
//
//    //Constructor
//    public GmailPageElements() {
//        PageFactory.initElements(driver, this);
//    }
//
//    //--------------
//
//    //Getters
//    public WebElement avatar_icon() {
//        return avatar_icon;
//    }
//
//    public WebElement email_field() {
//        return email_field;
//    }
//
//    public WebElement password_field() {
//        return password_field;
//    }
//
//    public WebElement nextEmail_BTN() {
//        return nextEmail_BTN;
//    }
//
//    public WebElement nextPass_BTN() {
//        return nextPass_BTN;
//    }
//
//    public WebElement emailBody() {
//        return emailBody;
//    }
//
//    public List<WebElement> listOfEmails() {
//        return listOfEmails;
//    }
//
//    public WebElement deleteEmail_BTN() {
//        return deleteEmail_BTN;
//    }
//
//    public String getEmail_XPATH() {
//        return email_XPATH;
//    }
//
//    public WebElement incomingEmail() {
//        return incomingEmail;
//    }
//
//    public WebElement selectAll_checkbox() {
//        TestUtil.waitForElementToLoad(selectAll_checkbox);
//        return selectAll_checkbox;
//    }
//
//    public WebElement newDeleteEmail_BTN() {
//        TestUtil.waitForElementToLoad(newDeleteEmail_BTN);
//        return newDeleteEmail_BTN;
//    }
//}
