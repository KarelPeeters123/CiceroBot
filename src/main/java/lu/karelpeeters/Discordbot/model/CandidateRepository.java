package lu.karelpeeters.Discordbot.model;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
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

public class CandidateRepository {
	public static String REGION = "eu-west-2";
	public static String CONSUL_TABLE = "Consul_Ballot";
	public static void addItemToDynamoDB(Candidate candidate, String tableName) {
		HashMap<String, AttributeValue> item_values =
				new HashMap<String, AttributeValue>();
		item_values.put("id", new AttributeValue(candidate.id));
		item_values.put("discord name", new AttributeValue(candidate.discordName));
		item_values.put("current nickname", new AttributeValue(candidate.currentNickname));
		item_values.put("profile picture url", new AttributeValue(candidate.profileURL));

		try {
			DynamoDBConnection.addToDB(REGION, tableName, item_values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static List<Candidate> getCandidates(String tableName) {
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();

		DynamoDB dynamoDB = new DynamoDB(ddb);

		Table table = dynamoDB.getTable(tableName);

		ScanSpec scanSpec = new ScanSpec();
		List<Candidate> candidates = new ArrayList<>();
		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);

			Iterator<Item> iter = items.iterator();
			while (iter.hasNext()) {
				Item item = iter.next();
				Candidate candidate = new Candidate(
						item.getString("id"),
						item.getString("current nickname"),
						item.getString("discord name"),
						item.getString("profile picture url")
				);
				candidates.add(candidate);
			}

		}
		catch (Exception e) {
			System.err.println("Unable to scan the table:");
			System.err.println(e.getMessage());
		}
		System.out.println(candidates.size());
		return candidates;
	}
	public static boolean idExistsInDB(String tableName, String id) {
		return DynamoDBConnection.idExistsInDB(REGION, tableName, id);
	}
	public static void deleteItem(String tableName, String id) {
		DynamoDBConnection.deleteItem(REGION, tableName, id);
	}
}
