package chapter4;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FileUploader {

	static final String baseUrl = "https://www.javawebscrapingsandbox.com/" ;
	static final String loginUrl = "account/login" ;
	static final String email = "test@test.com" ;
	static final String password = "test" ;
	static final String fileName = "file.png" ;
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setUseInsecureSSL(true);
		// Turn off the logger
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 

		// Get the login page
		HtmlPage page = client.getPage(String.format("%s%s", baseUrl, loginUrl)) ;
		
		// Select the email input
		HtmlInput inputEmail = page.getFirstByXPath("//form//input[@name='email']");
		
		// Select the password input
		HtmlInput inputPassword = page.getFirstByXPath("//form//input[@name='password']");
		
		// Set the value for both inputs
		inputEmail.setValueAttribute(email);
		inputPassword.setValueAttribute(password);
		
		// Select the form
		HtmlForm loginForm = inputPassword.getEnclosingForm() ;
		
		// Generate the POST request with the form
		page = client.getPage(loginForm.getWebRequest(null));
		
		if(!page.asText().contains("You are now logged in")){
			System.err.println("Error: Authentication failed");
		}else{
			System.out.println("Success ! Logged in");
		
		}
		
		page = client.getPage(baseUrl + "upload_file") ;
		HtmlForm uploadFileForm = page.getFirstByXPath("//form[@action='/upload_file']");
		HtmlFileInput fileInput = uploadFileForm.getInputByName("user_file");
		
		fileInput.setValueAttribute(fileName);
		fileInput.setContentType("image/png");
		
		HtmlElement button = page.getFirstByXPath("//button");
		page = button.click();
		
		
		if(page.asText().contains("Your file was successful uploaded")){
			System.out.println("File successfully uploaded");
		}else{
			System.out.println("Error uploading the file");
		}
		
	}

}
