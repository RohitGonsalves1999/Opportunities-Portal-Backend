package com.accolite.opportunitiesportal.auth.model;

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
    
    private String primaryContactNum;

    private String imageUrl;

    private String hashedPassword;


    private AuthProvider provider;


    private String salt;

}
