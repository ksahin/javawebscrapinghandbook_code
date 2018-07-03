package chapter6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PDFScraping {

	public static void main(String[] args) throws IOException {
		
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(true);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setUseInsecureSSL(true);
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		
		HtmlPage html = client.getPage("https://www.javawebscrapingsandbox.com/pdf");
		
		// selects the first anchor which contains "pdf" 
		HtmlAnchor anchor = html.getFirstByXPath("//a[contains(@href, 'pdf')]");
		String pdfUrl = anchor.getHrefAttribute();
		
		Page pdf = client.getPage(pdfUrl);
		
		if(pdf.getWebResponse().getContentType().equals("application/pdf")){
			System.out.println("Pdf downloaded");
			IOUtils.copy(pdf.getWebResponse().getContentAsStream(), 
					new FileOutputStream("invoice.pdf"));
			System.out.println("Pdf file created");
			PDDocument document = null;
			try{
			 	document = PDDocument.load(new File("invoice.pdf")) ;

		        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		        stripper.setSortByPosition(true);

		        PDFTextStripper tStripper = new PDFTextStripper();

		        String stringPdf = tStripper.getText(document);
		        String lines[] = stringPdf.split("\\n");
		        String pattern = "Total\\s+€\\s+(.+)";
		        Pattern p = Pattern.compile(pattern);
		        String price = "";
		        for (String line : lines) {
		        	Matcher m = p.matcher(line);
		        	if(m.find()){
		        		price = m.group(1);
		        	}
		        }
		        
		        if(!price.isEmpty()){
		        	System.out.println("Price found: " + price);
		        }else{
		        	System.out.println("Price not found");
		        }
			}finally{
				if(document != null){
					document.close();
				}
			}
		  
		}
 
	}
}
