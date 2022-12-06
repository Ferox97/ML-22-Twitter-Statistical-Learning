package com.twitterAPI.TwitterAPI;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.*;

public class Q3_Favorites_List {

	public static void popolaQ3 (String topUser) throws Exception {
		
		int maxResults = 10;
		
		
		Unirest.setTimeouts(0, 0);
	    HttpResponse<JsonNode> response = Unirest.get("https://api.twitter.com/2/users/"+ topUser +"/liked_tweets?tweet.fields=author_id&max_results=" + maxResults)
	      .header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAgihAEAAAAA08V3UoQzaQb4CXxqOWxuG%2FCZSAQ%3DZSLn3cIjU18U8NJrwzIYysPYrhbh07kslN37m3QjBg9wslgz3r")
	      .header("Cookie", "guest_id=v1%3A166315287309161276")
	      .asJson();
	    
	 
	    JSONObject myObj = response.getBody().getObject(); // Mi salvo in un oggetto JSON la risposta della mia API

		List<String> TweetAuthors = new ArrayList<String>(); // Lista che conterrà gli autori

		JSONArray array = myObj.getJSONArray("data"); // Array provvisorio nel quale colleziono DATA (contenitore JSON)

		for(int i = 0 ; i < array.length() ; i++){ // Riempio la lista con gli ID dei tweet scorrendo array
			TweetAuthors.add(array.getJSONObject(i).getString("author_id")); //Lista di ID
		}
		
		System.out.println("\nLista degli autori dei Tweet ai quali l'utente più popolare ha messo like:\n");

		for(int i = 0 ; i < TweetAuthors.size() ; i++){

			System.out.println((i+1)+ ") " +TweetAuthors.get(i));

		}
		
		// Chiedere al professore se questa lista di utenti deve essere esplorata a parte oppure può
		// andare in Q1 e partecipare nuovamente al calcolo della priorità

	}

}
