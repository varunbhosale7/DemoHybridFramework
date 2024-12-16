package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.javautils.BaseUtils;


public class FE_Login extends BaseUtils {

    public FE_Login(){
        PageFactory.initElements(driver,this);
    }
	//Initialize WebElement
    @FindBy(xpath = "//input[@id='un']")
    public WebElement usernameTxt;

    @FindBy(xpath = "//input[@id='password']")
    public WebElement passwordTxt;

    @FindBy(xpath = "//button[@id='submit']")
    public WebElement loginBtn;

    @FindBy(xpath="//strong[contains(text(),'Congratulations')]")
    public WebElement welcomeMsg;

    @FindBy(xpath = "//div[@id='error']")
    public WebElement errMsg;


    public void enterUsername(String username)
    {
        sendText(usernameTxt,username);
    }

    public void enterPassword(String password)
    {
        sendText(passwordTxt,password);
    }

    public void clickLoginButton()
    {
//        waitForElement(loginBtn,10);
        click(loginBtn);
    }


    public boolean isEnabled(String element)
    {
        switch (element)
        {
            case "usernameTxt" : return usernameTxt.isEnabled();
            case "passwordTxt" : return passwordTxt.isEnabled();
            case "loginBtn" : return loginBtn.isEnabled();

            default: logStep("Invalid Locator" + element);
        }
        return false;
    }

    public boolean isDisplayed(String element)
    {
        switch (element)
        {
            case "Welcome" : return welcomeMsg.isDisplayed();
            case "Error": return errMsg.isDisplayed();

            default: logStep("Invalid Locator" + element);
        }
        return false;
    }



}
