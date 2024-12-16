package utils.javautils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.search.SubjectTerm;

public class BaseUtils extends GenerateTestData{

    public static WebDriver driver;
    private static final Logger LOGGER = LoggerUtil.getLogger();
    public static String otp=null;


    /* Logger Methods */
    public static void logStep(String message) {
        LOGGER.info(message);
        Reporter.logStep(message);
    }

    public static void logException(String exception) {
        LOGGER.severe(exception);
    }

    /* Assertion Methods */
    public static void assertEquals(Object expected, Object actual, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    public static void assertTrue(boolean condition, String message) throws Exception {
        if(!condition)
        {
            Assert.assertTrue(condition, message);
            fail(message);
        }
    }

    /* Element Interaction Methods */
    public static void click(WebElement element) {
        try {
            scrollToElement(element);
            element.click();
        } catch (Exception e) {
            logStep("Failed to click element: " + e.getMessage());
        }
    }

    public static void sendText(WebElement element, String text) {
        try {
            scrollToElement(element);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            logStep("Failed to send text: " + e.getMessage());
        }
    }

    public static void scrollToElement(WebElement element) {
        if (driver == null) {
            logStep("Driver is not initialized.");
        }
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            logStep("Failed to scroll to element: " + e.getMessage());
        }
    }


    /* Wait Methods */

    public static void waitForUIToLoad(WebDriver driver, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            // Wait for the page to be fully loaded (document.readyState = 'complete')
            wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            logStep("UI has fully loaded.");
        } catch (TimeoutException e) {
            logStep("Timeout occurred while waiting for the UI to load: " + e.getMessage());
        } catch (Exception e) {
            logStep("An unexpected error occurred while waiting for the UI to load: " + e.getMessage());
        }
    	}



    public static void waitForElement(WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logStep("Element not visible: " + e.getMessage());
        }
    }

    public static void waitForClickable(WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logStep("Element not clickable: " + e.getMessage());
        }
    }

    /* Popup Handling */
    public static String handlePopup(boolean accept) {
        try {
            Alert alert = driver.switchTo().alert();
            String message = alert.getText();
            if (accept) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return message;
        } catch (NoAlertPresentException e) {
            logStep("No popup present: " + e.getMessage());
            return null;
        }
    }


    //FETCH EMAIL OTP
    public static String fetchOTP()
    {
        // Email Configuration
        String host = "imap.gmail.com"; // IMAP host for Gmail
        String username = ""; // Enter Your email address
        String password = ""; // Enter Your App Password

        // Set up the mail session
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");

        try {
            // Connect to the mail store
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore();
            store.connect(host, username, password);

            // Open the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Search for the email with the specific subject
            Message[] messages = inbox.search(new SubjectTerm("Your OTP for CDRI Login")); //Edit email subject

            if (messages.length > 0) {
                // Get the most recent email (last one in the array)
                Message latestEmail = messages[messages.length - 1];

                // Handling email content
                String content = latestEmail.getContent().toString();

                // Print the email body
                //System.out.println("Email Content: \n" + content);

                // Extract the OTP from the email content
                otp = extractOTP(content);
                System.out.println("Extracted OTP: " + otp);

            } else {
                System.out.println("No email found with the specified subject.");
            }

            // Close the email connection
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return otp;
    }

    // Method to extract the OTP from the email content
    public static String extractOTP(String content) {
        // Regular expression to extract the 4-digit OTP
        Pattern pattern = Pattern.compile("\\b\\d{4}\\b");
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.group(0); // Return the matched OTP
        } else {
            throw new IllegalStateException("No OTP found in the email content.");
        }
    }


    protected static void fail(String message) throws Exception {
        LOGGER.info(message);
        Reporter.logFail(message,driver);
        Reporter.logStep(message);

    }



}
