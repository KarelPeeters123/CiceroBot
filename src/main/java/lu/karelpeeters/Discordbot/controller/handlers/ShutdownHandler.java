package lu.karelpeeters.Discordbot.controller.handlers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class ShutdownHandler implements DiscordHandler {
	@Override
	public void handle(@NotNull MessageReceivedEvent event) {
		event.getJDA().shutdownNow();
	}
}
