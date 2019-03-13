package org.atelier.geoquizzplayer.EntityMirror;

import java.util.List;
import java.util.Set;

import org.atelier.geoquizzplayer.entity.Photo;
import org.atelier.geoquizzplayer.entity.Serie;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "parties")
public class PartieMirroir {

	private String id;
	private int nb_photos;
	private int status;
	private int score;
	private String joueur;
	private Set<Photo> photos;
	private Serie serie;


	public PartieMirroir(String id, int nb, int status, int score, String joueur, Set<Photo> set, Serie serie) {
		this.id = id;
		this.nb_photos = nb;
		this.status = status;
		this.score = score;
		this.joueur = joueur;
		this.setPhotos(set);
		this.setSerie(serie);
	}
    
   

	public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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
		return photos;
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