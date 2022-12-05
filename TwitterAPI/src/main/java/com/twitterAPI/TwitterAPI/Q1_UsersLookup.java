package com.twitterAPI.TwitterAPI;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.*;
import com.opencsv.CSVWriter;

public class Q1_UsersLookup {

	public static void popolaQ1 (List<String> GoogleNewsURLs , FileWriter outputfile) throws Exception {

		int maxResults = 100;

		CSVWriter writer = new CSVWriter(outputfile);

		for (int j = 0; j<GoogleNewsURLs.size(); j++) {

			int leftCounter = j+1;

			String query = "";

			try {

				query = GoogleNewsURLs.get(j);

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

				System.out.println("\nPer " + leftCounter + ") " + query + " ho trovato " + TweetAuthors.toArray().length + " Profilo/i\n"); // La dimensione della lista corrisponderà al numero di Tweet trovati

				//Qua inizia la scrittura su file

				for(int i = 0 ; i < TweetAuthors.size() ; i++){

					//System.out.println(TweetAuthors.get(i));

				}

				for(int i = 0 ; i < TweetAuthors.size() ; i++){ //Loop che scrive su CSV i ID utente, Followers, Following e bio

					String id_autore =  TweetAuthors.get(i);
					String bio = "";
					int followers = 0;
					int following = 0;

					System.out.println(id_autore);

					HttpResponse<JsonNode> response2 = Unirest.get("https://api.twitter.com/2/users/"+id_autore+"?user.fields=public_metrics,description")
							.header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAgihAEAAAAA08V3UoQzaQb4CXxqOWxuG%2FCZSAQ%3DZSLn3cIjU18U8NJrwzIYysPYrhbh07kslN37m3QjBg9wslgz3r")
							.header("Cookie", "guest_id=v1%3A166315287309161276")
							.asJson();

					////////////////////////////////


					JSONObject myObj2 = response2.getBody().getObject();

					JSONObject myObj3 = myObj2.getJSONObject("data");
					bio = myObj3.getString("description");

					JSONObject myObj4 = myObj3.getJSONObject("public_metrics");

					followers = myObj4.getInt("followers_count");
					following = myObj4.getInt("following_count");

					////////////////////////////////

					String[] data = {id_autore , bio , Integer.toString(followers) , Integer.toString(following)};
					writer.writeNext(data);

				}	      				

			} catch (JSONException e ) {

				System.out.println("\nPer " + leftCounter + ") "  + query + " non sono stati trovati tweet recenti.");

			}

		}

		writer.close();

	}
	
}
