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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
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
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
//            System.setProperty("webdriver.chrome.logfile", "C:\\Users\\dhosman\\Work Folders\\Desktop\\chromedriver.log");
//            System.setProperty("webdriver.chrome.verboseLogging", "true");

            if (prop.getProperty("runHeadless").toLowerCase().contains("yes")) {
                options.addArguments("--headless");
            }

            options.addArguments("--no-sandbox");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("enable-automation");
            options.addArguments("disable-infobars"); // disabling infobars
            options.addArguments("--disable-extensions"); // disabling extensions
            options.addArguments("--disable-gpu"); // applicable to windows os only
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            driver = new ChromeDriver(options);
        } else if ("firefox".equals(browserName)) {

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

        } else if ("edge".equals(browserName)) {
            WebDriverManager.edgedriver().setup();
            //Edge doesn't obey the driver.manage().deleteAllCookies() command like all of the other normal browsers
            // as a workaround it works if you open it in Private mode
            EdgeOptions options = new EdgeOptions();
            options.setCapability("InPrivate", true);
            driver = new EdgeDriver(options);
        }

        EventFiringWebDriver e_driver = new EventFiringWebDriver(driver);
        WebEventListener eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

        driver.get(prop.getProperty("url"));

    }

    protected static void extentInitialization() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
        htmlReporter.config().setDocumentTitle("Automation Report"); // Title of the report
        htmlReporter.config().setReportName("Sanity Report or whatever"); // Name of the report
        htmlReporter.config().setTheme(Theme.DARK);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "Dan's Laptop");
        extent.setSystemInfo("User Name", "Dan Hosman");
        extent.setSystemInfo("Environment", "DEV");
        extent.setSystemInfo("Browser", prop.getProperty("browser"));
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
        driver.quit();
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