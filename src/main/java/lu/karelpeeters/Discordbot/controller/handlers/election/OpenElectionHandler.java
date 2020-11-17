package lu.karelpeeters.Discordbot.controller.handlers.election;

import lu.karelpeeters.Discordbot.controller.DiscordListener;
import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class OpenElectionHandler implements DiscordHandler {
	@Override
	public void handle(@NotNull MessageReceivedEvent event) {
		DiscordListener.ELECTION = event.getMessage().getContentDisplay().split(" ")[1];
		event.getChannel()
				.sendMessage("The "
						+ DiscordListener.ELECTION
						+ " election has started! react the VOTE emoji below, to get the ballot sent to your DM!"
				).queue((message -> {
					message.addReaction(event.getJDA().getEmotesByName("vote", true).get(0)).queue();
		}));
	}
}
