package org.atelier.geoquizz_photos.boundaries;

import org.atelier.geoquizz_photos.entities.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoResource extends CrudRepository<Photo, String>{

}
