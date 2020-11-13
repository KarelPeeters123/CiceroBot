package lu.karelpeeters.Discordbot.controller.handlers;

public enum AuthRole {
	IMPERATOR ("Imperator"),
	CONSUL("Consul"),
	SENATOR("Senator"),
	CENTURION("Centurion"),
	CABBAGE_FARMER("Cabbage Farmer"),
	PONTIFEX_MAXIMUS("Pontifex Maximus"),
	MILES("Miles"),
	ROMAN_CITIZEN("Roman Citizen"),
	ROMAN_SUBJECT("Roman Subject")
	;
	private String role;
	AuthRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
