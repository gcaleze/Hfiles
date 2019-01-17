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
	
	
	WebDriver driver;
	
	@BeforeClass (alwaysRun = true)
	public void setup() {
		locateDriver();
		String reportPath = System.getProperty("user.dir") + "\\TestReports\\report.html";
		String xmlConfig = System.getProperty("user.dir") + "\\src\\extent-config.xml";
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
	
	public void locateDriver() {
		String OS = System.getProperty("os.name");
		System.out.println("locateDriver()");
		if (OS.equals("Windows 7")) {
			driverPath = "C:\\Selenium\\chromedriver.exe";
			System.out.println("Windows 7");
		} else {
			System.out.println("Other OS");
			driverPath = "/usr/bin/chromedriver";
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