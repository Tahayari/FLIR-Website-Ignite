package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LibraryPage extends TestBase {

    @FindBy(xpath = "//button[@class='flir-button inverted outline has-icon create-folder']")
    public WebElement newFolder_btn;

    public LibraryPage() {
        PageFactory.initElements(driver, this);
    }



}
