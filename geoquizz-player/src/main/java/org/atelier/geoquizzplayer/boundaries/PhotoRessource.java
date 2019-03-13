package org.atelier.geoquizzplayer.boundaries;

import org.atelier.geoquizzplayer.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRessource extends JpaRepository<Photo, String>{
	
}
