package lu.karelpeeters.Discordbot.controller;

import lu.karelpeeters.Discordbot.controller.handlers.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class DiscordListener extends ListenerAdapter {
	private DiscordHandler handler;
	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		if (event.getAuthor().isBot()) return;
		String msg = event.getMessage().getContentDisplay();
		if (!msg.startsWith("!")) return;
		Command command;
		try {
			command = Command.getCommandWithPrefix(msg.split(" ")[0]);
		} catch (NoSuchCommandException e) {
			command = Command.INVALIDCOMMAND;
		}
		handler = command.getHandler();
		if (handler == null) {
			handler = new InvalidCommandHandler();
		}
		if (command.getAuthRoles().length == 0) {
			handler.handle(event);
		} else {
			new UnauthorisedHandler().handle(event);
		}
	}
}
