package org.atelier.geoquizzplayer.boundaries;

import java.util.Optional;

import org.atelier.geoquizzplayer.entity.Serie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SerieResource extends CrudRepository<Serie, String> {

	public Optional<Serie> findByVille(String ville);
	
}