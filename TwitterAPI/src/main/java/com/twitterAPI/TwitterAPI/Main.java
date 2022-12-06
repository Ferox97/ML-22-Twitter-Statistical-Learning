package com.twitterAPI.TwitterAPI;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.opencsv.CSVWriter;

public class Main {
	
  public static void main (String []args) throws Exception {
	  
	  	File file = new File("./lista_q1");
		FileWriter outputfile = new FileWriter(file);
		

	  System.out.println("Ricerca news più recenti in corso...\n");
	  
	  int counter = 10;
	  
	  List<String> GoogleNewsURLs = GoogleNews.cercaNotizie();
	  
	  System.out.println("Questi sono gli URL delle " + GoogleNewsURLs.size() + " notizie più recenti:\n");
	  
	  for (int i=0; i<GoogleNewsURLs.size();i++) {

		  int leftCounter = i+1;
		  System.out.println(leftCounter +") " +GoogleNewsURLs.get(i));
	  }
	  

	  
	  System.out.println("\nCerco gli autori dei Tweet più recenti che contengono questi Link...");
	  
	  Q1_UsersLookup.popolaQ1(GoogleNewsURLs , outputfile); // Questo è da modificare perchè Q1 deve avere un ciclo nel quale cerca i Tweet più recenti a partire da GoogleNewsURLs
	  
	  String mostPopularUser = "917929550"; //Questo poi sarà il primo utente della lista ordinata Q2
	  
	  Q3_Favorites_List.popolaQ3(mostPopularUser);
	  
	  Q4_Statuses_Retweeters_Ids.popolaQ4(mostPopularUser);
	  
	  Q5_List_List.popolaQ5(mostPopularUser);
	  
	  ////////////////////////////////////////////////////
	  
//	  do {

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
	  
	  
	  
	  
  }
  
}

