package org.atelier.geoquizzplayer.EntityMirror;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "series")
public class SerieMirroir {

	private String id;
	private String ville;
	private float map_long;
	private float map_lat;
	
	public SerieMirroir(String id, String ville, float lng, float lat) {
		this.id = id;
		this.ville = ville;
		this.map_long = lng;
		this.map_lat = lat;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public float getMap_long() {
		return map_long;
	}

	public void setMap_long(float map_long) {
		this.map_long = map_long;
	}

	public float getMap_lat() {
		return map_lat;
	}

	public void setMap_lat(float map_lat) {
		this.map_lat = map_lat;
	}

    
    
}