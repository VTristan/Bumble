package fr.bastion.rest.specificMessages;

import fr.bastion.utiles.HttpMethod;

public class UpdateLocation extends SpecificMessages {

	private final HttpMethod method = HttpMethod.POST;
	private final String url = "https://bumble.com/mwebapi.phtml?SERVER_UPDATE_LOCATION";
	private double latitude;
	private double longitude;
	
	/*************Constructor*************/
	/**
	 * The attributes of the super class should be initialized.
	 */
	public UpdateLocation() {
		super.method = this.method;
		super.url = this.url;
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
		return "{\"body\":[{\"message_type\":4,\"server_update_location\":{\"location\":[{\"latitude\":"+latitude+",\"longitude\":"+longitude+"}]}}],\"message_id\":18,\"message_type\":4,\"version\":1,\"is_background\":false}";
	}
	public HttpMethod getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}

	@Override
	public void loadOutputFilePath() {
		// No outputFilePath.
	}
	
	class GetCity{
		private final HttpMethod method = HttpMethod.POST;
		private final String url = "https://bumble.com/mwebapi.phtml?SERVER_GET_CITY";
		private double latitude;
		private double longitude;
		
		/*************Constructor*************/
		/**
		 * The attributes of the super class should be initialized.
		 */
		public GetCity() {
//			super.method = this.method;
//			super.url = this.url;
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
		public HttpMethod getMethod() {
			return method;
		}
		public String getUrl() {
			return url;
		}
		

	}
}


