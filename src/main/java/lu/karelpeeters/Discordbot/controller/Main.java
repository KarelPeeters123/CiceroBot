package lu.karelpeeters.Discordbot.controller;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.Map;

public class Main{
	public static void main(String[] args) throws LoginException {
		String token = "";
		Map<String, String> env = System.getenv();
		token = env.get("TOKEN");
		JDABuilder builder = JDABuilder.createDefault(token);
		builder.addEventListeners(new DiscordListener());
		builder.build();
	}
}
