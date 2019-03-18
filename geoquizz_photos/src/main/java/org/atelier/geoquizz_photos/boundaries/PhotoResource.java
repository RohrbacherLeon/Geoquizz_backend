package org.atelier.geoquizz_photos.boundaries;

import java.util.Optional;

import org.atelier.geoquizz_photos.entities.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoResource extends CrudRepository<Photo, String>{

	public Optional<Photo> findByToken(String token);
	
	public Optional<Photo> findByUrl(String url);
}