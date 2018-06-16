package chapter5;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.logging.Level;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

public class DirectApiCalls {
	
	public static void parseJson(String jsonString) throws JsonProcessingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(jsonString);
		Iterator<JsonNode> elements = rootNode.elements();
		while(elements.hasNext()){
			JsonNode node = elements.next();
			Long id = node.get("id").asLong();
			String name = node.get("name").asText();
			String price = node.get("price").asText();
			System.out.println(String.format("Id: %s - Name: %s - Price: %s", id, name, price));
		}
	}
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setUseInsecureSSL(true);
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		
		for(int i = 1; i < 5; i++){
			Page json = client.getPage("https://www.javawebscrapingsandbox.com/product/api/" + i );
			parseJson(json.getWebResponse().getContentAsString());
		}
		
	}

}
