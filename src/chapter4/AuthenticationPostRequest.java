package chapter4;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.FormEncodingType;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class AuthenticationPostRequest {
	
	
	static final String baseUrl = "https://www.javawebscrapingsandbox.com/" ;
	//static final String baseUrl = "http://localhost:8000/" ;
	static final String loginUrl = "account/login" ;
	static final String email = "test@test.com" ;
	static final String password = "test" ;
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
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
		
		HtmlInput csrfToken = page.getFirstByXPath("//form//input[@name='csrf_token']") ;
		WebRequest request = new WebRequest(
				new URL("http://www.javawebscrapingsandbox.com/account/login"), HttpMethod.POST);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new NameValuePair("csrf_token", csrfToken.getValueAttribute()));
		params.add(new NameValuePair("email", email));
		params.add(new NameValuePair("password", password));

		request.setRequestParameters(params);
		request.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded");
		request.setAdditionalHeader("Accept-Encoding", "gzip, deflate");
		
		page = client.getPage(request);
		
		if(!page.asText().contains("You are now logged in")){
			System.err.println("Error: Authentication failed");
		}else{
			System.out.println("Success ! Logged in");
		}

	}

}
