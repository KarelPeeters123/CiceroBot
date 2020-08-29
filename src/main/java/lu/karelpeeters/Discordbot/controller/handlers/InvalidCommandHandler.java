package lu.karelpeeters.Discordbot.controller.handlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class InvalidCommandHandler implements DiscordHandler {
	public void handle(@Nonnull MessageReceivedEvent event) {
		event.getChannel().sendMessage("This is not a valid command").queue();
	}
}
