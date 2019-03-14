package org.atelier.geoquizz_photos.boundaries;

import java.util.Optional;

import org.atelier.geoquizz_photos.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserResource extends CrudRepository<User, String>{

	public Optional<User> findByLogin(String login);
	
}