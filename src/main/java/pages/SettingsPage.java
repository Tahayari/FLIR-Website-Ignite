package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.ElementManager;
import utils.ExtentReport;

import static base.TestBase.browser;
import static pages.LibraryPage.getLibraryPage;
import static utils.DriverFactory.getDriver;

public class SettingsPage extends FlirWebPage {
    private final WebDriver driver;

    //Constructor
    private SettingsPage() {
        driver = getDriver(browser);
    }

    public static SettingsPage getSettingsPage() {
        return new SettingsPage();
    }
    //-----------

    //-----------GETTERS
    public WebElement account_tab() {
        return getWebElement(driver, ElementManager.SETTINGSPAGE_ACCOUNTTAB);
    }

    public WebElement sharing_tab() {
        return getWebElement(driver, ElementManager.SETTINGSPAGE_SHARINGTAB);
    }

    public WebElement profile_tab() {
        return getWebElement(driver, ElementManager.SETTINGSPAGE_PROFILETAB);
    }

    public WebElement dataSecurity_tab() {
        return getWebElement(driver, ElementManager.SETTINGSPAGE_DATASECURITYTAB);
    }

    public WebElement temperatureUnits_dropdown() {
        return getWebElement(driver, ElementManager.SETTINGSPAGE_TEMPERATUREUNITS);
    }

    public WebElement distanceUnits_dropdown() {
        return getWebElement(driver, ElementManager.SETTINGSPAGE_DISTANCEUNITS);
    }

    public WebElement language_dropdown() {
        return getWebElement(driver, ElementManager.SETTINGSPAGE_LANGUAGE);
    }

    public WebElement dateFormat_dropdown() {
        return getWebElement(driver, ElementManager.SETTINGSPAGE_DATEFORMAT);
    }

    public WebElement library_link(){
        return getWebElement(driver,ElementManager.SETTINGSPAGE_LIBRARY_LINK);
    }

    //-----------

    //Actions
    public void verifyIfPageLoaded() {
        checkIfElementHasLoaded(temperatureUnits_dropdown(), "Navigated to the Settings page");
    }

    public SettingsPage setTemperature(String temp) {
        Select temperature_select = new Select(temperatureUnits_dropdown());
        switch (temp) {
            case "DEFAULT":
                temperature_select.selectByIndex(1);
                ExtentReport.addTestCaseStep("Selected " + temp + " from the temperature units dropdown list");
                break;
            case "FAHRENHEIT":
                temperature_select.selectByIndex(2);
                ExtentReport.addTestCaseStep("Selected " + temp + " from the temperature units dropdown list");
                break;
            case "CELSIUS":
                temperature_select.selectByIndex(3);
                ExtentReport.addTestCaseStep("Selected " + temp + " from the temperature units dropdown list");
                break;
            case "KELVIN":
                temperature_select.selectByIndex(4);
                ExtentReport.addTestCaseStep("Selected " + temp + " from the temperature units dropdown list");
                break;
        }
        return this;
    }

    public SettingsPage setDistance(String distance) {
        Select distance_select = new Select(distanceUnits_dropdown());
        switch (distance) {
            case "DEFAULT":
                distance_select.selectByIndex(1);
                ExtentReport.addTestCaseStep("Selected " + distance + " from the distance units dropdown list");
                break;
            case "METERS":
                distance_select.selectByIndex(2);
                ExtentReport.addTestCaseStep("Selected " + distance + " from the distance units dropdown list");
                break;
            case "FEET":
                distance_select.selectByIndex(3);
                ExtentReport.addTestCaseStep("Selected " + distance + " from the distance units dropdown list");
                break;
        }
        return this;
    }

    public SettingsPage setLanguage(String language) {
        Select language_select = new Select(language_dropdown());
        switch (language) {
            case "DEFAULT":
                language_select.selectByIndex(1);
                ExtentReport.addTestCaseStep("Selected " + language + " from the Language dropdown list");
                break;
            case "CZECH":
                language_select.selectByIndex(2);
                ExtentReport.addTestCaseStep("Selected " + language + " from the Language dropdown list");
                break;
            case "DANISH":
                language_select.selectByIndex(3);
                ExtentReport.addTestCaseStep("Selected " + language + " from the Language dropdown list");
                break;
            case "GERMAN":
                language_select.selectByIndex(4);
                ExtentReport.addTestCaseStep("Selected " + language + " from the Language dropdown list");
                break;
            case "ENGLISH":
                language_select.selectByIndex(6);
                ExtentReport.addTestCaseStep("Selected " + language + " from the Language dropdown list");
                break;
        }
        return this;
    }

    public SettingsPage setDate(String date) {
        Select date_dropdown = new Select(dateFormat_dropdown());
        switch (date) {
            case "DEFAULT":
                date_dropdown.selectByIndex(1);
                ExtentReport.addTestCaseStep("Selected " + date + " from the Date Format dropdown list");
                break;
            case "MMDDYYYY":
                date_dropdown.selectByIndex(2);
                ExtentReport.addTestCaseStep("Selected " + date + " from the Date Format dropdown list");
                break;
            case "DDMMYYYY":
                date_dropdown.selectByIndex(3);
                ExtentReport.addTestCaseStep("Selected " + date + " from the Date Format dropdown list");
                break;
        }
        return this;
    }

    public LibraryPage goToLibraryPage(){
        library_link().click();
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded();
        return libraryPage;
    }

}
