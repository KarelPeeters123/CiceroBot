package lu.karelpeeters.Discordbot.controller.handlers.election;

import lu.karelpeeters.Discordbot.controller.DiscordListener;
import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import lu.karelpeeters.Discordbot.model.VoteRepository;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class CloseElectionHandler implements DiscordHandler {
	@Override
	public void handle(@NotNull MessageReceivedEvent event) {
		event.getChannel().sendMessage("The " + DiscordListener.ELECTION + " election has closed! The results will be in soon!").queue();
		if (DiscordListener.ELECTION.equals("consul")) {
			event.getChannel().sendMessage(
					"The winners of the election are "
							+ event.getGuild().getMemberById(VoteRepository.getConsulWinners()[0]).getAsMention()
							+ " and "
							+ event.getGuild().getMemberById(VoteRepository.getConsulWinners()[1]).getAsMention()
			).queue();
		} else if (DiscordListener.ELECTION.equals("senator")) {
			event.getChannel().sendMessage(
					"The winners of the election are "
							+ event.getGuild().getMemberById(VoteRepository.getSenatorWinners()[0]).getAsMention()
							+ " , "
							+ event.getGuild().getMemberById(VoteRepository.getSenatorWinners()[1]).getAsMention()
							+ " and "
							+ event.getGuild().getMemberById(VoteRepository.getSenatorWinners()[2]).getAsMention()
			).queue();
		} else if (DiscordListener.ELECTION.equals("centurion")) {
			event.getChannel().sendMessage(
					"The winners of the election are "
							+ event.getGuild().getMemberById(VoteRepository.getSenatorWinners()[0]).getAsMention()
							+ " , "
							+ event.getGuild().getMemberById(VoteRepository.getSenatorWinners()[1]).getAsMention()
							+ " , "
							+ event.getGuild().getMemberById(VoteRepository.getSenatorWinners()[2]).getAsMention()
							+ " , "
							+ event.getGuild().getMemberById(VoteRepository.getSenatorWinners()[3]).getAsMention()
							+ " and "
							+ event.getGuild().getMemberById(VoteRepository.getSenatorWinners()[4]).getAsMention()
			).queue();
		}
		DiscordListener.ELECTION = "";
	}
}
