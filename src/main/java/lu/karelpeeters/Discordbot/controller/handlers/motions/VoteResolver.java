package lu.karelpeeters.Discordbot.controller.handlers.motions;

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
		event.getGuild().getTextChannelsByName("motions", true).get(0)
				.sendMessage("The vote for the motion: **" + event.getMessage().getContentDisplay().substring(8) + "** is over!").queue();
	}
}
