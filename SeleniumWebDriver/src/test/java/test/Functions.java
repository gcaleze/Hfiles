package test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import utilities.Screenshot;

public class Functions extends BaseTest {
	
	
	@Test(priority = 5)
	public void globalSearchNoInput() throws InterruptedException {
		System.out.println("Search Test");
		reporter.setAppendExisting(true);
		logger = reports.createTest("Global Search Without Input");
		String verifyLabel;
		String username = "gemvo";
		String password = "1234567";
		String expectedErr = 
				"Can't search if we don't know what you're looking for. "
				+ "Enter keyword(s) then try again";
		boolean toggle = false;
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("https://52.202.44.59:8181/sso4-portal/login");
		
		Thread.sleep(1000);
		
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
		
		Thread.sleep(3000);
		
		
			try {
				WebElement empMod = driver.findElement(By.id("emp"));
				Thread.sleep(500);
				empMod.click();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		Thread.sleep(3000);
		
		
		while(!toggle) {
			int counter = 0;
			try {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBtn2")));
				WebElement searchBtn = driver.findElement(By.xpath("//button[@id='searchBtn2']"));
				Thread.sleep(1000);
				searchBtn.click();
				toggle = true;
				counter = counter + 1;
				if (counter == 5) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		toggle = false;
		
		Thread.sleep(2000);
		driver.findElement(By.className("sr_srchBtn")).click();
		
		Thread.sleep(2000);
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='sso']/div[9]/div/div[2]/button[1]")));
		String errorMessage = driver.findElement(
				By.xpath("//*[@id='sso']/div[9]/div/div[2]/button[1]")).getText();
		
		verifyLabel = "error_message_check";
		if(errorMessage.equals(expectedErr)) {
			logger.log(Status.PASS, "Error Message Match");
		} else {
			
			try {
				String imgPath = Screenshot.capture(driver, "test_"+ verifyLabel);
				logger.log(Status.FAIL, "image below: " + logger.addScreenCaptureFromPath(imgPath));
				//logger.log(Status.FAIL, "image below: " + logger.addScreenCaptureFromPath(Screenshot.takeScreenshot().getAbsolutePath()));
				logger.log(Status.FAIL, "Error Message Mismatch");
				Thread.sleep(1000);
				driver.findElement(By.xpath("//*[@id='sso']/div[9]/div/div[2]/button[2]")).click();
				
				Thread.sleep(1250);
				driver.findElement(By.xpath("//*[@id='sso']/div[1]/div[2]/button[2]")).click();
				
				Thread.sleep(1250);
				driver.findElement(By.xpath(
						"//*[@id='sso']/div[8]/div/div[2]/button[2]")).click();
				
				driver.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='sso']/div[9]/div/div[2]/button[2]")).click();
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='sso']/div[1]/div[2]/button[2]")).click();
		
		Thread.sleep(1000);
		driver.findElement(By.xpath(
				"//*[@id='sso']/div[8]/div/div[2]/button[2]")).click();
		
		driver.close();
		
	}

}