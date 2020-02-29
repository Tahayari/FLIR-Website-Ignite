package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utils.TestUtil;
import utils.WebEventListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static utils.TestUtil.WAIT_FOR_ELEMENT_TIMEOUT;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;
    public static ExtentReports extent;
    public static ExtentTest extentTest;
    public static ExtentTest extentTestChild;

    protected TestBase() {

        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/config/config.properties");
            prop.load(ip);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void initialization() {
        String browserName = prop.getProperty("browser");

        if ("chrome".equals(browserName)) {
            chromeSetup();
        } else if ("firefox".equals(browserName)) {
            firefoxSetup();
        } else if ("edge".equals(browserName)) {
            edgeSetup();
        }

        EventFiringWebDriver e_driver = new EventFiringWebDriver(driver);
        WebEventListener eventListener = new WebEventListener();
        e_driver.register(eventListener);

        try {
            driver = e_driver;
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            driver.get(prop.getProperty("url"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected static void extentInitialization() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
        htmlReporter.config().setDocumentTitle("Automation Report"); // Title of the report
        htmlReporter.config().setReportName("Automated Tests Report"); // Name of the report
        htmlReporter.config().setTheme(Theme.DARK);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Operating system name", System.getProperty("os.name"));
        extent.setSystemInfo("OS architecture", System.getProperty("os.arch").toUpperCase());
        extent.setSystemInfo("Java version", System.getProperty("java.version"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", prop.getProperty("url"));
        extent.setSystemInfo("Browser used", prop.getProperty("browser").toUpperCase());
    }

    protected static void chromeSetup() {
        WebDriverManager.chromedriver().setup();
//        set userAgent for headless mode
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36";

        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.logfile", System.getProperty("user.dir")+"\\src\\main\\java\\driverLogs\\chromedriver.log");
        System.setProperty("webdriver.chrome.verboseLogging", "true");

        if (prop.getProperty("runHeadless").toLowerCase().contains("yes")) {
            options.addArguments("--headless");
            options.addArguments("--user-agent=" + userAgent);
        }

        options.addArguments("--window-size=1920,1080");
//        options.addArguments("--start-maximized"); // https://stackoverflow.com/a/26283818/1689770
        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
//        options.addArguments("--disable-extensions"); // disabling extensions
        driver = new ChromeDriver(options);
    }

    protected static void firefoxSetup() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        if (prop.getProperty("runHeadless").toLowerCase().contains("yes")) {
            options.setHeadless(true);
        }

        driver = new FirefoxDriver(options);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

    }

    protected static void edgeSetup() {
        WebDriverManager.edgedriver().setup();
        //Edge doesn't obey the driver.manage().deleteAllCookies() command like all of the other normal browsers
        // as a workaround it works if you open it in Private mode
        EdgeOptions options = new EdgeOptions();
        options.setCapability("InPrivate", true);
        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
    }

    protected void addTestCaseStep(String testCaseStep){
        extentTestChild.log(Status.PASS, testCaseStep);
    }

    protected void createTestCaseDescription(String testCaseDescription){
        extentTestChild = extentTest.createNode(testCaseDescription);
    }

    protected void createTestCaseTitle(String testCaseTitle){
        extentTest = extent.createTest(testCaseTitle);
    }

    protected void createTestCase(String testCaseTitle,String testCaseDescription){
        createTestCaseTitle(testCaseTitle);
        createTestCaseDescription(testCaseDescription);
    }

    public void waitForElementToBeClickable(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void checkIfCorrectErrMsg(WebElement element, String error_msg){
        Assert.assertEquals(element.getText(),error_msg);
    }

    @AfterMethod(alwaysRun = true)
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

//        driver.close();
        driver.quit();
        //remove background processes; TODO: investigate if this is an optimal approach
        if (prop.getProperty("browser").toLowerCase().equals("firefox"))
            Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
        else if (prop.getProperty("browser").toLowerCase().equals("chrome"))
            Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
        else if (prop.getProperty("browser").toLowerCase().equals("edge"))
            Runtime.getRuntime().exec("taskkill /F /IM msedge.exe");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterTest(alwaysRun = true)
    public void endReport() {
        extent.flush();
    }

    @BeforeTest(alwaysRun = true)
    public void setExtent() {
        extentInitialization();
    }

}