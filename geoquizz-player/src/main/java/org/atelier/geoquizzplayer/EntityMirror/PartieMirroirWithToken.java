package org.atelier.geoquizzplayer.EntityMirror;


import org.springframework.hateoas.core.Relation;


@Relation(collectionRelation = "commandes")
public class PartieMirroirWithToken extends PartieMirroir {

  
    private String token = "";

    
    public PartieMirroirWithToken (String id, int photos, String status, int score, String joueur, String token ) {
    	super(id, photos, status, score, joueur);
    	this.token = token;
    }

    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
}