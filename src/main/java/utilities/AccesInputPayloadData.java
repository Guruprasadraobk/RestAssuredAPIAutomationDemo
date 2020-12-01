package utilities;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AccesInputPayloadData {
	
	public static String accessPayLoadFiles(String payloadURL) throws IOException
	{
		System.out.println("accessing the file");
		Path path = Paths.get(payloadURL);
		System.out.println("accessed the file");
		byte[] byteFile = Files.readAllBytes(path);
		System.out.println("read the file");
		String payloadStringData = new String(byteFile);
		System.out.println("converted the file to string");
		System.out.println(payloadStringData);
		return payloadStringData;
		
		
	}

}
