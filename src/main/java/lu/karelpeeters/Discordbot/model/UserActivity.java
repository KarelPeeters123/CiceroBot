package lu.karelpeeters.Discordbot.model;

public class UserActivity {
	public String id;
	public int messages;
	public UserActivity(String id, int messages) {
		this.id = id;
		this.messages = messages;
	}
	public void increment() {
		this.messages++;
	}
}
