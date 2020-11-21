package lu.karelpeeters.Discordbot.controller.handlers.motions;

import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.TimerTask;

public class VoteResolver extends TimerTask {
	public String messageID;
	public MessageReceivedEvent event;

	public VoteResolver(String messageID, MessageReceivedEvent event) {
		this.messageID = messageID;
		this.event = event;
	}

	@Override
	public void run() {
		event.getGuild().getTextChannelsByName("motions", true).get(0).retrieveMessageById(messageID).queue(
				message -> {
					long ayeCount = message.getReactions().stream().filter(
							messageReaction -> {
//								System.out.println(messageReaction.getReactionEmote().getName());
//								System.out.println(messageReaction.getReactionEmote().getName().equals("aye"));
								return messageReaction.getReactionEmote().getName().equals("aye");
							}
					).count();
					long nayCount = message.getReactions().stream().filter(
							messageReaction -> {
//								System.out.println(messageReaction.getReactionEmote().getName());
//								System.out.println(messageReaction.getReactionEmote().getName().equals("nay"));
								return messageReaction.getReactionEmote().getName().equals("nay");
							}
					).count();

					event.getGuild().getTextChannelsByName("motions", true).get(0)
							.sendMessage(
									"The motion: **"
											+ event.getMessage().getContentDisplay().substring(8)
											+ "** "
											+ getPassedPhrase(ayeCount, nayCount)
											+ " "
											+ ayeCount + " - " + nayCount
							).queue();
				}
		);
	}
	public boolean hasPassed(long ayeCount, long nayCount) {
		return ayeCount >= 4 && ayeCount > nayCount;
	}
	public String getPassedPhrase(long ayeCount, long nayCount) {
		if (hasPassed(ayeCount, nayCount)) {
			return "has passed!";
		} else {
			return "has failed!";
		}
	}
}
