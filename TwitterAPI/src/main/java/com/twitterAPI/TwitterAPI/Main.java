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

		//-// TROVO GLI URL DELLE NEWS PIU' RECENTI //-//
	
		// La ricerca delle google news più recenti è gestita dalla classe GoogleNews.java che restituisce una List di URL
		// opportunamente formattati e pronti ad essere dati in pasto alle API di Twitter

		System.out.println("\nRicerca news più recenti in corso...\n");
		List<String> GoogleNewsURLs = GoogleNews.cercaNotizie();
		System.out.println("Questi sono gli URL delle " + GoogleNewsURLs.size() + " notizie più recenti:\n");

		//-// STAMPO LA LISTA DEGLI URL TROVATA DAL PRECEDENTE METODO PER DARE CONTO ALL'UTENTE DEI CRITERI DI RICERCA //-//
		
		for (int i=0; i<GoogleNewsURLs.size();i++) {

			int leftCounter = i+1;
			System.out.println(leftCounter +") " +GoogleNewsURLs.get(i));

		}

		//-// CERCO I TWEET PIU RECENTI CHE INCLUDONO QUELLE NEWS E MI SALVO NEL DB GLI AUTORI //-//

		System.out.println("\nCerco gli autori dei Tweet più recenti che contengono questi Link...");

		Q1_UsersLookup.popolaQ1(GoogleNewsURLs , connection);

		//-// PARTO CON IL LOOP ITERATIVO SULLE VARIE CLASSI. I TEMPI DI ATTESA SONO SCANDITI DAI LIMITI DI RICHIESTE DELLE TWITTER API //-//

		int maxCicli = 10; 
		
		String mostPopularUser = "";

		do {
			
			//-// PRENDO L'UTENTE CON PRIORITA' PIU' ALTA //-//
			
			mostPopularUser = utility.getMaxPriorityUser(connection); // Questo poi sarà il primo utente della lista ordinata Q2

			//Q2
			
			//-// ESPLORO L'UTENTE E TROVO ALTRI UTENTI A PARTIRE DAI LIKE CHE HA MESSO //-//
			
			Q3_Favorites_List.popolaQ3(mostPopularUser , connection);
			
			//-// ESPLORO L'UTENTE E TROVO ALTRI UTENTI A PARTIRE DAI RETWEET CHE HA FATTO //-//
			
			Q4_Statuses_Retweeters_Ids.popolaQ4(mostPopularUser , connection);
			
			//-// ESPLORO L'UTENTE E VEDO LE LISTE ALLE QUALI L'UTENTE E' SOTTOSCRITTO //-//

			Q5_List_List.popolaQ5(mostPopularUser, connection);

			System.out.println("Ho finito un ciclo, attendo 5 secondi");
		    Thread.sleep(5000); // 5 secondi ma poi questo dovrà essere 15 minuti
			
		    //-// RIMUOVO L'UTENTE PIU' POPOLARE DAL DATABASE //-//
		    
			utility.setUserVisited(mostPopularUser , connection);

			maxCicli--;

		} while (maxCicli !=0);

	}

}

