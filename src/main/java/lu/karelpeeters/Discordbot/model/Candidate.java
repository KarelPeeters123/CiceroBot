package lu.karelpeeters.Discordbot.model;

public class Candidate {
	public String id;
	public String discordName;
	public String currentNickname;
	public String profileURL;

	public Candidate(String id, String discordName, String currentNickname, String profileURL) {
		this.setId(id);
		this.setDiscordName(discordName);
		this.setCurrentNickname(currentNickname);
		this.setProfileURL(profileURL);
	}

	public void setId(String id) {
		if (id.isEmpty()) {
			throw new IllegalArgumentException("Must have a valid ID");
		}
		this.id = id;
	}

	public void setDiscordName(String discordName) {
		if (discordName == null || discordName.isEmpty()) {
			this.discordName = "N/A";
		}
		this.discordName = discordName;
	}

	public void setCurrentNickname(String currentNickname) {
		if (currentNickname == null || currentNickname.isEmpty()) {
			this.currentNickname = "N/A";
		}
		this.currentNickname = currentNickname;
	}

	public void setProfileURL(String profileURL) {
		if (profileURL == null || profileURL.isEmpty()) {
			this.profileURL = "N/A";
		}
		this.profileURL = profileURL;
	}
}
