package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import utils.TestUtil;
import utils.WebEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;
    public static EventFiringWebDriver e_driver;
    public static WebEventListener eventListener;
    public static ExtentReports extent;
    public ExtentTest extentTest;

    public TestBase() {

        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/config/config.properties");
            prop.load(ip);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialization() {

        String browserName;
        browserName = prop.getProperty("browser");
//
//        OLD SETUP
//        if (browserName.equals("chrome")) {
//            //System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/drivers/Chrome/78/chromedriver.exe"); // atentie la versiune !!
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//        } else if (browserName.equals("firefox")) {
//            //System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/java/drivers/Firefox/geckodriver.exe");
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//        }

        if ("chrome".equals(browserName)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if ("firefox".equals(browserName)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if ("edge".equals(browserName)) {

            WebDriverManager.edgedriver().setup();
            //Edge doesn't obey the driver.manage().deleteAllCookies() command like all of the other normal browsers
            // as a workaround it works if you open it in Private mode
            EdgeOptions options = new EdgeOptions();
            options.setCapability("InPrivate", true);
            driver = new EdgeDriver(options);
        }

        e_driver = new EventFiringWebDriver(driver);
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

        driver.get(prop.getProperty("url"));

    }

}