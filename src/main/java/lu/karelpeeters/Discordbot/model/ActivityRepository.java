package lu.karelpeeters.Discordbot.model;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import lu.karelpeeters.Discordbot.controller.handlers.ApplyHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.OffsetDateTime;
import java.util.*;

public class ActivityRepository {
	public static String TABLE_NAME = "User_Activity";
	public static String REGION = "eu-west-2";
	public static void addItemToDynamoDB(UserActivity userActivity) {
		HashMap<String, AttributeValue> item_values =
				new HashMap<String,AttributeValue>();
		item_values.put("ID", new AttributeValue(userActivity.id));
		item_values.put("messages", new AttributeValue(String.valueOf(userActivity.messages)));

		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();

		try {
			ddb.putItem(TABLE_NAME, item_values);
		} catch (ResourceNotFoundException e) {
			System.err.format("Error: The table \"%s\" can't be found.\n", TABLE_NAME);
			System.err.println("Be sure that it exists and that you've typed its name correctly!");
			System.exit(1);
		} catch (AmazonServiceException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	public static long getNumberOfMotions() {
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();

		try {
			TableDescription table_info =
					ddb.describeTable(TABLE_NAME).getTable();

			if (table_info != null) {
				return table_info.getItemCount();
			}
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}
		return -1;
	}
	public static void incrementUserActivity(String id, MessageReceivedEvent event) {
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();

		DynamoDB dynamoDB = new DynamoDB(ddb);

		Table table = dynamoDB.getTable(TABLE_NAME);

		GetItemSpec itemSpec = new GetItemSpec().withPrimaryKey("ID", id);
		Item item = table.getItem(itemSpec);
		UserActivity activity = new UserActivity(item.get("ID").toString(), Integer.parseInt(item.get("messages").toString()));
		if (activity.messages >= 50) {
			System.out.println(activity.messages);
			new ApplyHandler().handle(event);
		}
		activity.increment();
		HashMap<String, AttributeValue> item_values =
				new HashMap<String,AttributeValue>();
		item_values.put("ID", new AttributeValue(activity.id));
		item_values.put("messages", new AttributeValue(String.valueOf(activity.messages)));
		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("ID", id).withAttributeUpdate(new AttributeUpdate("messages").put(activity.messages));
		table.updateItem(updateItemSpec);
	}
	public static void resolveMotion() {

	}
	private static List<Motion> sortMotionsById(List<Motion> motions) {
		Collections.sort(motions);
		return motions;
	}
}
