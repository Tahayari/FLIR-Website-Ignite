package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ExtentReport;
import utils.TestUtil;

import static utils.DriverFactory.getDriver;

public class LandingPage {
    private WebDriver driver = getDriver();
    private TestUtil testUtil;

    //-------PATHS-------
    private final String loginBTN_XPATH = "//span[contains(text(),'Log in')]";
    private final String signUpBTN_XPATH = "//div[@class='button-bar']//span[@class='text'][contains(text(),'Sign up')]";
    //--------------


    //-------Locators-------
    @FindBy(xpath = loginBTN_XPATH)
    private WebElement login_BTN;
    @FindBy(xpath = signUpBTN_XPATH)
    private WebElement signup_BTN;
    //--------------

    //Constructor
    private LandingPage() {
        PageFactory.initElements(driver, this);
    }

    public static LandingPage getLandingPage(){
        return new LandingPage();
    }
    //-----------GETTERS
    public WebElement login_BTN() {
        return login_BTN;
    }

    public WebElement signup_BTN() {
        return signup_BTN;
    }
    //-----------


    //-----------SETTERS
    //-----------


    //-----------Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public LandingPage clickOn_loginBTN() {
        login_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on Login Button");
        return this;
    }

    public void clickOn_signUpBTN() {
        signup_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on SignUp Button");
    }
    //-----------
}
