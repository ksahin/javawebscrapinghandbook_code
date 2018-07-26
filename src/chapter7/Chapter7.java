package chapter7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;

public class Chapter7 {

	
	private static final String FILENAME = "user-agents.txt";
	
	public static WebClient initWebClientWithHeaders(){
		WebClient client = new WebClient();		
		client.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		client.addRequestHeader("Accept-Encoding", "gzip, deflate, br");
		client.addRequestHeader("Accept-Language", "en-US,en;q=0.9,fr-FR;q=0.8,fr;q=0.7,la;q=0.6");
		client.addRequestHeader("Connection", "keep-alive");
		client.addRequestHeader("Host", "ksah.in");
		client.addRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
		client.addRequestHeader("Pragma", "no-cache");
		
		return client ; 
	}
	
	private static String getRandomUseragent(){
		List<String> userAgents = new ArrayList<String>();
		Random rand = new Random();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				userAgents.add(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return userAgents.get(rand.nextInt(userAgents.size()));
	}
	public static void main(String[] args) {
		WebClient client = new WebClient() ;
		ProxyConfig proxyConfig = new ProxyConfig("host", 12345);
		client.getOptions().setProxyConfig(proxyConfig);
		System.out.println(getRandomUseragent());
	}
}
