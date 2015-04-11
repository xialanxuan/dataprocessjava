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


public class Preprocess {
	public static void main(String [] args){
		  JSONParser parser = new JSONParser();
		  HashSet<String> users = new HashSet<String>();
		  try {
				File folder = new File("/Users/siyang/Documents/itp/dataprocess/");
				String [] partname= folder.list();
				for(int i = 9; i <partname.length; i++){
					String currentfile = "/Users/siyang/Documents/itp/dataprocess/"+partname[i];
					System.out.println(currentfile);
					Object obj = parser.parse(new FileReader(currentfile));
					JSONObject jsonObject1 = (JSONObject) obj;
					Set<String> keysetuser = jsonObject1.keySet();
					for (String user : keysetuser) { 
						System.out.println(user);
						users.add(user);
					}
				}
				System.out.println(users);
		  }
		  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	
	
  public static void main1(String[] args){
	  HashMap<String, HashMap<String, HashMap<String, HashMap<String, JSONArray>>>> users = 
			  new HashMap<String, HashMap<String, HashMap<String, HashMap<String, JSONArray>>>>();
	  
	  HashMap<String, HashMap<String, HashMap<String, JSONArray>>> devices = 
			  new HashMap<String, HashMap<String, HashMap<String, JSONArray>>>();
	  HashMap<String, HashMap<String, JSONArray>> times = new HashMap<String, HashMap<String, JSONArray>>();
	  HashMap<String, JSONArray> categories = new HashMap<String, JSONArray>();
	  
	  JSONParser parser = new JSONParser();
      File f=new File("output.txt");

	  try {
	        BufferedWriter bw=new BufferedWriter(new FileWriter(f));

			File folder = new File("/Users/siyang/Documents/itp/dataprocess/");
			String [] partname= folder.list();
			for(int i = 1; i <partname.length; i++){
				String currentfile = "/Users/siyang/Documents/itp/dataprocess/"+partname[i];
				Object obj = parser.parse(new FileReader(currentfile));
				JSONObject jsonObject1 = (JSONObject) obj;
				Set<String> keysetuser = jsonObject1.keySet();
				for (String user : keysetuser) {  
					JSONObject jsonObject2 = (JSONObject) jsonObject1.get(user);
					Set<String> keysetdevice=jsonObject2.keySet();
					for (String device : keysetdevice){
						JSONObject jsonObject3= (JSONObject) jsonObject2.get(device);		  
						Set<String> keysettime=jsonObject3.keySet();
						String[] time = (String[]) keysettime.toArray(new String[keysettime.size()]); 
						Arrays.sort(time);
						for(int j=0; j<time.length; j++){  
							  JSONObject jsonObject4 = (JSONObject)jsonObject3.get(time[j]);
							  Set<String> keysetcategory = jsonObject4.keySet();
							  String[] category = (String[]) keysetcategory.toArray(new String[keysetcategory.size()]);
							  if(category != null){
								  for(int k=0; k<category.length; k++){
									  JSONArray jsonObject5 = (JSONArray)jsonObject4.get(category[k]);
									  categories.put(category[k], jsonObject5);	  
								  }
							  }
							  times.put(time[j],categories);
						  }
						devices.put(device, times);
					}
				users.put(user, devices);
				}
				bw.write(users.toString());
				bw.newLine();
				users = new HashMap<String, HashMap<String, HashMap<String, HashMap<String, JSONArray>>>>();
				devices = new HashMap<String, HashMap<String, HashMap<String, JSONArray>>>();
				times = new HashMap<String, HashMap<String, JSONArray>>();
				categories = new HashMap<String, JSONArray>();
			}
			  bw.close();
	  }
	  
 catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

  }
}
