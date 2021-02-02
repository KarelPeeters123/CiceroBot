package lu.karelpeeters.Discordbot.controller.handlers.election;

import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import lu.karelpeeters.Discordbot.discord.Embed;
import lu.karelpeeters.Discordbot.model.Candidate;
import lu.karelpeeters.Discordbot.model.CandidateRepository;
import lu.karelpeeters.Discordbot.model.DynamoDBConnection;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class RegisterConsulHandler implements DiscordHandler {
	public final static String TABLE_NAME = "Consul_Ballot";
	@Override
	public void handle(@Nonnull MessageReceivedEvent event) {
		if (CandidateRepository.idExistsInDB(TABLE_NAME, event.getMember().getId())) {
			CandidateRepository.deleteItem(TABLE_NAME, event.getMember().getId());
			event.getChannel().sendMessage(event.getAuthor().getAsMention() + " You have deregistered from the Consular election!").queue();
		} else {
			Candidate candidate = new Candidate(
					event.getMember().getId(),
					event.getAuthor().getName(),
					event.getMember().getNickname(),
					event.getAuthor().getAvatarUrl()
			);
			CandidateRepository.addItemToDynamoDB(candidate, TABLE_NAME);
			event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Thank you for registering for the Consular election!").queue();
		}
//		event.getGuild().getTextChannelsByName("elections", true).get(0)
//				.retrieveMessageById("782138322432557077").queue(
//						message -> {
//							message.editMessage(
//									Embed.getBallotEmbedBuilder(
//											CandidateRepository.getCandidates("Consul_Ballot"),
//											"Consul"
//									).build()
//							).queue();
//						}
//		);
	}
}
