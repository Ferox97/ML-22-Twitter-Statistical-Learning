package com.twitterAPI.TwitterAPI;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.*;

public class Q4_Statuses_Retweeters_Ids {

	public static void popolaQ4 (String topUser) throws Exception {

		int maxResults = 30;

		Unirest.setTimeouts(0, 0);
		HttpResponse<JsonNode> response = Unirest.get("https://api.twitter.com/2/users/1396537009817018371/tweets?max_results="+ maxResults)
				.header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAgihAEAAAAA08V3UoQzaQb4CXxqOWxuG%2FCZSAQ%3DZSLn3cIjU18U8NJrwzIYysPYrhbh07kslN37m3QjBg9wslgz3r")
				.header("Cookie", "guest_id=v1%3A166315287309161276")
				.asJson();

		JSONObject myObj = response.getBody().getObject(); // Mi salvo in un oggetto JSON la risposta della mia API

		List<String> tweetAuthorsUsernames = new ArrayList<String>(); // Lista che conterrà gli autori

		JSONArray array = myObj.getJSONArray("data"); // Array provvisorio nel quale colleziono DATA (contenitore JSON)

		for(int i = 0 ; i < array.length() ; i++){ // Riempio la lista con gli ID dei tweet scorrendo array
			String temp = array.getJSONObject(i).getString("text"); //Lista di ID

			if (temp.substring(0, 4).equals("RT @")) {

				temp = temp.substring(temp.indexOf("@") + 1);
				temp = temp.substring(0, temp.indexOf(":"));

				tweetAuthorsUsernames.add(temp);

			}

		}
		
		System.out.println("\nLista degli username degli autori dei Tweet che l'utente ha retweettato:\n");

		for(int i = 0 ; i < tweetAuthorsUsernames.size() ; i++){

			System.out.println((i+1)+ ") " + tweetAuthorsUsernames.get(i));

		}
		
		List<String> tweetAuthors = new ArrayList<String>();
		
		for(int i = 0 ; i < tweetAuthorsUsernames.size() ; i++){

		Unirest.setTimeouts(0, 0);
	    HttpResponse<JsonNode> response2 = Unirest.get("https://api.twitter.com/2/users/by/username/" + tweetAuthorsUsernames.get(i))
	      .header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAgihAEAAAAA08V3UoQzaQb4CXxqOWxuG%2FCZSAQ%3DZSLn3cIjU18U8NJrwzIYysPYrhbh07kslN37m3QjBg9wslgz3r")
	      .header("Cookie", "guest_id=v1%3A166315287309161276")
	      .asJson();
	    
	    JSONObject myObj2 = response2.getBody().getObject();

		JSONObject myObj3 = myObj2.getJSONObject("data");
		tweetAuthors.add(myObj3.getString("id")) ;
	    
		}
		
		System.out.println("\nLista degli id degli autori dei Tweet che l'utente ha retweettato:\n");

		for(int i = 0 ; i < tweetAuthors.size() ; i++){

			System.out.println((i+1)+ ") " + tweetAuthors.get(i));

		}
		
	}

}
