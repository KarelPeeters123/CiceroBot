package lu.karelpeeters.Discordbot.model;

public class Candidate {
	public String id;
	public String discordName;
	public String currentNickname;
	public String profileURL;

	public Candidate(String id, String discordName, String currentNickname, String profileURL) {
		this.id = id;
		this.discordName = discordName;
		this.currentNickname = currentNickname;
		this.profileURL = profileURL;
	}
}
