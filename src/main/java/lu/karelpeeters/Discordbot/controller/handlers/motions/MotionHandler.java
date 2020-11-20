package lu.karelpeeters.Discordbot.controller.handlers.motions;

import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import lu.karelpeeters.Discordbot.model.Motion;
import lu.karelpeeters.Discordbot.model.MotionRepository;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import javax.annotation.Nonnull;
import java.util.Timer;
import java.util.TimerTask;

public class MotionHandler implements DiscordHandler {
	public void handle(@Nonnull MessageReceivedEvent event) {
		event.getChannel().sendMessage("Motion logged: " + event.getMessage().getContentDisplay().split(" ")[1]).queue();
		Motion motion = new Motion(event);
//		MotionRepository.addItemToDynamoDB(motion);
		event.getGuild().getTextChannelsByName("motions", true).get(0).sendMessage(motion.getContent()).queue(
				message -> {
					VoteResolver resolver = new VoteResolver(message.getId(), event);
					Timer timer = new Timer("motionTimer");
					long seconds = 60;
					long minutes = 60;
					long hours = 24;
					timer.schedule(resolver, 1000 * seconds * minutes * hours);
				}
		);
	}

}
