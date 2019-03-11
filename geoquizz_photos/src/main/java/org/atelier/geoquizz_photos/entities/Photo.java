package org.atelier.geoquizz_photos.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Photo {

	@Id
	private String id;
	
	Photo(){}
	
}