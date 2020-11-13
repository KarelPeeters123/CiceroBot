package lu.karelpeeters.Discordbot.model;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DynamoDBConnection {
	public static void addToDB(String region, String tableName, HashMap<String, AttributeValue> values) {
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();

		try {
			ddb.putItem(tableName, values);
		} catch (ResourceNotFoundException e) {
			System.err.format("Error: The table \"%s\" can't be found.\n", tableName);
			System.err.println("Be sure that it exists and that you've typed its name correctly!");
			System.exit(1);
		} catch (AmazonServiceException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	public static boolean idExistsInDB(String region, String tableName, String id) {
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();

		DynamoDB dynamoDB = new DynamoDB(ddb);

		Table table = dynamoDB.getTable(tableName);

		ScanSpec scanSpec = new ScanSpec()
				.withFilterExpression("#id = :id")
				.withNameMap(new NameMap().with("#id", "id"))
				.withValueMap(new ValueMap().with(":id", id));
		List<Motion> motions = new ArrayList<>();
		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);

			Iterator<Item> iter = items.iterator();
			return iter.hasNext();

		}
		catch (Exception e) {
			System.err.println("Unable to scan the table:");
			System.err.println(e.getMessage());
		}
		return false;
	}
	public static void deleteItem(String region, String tableName, String id) {
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(region).build();

		DynamoDB dynamoDB = new DynamoDB(ddb);

		Table table = dynamoDB.getTable(tableName);
		DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey(new PrimaryKey("id", id));
		try {
			System.out.println("Attempting a conditional delete...");
			table.deleteItem(deleteItemSpec);
			System.out.println("DeleteItem succeeded");
		}
		catch (Exception e) {
			System.err.println("Unable to delete item: " + id);
			e.printStackTrace();
		}
	}
}
