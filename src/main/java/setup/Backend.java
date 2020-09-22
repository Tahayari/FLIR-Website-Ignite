package setup;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Backend {

    WebDriver driver;
    JavascriptExecutor js = null;

    public Backend(WebDriver driver) {
        this.driver = driver;
    }

    public void setAPI(String webrellaURL, String ssoURL) {
        js = (JavascriptExecutor) driver;

        switch (webrellaURL.trim()) {
            case "DEV":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-develop.azurewebsites.net"));
                break;
            case "DEV-STG":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-develop-stage.azurewebsites.net"));
                break;
            case "FEATURE":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-feature.azurewebsites.net"));
                break;
            case "FEATURE-STG":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-feature-stage.azurewebsites.net"));
                break;
            case "PROD":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella.api-fs.com"));
                break;
            case "PROD-STG":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-stage.azurewebsites.net"));
                break;
            default:
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "webrella.api.url", "https://webrella-develop.azurewebsites.net"));
                break;
        }

        switch (ssoURL.trim()) {
            case "LAB":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "flir.sso.env", "flirb2clab"));
                break;
            case "PROD":
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "flir.sso.env", "flirb2cprod"));
                break;
            default:
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", "flir.sso.env", "flirb2clab"));
                break;
        }

    }

    public String getWebrellaAPI() {
//        js = (JavascriptExecutor) driver;
        return (String) js.executeScript(String.format(
                "return window.localStorage.getItem('%s');", "webrella.api.url"));
    }

    public String getSsoAPI() {
//        js = (JavascriptExecutor) driver;
        return (String) js.executeScript(String.format(
                "return window.localStorage.getItem('%s');", "flir.sso.env"));
    }
}
