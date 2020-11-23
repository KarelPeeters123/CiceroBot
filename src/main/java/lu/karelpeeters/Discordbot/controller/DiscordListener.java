package lu.karelpeeters.Discordbot.controller;

import lu.karelpeeters.Discordbot.controller.handlers.*;
import lu.karelpeeters.Discordbot.controller.handlers.errors.InvalidCommandHandler;
import lu.karelpeeters.Discordbot.controller.handlers.errors.UnauthorisedHandler;
import lu.karelpeeters.Discordbot.model.ActivityRepository;
import lu.karelpeeters.Discordbot.model.UserActivity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class DiscordListener extends ListenerAdapter {
	private DiscordHandler handler;
	public static String ELECTION = "";
	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		if (event.getMember() != null && event.getMember().getRoles().contains(event.getGuild().getRolesByName("Roman Subject", true).get(0))) {
			try {
				ActivityRepository.incrementUserActivity(event.getMember().getId(), event);
			} catch (NullPointerException e) {
				UserActivity activity = new UserActivity(event.getMember().getId(), 1);
				ActivityRepository.addItemToDynamoDB(activity);
			}
		}
		if (event.getAuthor().isBot()) return;
		String msg = event.getMessage().getContentDisplay();
		if (!msg.startsWith("-")) return;
		Command command;
		try {
			command = Command.getCommandWithPrefix(msg.split(" ")[0]);
		} catch (NoSuchCommandException e) {
			command = Command.INVALIDCOMMAND;
		}
		handler = command.getHandler();
		if (handler == null) {
			handler = new InvalidCommandHandler();
		}
		System.out.println("auth: " + isAuthorised(command, event.getMember()));
		if (isAuthorised(command, event.getMember())) {
			handler.handle(event);
		} else {
			new UnauthorisedHandler().handle(event);
		}
	}

	@Override
	public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
//		if (event.getChannel().equals(event.getGuild().getTextChannelsByName("motions", true).get(0))
//				&& (
//						event.getMember().getRoles().contains(event.getJDA().getRolesByName("Consul", true).get(0))
//								|| event.getMember().getRoles().contains(event.getJDA().getRolesByName("Imperator", true).get(0))
//				)) {
//			event.getMessageId()
//		}
		if(!DiscordListener.ELECTION.isEmpty()
				&& event.getChannel().equals(event.getGuild().getTextChannelsByName("elections", true).get(0))
				&& event.getReaction().getReactionEmote().getEmote().getName().equals("vote")
				&& !event.getUser().isBot()) {
			event.getUser().openPrivateChannel().queue((channel) ->
			{
				channel.sendMessage("http://rrf-vote.s3-website.eu-west-2.amazonaws.com/" + DiscordListener.ELECTION + ".html?id=" + event.getMember().getId()).queue();
			});
		}
	}

	private boolean isAuthorised(Command command, Member member) {
//		for
		System.out.println(command.getAuthRoles().length);
		for (AuthRole role : command.getAuthRoles()) {
			if (hasRole(member, AuthRoleToRole(role, member.getGuild()))) {
				return true;
			}
		}
		return false;
	}
	private boolean hasRole(Member member, Role role) {
		return member.getRoles().contains(role);
	}
	private Role AuthRoleToRole(AuthRole authRole, Guild guild) {
		return guild.getRolesByName(authRole.getRole(), false).get(0);
	}
}
