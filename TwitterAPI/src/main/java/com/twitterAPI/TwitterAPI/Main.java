package com.twitterAPI.TwitterAPI;

import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

	public static void main (String []args) throws Exception {

		//-// MI CONNETTO AL DATABASE //-//

		String url = "jdbc:mysql://localhost:3306/twitterapi";
		String user = "root";
		String password = "root";
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url, user, password);
		System.out.println("\nConnection is Successful to the database" + url);

		//-// TROVO GLI URL DELLE NEWS PIU' RECENTI E LI STAMPO //-//

		System.out.println("\nRicerca news più recenti in corso...\n");
		List<String> GoogleNewsURLs = GoogleNews.cercaNotizie();
		System.out.println("Questi sono gli URL delle " + GoogleNewsURLs.size() + " notizie più recenti:\n");

		for (int i=0; i<GoogleNewsURLs.size();i++) {

			int leftCounter = i+1;
			System.out.println(leftCounter +") " +GoogleNewsURLs.get(i));

		}

		//-// CERCO I TWEET PIU RECENTI CHE INCLUDONO QUELLE NEWS E MI SALVO NEL DB GLI AUTORI //-//

		System.out.println("\nCerco gli autori dei Tweet più recenti che contengono questi Link...");

		Q1_UsersLookup.popolaQ1(GoogleNewsURLs , connection);

		//-// PARTO CON IL LOOP ITERATIVO SULLE VARIE CLASSI //-//

		int counter = 4;
		
		String mostPopularUser = "";

		do {
			
			//-// PRENDO L'UTENTE CON PRIORITA' PIU' ALTA //-//
			
			mostPopularUser = utility.getMaxPriorityUser(connection); // Questo poi sarà il primo utente della lista ordinata Q2

			//Q2
			
			Q3_Favorites_List.popolaQ3(mostPopularUser , connection);
			
			Q4_Statuses_Retweeters_Ids.popolaQ4(mostPopularUser , connection);

			// Q5 Si prende la lista di liste alla quale l'utente è iscritto

			// Q6 si prende i subscribers di ogni lista proveniente da Q5 (Viene chiamato da Q5 e non dal main)

			// Thread.sleep(5000); // 5 secondi ma poi questo dovrà essere 15 minuti
			
			utility.dropMostPopularUser(mostPopularUser , connection); //Sta cosa in futuro diventa azzeramento priorità - mi serve solo per test

			counter--;

		} while (counter !=0);


	}

}

