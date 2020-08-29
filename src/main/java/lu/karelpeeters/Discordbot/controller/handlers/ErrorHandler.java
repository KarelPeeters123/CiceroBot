package lu.karelpeeters.Discordbot.controller.handlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class ErrorHandler implements DiscordHandler {
	public void handle(@Nonnull MessageReceivedEvent event) {
		event.getChannel().sendMessage("An unknown error occured, contact @2Girls1Lane to resolve the issue").queue();
	}
}
