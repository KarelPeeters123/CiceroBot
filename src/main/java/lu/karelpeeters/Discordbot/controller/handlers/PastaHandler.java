package lu.karelpeeters.Discordbot.controller.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lu.karelpeeters.Discordbot.controller.handlers.errors.ErrorHandler;
import lu.karelpeeters.Discordbot.discord.Utils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PastaHandler implements DiscordHandler{
	@Override
	public void handle(@NotNull MessageReceivedEvent event) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://www.creepypasta.com/random");
		CloseableHttpResponse response2 = null;
		String responseBody = "";
		try {
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(
						final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			responseBody = client.execute(httpGet, responseHandler);
			Document doc = Jsoup.parse(responseBody);
			Element story = doc.selectFirst("div.entry-content");
			for (Element p : story.getElementsByTag("p")) {
				try {
					if (p.text().length() > 1999) {
						Utils.sendMessageAsBuffer(event.getChannel(), p.text());
					}
					event.getChannel().sendMessage(p.text()).queue();

				} catch (Exception e) {
					if (!e.getMessage().equals("Provided text for message may not be empty")) {
						new ErrorHandler().handleWithMessage(event, e.getMessage());
					}
				}
			}
//			ObjectMapper mapper = new ObjectMapper();
//			Map map = mapper.readValue(responseBody, Map.class);
//			String story = ((String)map.get("output"));
//			List<String> messages = new ArrayList<>();
//			for (int i = 0; i < (story.length() / 2000) + 1; i++) {
//				int startIndex = i * 2000;
//				int endIndex = startIndex + 2000;
//				if (story.length() - startIndex < 2000) {
//					endIndex = story.length();
//				}
//				messages.add(story.substring(startIndex, endIndex));
//			}
//			for (String msg : messages) {
//				event.getChannel().sendMessage(msg).queue();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
