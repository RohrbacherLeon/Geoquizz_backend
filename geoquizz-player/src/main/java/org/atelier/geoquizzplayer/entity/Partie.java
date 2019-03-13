package org.atelier.geoquizzplayer.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "parties")
public class Partie {
	
	@Id
	private String id;
	private String token;
	private int nb_photos;
	private int status;
	private int score;
	private String joueur;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="serie_id", nullable=false)
	@JsonBackReference
	private Serie serie;
	
	

	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name = "partie_photo", 
      joinColumns = @JoinColumn(name = "partie_id", referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id"))
    private Set<Photo> photos;
	
	Partie () {
		
	}
	
	public Partie(String joueur, Serie serie, Set<Photo> photos) {
		this.joueur = joueur;
		this.serie = serie;
		this.photos = photos;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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
    
    public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}
}
