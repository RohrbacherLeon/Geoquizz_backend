package org.atelier.geoquizz_photos;

import org.atelier.geoquizz_photos.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class GeoquizzPhotosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoquizzPhotosApplication.class, args);
	}

}
