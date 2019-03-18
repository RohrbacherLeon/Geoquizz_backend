package org.atelier.geoquizzplayer.boundaries;

import java.util.Optional;

import org.atelier.geoquizzplayer.entity.Partie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartieRessource extends JpaRepository<Partie, String>{
	Optional<Partie> findByIdAndToken(String id, String token);
	Iterable<Partie> findAllByOrderByScoreAsc();
}
