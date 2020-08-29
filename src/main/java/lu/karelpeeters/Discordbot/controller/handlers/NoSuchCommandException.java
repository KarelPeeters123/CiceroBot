package lu.karelpeeters.Discordbot.controller.handlers;

public class NoSuchCommandException extends Exception {
	NoSuchCommandException(String prefix) {
		super(prefix + " is not a valid command!");
	}
}
