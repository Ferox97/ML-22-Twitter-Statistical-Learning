package com.twitterAPI.TwitterAPI;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class GoogleNews {

	//TO-DO: RIMUOVERE https:// da ogni inizio titolo altrimenti la richiesta non va a buon fine
	//Prima del punto precedente fare metodo final UR



	public static List<String> cercaNotizie () throws Exception {

		List<String> GoogleNewsURLs = new ArrayList<String>();
		Document doc = Jsoup.parse(Jsoup.connect("https://news.google.com/rss?hl=it&gl=IT&ceid=IT:it").get().toString(),Parser.xmlParser());
		Elements newsLinks = doc.select("rss channel item link");

		for(int i = 0; i < newsLinks.size(); i++) { 

			// TO:DO 
			// SE LINK NOTIZIA HA PIU' DI 128 CARATTERI (https:// escluso) non viene accettato dalla Query delle Twitter API
			// MI DEVO INVENTARE QUALCOSA (NON POSSO FARE CUT RANDOM)
			// PROBABILMENTE LO ESCLUDO E MI PRENDO SOLAMENTE I LINK VALIDI ENTRO IL LIMITE DI CHAR 

			try {
				String temp = newsLinks.get(i).toString();
				String tempDue = temp.substring(6 , temp.length() - 7); //Rimuovo i tag HTML <link> e </link>

				String tempTre = getFinalURL(tempDue).substring(8); //Mi prendo il link del REDIRECT e rimuovo il https:// visto che le API di Twitter non lo vogliono nella Query

				if (tempTre.length()<=128) {
					GoogleNewsURLs.add(tempTre);	 
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} 
		}  

		return GoogleNewsURLs;

	}

	public static String getFinalURL(String url) throws IOException , MalformedURLException { // metodo Per avere i redirect finali dei link di google news
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setInstanceFollowRedirects(false);
		con.connect();
		con.getInputStream();

		if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
			String redirectUrl = con.getHeaderField("Location");
			return getFinalURL(redirectUrl);
		}

		return url;
	}

}
