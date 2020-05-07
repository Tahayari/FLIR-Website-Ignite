package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ExtentReport;
import utils.TestUtil;

import static utils.DriverFactory.getDriver;

public class LandingPage {
    private WebDriver driver;

    //-------PATHS-------
    private final String loginBTN_XPATH = "//span[contains(text(),'Log in')]";
    private final String signUpBTN_XPATH = "//div[@class='button-bar']//span[@class='text'][contains(text(),'Sign up')]";
    //--------------


    //-------Locators-------
    @FindBy(xpath = loginBTN_XPATH)
    private WebElement login_BTN;
    @FindBy(xpath = signUpBTN_XPATH)
    private WebElement signUp_BTN;
    //--------------

    //Constructor
    private LandingPage() {
        driver = getDriver();
        PageFactory.initElements(driver, this);
    }

    public static LandingPage getLandingPage() {
        return new LandingPage();
    }

    //-----------GETTERS
    public WebElement login_BTN() {
        return login_BTN;
    }

    public WebElement signUp_BTN() {
        return signUp_BTN;
    }
    //-----------


    //-----------Actions
    public void clickOn_loginBTN() {
        TestUtil.waitForElementToLoad(login_BTN);
        login_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on Login Button");
    }

    public void clickOn_signUpBTN() {
        TestUtil.waitForElementToLoad(signUp_BTN);
        signUp_BTN.click();
        ExtentReport.addTestCaseStep("Clicked on SignUp Button");
    }

    public LandingPage verifyIfPageLoaded(){
        TestUtil.waitForElementToLoad(login_BTN);
        ExtentReport.addTestCaseStep("Navigated to the Landing page");
        return this;
    }
    //-----------
}
