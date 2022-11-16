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
	  
	  int counter=99999;
	  
	  List<String> GoogleNewsURLs = GoogleNews.cercaNotizie();
	  
	  System.out.println("Questi sono gli URL delle " + GoogleNewsURLs.size() + " notizie più recenti:\n");
	  
	  for (int i=0; i<GoogleNewsURLs.size();i++) {

		  int leftCounter = i+1;
		  System.out.println(leftCounter +") " +GoogleNewsURLs.get(i));
	  }
	  
	  ////////////////////////////////////////////////
	  
	  //do {

		  //Thread.sleep(10000);

		  //counter--;

	  //} while (counter !=0);
	  
	  //////////////////////////////////////////////////////////
	  
	  System.out.println("\nCerco gli autori dei Tweet più recenti che contengono questi Link...");
	  
	  Q1_UsersLookup.popolaQ1(GoogleNewsURLs , outputfile); // Questo è da modificare perchè Q1 deve avere un ciclo nel quale cerca i Tweet più recenti a partire da GoogleNewsURLs
	  
	
  }
  
}

