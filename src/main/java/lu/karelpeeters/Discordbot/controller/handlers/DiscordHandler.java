package lu.karelpeeters.Discordbot.controller.handlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public interface DiscordHandler {
	void handle(@Nonnull MessageReceivedEvent event);
}
