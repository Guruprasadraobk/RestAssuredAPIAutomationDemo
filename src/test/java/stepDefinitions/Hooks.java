package stepDefinitions;

import io.cucumber.java.Before;

public class Hooks {
	StepDefinition sd = new StepDefinition();
	
	@Before("@DeletePlace")
	public void beforeScenarios() throws Throwable
	{
		if(StepDefinition.extractedValueFromResponse ==null)
		{
		sd.something_payload("addPlaceAPIPayload");
		sd.user_calls_something_api_with_something_http_method("addPlaceAPI", "POST");
		}
	}

}
