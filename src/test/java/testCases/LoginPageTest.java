package testCases;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LibraryPage;
import pages.LoginPage;

public class LoginPageTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;
    LibraryPage libraryPage;

    public LoginPageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
    }

    @Test(priority = 1)
    public void titleTest() {
        landingPage.login_btn_click();
        String title = landingPage.getPageTitle();
        Assert.assertEquals(title, "FLIR Log in");
        System.out.println(landingPage.getPageTitle());
    }

    @Test
    public void blankEmailTest() {
        loginPage = landingPage.login_btn_click();
        loginPage.email_field.clear();
        loginPage.login_btn.click();
        boolean errorMsg = driver.findElement(By.xpath("//p[contains(text(),'Please enter your email')]")).isDisplayed();
        Assert.assertTrue(errorMsg,true);
    }

    @Test (priority = 2)
    public void LoginTest(){
        loginPage = landingPage.login_btn_click();
        System.out.println("Am dat click pe butonul de Login");
        libraryPage = loginPage.login(prop.getProperty("email"),prop.getProperty("password"));
        System.out.println("Am facut logarea");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();

    }


}
