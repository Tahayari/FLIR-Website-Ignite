package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.IOException;
import java.util.Properties;

import static setup.ReadProperties.loadProperties;

public class DriverFactory {

    private static WebDriver driver;
    private static Properties prop;
    private static boolean eventListAlreadyInstantiated = false;

    public static WebDriver getDriver() {
        if (driver == null) {
            try {
                prop = loadProperties();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String browserName = prop.getProperty("browser");
            if (browserName.equalsIgnoreCase("chrome")) {
                chromeSetup();
            } else if (browserName.equalsIgnoreCase("firefox")) {
                firefoxSetup();
            } else if (browserName.equalsIgnoreCase("edge")) {
                edgeSetup();
            }
        }
        if (!eventListAlreadyInstantiated) {
            setupEventListener();
            eventListAlreadyInstantiated = true ;
        }
//        setupEventListener();
        return driver;
    }

    public static WebDriver quitDriver(){
        driver.quit();
        driver = null;
        return null;
    }

    private static void chromeSetup() {
        WebDriverManager.chromedriver().setup();
//        set userAgent for headless mode
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36";

        ChromeOptions options = new ChromeOptions();
//        System.setProperty("webdriver.chrome.logfile", System.getProperty("user.dir") + "\\src\\main\\java\\driverLogs\\chromedriver.log");
//        System.setProperty("webdriver.chrome.verboseLogging", "true");

        if (prop.getProperty("runHeadless").toLowerCase().contains("yes")) {
            options.addArguments("--headless");
            options.addArguments("--user-agent=" + userAgent);
        }
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--start-maximized"); // https://stackoverflow.com/a/26283818/1689770
        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
        options.addArguments("--disable-extensions"); // disabling extensions
        driver = new ChromeDriver(options);
    }

    private static void firefoxSetup() {
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

    private static void edgeSetup() {
        WebDriverManager.edgedriver().setup();
        //Edge doesn't obey the driver.manage().deleteAllCookies() command like all of the other normal browsers
        // as a workaround it works if you open it in Private mode
        EdgeOptions options = new EdgeOptions();
        options.setCapability("InPrivate", true);
        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
    }

    private static void setupEventListener() {
        EventFiringWebDriver e_driver = new EventFiringWebDriver(driver);
        WebEventListener eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;
    }

}
