package chapter4;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BasicAuthentication {
	
	static final String baseUrl = "https://www.javawebscrapingsandbox.com/" ;
	static final String username = "basic" ;
	static final String password = "auth" ;
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setUseInsecureSSL(true);
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		
		HtmlPage page = client.getPage(String.format("https://%s:%s@www.javawebscrapingsandbox.com/basic_auth", username, password));
		System.out.println(page.asText());
		
	}

}
