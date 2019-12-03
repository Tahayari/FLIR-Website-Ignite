package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LibraryPage extends TestBase {
    //-------PATHS-------
    private final String newFolderBTN_class = "//button[@class='flir-button inverted outline has-icon create-folder']";

    private final String termsAndConditions_xpath = "//h1[contains(text(),'Terms and Conditions')]";
    //--------------


    //-------Locators-------
    @FindBy(className = newFolderBTN_class)
    @CacheLookup
    private WebElement newFolder_btn;
    @FindBy(xpath = termsAndConditions_xpath)
    private WebElement termsAndConditions;
    //--------------

    //Constructor
    public LibraryPage() {
        PageFactory.initElements(driver, this);
    }
    //--------------

    //-----------GETTERS
    public WebElement getTermsAndConditions() {
        return termsAndConditions;
    }

    public WebElement getNewFolder_btn() {
        return newFolder_btn;
    }
    //--------------

    //-----------SETTERS
    //--------------

    //Actions
    public String getPageTitle() {
        return driver.getTitle();
    }
    //--------------
}
