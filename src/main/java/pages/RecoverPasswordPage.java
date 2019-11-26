package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RecoverPasswordPage extends TestBase {

    @FindBy(id = "email")
    public WebElement email_field;

    public RecoverPasswordPage(){
        PageFactory.initElements(driver,this);
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

//TODO : Rest of the elements from this page

}
