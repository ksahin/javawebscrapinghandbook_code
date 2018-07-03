package chapter6;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept.*;

import org.bytedeco.javacpp.tesseract.TessBaseAPI;

public class OpticalCharacterRecognition {

	final static String TESS_DATA_PATH = "/usr/local/Cellar/tesseract/3.05.02/share/tessdata" ;
	
	public static void main(String[] args) {
		BytePointer outText;
		TessBaseAPI api = new TessBaseAPI();
		
		if (api.Init(TESS_DATA_PATH, "ENG") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }
		//api.SetVariable("tessedit_char_whitelist", "0123456789,");
		PIX image = lept.pixRead("ocr_exemple.jpg");
        api.SetImage(image);
        
        // Get OCR result
        outText = api.GetUTF8Text();
        String string = outText.getString();
        String invoiceNumber = "" ;
        for(String lines : string.split("\\n")){
        	if(lines.contains("Invoice")){
        		invoiceNumber = lines.split("Invoice Number: ")[1];
        		System.out.println(String.format("Invoice number found : %s", invoiceNumber));
        	}
        }
        
        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        lept.pixDestroy(image);

	}

}
