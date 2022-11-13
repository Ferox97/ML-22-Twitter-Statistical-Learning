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

	public static void popolaQ1 (List<String> GoogleNewsURLs , FileWriter outputfile , CSVWriter writer) throws Exception {

		int maxResults = 100;
		
		

		for (int j = 0; j<GoogleNewsURLs.size(); j++) {

			try {

				String query = GoogleNewsURLs.get(j);

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

				System.out.println("Ho trovato " + TweetAuthors.toArray().length + " Profili\n"); // La dimensione della lista corrisponderà al numero di Tweet trovati

				    


				for(int i = 0 ; i < array.length() ; i++){ //Loop che stampa gli utenti trovati
					String[] data = { array.getJSONObject(i).getString("author_id") };
					writer.writeNext(data);
				}	      

				



			} catch (JSONException e ) {
				
				System.out.println("Qualcosa è andato storto con un URL, ignoro e vado avanti");
				
			}

		}
		
		writer.close();

	}

}
