package Tests.Negative;


import Pages.FE_Login;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.javautils.BaseTest;
import utils.javautils.ReadExcelData;

public class Neg_FE_Login_Test extends BaseTest {

    @DataProvider(name="negative_loginData")
    public Object[][] getNegativeLoginData()
    {
        ReadExcelData readExcel=ReadExcelData.getInstance();
        readExcel.setPath(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\testData\\");
        return ReadExcelData.getExcelDataIn2DArray("login_data-fe.xlsx","sheet2");
    }



    @Test(dataProvider = "negative_loginData")
    public void FE_Negative_LoginTest(String username,String password) throws Exception
    {
        FE_Login fl= PageFactory.initElements(driver,FE_Login.class);
        openFEUrl();

        assertTrue(fl.isEnabled("usernameTxt"),"Username field is not enabled");
        logStep("Entering Username");
        fl.enterUsername(username);

        assertTrue(fl.isEnabled("passwordTxt"),"Password field is not enabled");
        logStep("Entering Password");
        fl.enterPassword(password);

        assertTrue(fl.isEnabled("loginBtn"),"Login Button is not enabled");
        logStep("Clicking Log in");
        fl.clickLoginButton();

        assertTrue(fl.isDisplayed("Error"),"User not able to see validation message");

    }
}
