package fr.bastion.rest.specificMessages;

public class VoteEncounter {
	
	//TODO: Static? Parameters useful?
	private String personId = null;
	private byte voteNumber = 0;
	/* 1=NotYet 2=Yes. 3=No.*/
	private String body = null;
	
	public VoteEncounter(String personId, boolean choice) {
		//TODO: Exception...
		this.personId = personId;
		
		if (choice) this.voteNumber = 2; 
		else this.voteNumber = 3;
		
		this.body = "{\"body\":[{\"message_type\":80,\"server_encounters_vote\":{\"person_id\":\""+personId+"\",\"vote\":"+voteNumber+",\"vote_source\":1,\"game_mode\":0}}],\"message_id\":17,\"message_type\":80,\"version\":1,\"is_background\":false}";
	}
	
	public String getBody(){
		return body;
	}
		
}
