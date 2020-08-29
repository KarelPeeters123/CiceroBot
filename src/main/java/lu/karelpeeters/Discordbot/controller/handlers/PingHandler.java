package lu.karelpeeters.Discordbot.controller.handlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class PingHandler implements DiscordHandler {
	public void handle(@Nonnull MessageReceivedEvent event) {
		event.getChannel().sendMessage("Pong!").queue();
	}
}
