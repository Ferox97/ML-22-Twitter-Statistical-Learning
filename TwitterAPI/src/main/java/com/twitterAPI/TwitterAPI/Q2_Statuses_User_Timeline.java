package com.twitterAPI.TwitterAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Q2_Statuses_User_Timeline {

	public static void popolaQ2(Connection connection) throws Exception {

		//-// INIZIALIZZO I SET DOVE SALVERO' RISPETTAVAMENTE KEYWORDS ED UTENTI //-//

		ResultSet keywordsSet;
		ResultSet usersSet;

		//-// ESTRAGGO LA LISTA DI TUTTE LE KEYWORD DAL DATABASE //-//

		Statement statementKeywords  = connection.createStatement();
		Statement statementUsers  = connection.createStatement();

		keywordsSet = statementKeywords.executeQuery("SELECT * FROM keywords");

		//-// PER OGNI KEYWORD.... //-//

		while (keywordsSet.next()) {

			List<String> utentiAttuali = new ArrayList<String>(); // Lista utenti che contengono la parola attuale

			int globalCounter = 0;
			int interestingCounter = 0;
			String keywordCorrente = " " + keywordsSet.getString("id") + " ";

			System.out.println(keywordCorrente);

			//-// MI PRENDO TUTTI GLI UTENTI //-//

			usersSet = statementUsers.executeQuery("SELECT * FROM users");

			//-// PER OGNI UTENTE VEDO SE LA PAROLA I-ESIMA E' CONTENUTA NELLA BIO //-//

			while (usersSet.next()) {

				if (usersSet.getString("bio").contains(keywordCorrente)) {

					//-// SE E' CONTENUTA INCREMENTO IL GLOBAL COUNTER //-//

					globalCounter++;
					
					//-// AGGIUNGO L'UTENTE AD UNA LISTA PROVVISORIA CHE MI SERVIRA' PER CALCOLARE LA PRIORITA' //-//

					utentiAttuali.add(usersSet.getString("id"));

					if (usersSet.getInt("isInteresting") == 1) {

						//-// SE L'UTENTE E' INTERESSANTE, INCREMENTO ANCHE L'INTERESTING COUNTER //-//

						interestingCounter++;

					}

				}

			}

			//-// HO FINITO DI CONTARE GLI UTENTI QUINDI AGGIORNO LA KEYWORD I-ESIMA //-//

			if (interestingCounter != 0) {

				String update = "UPDATE keywords SET globalCounter = ?, interestingCounter = ?, ib = ? WHERE id = ?";
				PreparedStatement statementUpdate = connection.prepareStatement(update);
				
				float ib = (float)interestingCounter/(float)globalCounter;

				statementUpdate.setInt(1, globalCounter);
				statementUpdate.setInt(2, interestingCounter);
				statementUpdate.setFloat(3, ib);
				statementUpdate.setString(4, keywordsSet.getString("id"));
				statementUpdate.executeUpdate();

			}

			//-// AGGIORNO LA PRIORITY DEGLI UTENTI CHE CONTENGONO LA KEYWORD I-ESIMA //-//

			for(int i = 0 ; i < utentiAttuali.size() ; i++){ //

				System.out.println(utentiAttuali.get(i));

				PreparedStatement statementPriority = connection.prepareStatement("SELECT * FROM users WHERE id = " + utentiAttuali.get(i));
				ResultSet result = statementPriority.executeQuery();

				if (result.next()) {

					float priority = result.getFloat("priority");
					float ib = (float)interestingCounter/(float)globalCounter;
					
					priority = priority * ib;

					String update2 = "UPDATE users SET priority = ? WHERE id = ?";
					PreparedStatement statementUpdate2 = connection.prepareStatement(update2);

					statementUpdate2.setFloat(1, priority);
					statementUpdate2.setString(2, utentiAttuali.get(i));
					statementUpdate2.executeUpdate();

				}

			}

		}

	}

}
