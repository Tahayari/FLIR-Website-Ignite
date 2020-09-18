package testCases;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.ExtentReport;
import utils.TestUtil;
import utils.testCaseManager.TestCaseCategory;
import utils.testCaseManager.TestCaseHeader;

import java.util.List;

import static pages.ImageDetailsPage.getImageDetailsPage;
import static pages.LandingPage.getLandingPage;
import static pages.LoginPage.getLoginPage;

public class SmokeTest extends TestBase {
    LandingPage landingPage;
    LoginPage loginPage;
    LibraryPage libraryPage;
    SettingsPage settingsPage;
    ImageDetailsPage imageDetailsPage;

    enum Temperature {DEFAULT,CELSIUS, FAHRENHEIT, KELVIN}

    enum Distance {DEFAULT,METERS, FEET}

    enum Language {DEFAULT,CZECH, DANISH, GERMAN, ENGLISH}

    enum Date {DEFAULT, MMDDYYYY, DDMMYYYY}

    @Test
    public void smokeTest1() {

        executeSetup(TestCaseHeader.SMOKETEST_TEST1);

        String temp = String.valueOf(Temperature.FAHRENHEIT);
        String dist = String.valueOf(Distance.FEET);
        String lang = String.valueOf(Language.ENGLISH);
        String date = String.valueOf(Date.MMDDYYYY);

        libraryPage = loginPage.login(testData.getSmokeTestAccount(), testData.getPassOfExistingAcc());

        settingsPage = libraryPage.goToSettingsPage();
        settingsPage.setTemperature(temp)
                .setDistance(dist)
                .setLanguage(lang)
                .setDate(date);

        TestUtil.refreshPage();

        checkIfValuesAreSaved(temp,dist,lang,date);

        libraryPage.logout();

        goToLoginPage();
        libraryPage = loginPage.login(testData.getSmokeTestAccount(), testData.getPassOfExistingAcc());
        settingsPage = libraryPage.goToSettingsPage();
        checkIfValuesAreSaved(temp,dist,lang,date);

        settingsPage.goToLibraryPage();

        String newFolderName = TestUtil.getRandomString(7);
        libraryPage.createNewFolder(newFolderName)
                .openFolder(newFolderName)
                .uploadFile(System.getProperty("user.dir") + "\\src\\main\\java\\testData\\Images\\measurements.jpg")
                .closeToastMessage()
                .uploadFile(System.getProperty("user.dir") + "\\src\\main\\java\\testData\\Images\\GPS.jpg")
                .closeToastMessage();

        imageDetailsPage = libraryPage.openFile("GPS.jpg");
        imageDetailsPage.clickOn_showAll_BTN();

        checkSettingsInImageDetails(temp, dist, date);

        imageDetailsPage.clickOn_comments_TAB()
                .checkForExistingNotes();

        imageDetailsPage.clickOn_location_TAB()
                .checkForExistingLocation();

        imageDetailsPage.clickOn_zoomIn_BTN()
                .clickOn_zoomIn_BTN()
                .clickOn_zoomOut_BTN()
                .clickOn_zoomToFit_BTN()
                .clickOn_toggleDC_BTN()
                .clickOn_toggleIR_BTN()
                .clickOn_enterFullscreen_BTN()
                .clickOn_exitFullscreen_BTN();

        imageDetailsPage.clickOn_nextImage_BTN()
                .clickOn_prevImage_BTN();

        imageDetailsPage.clickOn_backToLibrary_BTN();

        libraryPage.goToMainFolder()
                .clickOn_gridView_BTN()
                .clickOn_listView_BTN()
                .deleteFolder(newFolderName)
                .logout();
    }

    //-----------
    private void goToLoginPage() {
        landingPage.verifyIfPageLoaded()
                .clickOn_loginBTN();
        loginPage.verifyIfPageLoaded();
    }

    private void executeSetup(TestCaseHeader testCaseHeader) {
        String parentMethodName = new Throwable().fillInStackTrace().getStackTrace()[1].getMethodName();
        log.info("----Begin to test " + parentMethodName + "----");
        ExtentReport.createTestCase(parentMethodName, testCaseHeader.description);
        ExtentReport.assignCategory(String.valueOf(TestCaseCategory.LANDING_PAGE));

        landingPage = getLandingPage();

        loginPage = getLoginPage();
        goToLoginPage();
    }

    private String getSelectedDate() {
        String selectedDate = null;
        Select select = new Select(settingsPage.dateFormat_dropdown());
        WebElement currElement = select.getFirstSelectedOption();
        switch (currElement.getText()) {
            case "Jan 1, 1970 3:53 PM (Automatic)":
                selectedDate = Date.DEFAULT.toString();
                break;
            case "1 Jan 1970, 15:53":
                selectedDate = Date.DDMMYYYY.toString();
                break;
            case "January 1, 1970, 3:53 pm":
                selectedDate = Date.MMDDYYYY.toString();
                break;
        }
        return selectedDate;
    }

    private void checkIfValuesAreSaved(String temp, String dist, String lang, String date) {
        String selectedTemp = settingsPage.getSelectedElementFromDropdown(settingsPage.temperatureUnits_dropdown()).toUpperCase();
        String selectedDist = settingsPage.getSelectedElementFromDropdown(settingsPage.distanceUnits_dropdown()).toUpperCase();
        String selectedLang = settingsPage.getSelectedElementFromDropdown(settingsPage.language_dropdown()).toUpperCase();
        String selectedDate = getSelectedDate().toUpperCase();

        Assert.assertTrue(selectedTemp.contains(temp));
        ExtentReport.addTestCaseStep("Temperature settings remained the same after page was refreshed");

        Assert.assertTrue(selectedDist.contains(dist));
        ExtentReport.addTestCaseStep("Distance settings remained the same after page was refreshed");

        Assert.assertTrue(selectedLang.contains(lang));
        ExtentReport.addTestCaseStep("Language settings remained the same after page was refreshed");

        Assert.assertTrue(selectedDate.contains(date));
        ExtentReport.addTestCaseStep("Date settings remained the same after page was refreshed");
    }

    private void checkSettingsInImageDetails(String temp, String dist, String date) {
        ImageDetailsPage imageDetailsPage = getImageDetailsPage();
        if (imageDetailsPage.temperatures().isDisplayed()) {
            List<WebElement> temperatures = driver.findElements(By.xpath("//span[@class='flir-unit' and contains(.,'°')]"));
            for (int i = 0, temperaturesSize = temperatures.size(); i < temperaturesSize; i++) {
                WebElement temperature = temperatures.get(i);
                System.out.println(temperature.getText());
                String tempUnit = temperature.getText();
                tempUnit = tempUnit.substring(tempUnit.lastIndexOf("°") + 1);
                Assert.assertTrue(temp.contains(tempUnit));
            }
            ExtentReport.addTestCaseStep("All of the temperature units are the ones set in Settings: " + temp);
        }

        WebElement distance = driver.findElement(By.xpath("//h4[contains(text(),'Distance')]//following::span[1]"));
        String distUnit = distance.getText().substring(distance.getText().length() - 2, distance.getText().length() - 1).toUpperCase();
        Assert.assertTrue(dist.contains(distUnit));
        ExtentReport.addTestCaseStep("All of the distance units are the ones set in Settings: " + dist);

        imageDetailsPage.clickOn_details_TAB();
        checkDateSettings(imageDetailsPage.created(), date);
        checkDateSettings(imageDetailsPage.uploaded(), date);
    }

    private void checkDateSettings(WebElement webElement, String savedDateFromSettings) {
        String format;
        String date = webElement.getText();
        long count = date.chars().filter(ch -> ch == ',').count();
        if (count == 2) {
            format = String.valueOf(Date.MMDDYYYY);
        } else if (date.contains("AM") || date.contains("PM")) {
            format = String.valueOf(Date.DEFAULT);
        } else {
            format = String.valueOf(Date.DDMMYYYY);
        }

        Assert.assertEquals(format, savedDateFromSettings);
        ExtentReport.addTestCaseStep("Date displayed in has the same format as the one saved in Settings");
    }
}
