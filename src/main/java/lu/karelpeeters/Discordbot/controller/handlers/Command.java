package lu.karelpeeters.Discordbot.controller.handlers;

import lu.karelpeeters.Discordbot.controller.handlers.errors.ErrorHandler;
import lu.karelpeeters.Discordbot.controller.handlers.errors.InvalidCommandHandler;
import lu.karelpeeters.Discordbot.controller.handlers.motions.GetMotionsHandler;
import lu.karelpeeters.Discordbot.controller.handlers.motions.MotionHandler;
import lu.karelpeeters.Discordbot.controller.handlers.motions.PassMotionHandler;

public enum Command {
	ERROR(
			"!error",
			"internal error function, should not be called by users",
			new ErrorHandler()
	),
	INVALIDCOMMAND(
			"!invalidcommand",
			"internal error function, should not be called by users",
			new InvalidCommandHandler()
	),
	MOTION(
			"!motion",
			"propose a motion",
			new MotionHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CABBAGE_FARMER,
			AuthRole.PONTIFEX_MAXIMUS,
			AuthRole.MILES,
			AuthRole.ROMAN_CITIZEN,
			AuthRole.ROMAN_SUBJECT
	),
	GETMOTIONS(
			"!getmotions",
			"list all motions",
			new GetMotionsHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CABBAGE_FARMER,
			AuthRole.PONTIFEX_MAXIMUS,
			AuthRole.MILES,
			AuthRole.ROMAN_CITIZEN,
			AuthRole.ROMAN_SUBJECT
	),
	PING(
			"!ping",
			"returns pong",
			new PingHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CABBAGE_FARMER,
			AuthRole.PONTIFEX_MAXIMUS,
			AuthRole.MILES,
			AuthRole.ROMAN_CITIZEN,
			AuthRole.ROMAN_SUBJECT
	),
	COMMANDS(
			"!commands",
			"returns list of possible commands",
			new CommandsHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CABBAGE_FARMER,
			AuthRole.PONTIFEX_MAXIMUS,
			AuthRole.MILES,
			AuthRole.ROMAN_CITIZEN,
			AuthRole.ROMAN_SUBJECT
	),
	PASSMOTION(
			"!passmotion",
			"consider the motion as passed",
			new PassMotionHandler("pass"),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CABBAGE_FARMER
	);

	private String prefix;
	private String description;
	private DiscordHandler handler;
	private AuthRole[] authRoles;

	Command(String prefix, String description, DiscordHandler handler, AuthRole... roles) {
		this.prefix = prefix;
		this.description = description;
		this.handler = handler;
		this.authRoles = roles;
	}

	public String getPrefix() {
		return prefix;
	}
	public DiscordHandler getHandler() {
		return handler;
	}
	public String getDescription() {
		return description;
	}
	public AuthRole[] getAuthRoles() {
		return authRoles;
	}

	public static Command getCommandWithPrefix(String prefix) throws NoSuchCommandException {
		for (Command command: Command.values()) {
			if (command.getPrefix().equals(prefix)) return command;
		}
		throw new NoSuchCommandException(prefix);
	}
}
