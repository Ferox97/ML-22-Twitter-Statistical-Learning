package com.twitterAPI.TwitterAPI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class utility {

	public static boolean userExists(Connection connection, String id) throws Exception  {

		String query = "SELECT * FROM users WHERE id = " + id;

		Statement statement = connection.createStatement();

		ResultSet resultSet;
		resultSet = statement.executeQuery(query);
		if(resultSet.next()){
			return true;
		} else {
			return false;
		} 

	}

}
