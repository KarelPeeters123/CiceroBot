package lu.karelpeeters.Discordbot.controller.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomStoryHandler implements DiscordHandler {
	@Override
	public void handle(@NotNull MessageReceivedEvent event) {
		event.getChannel().sendMessage("writing story...").queue();
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("https://api.deepai.org/api/text-generator");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("text", event.getMessage().getContentDisplay().split(" ", 2)[1]));
//		nvps.add(new BasicNameValuePair("password", "secret"));
		CloseableHttpResponse response2 = null;
		httpPost.addHeader("Api-Key", "ce079f47-430d-4377-bcd9-31dbcd2863ad");
		String responseBody = "";
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
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
			responseBody = client.execute(httpPost, responseHandler);
			System.out.println(responseBody);
			ObjectMapper mapper = new ObjectMapper();
			Map map = mapper.readValue(responseBody, Map.class);
			String story = ((String)map.get("output"));
			List<String> messages = new ArrayList<>();
			for (int i = 0; i < (story.length() / 2000) + 1; i++) {
				int startIndex = i * 2000;
				int endIndex = startIndex + 2000;
				if (story.length() - startIndex < 2000) {
					endIndex = story.length();
				}
				messages.add(story.substring(startIndex, endIndex));
			}
			for (String msg : messages) {
				event.getChannel().sendMessage(msg).queue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			System.out.println(response2.getStatusLine());
//			System.out.println(responseBody);
//			HttpEntity entity2 = response2.getEntity();
//			// do something useful with the response body
//			// and ensure it is fully consumed
//			EntityUtils.consume(entity2);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		event.getChannel().sendMessage(response2.toString());
	}
}
