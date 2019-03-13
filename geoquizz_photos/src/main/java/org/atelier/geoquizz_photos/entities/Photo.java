package org.atelier.geoquizz_photos.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="photos")
public class Photo {

	@Id
	private String id;
	private String description;
	private float longitude;
	private float latitude;
	private String url;
	
	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="serie_id", nullable=false)
	private Serie serie;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="user_id")
	private User user;
	
	Photo(){}
	
	public Photo(String description, float longitude, float latitude, String url) {
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

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}