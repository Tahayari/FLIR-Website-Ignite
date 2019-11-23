package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage extends TestBase {

    @FindBy(xpath = "//input[@id='email']")
    WebElement email_field;
    @FindBy(xpath = "//input[@id='email_ver_input']")
    WebElement verificationCode_field;
    @FindBy(xpath = "//button[@id='email_ver_but_send']")
    WebElement sendVerCode_btn;
    @FindBy(xpath = "//input[@id='newPassword']")
    WebElement newPassword_field;
    @FindBy(xpath = "//input[@id='reenterPassword']")
    WebElement confNewPassword_field;
    @FindBy(xpath = "//input[@id='givenName']")
    WebElement firstName_field;
    @FindBy(xpath = "//input[@id='surname']")
    WebElement lastName_field;
    @FindBy(xpath = "//select[@id='country']")
    WebElement country_dropdown;
    @FindBy(xpath = "//button[@id='continue']")
    @CacheLookup
    public WebElement create_btn;
    @FindBy(xpath = "//button[@id='cancel']")
    WebElement cancel_btn;

    SignUpPage() {
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

}
