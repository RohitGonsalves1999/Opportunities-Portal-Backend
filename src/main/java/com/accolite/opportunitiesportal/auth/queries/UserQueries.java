package com.accolite.opportunitiesportal.auth.queries;

public class UserQueries {
	
	private UserQueries() {}
	
	public static final String GET_USER_BY_EMAIL = "Select * from userdetails where email = ?";
	
	public static final String GET_USER_BY_ID = "Select * from userdetails where id = ?";
	
	public static final String GET_USER_TOKEN_BY_EMAIL = "select authToken from userdetails where email = ?";
	
	public static final String GET_USER_BY_TOKEN = "select * from userdetails where authToken = ?";
	
	public static final String GET_USER_TOKEN_BY_ID = "select authToken from userdetails where id = ?";
	
	public static final String SAVE_USER = 
			"INSERT INTO userdetails(\r\n" + 
			"	 name, email, authtoken)\r\n" + 
			"	VALUES (?, ?, ?);";
	
	
	public static final String SAVE_USER_TOKEN_BY_ID = 
			"UPDATE userdetails\r\n" + 
			"	SET authtoken=?\r\n" + 
			"	WHERE id = ?;"  ;
	
	
	public static final String SAVE_USER_TOKEN_BY_EMAIL = 
			"UPDATE userdetails\r\n" + 
			"	SET authtoken=?\r\n" + 
			"	WHERE email = ?;"  ;
	

}
