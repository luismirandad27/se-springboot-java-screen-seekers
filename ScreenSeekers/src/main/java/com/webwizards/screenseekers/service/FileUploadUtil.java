/**
 * Java Class: FileUploadUtil
 * 
 * ------------
 * Description:
 * ------------ 
 * This class consists in a method to store a file into a Path inside the project
 * 
 * @author Luis Miguel MIranda
 * @version 1.0
 **/

package com.webwizards.screenseekers.service;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	public static void saveFile(String uploadDir, String fileName,
           
		MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
         
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }      
    }
	
	public static void deleteFile(String filePath) throws IOException {
        Path fileToDeletePath = Paths.get(filePath);
        System.out.println(fileToDeletePath);
        Files.deleteIfExists(fileToDeletePath);
    }
	
}
