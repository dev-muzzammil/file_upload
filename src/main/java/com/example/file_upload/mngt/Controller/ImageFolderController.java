package com.example.file_upload.mngt.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.file_upload.mngt.Entity.ImageFolder;
import com.example.file_upload.mngt.Service.ImageFolderService;


@RestController
@RequestMapping("/upload")

public class ImageFolderController {

	@Autowired
	private ImageFolderService imageFolderService;

	@PostMapping
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
        	// Save image and metadata
        	ImageFolder saved = imageFolderService.saveFolder(file);

        	// Build URL for accessing the uploaded file
        	String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        			.path("/upload/files/")
        			.path(saved.getFileName())
        			.toUriString();

        	return ResponseEntity.ok("File Saved! Download URL: " + fileDownloadUri);
	        }
	        catch (IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed.");
	        }
	}
		
		
	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		try {
			// 1. Ask the service to load the file from the upload folder
			Resource resource = imageFolderService.loadFileAsResource(filename);

			// 2. Return the file as a downloadable response
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_OCTET_STREAM) // Set content type as binary
					.body(resource); // Return the file

		    } 
			catch (Exception e) {
		        // ❌ File not found or something went wrong
		        return ResponseEntity.notFound().build();
		    }
		}


}
