package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.WebEventListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class DriverFactory {

    ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static DriverFactory instance = null;

    private DriverFactory(){

    }

    public static DriverFactory getInstance(){
        if(instance==null){
            instance = new DriverFactory();
        }
        return instance;
    }

    public final WebDriver createDriver(String browserName){
        switch(browserName.toLowerCase()){
            case "chrome":
                chromeSetup();
                break;
            case "firefox":
                firefoxSetup();
                break;
            case "edge":
                edgeSetup();
                break;
            default:
                System.out.println("++++Invalid browsername: "+browserName+".Switching to default: [CHROME]");
                chromeSetup();
                break;
        }

        driver.set(setupEventListener());
        return driver.get();
    }

    public WebDriver getDriver(){
        return driver.get();
    }

    public WebDriver quitDriver() {
        driver.get().quit();
        return null;
    }

    private void chromeSetup(){
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--start-maximized"); // https://stackoverflow.com/a/26283818/1689770
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
        options.addArguments("--disable-extensions"); // disabling extensions

        driver.set(new ChromeDriver());
    }

    private void firefoxSetup() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        driver.set(new FirefoxDriver(options));
    }

    private void edgeSetup() {
        WebDriverManager.edgedriver().setup();
        //Edge doesn't obey the driver.manage().deleteAllCookies() command like all of the other normal browsers
        // as a workaround it works if you open it in Private mode
        EdgeOptions options = new EdgeOptions();
        options.setCapability("InPrivate", true);
        driver.set(new EdgeDriver(options));
    }

    private WebDriver setupEventListener() {
        EventFiringWebDriver e_driver = new EventFiringWebDriver(driver.get());
        WebEventListener eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver.set(e_driver);
        return driver.get();
    }
}
