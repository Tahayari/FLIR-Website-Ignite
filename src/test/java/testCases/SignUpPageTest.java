package testCases;

import base.TestBase;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LandingPage;
import pages.SignUpPage;
import utils.TestUtil;

import java.io.IOException;

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

        signUpPage = landingPage.signUp_btn_click();
        extentTestChild.log(Status.PASS,"Clicked on Sign Up button in Index page");

        testUtil.waitForElementToLoad(driver, signUpPage.create_btn);
        extentTestChild.log(Status.PASS,"Sign Up page has loaded");

        Assert.assertEquals(signUpPage.getPageTitle(), "FLIR Sign up");
        extentTestChild.log(Status.PASS,"Page title is " + signUpPage.getPageTitle());
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
