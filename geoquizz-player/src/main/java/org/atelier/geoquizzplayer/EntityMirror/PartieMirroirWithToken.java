package org.atelier.geoquizzplayer.EntityMirror;


import java.util.List;
import java.util.Set;

import org.atelier.geoquizzplayer.entity.Photo;
import org.springframework.hateoas.core.Relation;


@Relation(collectionRelation = "commandes")
public class PartieMirroirWithToken extends PartieMirroir {

  
    private String token = "";

    
    public PartieMirroirWithToken (String id, int nb, String status, int score, String joueur, Set<Photo> photos, String token ) {
    	super(id, nb, status, score, joueur, photos);
    	this.token = token;
    }

    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
}