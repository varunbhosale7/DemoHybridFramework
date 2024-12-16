package Tests.Positive;

import Pages.FE_Login;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.javautils.BaseTest;
import utils.javautils.ReadExcelData;

public class FELoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getNegativeLoginData() {
        ReadExcelData readExcel = ReadExcelData.getInstance();
        readExcel.setPath(System.getProperty("user.dir") + "\\src\\test\\java\\resources\\testData\\");
        return ReadExcelData.getExcelDataIn2DArray("login_data-fe.xlsx", "sheet1");
    }

    @Test(dataProvider = "loginData")
    public void FE_LoginTest(String username, String password) throws Exception {
        FE_Login fl = PageFactory.initElements(driver, FE_Login.class);
        openFEUrl();

        assertTrue(fl.isEnabled("usernameTxt"), "Username field is not enabled");
        logStep("Entering Username");
        fl.enterUsername(username);

        assertTrue(fl.isEnabled("passwordTxt"), "Password field is not enabled");
        logStep("Entering Password");
        fl.enterPassword(password);

        assertTrue(fl.isEnabled("loginBtn"), "Login Button is not enabled");
        logStep("Clicking Log in");
        fl.clickLoginButton();

        assertTrue(fl.isDisplayed("Welcome"), "User failed to login");
    }
}
