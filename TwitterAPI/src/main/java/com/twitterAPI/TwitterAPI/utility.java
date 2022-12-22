package com.twitterAPI.TwitterAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class utility {

	//-// METODO CHE PARTENDO DA UN ID ESPLORA L'UTENTE E LO AGGIUNGE AL DATABASE //-//

	public static void addUserToDb(String id_autore, Connection connection) throws Exception  {

		String bio = "";
		int followers = 0;
		int following = 0;
		int isInteresting = 0 ;
		int alreadyVisited = 0 ;

		//-// CONTROLLO SE ESISTE GIA' NEL DATABASE OPPURE NO //-//

		if(utility.userExists(connection , id_autore)) {		

			System.out.println("L'utente " + id_autore + " Si trova già in lista");

		} else {

			//-// RETRIEVE DEI DATI DELL'UTENTE A PARTIRE DALL'ID //-//

			HttpResponse<JsonNode> response2 = Unirest.get("https://api.twitter.com/2/users/"+id_autore+"?user.fields=public_metrics,description")
					.header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAgihAEAAAAA08V3UoQzaQb4CXxqOWxuG%2FCZSAQ%3DZSLn3cIjU18U8NJrwzIYysPYrhbh07kslN37m3QjBg9wslgz3r")
					.header("Cookie", "guest_id=v1%3A166315287309161276")
					.asJson();

			JSONObject myObj2 = response2.getBody().getObject();

			JSONObject myObj3 = myObj2.getJSONObject("data");
			bio = myObj3.getString("description");

			//-// SE L'UTENTE NON HA NULLA NELLA BIO LO SCARTO //-//
			
			if (bio != "") {

				JSONObject myObj4 = myObj3.getJSONObject("public_metrics");

				followers = myObj4.getInt("followers_count");
				following = myObj4.getInt("following_count");
				
				//-// UN UTENTE E' INTERESSANTE SE HA FATTO PIU DI 5000 TWEET //-//

				if ( (myObj4.getInt("tweet_count")>5000) ) {
					isInteresting=1;
				}

				String inserimento = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement statement = connection.prepareStatement(inserimento);

				statement.setString(1, id_autore);
				statement.setString(2, bio);
				statement.setInt(3, followers);
				statement.setInt(4, following);
				statement.setInt(5, isInteresting);
				statement.setFloat(6, 0.0000f); // Priorità di default che poi verrà modificata da Q2
				statement.setInt(7, alreadyVisited);
				statement.executeUpdate();

			}

		}

	}

	//-// CONTROLLO SE UN UTENTE SI TROVA GIA' ALL'INTERNO DEL DATABASE //-//

	public static boolean userExists(Connection connection, String id) throws Exception  {

		String query = "SELECT * FROM users WHERE id = " + id;

		Statement statement = connection.createStatement();

		ResultSet resultSet;
		resultSet = statement.executeQuery(query);
		if(resultSet.next()){
			return true;
		} else {
			return false;
		} 

	}
	
	//-// METODO CHE RESTITUISCE L'UTENTE CON PRIORITA' MAGGIORE //-//

	public static String getMaxPriorityUser(Connection connection) throws Exception  {

		PreparedStatement Statement = connection.prepareStatement("SELECT * FROM twitterapi.users WHERE followers = (SELECT MAX(followers) FROM twitterapi.users WHERE alreadyVisited = 0) LIMIT 1");
		ResultSet result = Statement.executeQuery();
		String bestUser = "";

		if (result.next()) {

			bestUser = result.getString("id");

		}

		System.out.println("\nL'utente con priorità maggiore ha ID: " + bestUser );

		return bestUser;

	}
	
	//-// METODO CHE RIMUOVE UN UTENTE DAL DATABASE A PARTIRE DA UN ID //-//

	public static void setUserVisited(String mostPopularUser, Connection connection) throws Exception {

		String query = "UPDATE twitterapi.users SET alreadyVisited = 1 WHERE id = " + mostPopularUser;
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);

	}

}
