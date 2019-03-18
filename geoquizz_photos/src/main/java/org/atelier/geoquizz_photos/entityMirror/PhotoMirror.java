package org.atelier.geoquizz_photos.entityMirror;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation="photos")
public class PhotoMirror {
	private String id;
	private String description;
	private float longitude;
	private float latitude;
	private String url;
	
	public PhotoMirror(String id,String description, float longitude, float latitude, String url) {
		this.id = id;
		this.description = description;
		this.longitude = longitude;
		this.latitude = latitude;
		this.url = url;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
