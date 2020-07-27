package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static utils.DriverFactory.getDriver;

public class LandingPage extends FlirWebPage {
    private final WebDriver driver;

    private final String signInBTN_XPATH = "//span[contains(text(),'Sign in')]";
    private final String signUpBTN_XPATH = "//div[@class='button-bar']//span[@class='text'][contains(text(),'Sign up')]";

    //Constructor
    private LandingPage() {
        driver = getDriver();
    }

    public static LandingPage getLandingPage() {
        return new LandingPage();
    }
    //-----------

    //-----------GETTERS
    public WebElement signIn_BTN() {
        return driver.findElement(By.xpath(signInBTN_XPATH));
    }

    public WebElement signUp_BTN() {
        return driver.findElement(By.xpath(signUpBTN_XPATH));
    }
    //-----------


    //-----------Actions
    public void clickOn_loginBTN() {
        clickAction(signIn_BTN(), "Clicked on Login Button");
    }

    public void clickOn_signUpBTN() {
        clickAction(signUp_BTN(), "Clicked on SignUp Button");
    }

    public LandingPage verifyIfPageLoaded() {
        checkIfElementHasLoaded(signIn_BTN(),"Navigated to the Landing page");
        return this;
    }
    //-----------
}
