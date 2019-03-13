package org.atelier.geoquizzplayer.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="series")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Serie {
	
	@Id
	private String id;
	private String ville;
	private float map_long;
	private float map_lat;
	private int dist;
	
	@JsonManagedReference
	@OneToMany(mappedBy="serie", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Photo> photos;
	
	@JsonManagedReference
	@OneToMany(mappedBy="serie", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Partie> parties;
	
	public Set<Partie> getParties() {
		return parties;
	}

	public void setParties(Set<Partie> parties) {
		this.parties = parties;
	}

	Serie(){}
	
	public Serie(String ville, float map_long, float map_lat, int dist) {
		this.ville = ville;
		this.map_long = map_long;
		this.map_lat = map_lat;
		this.dist = dist;
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

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}
	
}