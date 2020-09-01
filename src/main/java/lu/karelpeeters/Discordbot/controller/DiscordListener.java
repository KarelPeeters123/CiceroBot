package lu.karelpeeters.Discordbot.controller;

import lu.karelpeeters.Discordbot.controller.handlers.*;
import lu.karelpeeters.Discordbot.controller.handlers.errors.InvalidCommandHandler;
import lu.karelpeeters.Discordbot.controller.handlers.errors.UnauthorisedHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class DiscordListener extends ListenerAdapter {
	private DiscordHandler handler;
	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		if (event.getAuthor().isBot()) return;
		String msg = event.getMessage().getContentDisplay();
		if (!msg.startsWith("!")) return;
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
