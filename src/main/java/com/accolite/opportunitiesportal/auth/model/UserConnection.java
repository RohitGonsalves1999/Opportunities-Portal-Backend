package com.accolite.opportunitiesportal.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConnection {
	
	int id;
	String email;
	String name;
	String firstName;
	String lastName;
	String idToken;
	String authToken;
	String photoUrl;
	String refreshToken;
	String provider;
}
