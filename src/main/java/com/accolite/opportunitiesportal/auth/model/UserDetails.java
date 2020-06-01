package com.accolite.opportunitiesportal.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetails {

	int id;
	String email;
	String name;
	String authToken;
	
}
