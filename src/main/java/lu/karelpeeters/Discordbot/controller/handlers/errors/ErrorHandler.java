package lu.karelpeeters.Discordbot.controller.handlers.errors;

import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class ErrorHandler implements DiscordHandler {
	public void handle(@Nonnull MessageReceivedEvent event) {
		event.getChannel().sendMessage("An unknown error occured, contact @2Girls1Lane to resolve the issue").queue();
	}
	public void handleWithMessage(@Nonnull MessageReceivedEvent event, String stacktrace) {
		event.getChannel().sendMessage("An unknown error occured, contact @2Girls1Lane to resolve the issue").queue();
		event.getChannel().sendMessage("Nature of error: **" + stacktrace + "**").queue();
	}
}
