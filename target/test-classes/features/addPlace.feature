Feature: Validate the addPlace and getPlace API's

@AddPlace
Scenario: Verify if Place is being Successfully added using addPlace API
Given "addPlaceAPIPayload" Payload
When User Calls "addPlaceAPI" API with "POST" http method
Then "statusCode" should be returned as 200
And "status" in response body is "OK"
And "scope" in response body is "APP"

@GetPlace
Scenario: Verify if user is able to fetch the response with place_id using getPlaceAPI
Given "addPlaceAPIPayload" Payload
When User Calls "addPlaceAPI" API with "POST" http method
Then "statusCode" should be returned as 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And Verify that user is able to call "getPlaceAPI" method by using "place_id"

@DeletePlace
Scenario: Verify if user is able delete a place with place_id using deletePlaceAPI
Given "deletePlaceAPIPayload" Payload with "place_id"
When User Calls "deletePlaceAPI" API with "POST" http method
Then "statusCode" should be returned as 200
And "status" in response body is "OK"