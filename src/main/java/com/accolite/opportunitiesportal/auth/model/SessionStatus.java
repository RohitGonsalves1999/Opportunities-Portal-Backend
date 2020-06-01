package com.accolite.opportunitiesportal.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionStatus {

	private boolean isValid;
	private int status;
	private String token;
}
