package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LibraryPage extends TestBase {

    @FindBy(xpath = "//button[@class='flir-button inverted outline has-icon create-folder']")
    private WebElement newFolder_btn;
    @FindBy(xpath = "//h1[contains(text(),'Terms and Conditions')]")
    private WebElement termsAndConditions;


    public LibraryPage() {
        PageFactory.initElements(driver, this);
    }

    public WebElement getTermsAndConditions() {
        return termsAndConditions;
    }

    public WebElement getNewFolder_btn() {
        return newFolder_btn;
    }

    public String getPageTitle(){
        return driver.getTitle();
    }
}
