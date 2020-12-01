package resources;

public enum PayloadExtraction {
	
	addPlaceAPIPayload("src/main/java/resources/addPlacePayload.json"),
	getPlaceAPIPayload("src/main/java/resources/getPlacePayload.json"),
	deletePlaceAPIPayload("src/main/java/resources/deletePlacePayload.json"),
	updatePlaceAPIPayload("src/main/java/resources/updatePlacePayload.json");
	
	private String payloadLocation;
	
	PayloadExtraction(String payloadLocation)
	{
		this.payloadLocation = payloadLocation;
	}
	
	public String getPayloadLocation()
	{
		return payloadLocation;
	}

}
