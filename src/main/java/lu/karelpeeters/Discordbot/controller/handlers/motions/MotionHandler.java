package lu.karelpeeters.Discordbot.controller.handlers.motions;

import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import lu.karelpeeters.Discordbot.model.Motion;
import lu.karelpeeters.Discordbot.model.MotionRepository;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import javax.annotation.Nonnull;

public class MotionHandler implements DiscordHandler {
	public void handle(@Nonnull MessageReceivedEvent event) {
		event.getChannel().sendMessage("Motion logged: " + event.getMessage().getContentDisplay().split(" ")[1]).queue();
		Motion motion = new Motion(event);
		MotionRepository.addItemToDynamoDB(motion);
	}

}
