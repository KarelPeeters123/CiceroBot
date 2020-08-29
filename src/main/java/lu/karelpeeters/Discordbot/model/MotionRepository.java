package lu.karelpeeters.Discordbot.model;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.OffsetDateTime;
import java.util.*;

public class MotionRepository {
	public static String TABLE_NAME = "motions";
	public static String REGION = "eu-west-2";
	public static void addItemToDynamoDB(Motion motion) {
		HashMap<String, AttributeValue> item_values =
				new HashMap<String,AttributeValue>();
		item_values.put("id", new AttributeValue(String.valueOf(motion.getId())));
		item_values.put("content", new AttributeValue(motion.getContent()));
		item_values.put("author", new AttributeValue(motion.getAuthor()));
		item_values.put("timestamp", new AttributeValue(motion.getTimestamp().toString()));
		item_values.put("status", new AttributeValue(motion.getStatus()));

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
	public static List<Motion> getMotions() {
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();

		DynamoDB dynamoDB = new DynamoDB(ddb);

		Table table = dynamoDB.getTable(TABLE_NAME);

		ScanSpec scanSpec = new ScanSpec();
		List<Motion> motions = new ArrayList<>();
		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);

			Iterator<Item> iter = items.iterator();
			while (iter.hasNext()) {
				Item item = iter.next();
				Motion motion = new Motion(
						item.getLong("id"),
						item.getString("content"),
						item.getString("author"),
						OffsetDateTime.parse(item.getString("timestamp")),
						item.getString("status")
				);
				System.out.println(motion);
				motions.add(motion);
			}

		}
		catch (Exception e) {
			System.err.println("Unable to scan the table:");
			System.err.println(e.getMessage());
		}
		System.out.println(motions.size());
		return sortMotionsById(motions);
	}
	private static List<Motion> sortMotionsById(List<Motion> motions) {
		Collections.sort(motions);
		return motions;
	}
}
