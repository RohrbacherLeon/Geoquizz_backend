package org.atelier.geoquizz_photos.entityMirror;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "users")
public class UserMirror {
	
	private String id;
	private String login;
	
	public UserMirror(String id, String login) {
		this.id = id;
		this.login = login;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
