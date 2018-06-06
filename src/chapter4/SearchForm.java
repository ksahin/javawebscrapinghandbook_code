package chapter4;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTableElement;

public class SearchForm {
	
	static final String baseUrl = "https://www.javawebscrapingsandbox.com/" ;
	static final String MINPRICE = "300";
	static final String MAXPRICE = "650" ;
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setUseInsecureSSL(true);
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		
		HtmlPage page = client.getPage(baseUrl + "product/search");
		
		HtmlInput minPrice = page.getHtmlElementById("min_price");
		HtmlInput maxPrice = page.getHtmlElementById("max_price");
		
		// set the min/max values
		minPrice.setValueAttribute(MINPRICE);
		maxPrice.setValueAttribute(MAXPRICE);
		HtmlForm form = minPrice.getEnclosingForm();
		
		page = client.getPage(form.getWebRequest(null));
		
		HtmlTable table = page.getFirstByXPath("//table");
		for(HtmlTableRow elem : table.getBodies().get(0).getRows()){
			System.out.println(String.format("Name : %s Price: %s", elem.getCell(0).asText(), elem.getCell(2).asText()));
		}
	}

}
