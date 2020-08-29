package lu.karelpeeters.Discordbot.controller.handlers;

import lu.karelpeeters.Discordbot.discord.Embed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class CommandsHandler implements DiscordHandler {
	@Override
	public void handle(@Nonnull MessageReceivedEvent event) {
		String output = "";
		for (Command command : Command.values()) {
			output += command.getPrefix() + "\t\t\t\t" + command.getDescription() + '\n';
		}
		event.getChannel().sendMessage(Embed.getCommandsEmbedBuilder().build()).queue();
	}
}
