package lu.karelpeeters.Discordbot.controller;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main{
	public static void main(String[] args) throws LoginException {
		String token = "NTE4Nzk3NTUyMzA1MzA3NjQ5.XAPt3w.aHxwt4XoLRGLwVmpJCVKeFDW97Q";
		JDABuilder builder = JDABuilder.createDefault(token);
		builder.addEventListeners(new DiscordListener());
		builder.build();
	}
}
