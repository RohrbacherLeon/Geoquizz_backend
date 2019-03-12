package org.atelier.geoquizzplayer.EntityMirror;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "parties")
public class PartieMirroir {

	private String id;
	private int nb_photos;
	private String status = "Créée";
	private int score;
	private String joueur;


	public PartieMirroir(String id, int photos, String status, int score, String joueur) {
		this.id = id;
		this.nb_photos = photos;
		this.status = status;
		this.score = score;
		this.joueur = joueur;
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