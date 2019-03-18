package org.atelier.geoquizz_photos.entityMirror;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "users")
public class UserMirrorWithToken extends UserMirror{
	
	private String token;
	
	public UserMirrorWithToken(String id, String login, String token) {
		super(id, login);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
