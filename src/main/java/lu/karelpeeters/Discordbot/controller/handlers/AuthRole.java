package lu.karelpeeters.Discordbot.controller.handlers;

public enum AuthRole {
	EVERYONE ("@everyone"),
	MARINUS("Marinus");
	private String role;
	AuthRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
