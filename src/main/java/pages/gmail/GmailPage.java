package pages.gmail;

import utils.TestUtil;

public class GmailPage extends GmailPageElements{
    private GmailPage(){
        super();
    }

    public static GmailPage getGmailPage(){
        return new GmailPage();
    }

    //Setters
    public GmailPage setEmail(String email) {
        TestUtil.waitForElementToLoad(email_field());
        email_field().sendKeys(email);
        return this;
    }

    public GmailPage setPass(String pass) {
        TestUtil.waitForElementToLoad(password_field());
        password_field().sendKeys(pass);
        return this;
    }

    //Actions
    public GmailPage clickOn_nextEmail_BTN() {
        TestUtil.waitForElementToLoad(nextEmail_BTN());
        nextEmail_BTN().click();
        return this;
    }

    public void clickOn_nextPass_BTN() {
        TestUtil.waitForElementToLoad(nextPass_BTN());
        nextPass_BTN().click();
    }

}
