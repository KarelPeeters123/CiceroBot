package lu.karelpeeters.Discordbot.discord;

import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	public static void sendMessageAsBuffer(MessageChannel channel, String message) {
		List<String> messages = new ArrayList<>();
		for (int i = 0; i < (message.length() / 2000) + 1; i++) {
			int startIndex = i * 2000;
			int endIndex = startIndex + 2000;
			if (message.length() - startIndex < 2000) {
				endIndex = message.length();
			}
			messages.add(message.substring(startIndex, endIndex));
		}
		for (String msg : messages) {
			channel.sendMessage(msg).queue();
		}
	}
}
