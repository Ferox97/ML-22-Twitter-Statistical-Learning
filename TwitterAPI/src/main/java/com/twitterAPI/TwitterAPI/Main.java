package com.twitterAPI.TwitterAPI;

import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

	public static void main (String []args) throws Exception {

		String url = "jdbc:mysql://localhost:3306/twitterapi";
		String user = "root";
		String password = "root";
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url, user, password);
		System.out.println("Connection is Successful to the database" + url);
		//Statement statement = connection.createStatement();
		//Statement statement = connection.prepareStatement();
		
		System.out.println("\nRicerca news più recenti in corso...\n");

		List<String> GoogleNewsURLs = GoogleNews.cercaNotizie();

		System.out.println("Questi sono gli URL delle " + GoogleNewsURLs.size() + " notizie più recenti:\n");

		for (int i=0; i<GoogleNewsURLs.size();i++) {
			
			int leftCounter = i+1;
			System.out.println(leftCounter +") " +GoogleNewsURLs.get(i));
			
		}

		System.out.println("\nCerco gli autori dei Tweet più recenti che contengono questi Link...");

		Q1_UsersLookup.popolaQ1(GoogleNewsURLs , connection);
		
//		  do {

			// Q2 Ordina per priorità la lista di utenti 

			// Viene preso l'utente con priorità più alta

			// Q3 si vede i like più recenti di quell'utente e riempie una lista di utenti (autori del post ai quali ha messo like)

			// Q4 Si prende i Retweet di quell'utente e riempie una lista di utenti (autori dei post originali che ha retweettato)

			// Q5 Si prende la lista di liste alla quale l'utente è iscritto

			// Q6 si prende i subscribers di ogni lista proveniente da Q5 (Viene chiamato da Q5 e non dal main)

			//		  Thread.sleep(5000); // 5 secondi ma poi questo dovrà essere 15 minuti
			//
			//		  counter--;
			//
			//	  } while (counter !=0);

			//////////////////////////////////

		//String mostPopularUser = "917929550"; //Questo poi sarà il primo utente della lista ordinata Q2

		//Q3_Favorites_List.popolaQ3(mostPopularUser);

		//Q4_Statuses_Retweeters_Ids.popolaQ4(mostPopularUser);

		//Q5_List_List.popolaQ5(mostPopularUser);

		////////////////////////////////////////////////////

	}

}

