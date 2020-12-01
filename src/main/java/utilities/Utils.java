package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.PayloadExtraction;

public class Utils {
	 static RequestSpecification reqSpec;
	 ResponseSpecification respSpec;
	 String extractedStringResponse;
	 JsonPath jp;

	public RequestSpecification requestSpecificationBuilder() throws IOException
	{
		if (reqSpec==null)
		{
		PrintStream ps = new PrintStream(new FileOutputStream("logFile.txt"));
		reqSpec = new RequestSpecBuilder().setBaseUri(getGlobalVariables("baseURI")).
				  addQueryParam(getGlobalVariables("loginUsername"),getGlobalVariables("loginPassword"))
				  .addFilter(RequestLoggingFilter.logRequestTo(ps))
				  .addFilter(ResponseLoggingFilter.logResponseTo(ps))
				  .setContentType(ContentType.JSON).build();
		
		return reqSpec;
		}
		
		else
		{
			return reqSpec;	
		}
	}

		
	public ResponseSpecification responseSpecificiationBuilder()
	{
		respSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		return respSpec;
	}
	
	public String getGlobalVariables(String Key) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/java/resources/globalVariables.properties");
		prop.load(fis);
		String propertyValue = prop.getProperty(Key);
		return propertyValue;
	}
	
	public String getJsonPath(Response extractedResponse, String valueToBeExtractedFromResponse)
	{
		extractedStringResponse = extractedResponse.asString();
		jp = new JsonPath(extractedStringResponse);
		String extractedValueFromResponse = jp.get(valueToBeExtractedFromResponse);
		return extractedValueFromResponse;
	}
	
	public String replacingValueInJsonPayloadFile(String payloadFileURL, String replacementKey, String valueToBeReplaced) throws IOException 
	{
		PayloadExtraction bodyPayloadURL = PayloadExtraction.valueOf(payloadFileURL);
		String methodSpecificPayloadURL = bodyPayloadURL.getPayloadLocation();
		String payloadData = AccesInputPayloadData.accessPayLoadFiles(methodSpecificPayloadURL);
		String replacedPayloadData = payloadData.replaceAll("#" + replacementKey, valueToBeReplaced);
		return replacedPayloadData;
	}
}
