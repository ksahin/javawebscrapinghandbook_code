package chapter3;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HNScraper {

	public static void main(String[] args) {
		String baseUrl = "https://news.ycombinator.com/" ;
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		try{
			HtmlPage page = client.getPage(baseUrl);
			List<HtmlElement> itemList =  page.getByXPath("//tr[@class='athing']");
			if(itemList.isEmpty()){
				System.out.println("No item found");
			}else{
				for(HtmlElement htmlItem : itemList){
					int position = Integer.parseInt(((HtmlElement) htmlItem.getFirstByXPath("./td/span")).asText().replace(".", ""));
					int id = Integer.parseInt(htmlItem.getAttribute("id"));
					String title =  ((HtmlElement) htmlItem.getFirstByXPath("./td[not(@valign='top')][@class='title']")).asText();
					String url = ((HtmlAnchor) htmlItem.getFirstByXPath("./td[not(@valign='top')][@class='title']/a")).getHrefAttribute();
					String author =  ((HtmlElement) htmlItem.getFirstByXPath("./following-sibling::tr/td[@class='subtext']/a[@class='hnuser']")).asText();
					int score = Integer.parseInt(((HtmlElement) htmlItem.getFirstByXPath("./following-sibling::tr/td[@class='subtext']/span[@class='score']")).asText().replace(" points", ""));
					
					HackerNewsItem hnItem = new HackerNewsItem(title, url, author, score, position, id);
					
					ObjectMapper mapper = new ObjectMapper();
					String jsonString = mapper.writeValueAsString(hnItem) ;
					
					System.out.println(jsonString);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			client.close();
		}
	}
}
