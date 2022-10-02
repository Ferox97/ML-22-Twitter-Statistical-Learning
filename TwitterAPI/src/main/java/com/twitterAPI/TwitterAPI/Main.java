package com.twitterAPI.TwitterAPI;

import com.mashape.unirest.http.*;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class Main {
	
  public static void main (String []args) throws Exception {
	  
	  Document doc = Jsoup.parse(Jsoup.connect("https://news.google.com/rss?hl=it&gl=IT&ceid=IT:it").get().toString(), Parser.xmlParser());

	  Elements title = doc.select("rss channel item title");
	  
	  System.out.println("Stampo i 10 titoli più recenti trovati:\n");
	  
	  for(int i = 0; i < 10; i++) {   // title.size()
		  String s = title.get(i).toString();
		  String s2 = s.substring(7,s.length() - 8);
		  System.out.println(s2);
		  
		}  
	  
	  //System.out.println(link.text()); // prints empty string

	  int maxResults = 10;
	  String query = "Prova"; // uso %20 come encoder al posto dello Spazio altrimenti ricevo errore dalla API | Qua ci andrà il titolo estratto da google news
	  
	  Unirest.setTimeouts(0, 0);
	    HttpResponse<JsonNode> response = Unirest.get("https://api.twitter.com/2/tweets/search/recent?query="+query+"&max_results="+maxResults)
	      .header("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAgihAEAAAAA08V3UoQzaQb4CXxqOWxuG%2FCZSAQ%3DZSLn3cIjU18U8NJrwzIYysPYrhbh07kslN37m3QjBg9wslgz3r")
	      .header("Cookie", "guest_id=v1%3A166315287309161276")
	      .asJson(); // Qua credo posso anche salvarmi direttamente una String
   
	    JSONObject myObj = response.getBody().getObject(); // Mi salvo in un oggetto JSON la risposta della mia API
	    
	    List<String> TweetIDs = new ArrayList<String>(); // Lista che contiene gli ID dei Tweet
	    List<String> TweetTexts = new ArrayList<String>(); // Lista che contiene i testi dei Tweet
	    
	    JSONArray array = myObj.getJSONArray("data"); // Array provvisorio nel quale colleziono DATA (contenitore JSON)
	    
	    for(int i = 0 ; i < array.length() ; i++){ // Riempio la lista con gli ID dei tweet scorrendo array
	    	TweetIDs.add(array.getJSONObject(i).getString("id")); //Lista di ID
	    	TweetTexts.add(array.getJSONObject(i).getString("text")); //Lista di Testi
	    }
	    
	    System.out.println("\n[ Numero di Tweet trovati ] : " + TweetIDs.toArray().length + "\n"); // La dimensione della lista corrisponderà al numero di Tweet trovati
	    System.out.println(TweetIDs); // Stampo gli ID
	    System.out.println(TweetTexts); // Stampo i Testi
	    
	    File file = new File("./export-tweets");
	    
	        FileWriter outputfile = new FileWriter(file);
	        CSVWriter writer = new CSVWriter(outputfile);      
	     
	        
	        for(int i = 0 ; i < array.length() ; i++){
	        	String[] data = { array.getJSONObject(i).getString("id") , array.getJSONObject(i).getString("text") };
		        writer.writeNext(data);
		    }	      
	  
	        writer.close();

	    //System.out.println(myObj.toString(4)); // Stampo il JSON che ricevo come risposta dalla Twitter API
	
  }
}

