package lu.karelpeeters.Discordbot.controller.handlers;

import lu.karelpeeters.Discordbot.controller.handlers.election.*;
import lu.karelpeeters.Discordbot.controller.handlers.errors.ErrorHandler;
import lu.karelpeeters.Discordbot.controller.handlers.errors.InvalidCommandHandler;
import lu.karelpeeters.Discordbot.controller.handlers.motions.GetMotionsHandler;
import lu.karelpeeters.Discordbot.controller.handlers.motions.MotionHandler;
import lu.karelpeeters.Discordbot.controller.handlers.motions.resolve.FailMotionHandler;
import lu.karelpeeters.Discordbot.controller.handlers.motions.resolve.PassMotionHandler;
import lu.karelpeeters.Discordbot.controller.handlers.motions.resolve.VetoMotionHandler;

public enum Command {
	ERROR(
			"-error",
			"internal error function, should not be called by users",
			new ErrorHandler()
	),
	INVALIDCOMMAND(
			"-invalidcommand",
			"internal error function, should not be called by users",
			new InvalidCommandHandler()
	),
	MOTION(
			"-motion",
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
			"-getmotions",
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
			"-ping",
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
			"-commands",
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
			"-passmotion",
			"consider the motion as passed",
			new PassMotionHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CABBAGE_FARMER
	),
	FAILMOTION(
			"-failmotion",
			"consider the motion as failed",
			new FailMotionHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CABBAGE_FARMER
	),
	VETOMOTION(
			"-vetomotion",
			"veto the motion",
			new VetoMotionHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL
	),
	REGISTERCONSUL(
			"-consul",
			"election for an upcoming consular election",
			new RegisterConsulHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CENTURION
	),
	REGISTERSENATOR(
			"-senator",
			"election for an upcoming senatorial election",
			new RegisterSenatorHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CENTURION,
			AuthRole.MILES,
			AuthRole.ROMAN_CITIZEN
	),
	REGISTERCENTURION(
			"-centurion",
			"election for an upcoming centurion election",
			new RegisterCenturionHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CENTURION,
			AuthRole.MILES,
			AuthRole.ROMAN_CITIZEN
	),
	BALLOT(
			"-ballot",
			"show the ballot with all the candidates running for office",
			new BallotHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CABBAGE_FARMER,
			AuthRole.PONTIFEX_MAXIMUS,
			AuthRole.MILES,
			AuthRole.ROMAN_CITIZEN,
			AuthRole.ROMAN_SUBJECT
	),
	OPENELECTION(
			"-open",
			"opens the poll booth for the election to start",
			new OpenElectionHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL
	),
	CLOSEELECTION(
			"-close",
			"close the poll booth for the election to start",
			new CloseElectionHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL
	),
	SHUTDOWN(
			"-shutdown",
			"shuts down the bot",
			new ShutdownHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL
	),
	RANDOMSTORY(
			"-random",
			"gets a random story",
			new RandomStoryHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CABBAGE_FARMER,
			AuthRole.PONTIFEX_MAXIMUS,
			AuthRole.MILES,
			AuthRole.ROMAN_CITIZEN
	),
	PASTA(
			"-pasta",
			"get a pasta from creepypasta.com",
			new PastaHandler(),
			AuthRole.IMPERATOR,
			AuthRole.CONSUL,
			AuthRole.SENATOR,
			AuthRole.CABBAGE_FARMER,
			AuthRole.PONTIFEX_MAXIMUS,
			AuthRole.MILES,
			AuthRole.ROMAN_CITIZEN
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
