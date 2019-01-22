package test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

import utilities.ExcelUtility;
import utilities.Screenshot;

public class LoginTests extends BaseTest {
	
	@Test (priority = 1)
	public void verifyTitle() throws InterruptedException {
		logger = reports.createTest("Verify Title");
		String expectedTitle = "SSO - Login";
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("https://52.202.44.59:8181/sso4-portal/login");
		
		Thread.sleep(1000);
		
		Assert.assertEquals(driver.getTitle(), expectedTitle);
		
		driver.close();
	}
	
	@Test (priority = 2)
	public void verifyLogin() throws InterruptedException {
		logger = reports.createTest("Verify Login");
		String username = "gemvo";
		String password = "1234567";
		String expectedLoginTitle = "SSO - My Apps";
		String postLoginTitle;
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://52.202.44.59:8181/sso4-portal/login");
		
		WebElement user = driver.findElement(
				By.cssSelector("div.sso-login-txtPanel:nth-child(2) > input:nth-child(2)"));
		
		WebElement pass = driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/input"));
		
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/div[3]/div/button[1]"));
	
		logger.log(Status.INFO, "Sending Username input");
		user.sendKeys(username);
		logger.log(Status.INFO, "Sending Password input");
		pass.sendKeys(password);
		
		logger.log(Status.INFO, "Click login button");
		loginBtn.click();
		
		Thread.sleep(2000);
		postLoginTitle = driver.getTitle();
		

		if (expectedLoginTitle.equals(postLoginTitle)) {
			logger.log(Status.PASS, "Login Title matched");
		} else {
			logger.log(Status.FAIL, "Login Title mismatched");
		}
		
		Thread.sleep(2000);
		
		logger.log(Status.INFO, "Logging out");
		WebElement profile = driver.findElement(By.xpath("//*[@id='rootPanel']/div[3]/div[3]"));
		profile.click();
		Thread.sleep(1000);
		
		WebElement logoutBtn = driver.findElement(By.xpath("//*[@id='rootPanel']/div[3]/div[2]/div[1]/button[4]"));
		logoutBtn.click();
		Thread.sleep(1250);
		
		Assert.assertEquals("SSO - Login", driver.getTitle());
		
		driver.close();
		
	}
	
	@Test (priority = 3 , dataProvider = "credentialData")
	public void dataDrivenLogin(String username, String password) throws InterruptedException {
		
		logger = reports.createTest("Verify Login - Data Driven (" + username + ")" );
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("https://52.202.44.59:8181/sso4-portal/login");
		
		WebElement user = driver.findElement(
				By.cssSelector("div.sso-login-txtPanel:nth-child(2) > input:nth-child(2)"));
		
		WebElement pass = driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/input"));
		
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/div[3]/div/button[1]"));
		
		logger.log(Status.INFO, "Logging in with " + username + " credentials");
		user.sendKeys(username);
		pass.sendKeys(password);
		loginBtn.click();
		
		Thread.sleep(2000);
		String postLoginTitle = driver.getTitle();
		String expectedLoginTitle = "SSO - My Apps";
		
		if (expectedLoginTitle.equals(postLoginTitle)) {
			logger.log(Status.PASS, "Login Title matched");
		} else {
			logger.log(Status.FAIL, "Login Title mismatched");
			driver.close();
		}
		
		Thread.sleep(2000);
		
		logger.log(Status.INFO, "Logging out");
		WebElement profile = driver.findElement(By.xpath("//*[@id='rootPanel']/div[3]/div[3]"));
		profile.click();
		Thread.sleep(1000);
		
		WebElement logoutBtn = driver.findElement(By.xpath("//*[@id='rootPanel']/div[3]/div[2]/div[1]/button[4]"));
		logoutBtn.click();
		Thread.sleep(1250);
		
		Assert.assertEquals("SSO - Login", driver.getTitle());
		
		driver.close();
		
	}
	
	@DataProvider(name = "credentialData")
	public Object[][] passData() {

//		Uses Object data type because credentials can be composed of strings,ints,doubles etc.	
//		Object[][] data = new Object[rows][col];
		Object[][] data = new Object[3][2];
		
		data[0][0] = "gemvo";
		data[0][1] = "1234567";
		
		data[1][0] = "cdecastro";
		data[1][1] = "1234567";
		
		data[2][0] = "wronguser";
		data[2][1] = "wrongpass";
		
		return data;
	}
	
	@Test (priority = 4, dataProvider = "newData")
	public void excelTest(String username, String password) throws InterruptedException {
	
		logger = reports.createTest(
				"Verify Login - Data Driven using Excel (" + username + ")" );
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://52.202.44.59:8181/sso4-portal/login");
		
		WebElement user = driver.findElement(
				By.cssSelector("div.sso-login-txtPanel:nth-child(2) > input:nth-child(2)"));
		
		WebElement pass = driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/input"));
		
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/div[3]/div/button[1]"));
		
		logger.log(Status.INFO, "Logging in with " + username + " credentials");
		user.sendKeys(username);
		pass.sendKeys(password);
		loginBtn.click();
		
		Thread.sleep(2000);
		String postLoginTitle = driver.getTitle();
		String expectedLoginTitle = "SSO - My Apps";
		
		if (expectedLoginTitle.equals(postLoginTitle)) {
			logger.log(Status.PASS, "Login Title matched");
		} else {
			logger.log(Status.FAIL, "Login Title mismatched");
			try {
				String imgPath = Screenshot.capture(driver, "test_"+ username);
				logger.log(Status.FAIL, "image below: " + logger.addScreenCaptureFromPath(imgPath));
				//Screenshot.capture(driver, "test_"+ username);
				//logger.log(Status.FAIL, "image below: " + logger.addScreenCaptureFromPath("/TestReports/Screenshots/" +"sample.png"));
				//logger.log(Status.FAIL, "image below: " + logger.addScreenCaptureFromPath(Screenshot.takeScreenshot().getAbsolutePath()));

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			driver.close();
		}
		
		Thread.sleep(2000);
		
		logger.log(Status.INFO, "Logging out");
		WebElement profile = driver.findElement(By.xpath("//*[@id='rootPanel']/div[3]/div[3]"));
		profile.click();
		Thread.sleep(1000);
		
		WebElement logoutBtn = driver.findElement(By.xpath("//*[@id='rootPanel']/div[3]/div[2]/div[1]/button[4]"));
		logoutBtn.click();
		Thread.sleep(1250);
		
		Assert.assertEquals("SSO - Login", driver.getTitle());
		
		driver.close();
	}
	
	@DataProvider(name = "newData")
	public Object[][] dataProvider() {
		String excelPath = "loginTestData.xlsx";
		String sheet_name = "Sheet1";
		
		ExcelUtility ExcelUtility = new ExcelUtility();
		Object[][] testData = ExcelUtility.getData("LoginTestData", excelPath, sheet_name);
		
		return testData;
	}

}