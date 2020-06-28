package fr.bastion.rest.specificMessages;

public class GetCity extends SpecificMessages{

	private final String method = "POST";
	private final String url = "https://bumble.com/mwebapi.phtml?SERVER_GET_CITY";
	private double latitude;
	private double longitude;
	
	/**
	 * TODO: Check the parameters's values.
	 * @param latitude
	 * @param longitude
	 */
	public GetCity(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/*************Coordinates*************/
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/*************Parameters*************/
	public String getBody() {
		return "{\"body\":[{\"message_type\":309,\"server_get_city\":{\"latitude\":"+latitude+",\"longitude\":"+longitude+"}}],\"message_id\":15,\"message_type\":309,\"version\":1,\"is_background\":false}";
	}	
	public String getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	
}
