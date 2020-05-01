package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.Properties;

import static setup.ReadProperties.loadProperties;
import static utils.DriverFactory.getDriver;

public class ExtentReport {
    public ExtentReports extent = new ExtentReports();
    public ExtentTest extentTest;
    public ExtentTest extentTestChild;
    public Properties prop;

    public ExtentReport() throws IOException {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
        htmlReporter.config().setDocumentTitle("Automation Report"); // Title of the report
        htmlReporter.config().setReportName("Automated Tests Report"); // Name of the report
        htmlReporter.config().setTheme(Theme.DARK);
        extent.attachReporter(htmlReporter);
        extent = new ExtentReports();

        prop = loadProperties();
        extent.setSystemInfo("Operating system name", System.getProperty("os.name"));
        extent.setSystemInfo("OS architecture", System.getProperty("os.arch").toUpperCase());
        extent.setSystemInfo("Java version", System.getProperty("java.version"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", prop.getProperty("url"));
        extent.setSystemInfo("Browser used", prop.getProperty("browser").toUpperCase());
    }

    public ExtentReport addTestCaseStep(String testCaseStep) {
        extentTestChild.log(Status.PASS, testCaseStep);
        return this;
    }

    public ExtentReport createTestCaseDescription(String testCaseDescription) {
        extentTestChild = extentTest.createNode(testCaseDescription);
        return this;
    }

    public ExtentReport createTestCaseTitle(String testCaseTitle) {
        extentTest = extent.createTest(testCaseTitle);
        return this;
    }

    public void createTestCase(String testCaseTitle, String testCaseDescription) {
        createTestCaseTitle(testCaseTitle);
        createTestCaseDescription(testCaseDescription);
    }

    public void logFailure(ITestResult result) throws IOException {
        WebDriver driver = getDriver();
        extentTestChild.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
        extentTestChild.log(Status.FAIL, result.getThrowable());

        String screenshotPath = TestUtil.getScreenshot(driver, result.getName());
        System.out.println(screenshotPath);
        extentTestChild.fail("Snapshot below : ").addScreenCaptureFromPath(screenshotPath);
    }

    public void logSuccess(ITestResult result) {
        extentTestChild.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case PASSED", ExtentColor.GREEN));
    }

    public void logSkip(ITestResult result) {
        extentTestChild.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
    }

    public void endReport(){
        extent.flush();
    }

}
