package testCases;

import base.TestBase;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.SignUpPage;
import utils.TestUtil;

import java.io.IOException;
import java.util.ArrayList;

public class SignUpPageTest extends TestBase {
    private LandingPage landingPage;
    private TestUtil testUtil;
    private SignUpPage signUpPage;


    public SignUpPageTest() {
        super();
    }

    @BeforeTest
    public void setExtent() {
        extentInitialization();
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        landingPage = new LandingPage();
        testUtil = new TestUtil();
    }

    @Test
    public void title_Test() {
        extentTest = extent.createTest("SIGN UP PAGE - title_test");
        extentTestChild = extentTest.createNode("Verify the title of the page");

        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Navigated to landing page");

        signUpPage = landingPage.signUp_btn_click();
        extentTestChild.log(Status.PASS, "Clicked on Sign Up button in Index page");

        testUtil.waitForElementToLoad(driver, signUpPage.getCreate_BTN());
        extentTestChild.log(Status.PASS, "Sign Up page has loaded");

        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
        extentTestChild.log(Status.PASS, "Page title is " + signUpPage.getPageTitle());
    }

    @Test
    public void registerNewAccount_test() {
        extentTest = extent.createTest("SIGN UP PAGE - create a new user");
        extentTestChild = extentTest.createNode("Create a new user");

        Assert.assertEquals(landingPage.getPageTitle(), "FLIR Tools");
        extentTestChild.log(Status.PASS, "Navigated to landing page");

        signUpPage = landingPage.signUp_btn_click();
        extentTestChild.log(Status.PASS, "Clicked on Sign Up button in Index page");

        testUtil.waitForElementToLoad(driver, signUpPage.getCreate_BTN());
        extentTestChild.log(Status.PASS, "Sign Up page has loaded");

        signUpPage.setEmailAddress("testaaaaaa@mailinator.com");
        signUpPage.getSendVerCode_BTN().click();
        testUtil.waitForElementToLoad(driver,signUpPage.getVerificationCode_field());

        //TODO: open new tab with mailinator and get the token. TBD in Utils class
        String a = "window.open('','_blank');";  // replace link with your desired link
        ((JavascriptExecutor)driver).executeScript(a);

        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1)); //switches to new tab
        driver.get("https://www.mailinator.com/v3/index.jsp?zone=public&query=dantest#/#msgpane");



        signUpPage.getVerificationCode_field().sendKeys("123456"); // get the code from the Utils class
        signUpPage.getVerifyCode_BTN().click();
        signUpPage.setNewPassword("QAZxsw123");
        signUpPage.setConfirmNewPassword("QAZxsw123");
        signUpPage.setFirstName("FirstName");
        signUpPage.setLastName("LastName");
        //TODO: select one of the countries from the dropdown list
        //TODO: select one of the two consent options

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        signUpPage.getCreate_BTN().click();


    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            extentTestChild.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            extentTestChild.log(Status.FAIL, result.getThrowable());

            String screenshotPath = TestUtil.getScreenshot(driver, result.getName());
            System.out.println(screenshotPath);
            extentTestChild.fail("Snapshot below : ").addScreenCaptureFromPath(screenshotPath);

        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTestChild.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTestChild.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case PASSED", ExtentColor.GREEN));
        }

        driver.quit();
    }
}
