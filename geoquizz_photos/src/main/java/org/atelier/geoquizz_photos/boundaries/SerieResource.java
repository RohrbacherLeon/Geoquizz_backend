package org.atelier.geoquizz_photos.boundaries;

import java.util.Optional;

import org.atelier.geoquizz_photos.entities.Serie;
import org.springframework.data.repository.CrudRepository;

public interface SerieResource extends CrudRepository<Serie, String> {

	public Optional<Serie> findByVille(String ville);
	
}