package org.atelier.geoquizz_photos.boundaries;

import org.atelier.geoquizz_photos.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserResource extends CrudRepository<User, String>{

}