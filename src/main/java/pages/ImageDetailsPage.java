package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ElementManager;
import utils.ExtentReport;
import utils.TestUtil;

import static base.TestBase.browser;
import static pages.LibraryPage.getLibraryPage;
import static utils.DriverFactory.getDriver;

public class ImageDetailsPage extends FlirWebPage {

    private final WebDriver driver;

    //Constructor
    private ImageDetailsPage() {
        driver = getDriver(browser);
    }

    public static ImageDetailsPage getImageDetailsPage() {
        return new ImageDetailsPage();
    }
    //--------------

    //-----------GETTERS
    public WebElement measurements_TAB() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_MEASUREMENTS_TAB);
    }

    public WebElement comments_TAB() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_COMMENTS_TAB);
    }

    public WebElement details_TAB() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_DETAILS_TAB);
    }

    public WebElement location_TAB() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_LOCATION_TAB);
    }

    public WebElement measurements_title() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_MEASUREMENTS_TITLE);
    }

    public WebElement comments_title() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_COMMENTS_TITLE);
    }

    public WebElement details_title() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_DETAILS_TITLE);
    }

    public WebElement location_title() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_LOCATION_TITLE);
    }

    public WebElement temperatures() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_TEMPERATURES_SECTION);
    }

    public WebElement showAll_BTN() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_SHOWALL_BTN);
    }

    public WebElement backToLibrary_BTN() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_BACKTOLIBRARY_BTN);
    }

    public WebElement notes() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_COMMENTS_TAB_NOTES);
    }

    public WebElement created() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_DETAILS_TAB_CREATED);
    }

    public WebElement uploaded() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_DETAILS_TAB_UPLOADED);
    }

    public WebElement coordinates() {
        return getWebElement(driver, ElementManager.IMAGEDETAILSPAGE_LOCATION_TAB_COORDINATES);
    }

    public WebElement zoomIn_BTN(){
        return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_ZOOM_IN);
    }

    public WebElement zoomOut_BTN(){
        return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_ZOOM_OUT);
    }

    public WebElement zoomToFit_BTN(){
        return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_ZOOM_TO_FIT);
    }

    public WebElement enterFullscreen_BTN(){
        return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_ENTER_FULLSCREEN);
    }

    public WebElement exitFullscreen_BTN(){
        return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_EXIT_FULSCREEN);
    }

    public WebElement toggleDC_BTN(){
        return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_TOGGLE_DC);
    }

    public WebElement toggleIR_BTN(){
        return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_TOGGLE_IR);
    }

    public WebElement IR_image(){return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_IR_IMAGE);}

    public WebElement DC_image(){return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_DC_IMAGE);}

    public WebElement nextImage_BTN(){return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_NEXT_IMAGE);}

    public WebElement prevImage_BTN(){return getWebElement(driver,ElementManager.IMAGEDETAILSPAGE_PREV_IMAGE);}

    public WebElement download_BTN(){return getWebElement(driver,ElementManager.IMAGEDEATILSPAGE_DOWNLOAD_BTN);}

    //--------------

    //-----------SETTERS

    //--------------

    //Actions
    public ImageDetailsPage clickOn_measurements_TAB() {
        clickAction(measurements_TAB(), "Clicked on the Measurements tab");
        checkIfElementHasLoaded(measurements_title(), "Measurements tab has loaded");
        return this;
    }

    public ImageDetailsPage clickOn_comments_TAB() {
        clickAction(comments_TAB(), "Clicked on the Comments tab");
        checkIfElementHasLoaded(comments_title(), "Comments tab has loaded");
        return this;
    }

    public ImageDetailsPage clickOn_details_TAB() {
        clickAction(details_TAB(), "Clicked on the Details tab");
        checkIfElementHasLoaded(details_title(), "Details tab has loaded");
        return this;
    }

    public ImageDetailsPage clickOn_location_TAB() {
        clickAction(location_TAB(), "Clicked on the Location tab");
        checkIfElementHasLoaded(location_title(), "Location tab has loaded");
        return this;
    }

    public ImageDetailsPage clickOn_showAll_BTN() {
        clickAction(showAll_BTN(), "Clicked on Show all button");
        return this;
    }

    public void clickOn_backToLibrary_BTN() {
        clickAction(backToLibrary_BTN(), "Clicked on Back button");
        LibraryPage libraryPage = getLibraryPage();
        libraryPage.verifyIfPageLoaded();
    }

    public ImageDetailsPage clickOn_zoomOut_BTN(){
        clickAction(zoomOut_BTN(),"Clicked on the Zoom Out button");
        return this;
    }

    public ImageDetailsPage clickOn_zoomIn_BTN(){
        clickAction(zoomIn_BTN(),"Clicked on the Zoom In button");
        return this;
    }

    public ImageDetailsPage clickOn_enterFullscreen_BTN(){
        clickAction(enterFullscreen_BTN(),"Clicked on the Enter Fullscreen button");
        checkIfElementHasLoaded(exitFullscreen_BTN(),"Entered fullscreen");
        return this;
    }

    public ImageDetailsPage clickOn_exitFullscreen_BTN(){
        clickAction(exitFullscreen_BTN(),"Clicked on the Exit Fullscreen button");
        checkIfElementHasLoaded(enterFullscreen_BTN(),"Exited fullscreen");
        return this;
    }
    public ImageDetailsPage clickOn_zoomToFit_BTN(){
        clickAction(zoomToFit_BTN(),"Clicked on the Zoom To Fit button");
        return this;
    }
    public ImageDetailsPage clickOn_toggleDC_BTN(){
        clickAction(toggleDC_BTN(),"Clicked on the Switch to DC button");
        checkIfElementHasLoaded(DC_image(),"Switched to DC");
        TestUtil.waitForElementToBeClickable(toggleIR_BTN());
        return this;
    }
    public ImageDetailsPage clickOn_toggleIR_BTN(){
        clickAction(toggleIR_BTN(),"Clicked on the Switch to IR button");
        checkIfElementHasLoaded(IR_image(),"Switched to IR");
        TestUtil.waitForElementToBeClickable(toggleDC_BTN());
        return this;
    }

    public ImageDetailsPage clickOn_nextImage_BTN(){
        clickAction(nextImage_BTN(),"Clicked on Next image button");
        try {
            checkIfElementHasLoaded(IR_image(), "Navigated to the next image");
        }
        catch(NoSuchElementException e){
            checkIfElementHasLoaded(DC_image(), "Navigated to the next image");
        }
        return this;
    }

    public ImageDetailsPage clickOn_prevImage_BTN(){
        clickAction(nextImage_BTN(),"Clicked on Previous image button");
        try {
            checkIfElementHasLoaded(IR_image(), "Navigated to the prev image");
        }
        catch(NoSuchElementException e){
            checkIfElementHasLoaded(DC_image(), "Navigated to the prev image");
        }
        return this;
    }

    private boolean hasText(WebElement webElement) {
        if (webElement.getText().isEmpty()) return false;
        else return true;
    }

    public ImageDetailsPage checkForExistingNotes() {
        try {
            hasText(notes());
            ExtentReport.addTestCaseStep("Image has the following notes/comments: " + notes().getText());
        } catch (NoSuchElementException e) {
            ExtentReport.addTestCaseStep("Image does not have any notes/comments");
        }
        return this;
    }

    public ImageDetailsPage checkForExistingLocation() {
        try {
            hasText(coordinates());
            ExtentReport.addTestCaseStep("Image has the following coordinates: " + coordinates().getText());
        } catch (NoSuchElementException e) {
            ExtentReport.addTestCaseStep("Image does not have any location data");
        }
        return this;
    }

    public ImageDetailsPage verifyIfPageLoaded() {
            checkIfElementHasLoaded(zoomIn_BTN(), "Supported image file opened. Image details page is displayed");
        return this;
    }

    public ImageDetailsPage verifyIfNonSupportedFilePageLoaded() {
        checkIfElementHasLoaded(download_BTN(), "Non-supported file opened. Image details page is displayed");
        return this;
    }
}
