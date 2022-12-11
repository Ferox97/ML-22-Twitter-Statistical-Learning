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

	//-// CERCO URL NEWS RECENTI //-//

	public static List<String> cercaNotizie () throws Exception {

		List<String> GoogleNewsURLs = new ArrayList<String>();
		Document doc = Jsoup.parse(Jsoup.connect("https://news.google.com/rss?hl=it&gl=IT&ceid=IT:it").get().toString(),Parser.xmlParser());
		Elements newsLinks = doc.select("rss channel item link");
		
		int maxNewsDaCercare = 10;

		for(int i = 0; i < maxNewsDaCercare; i++) { 

			try {

				String temp = newsLinks.get(i).toString();
				String tempDue = temp.substring(6 , temp.length() - 7); //Rimuovo i tag HTML <link> e </link>
				String tempTre = getFinalURL(tempDue).substring(8); // Mi prendo il link del REDIRECT e rimuovo il https:// visto che le API di Twitter non lo vogliono nella Query

				//-// SE UN URL HA PIU' DI 128 CARATTERI (https:// escluso) non viene accettato dalla Query delle Twitter API E QUINDI LO ESCLUDO //-//

				if (tempTre.length()<=128) {
					GoogleNewsURLs.add(tempTre);	 
				}

			} catch (MalformedURLException e) {
				// // L'URL non è costruito bene
			} catch (IOException e) {
				// Non so da che dipende ma comunque ignoro
			}
		}  

		return GoogleNewsURLs;
	}

	//-// GESTISCO IL REDIRECT DEL LINK PER ARRIVARE ALL'URL FINALE //-//

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
