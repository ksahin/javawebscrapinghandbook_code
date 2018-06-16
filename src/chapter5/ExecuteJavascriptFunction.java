package chapter5;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ExecuteJavascriptFunction {
	
	public static void processLines(List<WebElement> lines){
		int size = lines.size();
		System.out.println(String.format("There are %s product rows in the table", size));
	}
	public static void main(String[] args) throws InterruptedException {
		String chromeDriverPath = "/Users/kevin/.nvm/versions/node/v10.4.0/lib/node_modules/chromedriver/lib/chromedriver/chromedriver" ;
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless" ,"--disable-gpu", "--ignore-certificate-errors", "--silent");
        options.addArguments("window-size=600,400");
        
        WebDriver driver = new ChromeDriver(options);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int pageNumber = 5 ;
        
        driver.get("https://www.javawebscrapingsandbox.com/product/infinite_scroll");
        for(int i = 3; i < pageNumber + 3; i++){
        	js.executeScript("drawNextLines('/product/api/" + i +"');");
        	while((Boolean)js.executeScript("return win.data('ajaxready');") == false){
        		Thread.sleep(100);
        	}
        }
        List<WebElement> rows = driver.findElements(By.xpath("//tr"));
        
        // do something with the row list
        processLines(rows);

	}

}
