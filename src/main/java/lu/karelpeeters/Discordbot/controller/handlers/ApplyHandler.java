package lu.karelpeeters.Discordbot.controller.handlers;

import lu.karelpeeters.Discordbot.controller.handlers.errors.ErrorHandler;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.pagination.MessagePaginationAction;

import javax.annotation.Nonnull;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ApplyHandler implements DiscordHandler {
	public static long REQUIRED_NUMBER_OF_MESSAGES = 20;
	public static long REQUIRED_HOURS = 36;
	@Override
	public void handle(@Nonnull MessageReceivedEvent event) {
//		System.out.println(event.getJDA().getRolesByName("Roman Citizen", true).get(0));
		event.getGuild().addRoleToMember(
				event.getMember().getId(),
				event.getJDA().getRolesByName("Roman Citizen", true).get(0)
		).queue();
		event.getGuild().removeRoleFromMember(event.getMember(), event.getJDA().getRolesByName("Roman Subject", true).get(0)).queue();
		event.getGuild().removeRoleFromMember(event.getMember(), event.getJDA().getRolesByName("Auxillary Forces", true).get(0)).queue();
		event.getMessage().getChannel().sendMessage("Salve " + event.getAuthor().getAsMention() + ",\nthe senate of Rome recognized your dutiful service to the empire and by decree of the emperor himself, you are hereby granted roman citizenship with all right and privileges attached, you can now join our prestigious legions, instead of serving with the auxillary.\n ROMA INVICTA https://media.discordapp.net/attachments/559357924355473438/781440162335817728/untitled.png").queue();
	}
	public boolean hasSufficientMessages(long amount) {
		System.out.println("messages: " + amount);
		return amount >= REQUIRED_NUMBER_OF_MESSAGES;
	}
	public boolean hasSufficientTimeSpent(OffsetDateTime timeJoined) {
		long hours = timeJoined.until(OffsetDateTime.now(), ChronoUnit.HOURS);
		System.out.println("hours: " + hours);
		return hours >= REQUIRED_HOURS;
	}
}
