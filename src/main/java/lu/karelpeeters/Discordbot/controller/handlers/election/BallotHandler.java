package lu.karelpeeters.Discordbot.controller.handlers.election;

import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import lu.karelpeeters.Discordbot.discord.Embed;
import lu.karelpeeters.Discordbot.model.CandidateRepository;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class BallotHandler implements DiscordHandler {
	@Override
	public void handle(@Nonnull MessageReceivedEvent event) {
		event.getChannel().sendMessage(Embed.getBallotEmbedBuilder(CandidateRepository.getCandidates("Consul_Ballot"), "Consul").build()).queue();
		event.getChannel().sendMessage(Embed.getBallotEmbedBuilder(CandidateRepository.getCandidates("Senator_Ballot"), "Senator").build()).queue();
		event.getChannel().sendMessage(Embed.getBallotEmbedBuilder(CandidateRepository.getCandidates("Centurion_Ballot"), "Centurion").build()).queue();
	}
}
