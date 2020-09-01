package lu.karelpeeters.Discordbot.controller.handlers.errors;

import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class UnauthorisedHandler implements DiscordHandler {
	@Override
	public void handle(@Nonnull MessageReceivedEvent event) {
		event.getChannel().sendMessage("You are not authorised for this command!").queue();
	}
}
