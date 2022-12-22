package com.twitterAPI.TwitterAPI;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.*;

public class Q1_UsersLookup {

	public static void popolaQ1 (List<String> GoogleNewsURLs , Connection connection) throws Exception {

		int maxResults = 100;
		
		//-// PER OGNI NEWS VEDO GLI UTENTI PIU' RECENTI CHE HANNO TWEETTATO QUEL URL //-//

		for (int j = 0; j<GoogleNewsURLs.size(); j++) {

			int leftCounter = j+1;

			String query = "";

			try {

				query = GoogleNewsURLs.get(j);
				
				//-// OTTENGO I TWEET RECENTI E MI ESTRAGGO GLI ID AUTORI //-//

				Unirest.setTimeouts(0, 0);
				HttpResponse<JsonNode> response = Unirest.get("https://api.twitter.com/2/tweets/search/recent?query=" + query + "&max_results="+ maxResults + "&tweet.fields=author_id")
						.header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAgihAEAAAAA08V3UoQzaQb4CXxqOWxuG%2FCZSAQ%3DZSLn3cIjU18U8NJrwzIYysPYrhbh07kslN37m3QjBg9wslgz3r")
						.header("Cookie", "guest_id=v1%3A166315287309161276")
						.asJson(); // Qua credo posso anche salvarmi direttamente una String

				JSONObject myObj = response.getBody().getObject(); // Mi salvo in un oggetto JSON la risposta della mia API

				List<String> TweetAuthors = new ArrayList<String>(); // Lista che conterrà gli autori

				JSONArray array = myObj.getJSONArray("data"); // Array provvisorio nel quale colleziono DATA (contenitore JSON)

				for(int i = 0 ; i < array.length() ; i++){ // Riempio la lista con gli ID dei tweet scorrendo array

					TweetAuthors.add(array.getJSONObject(i).getString("author_id")); //Lista di ID
				}

				System.out.println("\nPer " + leftCounter + ") " + query + " ho trovato " + TweetAuthors.toArray().length + " Profilo/i"); // La dimensione della lista corrisponderà al numero di Tweet trovati

				for(int i = 0 ; i < TweetAuthors.size() ; i++){ // 

					String id_autore =  TweetAuthors.get(i);
					
					utility.addUserToDb(id_autore , connection);

				}	      				

			} catch (JSONException e ) {

				System.out.println("\nPer " + leftCounter + ") " + query + " non ho trovato alcun profilo");

			}

		}

	}

}
