import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Preprocess2 {
	public static void main(String [] args) {
		String inDir = args[0];
		String outFile = args[1];

		System.out.println(inDir + ' ' + outFile);

		HashMap<String, HashMap<String, HashMap<String, HashMap<String, JSONArray>>>> users =
		    new HashMap<String, HashMap<String, HashMap<String, HashMap<String, JSONArray>>>>();

		HashMap<String, HashMap<String, HashMap<String, JSONArray>>> devices;
		HashMap<String, HashMap<String, JSONArray>> times;
		HashMap<String, JSONArray> categories;
		JSONArray values;

		JSONParser parser = new JSONParser();
		File f = new File(outFile);

		String currentfile;
		Object obj;
		JSONObject jsonObject1, jsonObject2, jsonObject3, jsonObject4;
		JSONArray jsonObject5;
		Set<String> keysetuser, keysetdevice, keysettime, keysetcategory;

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));

			File folder = new File(inDir);
			String [] partname = folder.list();

			System.out.println(partname[0]);


			for (int i = 0; i < partname.length; i++) {

				//For Mac OS
				if (partname[i].equals(".DS_Store")) {
					continue;
				}

				currentfile = inDir + partname[i];
				obj = parser.parse(new FileReader(currentfile));
				jsonObject1 = (JSONObject) obj;
				keysetuser = jsonObject1.keySet();
				for (String user : keysetuser) {
					if (!users.containsKey(user)) {
						users.put(user, new HashMap<String, HashMap<String, HashMap<String, JSONArray>>>());
					}

					devices = users.get(user);

					jsonObject2 = (JSONObject) jsonObject1.get(user);
					keysetdevice = jsonObject2.keySet();
					for (String device : keysetdevice) {

						if (!devices.containsKey(device)) {
							devices.put(device, new HashMap<String, HashMap<String, JSONArray>>() );
						}

						times = devices.get(device);

						jsonObject3 = (JSONObject) jsonObject2.get(device);
						keysettime = jsonObject3.keySet();
						for (String time : keysettime) {

							if (!times.containsKey(time)) {
								times.put(time, new HashMap<String, JSONArray>());
							}

							categories = times.get(time);

							jsonObject4 = (JSONObject)jsonObject3.get(time);
							keysetcategory = jsonObject4.keySet();
							for (String category : keysetcategory) {
								if (!categories.containsKey(category)) {
									categories.put(category, new JSONArray());
								}

								values = categories.get(category);

								jsonObject5 = (JSONArray)jsonObject4.get(category);
								values.addAll(jsonObject5);

							}

						}
					}

				}
			}
			bw.write(users.toString());
			bw.close();
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

