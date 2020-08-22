package fr.bastion.rest.specificMessages;

import java.nio.file.Paths;

import fr.bastion.utiles.HttpMethod;

public class VoteEncounter extends SpecificMessages {
	
	private final HttpMethod method = HttpMethod.POST;
	private final String url = "https://bumble.com/mwebapi.phtml?SERVER_ENCOUNTERS_VOTE";
	private String idTargetBabe = null;
	private byte voteNumber = 0;
	private String body = "{\"body\":[{\"message_type\":80,\"server_encounters_vote\":{\"person_id\":\""+idTargetBabe+"\",\"vote\":"+voteNumber+",\"vote_source\":1,\"game_mode\":0}}],\"message_id\":17,\"message_type\":80,\"version\":1,\"is_background\":false}";
	
	public VoteEncounter(String personId, boolean choice) {
		this.idTargetBabe = personId;
		// Ternary condition : if choice = true : voteNumber = 2, else : voteNumber = 3.
		this.voteNumber = (byte) ((choice) ? 2 : 3);
	}
	
	/*************Constructor*************/
	/**
	 * The attributes of the super class should be initialized.
	 */
	public VoteEncounter() {
		super.method = this.method;
		super.url = this.url;
		loadOutputFilePath();
		}

	/*************ID babe*************/
	/**
	 * The id of the person we are voting for
	 * @return a String of 49 characters.
	 */
	public String getIdTargetBabe() {
		return idTargetBabe;
	}
	/**
	 * The id of the person we are voting for
	 * @param idTargetBabe must be String of 49 characters.
	 */
	public void setIdTargetBabe(String idTargetBabe) {
		if (idTargetBabe.length() != 49) {
			throw new IllegalArgumentException("idTargetBabe must be String of 49 characters.");
		}
		this.idTargetBabe = idTargetBabe;
	}

	/*************Choice*************/
	/**
	 * 	1 = not yet voted.
	 * 	2 = voted 'Yes'. 
	 *  3 = voted 'No'.
	 * @return byte between 1 and 3.
	 */
	public byte getVoteNumber() {
		return voteNumber;
	}
	/**
	 * 	1 = not yet voted.
	 * 	2 = voted 'Yes'. 
	 *  3 = voted 'No'.
	 *  @param voteNumber is a byte between 1 and 3.
	 */
	public void setVoteNumber(byte voteNumber) {
		if (voteNumber < 1 && voteNumber > 3) {
			throw new IllegalArgumentException("The parameter voteNumber must be between 1 and 3.");
		}
		this.voteNumber = voteNumber;
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
