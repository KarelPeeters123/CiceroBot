package lu.karelpeeters.Discordbot.controller.handlers.motions;

import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import lu.karelpeeters.Discordbot.discord.Embed;
import lu.karelpeeters.Discordbot.model.MotionRepository;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class GetMotionsHandler implements DiscordHandler {
	@Override
	public void handle(@Nonnull MessageReceivedEvent event) {
		event.getChannel().sendMessage(
				Embed.getMotionsEmbedBuilder(
						MotionRepository.getMotions()
				).build()
		).queue();
	}
}
