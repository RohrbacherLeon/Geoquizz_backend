package org.atelier.geoquizz_photos.service;

import org.springframework.web.multipart.MultipartFile;

public class FilesContainer {

	private MultipartFile[] files;
	
	FilesContainer(){}
	
	public FilesContainer(MultipartFile[] files) {
		this.files = files;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	
}