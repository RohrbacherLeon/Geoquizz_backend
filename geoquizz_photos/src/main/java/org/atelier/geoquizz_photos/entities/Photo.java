package org.atelier.geoquizz_photos.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="photos")
public class Photo {

	@Id
	private String id;
	private String desc;
	private float longitude;
	private float latitude;
	private String url;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="serie_id", nullable=false)
	private Serie serie;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="user_id")
	private User user;
	
	Photo(){}
	
	public Photo(String desc, float longitude, float latitude, String url) {
		this.desc = desc;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
	
}