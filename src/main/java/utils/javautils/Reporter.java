package utils.javautils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Reporter {

	static ExtentReports extent;
	static ExtentTest test;
	static String timestamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

	public static void setupReport(String filename) {
		filename = filename + "_" + timestamp;

		String parentDirectory = System.getProperty("user.dir") + File.separator + "reports" + File.separator;
		createDirectory(parentDirectory);

		ExtentSparkReporter report = new ExtentSparkReporter(
				parentDirectory + File.separator + timestamp + File.separator + filename + ".html");
		report.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(report);
	}

	public static void createTest(String testName) {
		test = extent.createTest(testName);
	}

	public static void logStep(String stepDescription) {
		if (test != null) {
			test.log(Status.INFO, stepDescription);
		} else {
			System.out.println("Extent test object is null. Logging step to console: $stepDescription");
		}
	}

	public static void logPass(String stepDescription) {
		test.log(Status.PASS, MarkupHelper.createLabel(stepDescription, ExtentColor.GREEN));
	}

	public static void logFail(String stepDescription, WebDriver driver) {
		try {
			test.log(Status.FAIL, MarkupHelper.createLabel(stepDescription, ExtentColor.RED));
			String base64Screenshot = captureScreenShot(driver);
			test.addScreenCaptureFromBase64String(base64Screenshot, "Failed Step Screenshot");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void flushReport() {
		extent.flush();
	}


	private static void createDirectory(String parentDirectory) {
		String directoryPath = parentDirectory + File.separator + timestamp;
		File directory = new File(directoryPath);
		directory.mkdirs();
	}

	private static String captureScreenShot(WebDriver driver) throws Exception {
		byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		return Base64.getEncoder().encodeToString(screenshotBytes);
	}
}
