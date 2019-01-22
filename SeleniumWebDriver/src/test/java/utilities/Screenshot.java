package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot extends test.BaseTest {

//	public static String capture(WebDriver driver, String fileName) throws IOException {
//		String OS = System.getProperty("os.name");
//		String destination = "";
//		TakesScreenshot ts = (TakesScreenshot) driver;
//		if (OS.equals("Windows 7")) {
//			destination = System.getProperty("user.dir") + "\\TestReports\\Screenshots\\" + fileName + ".png";
//			System.out.println(OS);
//		} else {
//			destination = System.getProperty("user.dir") + "/TestReports/Screenshots/" + fileName + ".png";
//			System.out.println(OS);
//		}
//		File srcFile = ts.getScreenshotAs(OutputType.FILE);
//		File target = new File(destination);
//		FileUtils.copyFile(srcFile, target);
//
//		return destination;
//	}
	public static File takeScreenshot() throws IOException{
		File destination = new File(System.getProperty("user.dir") + "/TestReports/Screenshots/" +"sample.png");
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, destination.getAbsoluteFile());
		return destination;
	}
}
