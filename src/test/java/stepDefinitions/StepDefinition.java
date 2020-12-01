package stepDefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.PayloadExtraction;

import static io.restassured.RestAssured.*;
import utilities.AccesInputPayloadData;
import utilities.Utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class StepDefinition extends Utils{
	RequestSpecification givenRequestSpec;
	Response extractedResponse;	
	static String extractedValueFromResponse;

	@Given("{string} Payload") 
	public void something_payload(String payloadURL) throws Throwable 
	{ 
		PayloadExtraction bodyPayloadURL = PayloadExtraction.valueOf(payloadURL);
		String methodSpecificPayloadURL = bodyPayloadURL.getPayloadLocation();
		givenRequestSpec = given().spec(requestSpecificationBuilder()).body(AccesInputPayloadData.accessPayLoadFiles(methodSpecificPayloadURL));
		
	}

	@When("User Calls {string} API with {string} http method") 
	public void user_calls_something_api_with_something_http_method(String resource, String httpMethod) throws Throwable 
	{ 
		APIResources resourceURL = APIResources.valueOf(resource);
		String apiSpecificResourceURL = resourceURL.getResouceURL();
		
		if(httpMethod.equalsIgnoreCase("GET"))
		{
		extractedResponse = givenRequestSpec.when().get(apiSpecificResourceURL).then()
				.spec(responseSpecificiationBuilder()).extract().response();
		}
		else if(httpMethod.equalsIgnoreCase("post"))
		{
		extractedResponse = givenRequestSpec.when().post(apiSpecificResourceURL).then()
				.spec(responseSpecificiationBuilder()).extract().response();
		}
		else if(httpMethod.equalsIgnoreCase("PUT"))
		{
		extractedResponse = givenRequestSpec.when().put(apiSpecificResourceURL).then()
				.spec(responseSpecificiationBuilder()).extract().response();
		}
	}

	@Then("{string} should be returned as {int}")
	public void should_be_returned_as(String string, Integer int1) {
		int statusCodeInResponse = extractedResponse.getStatusCode();
		assertEquals(statusCodeInResponse, 200);
	}

	@And("{string} in response body is {string}") 
	public void something_in_response_body_is_something(String keyToBeSearchedFor, String ValueToBeCompared) throws Throwable 
	{ 
		/*
		 * String extractedStringResponse = extractedResponse.asString(); JsonPath jp =
		 * new JsonPath(extractedStringResponse);
		 * assertEquals(jp.get(keyToBeSearchedFor),ValueToBeCompared);
		 */
		String extractedValueFromResponse = getJsonPath(extractedResponse,keyToBeSearchedFor);
		assertEquals(extractedValueFromResponse, ValueToBeCompared);
		
		//assertEquals(getJsonPath(extractedResponse,keyToBeSearchedFor),ValueToBeCompared);
		
	}
	
	@Then("Verify that user is able to call {string} method by using {string}")
	public void verify_that_user_is_able_to_call_method_by_using(String resourceName, String queryParameter) throws Throwable {
	   
		extractedValueFromResponse = getJsonPath(extractedResponse,queryParameter);
		givenRequestSpec = given().spec(requestSpecificationBuilder()).queryParam("place_id", extractedValueFromResponse);
		
		user_calls_something_api_with_something_http_method(resourceName,"GET");
		
	}
		
	
	@Given("{string} Payload with {string}")
    public void something_payload_with_something(String payloadURL, String replacementKey) throws Throwable {
		/*
		 * PayloadExtraction bodyPayloadURL = PayloadExtraction.valueOf(payloadURL);
		 * String methodSpecificPayloadURL = bodyPayloadURL.getPayloadLocation(); String
		 * payloadData =
		 * AccesInputPayloadData.accessPayLoadFiles(methodSpecificPayloadURL); String
		 * replacedPayloadData = payloadData.replaceAll(replacementKey,
		 * extractedValueFromResponse);
		 */
		String replacedPayloadData = replacingValueInJsonPayloadFile(payloadURL, replacementKey, extractedValueFromResponse);
		givenRequestSpec = given().spec(requestSpecificationBuilder()).body(replacedPayloadData);
		
    }

}
