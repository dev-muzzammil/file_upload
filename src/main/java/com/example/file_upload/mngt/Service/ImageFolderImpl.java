package com.example.file_upload.mngt.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.file_upload.mngt.Entity.ImageFolder;
import com.example.file_upload.mngt.Repository.ImageFolderRepo;
	
@Service

public class ImageFolderImpl implements ImageFolderService{

		
	public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";	// -> It defines the folder path where uploaded files will be stored.
		
		
	@Autowired
	private ImageFolderRepo imageFolderRepo;
		
			

	@Override
	public ImageFolder saveFolder(MultipartFile file) throws IOException {
	        
	// Clean file name (remove special characters or path)
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

	// Extract file extension (.jpg, .png, etc.)
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

	// Generate unique name using UUID to avoid filename conflicts
		String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

	// Create upload folder if it doesn't exist
		Path uploadPath = Paths.get(UPLOAD_DIR);
	        
		if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
		}
		

	// Full path where file will be saved
		Path filePath = uploadPath.resolve(uniqueFileName);

	// Copy file content to the target location (REPLACE if exists)
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

	// Save file details in database
		ImageFolder imageFolder = new ImageFolder();
		imageFolder.setFileName(uniqueFileName);
		imageFolder.setFilePath(filePath.toString());
		imageFolder.setFileType(file.getContentType());

		return imageFolderRepo.save(imageFolder);

    }
	    
	    
	
	@Override
    public Resource loadFileAsResource(String filename) throws MalformedURLException {
	    	
	// 1. Construct the full path to the file inside the upload directory
		Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();

	// 2. Convert the file path into a Resource object (Spring uses this to return files)
		Resource resource = new UrlResource(filePath.toUri());

	// 3. Check if the file/resource exists
		if (resource.exists()) {
	            return resource; // ✅ Return the file as a Resource if found
		} 
		else {
			// ❌ If not found, throw a runtime exception (can customize this later)
	            throw new RuntimeException("File not found: " + filename);
	        }
	    }


}