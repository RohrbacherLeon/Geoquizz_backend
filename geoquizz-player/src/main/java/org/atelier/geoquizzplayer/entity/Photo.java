package org.atelier.geoquizzplayer.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="photos")
public class Photo {
	
	@Id
	private String id;
	private String desc;
	private float longitude;
	private float latitude;
	private String url;
	
	@ManyToMany(mappedBy = "photos", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
	private Set<Partie> parties;
	
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
	public Set<Partie> getParties() {
		return parties;
	}
	public void setParties(Set<Partie> parties) {
		this.parties = parties;
	}
	
}
