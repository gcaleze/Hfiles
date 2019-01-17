package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {
	
public static String capture(WebDriver driver, String fileName) throws IOException {
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		String destination = System.getProperty("user.dir") +
				"\\reportImgs\\" + fileName + ".png";
		
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		File target = new File (destination);
		FileUtils.copyFile(srcFile, target);
		
		return destination;
	}
}

