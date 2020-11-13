package lu.karelpeeters.Discordbot.discord;

import lu.karelpeeters.Discordbot.controller.handlers.Command;
import lu.karelpeeters.Discordbot.model.Candidate;
import lu.karelpeeters.Discordbot.model.Motion;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;


public class Embed {
	public static EmbedBuilder getCommandsEmbedBuilder() {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Commands", null);
		eb.setColor(Color.red);
		eb.setDescription("List of Commands");
		for (Command command : Command.values()) {
			eb.addField(command.getPrefix(), command.getDescription(), false);
		}
		return eb;
	}
	public static EmbedBuilder getMotionsEmbedBuilder(List<Motion> motions) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Motions", null);
		eb.setColor(Color.red);
		eb.setDescription("List of all motions");
		for (Motion motion : motions) {
			eb.addField(
					String.valueOf(motion.getId()),
					motion.getContent() + "\n" +
							motion.getTimestamp().toString() + "\n" +
							motion.getAuthor() + "\n" +
							motion.getStatus(),
					false);
		}
		return eb;
	}
	public static EmbedBuilder getBallotEmbedBuilder(List<Candidate> candidates, String position) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(position + " Ballot", null);
		eb.setColor(Color.red);
		eb.setDescription("List of candidates running for " + position);
		for (Candidate candidate : candidates) {
			eb.addField(
					candidate.discordName,
					candidate.currentNickname,
					false);
		}
		return eb;
	}

}
