package org.atelier.geoquizzplayer.entity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="photos")
public class Photo {
	
	@Id
	private String id;
	private String description;
	private float longitude;
	private float latitude;
	private String url;
	
	@ManyToMany(mappedBy = "photos", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
	private Set<Partie> parties;
	
	@JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="serie_id", nullable=false)
    private Serie serie;
	
	public Photo() {}
	
	public Photo(String desc, float lng, float lat, String url) {
		this.description = desc;
		this.longitude = lng;
		this.latitude = lat;
		this.url = url;
	}
	
	public Serie getSerie() {
		return serie;
	}
	public void setSerie(Serie serie) {
		this.serie = serie;
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
	public void setDescription(String desc) {
		this.description = desc;
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
