package lu.karelpeeters.Discordbot.controller.handlers.motions;

import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class ResolveMotionHandler implements DiscordHandler {
	String newStatus;
	public ResolveMotionHandler(String newStatus) {
		this.newStatus = newStatus;
	}
	@Override
	public void handle(@Nonnull MessageReceivedEvent event) {
		event.getChannel().sendMessage(newStatus).queue();
	}
}
