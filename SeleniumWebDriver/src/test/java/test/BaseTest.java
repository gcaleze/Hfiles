package test;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class BaseTest {
	
	ExtentReports reports;
	ExtentHtmlReporter reporter;
	ExtentTest logger;
	String driverPath;
	String reportPath;
	String xmlConfig;
	
	public static WebDriver driver;
	
	@BeforeClass (alwaysRun = true)
	public void setup() {
		locateFiles();
//		reportPath = System.getProperty("user.dir") + "\\TestReports\\report.html";
//		xmlConfig = System.getProperty("user.dir") + "\\src\\test\\java\\extent-config.xml";
		String testerName = "Hurich Amanquiton";
		
		reporter = new ExtentHtmlReporter(reportPath);
		reports = new ExtentReports();
		reporter.setAppendExisting(true);
		reports.attachReporter(reporter);
		
		
		reporter.loadXMLConfig(xmlConfig);
		reports.setSystemInfo("OS", "Win 7");
		reports.setSystemInfo("Tester", testerName);
		
		System.setProperty("webdriver.chrome.driver",driverPath);	
	}
	
	public void locateFiles() {
		String OS = System.getProperty("os.name");
		System.out.println("locateDriver()");
		if (OS.equals("Windows 7")) {
			driverPath = "C:\\Selenium\\chromedriver.exe";
			reportPath = System.getProperty("user.dir") + "\\TestReports\\report.html";
			xmlConfig = System.getProperty("user.dir") + "\\src\\test\\java\\extent-config.xml";
			System.out.println("Windows 7");
		} else {
			driverPath = "/usr/bin/chromedriver";
			reportPath = System.getProperty("user.dir") + "/TestReports/report.html";
			xmlConfig = System.getProperty("user.dir") + "/src/test/java/extent-config.xml";
			System.out.println("Other OS");
		}
	}
	
	@AfterMethod
	public void getResult(ITestResult result) throws IOException {
		if(result.getStatus()==ITestResult.FAILURE) {
			
			logger.fail(MarkupHelper.createLabel(result.getName() + 
					" Test Case Failed", ExtentColor.RED));
			logger.fail(result.getThrowable());
			
		} else if(result.getStatus()==ITestResult.SUCCESS) {
			
			logger.pass(MarkupHelper.createLabel(result.getName() + 
					" Test Case Passed", ExtentColor.GREEN));
		} else {
			
			logger.skip(MarkupHelper.createLabel(result.getName() +
					" Test Case Skipped", ExtentColor.ORANGE));
			
			logger.skip(result.getThrowable());
		}
	}
	
	@AfterClass
	public void cleanUp() {
		reports.flush();
	}
}