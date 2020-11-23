package lu.karelpeeters.Discordbot.controller.handlers.motions;

import lu.karelpeeters.Discordbot.controller.handlers.DiscordHandler;
import lu.karelpeeters.Discordbot.model.Motion;
import lu.karelpeeters.Discordbot.model.MotionRepository;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import javax.annotation.Nonnull;
import java.util.Timer;
import java.util.TimerTask;

public class MotionHandler implements DiscordHandler {
	public void handle(@Nonnull MessageReceivedEvent event) {
		Motion motion = new Motion(event);
//		MotionRepository.addItemToDynamoDB(motion);
		event.getGuild().getTextChannelsByName("motions", true).get(0).sendMessage(motion.getContent()).queue(
				message -> {
//					Emote aye = message.getGuild().getEmotes().stream().filter(
//							emote -> {
//								return emote.getName().equals("aye");
//							}
//					).findFirst().orElse(null);
//					Emote nay = message.getGuild().getEmotes().stream().filter(
//							emote -> {
//								return emote.getName().equals("nay");
//							}
//					).findFirst().orElse(null);
//					System.out.println(aye);
					message.addReaction(event.getJDA().getEmotesByName("aye", true).get(0)).queue();
					message.addReaction(event.getJDA().getEmotesByName("nay", true).get(0)).queue();
					message.addReaction(event.getJDA().getEmotesByName("veto", true).get(0)).queue();
					VoteResolver resolver = new VoteResolver(message.getId(), event);
					Timer timer = new Timer("motionTimer");
					long seconds = 60;
					long minutes = 60;
					long hours = 24;
					timer.schedule(resolver, 1000 * 20);
				}
		);
	}

}
