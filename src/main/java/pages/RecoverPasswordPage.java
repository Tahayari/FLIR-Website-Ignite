package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RecoverPasswordPage extends TestBase {
    //-------PATHS-------
    private final String emailAddressField_ID = "email";
    //--------------

    //-------Locators-------
    @FindBy(id = emailAddressField_ID)    private WebElement email_field;
    //--------------

    //Constructor
    public RecoverPasswordPage(){
        PageFactory.initElements(driver,this);
    }
    //-----------

    //-----------GETTERS
    //--------------

    //-----------SETTERS
    //--------------

    //Actions
    public String getPageTitle(){
        return driver.getTitle();
    }
    //--------------

//TODO : Rest of the elements from this page

}
