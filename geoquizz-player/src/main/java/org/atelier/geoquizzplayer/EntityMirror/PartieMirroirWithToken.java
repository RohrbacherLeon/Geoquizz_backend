package org.atelier.geoquizzplayer.EntityMirror;


import java.util.List;
import java.util.Set;

import org.atelier.geoquizzplayer.entity.Photo;
import org.atelier.geoquizzplayer.entity.Serie;
import org.springframework.hateoas.core.Relation;


@Relation(collectionRelation = "parties")
public class PartieMirroirWithToken extends PartieMirroir {

  
    private String token = "";

    
    public PartieMirroirWithToken (String id, int nb, int status, int score, String joueur, Serie serie, String token ) {
    	super(id, nb, status, score, joueur, serie);
    	this.token = token;
    }

    
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
}