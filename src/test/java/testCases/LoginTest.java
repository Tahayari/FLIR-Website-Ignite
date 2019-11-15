package testCases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LandingPage;

public class LoginTest extends TestBase {
    LandingPage landingPage;

    public LoginTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
    }

    @Test
    public void titleTest(){
        String title = landingPage.getPageTitle();
        Assert.assertEquals(title,"FLIR Tools");
        System.out.println(landingPage.getPageTitle());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();

    }


}
