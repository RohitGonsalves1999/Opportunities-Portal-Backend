package com.accolite.opportunitiesportal.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
	
	
    private int id;

    private String name;
    
    private String email;
    
    private String primary_contact_num;

    private String imageUrl;

    private String hashed_password;


    private AuthProvider provider;


    private String salt;

}
