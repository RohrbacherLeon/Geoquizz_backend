package org.atelier.geoquizzplayer.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "parties")
public class Partie {
	
	@Id
	private String id;
	private String token;
	private int nb_photos;
	private String status = "Créée";
	private int score;
	private String joueur;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "partie_photo", 
      joinColumns = @JoinColumn(name = "partie_id", referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Photo> photos;
	
	public Partie () {
	}
	
	public Partie(String joueur) {
		this.joueur = joueur;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getNb_photos() {
		return nb_photos;
	}

	public void setNb_photos(int nb_photos) {
		this.nb_photos = nb_photos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getJoueur() {
		return joueur;
	}

	public void setJoueur(String joueur) {
		this.joueur = joueur;
	}
	
	 public Set<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
}
