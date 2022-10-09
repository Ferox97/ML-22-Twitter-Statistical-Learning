package com.twitterAPI.TwitterAPI;

import java.util.List;


public class Main {
	
  public static void main (String []args) throws Exception {

	  System.out.println("Questi sono i Link delle 10 notizie più recenti trovate su Google News:\n");

	  List<String> GoogleNewsURLs = GoogleNews.cercaNotizie();
	  for (int i=0; i<GoogleNewsURLs.size();i++) {

		  System.out.println(GoogleNewsURLs.get(i));
	  }
	  
	  System.out.println("\nPer ognuno di questi link cerco dei Tweet correlati:\n");
	  
	  Q1_UsersLookup.popolaQ1();
	
  }
}

