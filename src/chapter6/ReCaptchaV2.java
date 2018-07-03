package chapter6;

import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

public class ReCaptchaV2 {
	
	
	public static final String API_KEY = "" ;
	
	
	
	public static void main(String[] args) throws Exception {
		final String API_BASE_URL = "http://2captcha.com/" ;
		final String BASE_URL = "https://www.javawebscrapingsandbox.com/captcha";
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setUseInsecureSSL(true);
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		
		
		final String chromeDriverPath = "/usr/local/bin/chromedriver" ;
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--silent");
        options.addArguments("--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/60.0.3112.113 Chrome/60.0.3112.113 Safari/537.36");
        WebDriver driver = new ChromeDriver(options);
        
        driver.get(BASE_URL);
	
	        
		
		String siteId = "" ;
		WebElement elem = driver.findElement(By.xpath("//div[@class='g-recaptcha']"));
		
		try {
			siteId = elem.getAttribute("data-sitekey");
		} catch (Exception e) {
			System.err.println("Catpcha's div cannot be found or missing attribute data-sitekey");
			e.printStackTrace();
		}
		String QUERY = String.format("%sin.php?key=%s&method=userrecaptcha&googlekey=%s&pageurl=%s&here=now", 
				API_BASE_URL, API_KEY, siteId, BASE_URL);
		Page response = client.getPage(QUERY);
		String stringResponse = response.getWebResponse().getContentAsString();
		String jobId = "";
		if(!stringResponse.contains("OK")){
			throw new Exception("Error with 2captcha.com API, received : " + stringResponse);
		}else{
			jobId = stringResponse.split("\\|")[1];
		}
		
		boolean captchaSolved = false ;
		while(!captchaSolved){
			response = client.getPage(String.format("%sres.php?key=%s&action=get&id=%s", API_BASE_URL, API_KEY, jobId));
			if (response.getWebResponse().getContentAsString().contains("CAPCHA_NOT_READY")){
				Thread.sleep(3000);
				System.out.println("Waiting for 2Captcha.com ...");
			}else{
				captchaSolved = true ;
				System.out.println("Captcha solved !");
			}
		}
		String captchaToken = response.getWebResponse().getContentAsString().split("\\|")[1];
		JavascriptExecutor js = (JavascriptExecutor) driver ;
		js.executeScript("document.getElementById('g-recaptcha-response').style.display = 'block';");
		WebElement textarea = driver.findElement(By.xpath("//textarea[@id='g-recaptcha-response']"));
		
		textarea.sendKeys(captchaToken);
		js.executeScript("document.getElementById('g-recaptcha-response').style.display = 'none';");
		driver.findElement(By.id("name")).sendKeys("Kevin");
		driver.getPageSource();
		driver.findElement(By.id("submit")).click();
		
		if(driver.getPageSource().contains("your captcha was successfully submitted")){
			System.out.println("Captcha successfuly submitted !");
		}else{
			System.out.println("Error while submitting captcha");
		}
		
		
		System.out.println();

	}

}
