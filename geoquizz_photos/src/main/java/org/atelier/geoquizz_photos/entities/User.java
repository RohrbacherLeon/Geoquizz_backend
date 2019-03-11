package org.atelier.geoquizz_photos.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	private String id;
	
	User(){}
	
}