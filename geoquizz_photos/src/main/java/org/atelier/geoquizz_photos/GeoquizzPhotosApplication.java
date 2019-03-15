package org.atelier.geoquizz_photos;

import org.atelier.geoquizz_photos.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
@EnableSwagger2
public class GeoquizzPhotosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoquizzPhotosApplication.class, args);
	}

}
