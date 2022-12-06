package com.twitterAPI.TwitterAPI;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.*;

public class Q6_List_Subscribers {
	
	public static void popolaQ6 (String listId) throws Exception {

		int maxResults = 10;
		
		
		 Unirest.setTimeouts(0, 0);
		    HttpResponse<JsonNode> response = Unirest.get("https://api.twitter.com/2/lists/"+ listId +"/followers?max_results=" + maxResults)
		      .header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAgihAEAAAAA08V3UoQzaQb4CXxqOWxuG%2FCZSAQ%3DZSLn3cIjU18U8NJrwzIYysPYrhbh07kslN37m3QjBg9wslgz3r")
		      .header("Cookie", "guest_id=v1%3A166315287309161276")
		      .asJson();
		    
		    JSONObject myObj = response.getBody().getObject(); // Mi salvo in un oggetto JSON la risposta della mia API

			List<String> subscribersIds = new ArrayList<String>(); // Lista che conterrà gli autori

			JSONArray array = myObj.getJSONArray("data"); // Array provvisorio nel quale colleziono DATA (contenitore JSON)

			for(int i = 0 ; i < array.length() ; i++){ // Riempio la lista con gli ID dei tweet scorrendo array
				subscribersIds.add(array.getJSONObject(i).getString("id")); //Lista di ID
			}
			
			System.out.println("\nLista degli id degli utenti che sono subscribers della lista #"+ listId +":\n");

			for(int i = 0 ; i < subscribersIds.size() ; i++){

				System.out.println((i+1)+ ") " +subscribersIds.get(i));

			}

	}

}
