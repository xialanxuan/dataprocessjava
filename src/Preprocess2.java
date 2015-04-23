import java.io.File;
import java.io.FileReader;
import java.util.Set;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Preprocess2 {
	public static void main(String[] args) {
		// String inDir = args[0];
		// String outFile = args[1];

		MongoClient mongoClient = new MongoClient();

		MongoDatabase db = mongoClient.getDatabase("test");

		String[] users = {
				"com:mdsol:users:52344b6e-e6d5-4ec2-b83d-bbc64725d189",
				"com:mdsol:users:foo-bar",
				"com:mdsol:users:120ac13a-1fd3-4910-8aa9-eccee6a12643",
				"com:mdsol:users:ae3292f0-08ca-4a57-ae99-a1a7a36988c0",
				"com:mdsol:users:023e65de-a6d0-45eb-ae9e-c17adad47f45",
				"com:mdsol:users:53cede99-078e-4a05-b04a-39d3cd51f83a",
				"com:mdsol:users:98fc405a-7502-41ad-bd9c-bceecae56d57" };
		/*
		 * String[] devices = {
		 * "com:mdsol:devices:07ff5774-e0ef-58ea-87ee-6c3727c3a3da",
		 * "com:mdsol:devices:354fb2af-d9c0-5ba5-86ab-af96a5c2ac87",
		 * "com:mdsol:devices:42d53bcc-7e0f-5710-baa3-1e97edbadd7d",
		 * "com:mdsol:devices:29109ca7-2ccb-5fa8-a54f-341e42c3fae7",
		 * "com:mdsol:devices:07ff5774-e0ef-58ea-87ee-6c3727c3a3da",
		 * "com:mdsol:devices:29109ca7-2ccb-5fa8-a54f-341e42c3fae7",
		 * "com:mdsol:devices:e4eb4227-c13a-508d-8949-c1a42eb34692" };
		 */

		System.out.println(args[0] + ' ' + args[1]);

		JSONParser parser = new JSONParser();

		String currentfile;
		Object obj;
		JSONObject jsonObject1, jsonObject2, jsonObject3, jsonObject4;
		JSONArray jsonObject5;
		Set<String> keysetuser, keysetdevice, keysettime, keysetcategory;

		try {
			System.out.println(args[0]);
			File folder = new File(args[0]);
			String[] partname = folder.list();

			System.out.println(partname.length);

			for (int i = 0; i < partname.length; i++) {
				currentfile = args[0] + partname[i];
				System.out.println("Start " + currentfile + "...");
				if (partname[i].equals(".DS_Store")) {
					continue;
				}
				obj = parser.parse(new FileReader(currentfile));
				jsonObject1 = (JSONObject) obj;
				keysetuser = jsonObject1.keySet();
				for (String user : keysetuser) {

					MongoCollection<Document> userCollection = db
							.getCollection(user);

					jsonObject2 = (JSONObject) jsonObject1.get(user);
					keysetdevice = jsonObject2.keySet();

					for (String device : keysetdevice) {

						jsonObject3 = (JSONObject) jsonObject2.get(device);
						keysettime = jsonObject3.keySet();
						for (String time : keysettime) {
							if (time.equals("NaN")) {
								System.out.println(time);
								continue;
							}

							if (userCollection.count(new Document("deviceId",
									device).append("timeStamp", time)) == 0) {
								userCollection.insertOne(new Document(
										"deviceId", device).append("timeStamp",
										time).append("categories",
										new Document()));
							}

							jsonObject4 = (JSONObject) jsonObject3.get(time);
							keysetcategory = jsonObject4.keySet();

							for (String category : keysetcategory) {

								jsonObject5 = (JSONArray) jsonObject4
										.get(category);
								Object tmp = jsonObject5
										.get(jsonObject5.size() - 1);

								userCollection.updateOne(new Document(
										"deviceId", device).append("timeStamp",
										time), new Document("$set",
										new Document("categories." + category,
												tmp.toString())));

							}

						}
					}
				}
				
				System.out.println("done");
			}

			
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}