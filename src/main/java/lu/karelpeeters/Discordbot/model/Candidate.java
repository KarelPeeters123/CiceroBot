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
		System.out.println(this.currentNickname + " : " + currentNickname);
	}

	@Override
	public String toString() {
		return "Candidate{" +
				"id='" + id + '\'' +
				", discordName='" + discordName + '\'' +
				", currentNickname='" + currentNickname + '\'' +
				", profileURL='" + profileURL + '\'' +
				'}';
	}

	public void setId(String id) {
		if (id.isEmpty()) {
			throw new IllegalArgumentException("Must have a valid ID");
		}
		this.id = id;
	}

	public void setDiscordName(String discordName) {
		if (currentNickname == null) {
			this.currentNickname = "N/A";
		} else if (currentNickname.isEmpty()) {
			this.currentNickname = "N/A";
		}
		this.discordName = discordName;
	}

	public void setCurrentNickname(String currentNickname) {
		if (currentNickname == null) {
			this.currentNickname = "N/A";
		} else if (currentNickname.isEmpty()) {
			this.currentNickname = "N/A";
		}
		this.currentNickname = currentNickname;
	}

	public void setProfileURL(String profileURL) {
		if (currentNickname == null) {
			this.currentNickname = "N/A";
		} else if (currentNickname.isEmpty()) {
			this.currentNickname = "N/A";
		}
		this.profileURL = profileURL;
	}
}
