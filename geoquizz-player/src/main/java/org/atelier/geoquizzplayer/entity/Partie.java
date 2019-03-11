package org.atelier.geoquizzplayer.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	public Partie () {
		System.out.println("ici");
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
}
