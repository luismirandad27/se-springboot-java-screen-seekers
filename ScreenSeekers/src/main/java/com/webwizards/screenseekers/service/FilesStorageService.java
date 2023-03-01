package com.webwizards.screenseekers.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

public interface FilesStorageService {

	public void init();

	public void save(MultipartFile file);

	public Resource load(String filename);

	public void deleteAll();

	public Stream<Path> loadAll();
	
	public void deleteFile(String filename);
	
	public boolean fileExists(String filename);
	
}
