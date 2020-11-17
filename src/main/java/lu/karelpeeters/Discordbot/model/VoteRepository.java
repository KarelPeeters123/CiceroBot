package lu.karelpeeters.Discordbot.model;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;

import java.util.*;
import java.util.function.Consumer;

public class VoteRepository {
	public static final String REGION = "eu-west-2";
	public static final String CONSUL_TABLE = "Consul_Votes";
	public static final String SENATOR_TABLE = "Senator_Votes";
	public static final String CENTURION_TABLE = "Centurion_Votes";

	public static String[] getConsulWinners() {
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();

		DynamoDB dynamoDB = new DynamoDB(ddb);

		Table table = dynamoDB.getTable(CONSUL_TABLE);

		ScanSpec scanSpec = new ScanSpec();
		List<Candidate> candidates = new ArrayList<>();
		Map<String, Integer> results = new HashMap<>();
		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);

			Iterator<Item> iter = items.iterator();
			System.out.println("HASITEMS: " + iter.hasNext());
			Item item = iter.next();
			results.put(item.getString("first vote"), 3);
			results.put(item.getString("second vote"), 2);
			results.put(item.getString("third vote"), 3);
			while (iter.hasNext()) {
				item = iter.next();
				results.put(item.getString("first vote"), 3 + results.get(item.getString("first vote")));
				results.put(item.getString("second vote"), 2 + results.get(item.getString("second vote")));
				results.put(item.getString("third vote"), 3 + results.get(item.getString("third vote")));
			}

		}
		catch (Exception e) {
			System.err.println("Unable to scan the table:");
			e.printStackTrace();
		}
		String[] output =  new String[2];
		int maxValue = Integer.MIN_VALUE;
		String winnerID = "";
		for (String id : results.keySet()) {
			if (results.get(id) >= maxValue) {
				maxValue = results.get(id);
				winnerID = id;
			}
		}
		output[0] = winnerID;
		results.remove(winnerID);
		maxValue = Integer.MIN_VALUE;
		winnerID = "";
		for (String id : results.keySet()) {
			if (results.get(id) >= maxValue) {
				maxValue = results.get(id);
				winnerID = id;
			}
		}
		output[1] = winnerID;
		return output;
//		return new String[2];
	}
	public static String[] getSenatorWinners() {
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();

		DynamoDB dynamoDB = new DynamoDB(ddb);

		Table table = dynamoDB.getTable(SENATOR_TABLE);

		ScanSpec scanSpec = new ScanSpec();
		Map<String, Integer> results = new HashMap<>();
		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);

			Iterator<Item> iter = items.iterator();
			System.out.println("HASITEMS: " + iter.hasNext());
			Item item = iter.next();
			results.put(item.getString("first vote"), 3);
			results.put(item.getString("second vote"), 2);
			results.put(item.getString("third vote"), 3);
			while (iter.hasNext()) {
				item = iter.next();
				results.put(item.getString("first vote"), 3 + results.get(item.getString("first vote")));
				results.put(item.getString("second vote"), 2 + results.get(item.getString("second vote")));
				results.put(item.getString("third vote"), 3 + results.get(item.getString("third vote")));
			}

		}
		catch (Exception e) {
			System.err.println("Unable to scan the table:");
			e.printStackTrace();
		}
		String[] output =  new String[2];
		for(int i = 0; i < 3; i++) {
			int maxValue = Integer.MIN_VALUE;
			String winnerID = "";
			for (String id : results.keySet()) {
				if (results.get(id) >= maxValue) {
					maxValue = results.get(id);
					winnerID = id;
				}
			}
			output[i] = winnerID;
			results.remove(winnerID);
		}
		return output;
	}
	public static String[] getCenturionWinners() {
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();

		DynamoDB dynamoDB = new DynamoDB(ddb);

		Table table = dynamoDB.getTable(CENTURION_TABLE);

		ScanSpec scanSpec = new ScanSpec();
		Map<String, Integer> results = new HashMap<>();
		try {
			ItemCollection<ScanOutcome> items = table.scan(scanSpec);

			Iterator<Item> iter = items.iterator();
			System.out.println("HASITEMS: " + iter.hasNext());
			Item item = iter.next();
			results.put(item.getString("first vote"), 3);
			results.put(item.getString("second vote"), 2);
			results.put(item.getString("third vote"), 3);
			while (iter.hasNext()) {
				item = iter.next();
				results.put(item.getString("first vote"), 3 + results.get(item.getString("first vote")));
				results.put(item.getString("second vote"), 2 + results.get(item.getString("second vote")));
				results.put(item.getString("third vote"), 3 + results.get(item.getString("third vote")));
			}

		}
		catch (Exception e) {
			System.err.println("Unable to scan the table:");
			e.printStackTrace();
		}
		String[] output =  new String[2];
		for(int i = 0; i < 5; i++) {
			int maxValue = Integer.MIN_VALUE;
			String winnerID = "";
			for (String id : results.keySet()) {
				if (results.get(id) >= maxValue) {
					maxValue = results.get(id);
					winnerID = id;
				}
			}
			output[i] = winnerID;
			results.remove(winnerID);
		}
		return output;
	}
}
