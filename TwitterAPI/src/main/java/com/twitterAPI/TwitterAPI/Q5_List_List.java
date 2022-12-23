package com.twitterAPI.TwitterAPI;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Q5_List_List {

	public static void popolaQ5 (String topUser , Connection connection) throws Exception {

		int maxResults = 50;

		try {
			Unirest.setTimeouts(0, 0);
			HttpResponse<JsonNode> response = Unirest.get("https://api.twitter.com/2/users/"+ topUser +"/followed_lists?max_results=" + maxResults)
					.header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAgihAEAAAAA08V3UoQzaQb4CXxqOWxuG%2FCZSAQ%3DZSLn3cIjU18U8NJrwzIYysPYrhbh07kslN37m3QjBg9wslgz3r")
					.header("Cookie", "guest_id=v1%3A166315287309161276")
					.asJson();

			JSONObject myObj = response.getBody().getObject(); // Mi salvo in un oggetto JSON la risposta della mia API

			List<String> subscribedLists = new ArrayList<String>(); // Lista che conterrà gli autori

			JSONArray array = myObj.getJSONArray("data"); // Array provvisorio nel quale colleziono DATA (contenitore JSON)

			for(int i = 0 ; i < array.length() ; i++){ // Riempio la lista con gli ID dei tweet scorrendo array
				subscribedLists.add(array.getJSONObject(i).getString("id")); //Lista di ID
			}

			System.out.println("\nLista degli id delle liste alle quali l'utente è iscritto:\n");

			for(int i = 0 ; i < subscribedLists.size() ; i++){

				System.out.println((i+1)+ ") " +subscribedLists.get(i));

			}

			//-// PER OGNI LISTA ALLA QUALE L'UTENTE E' SOTTOSCRITTO MI PRENDO I SUBSCRIBERS //-//

			for(int i = 0 ; i < subscribedLists.size() ; i++){

				Q6_List_Subscribers.popolaQ6(subscribedLists.get(i) , connection);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
		}

	}

}
