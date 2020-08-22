package fr.bastion.rest.specificMessages;

import java.nio.file.Paths;

import fr.bastion.utiles.HttpMethod;

public class GetEncounters extends SpecificMessages{
	
	private final HttpMethod method = HttpMethod.POST;
	private final String url = "https://bumble.com/mwebapi.phtml?SERVER_GET_ENCOUNTERS";
	private byte number = 20;
	private String body = "{\"body\":[{\"message_type\":81,\"server_get_encounters\":{\"number\":"+number+",\"context\":1,\"user_field_filter\":{\"projection\":[210,370,200,230,490,540,530,560,291,732,890,930,662,570,380,493,1140,1150,1160,1161],\"request_albums\":[{\"album_type\":7},{\"album_type\":12,\"external_provider\":12,\"count\":8}],\"game_mode\":0,\"request_music_services\":{\"top_artists_limit\":8,\"supported_services\":[29],\"preview_image_size\":{\"width\":120,\"height\":120}}}}}],\"message_id\":26,\"message_type\":81,\"version\":1,\"is_background\":true}";
	
	/*************Constructor*************/
	/**
	 * The attributes of the super class should be initialized.
	 */
	public GetEncounters() {
		super.method = this.method;
		super.url = this.url;	
		super.body = this.body;
		loadOutputFilePath();
	}
	
	/*************Number*************/
	/**
	 * The number of profiles expected in the HTTP response.
	 * @param number (byte) between 5 and 20.
	 */
	public void setNumber(byte number) {
		if (number < 5 && number > 20) {
			throw new IllegalArgumentException("The number parameter must be between 5 and 20.");
		}
		this.number = number;
	}
	public byte getNumber() {
		return number;
	}
	
	/*************Parameters*************/
	public HttpMethod getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public String getBody() {
		return body;
	}

	@Override
	public void loadOutputFilePath() {
		super.outputFilePath = Paths.get(super.applyXpath("parameters/parameter[@name='"+this.getClass().getSimpleName()+"']/outputFile/@path"));
	}

}
