package utils.BrowserManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.javautils.BaseUtils;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BrowserManager extends BaseUtils {



    public static void browserRun() throws Exception {
        // Load properties file
        System.out.println("Loading ENV properties...");
        FileReader fr = new FileReader(System.getProperty("user.dir") + "\\src\\test\\ENV.properties");
        Properties prop = new Properties();
        prop.load(fr);

        // Get browser property
        String browser = prop.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            throw new IllegalArgumentException("Browser not specified in ENV.properties");
        }

        // Initialize WebDriver based on browser
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--incognito", "window-size=1920,1080");
                // Uncomment if headless mode is needed
                // chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Invalid browser specified in ENV.properties: " + browser);
        }

        // Maximize browser window
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        logStep(browser + " browser launched successfully.");
    }

    public void openBEUrl() throws IOException {
        // Load properties file
        FileReader fr = new FileReader(System.getProperty("user.dir") + "\\src\\test\\ENV.properties");
        Properties prop = new Properties();
        prop.load(fr);

        // Open Backend URL
        String beUrl = prop.getProperty("BE_URL");
        if (driver != null && beUrl != null) {
            driver.get(beUrl);
            logStep("Opening BE URL");
        } else {
            throw new IllegalStateException("Driver is not initialized or BE_URL is missing in ENV.properties");
        }
    }

    public void openFEUrl() throws IOException {
        // Load properties file
        FileReader fr = new FileReader(System.getProperty("user.dir") + "\\src\\test\\ENV.properties");
        Properties prop = new Properties();
        prop.load(fr);

        // Open Frontend URL
        String feUrl = prop.getProperty("FE_URL");
        if (driver != null && feUrl != null) {
            driver.get(feUrl);
            logStep("Opening FE URL");
        } else {
            throw new IllegalStateException("Driver is not initialized or FE_URL is missing in ENV.properties");
        }
    }

    public static void main(String[] args) {
        try {
            browserRun();
            System.out.println("Browser launched successfully!");
        } catch (Exception e) {
            System.err.println("Error launching browser: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
