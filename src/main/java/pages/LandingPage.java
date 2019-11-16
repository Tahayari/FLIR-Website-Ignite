package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends TestBase {

    @FindBy(xpath = "//span[contains(text(),'LOG IN')]")
    WebElement login_BTN;
    @FindBy(xpath = "//span[contains(text(),'SIGN UP')]")
    WebElement signup_BTN;

    //Class constructor
    public LandingPage() {
        PageFactory.initElements(driver, this); // "this" points to the current class object(s)
    }

    //Actions
    public String getPageTitle() {
        return driver.getTitle();
    }

    public LoginPage login_btn_click() {
        login_BTN.click();
        return new LoginPage();
    }
}
