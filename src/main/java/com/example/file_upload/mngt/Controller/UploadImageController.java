 package com.example.file_upload.mngt.Controller;
	
import org.springframework.http.HttpHeaders; 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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

import com.example.file_upload.mngt.ResponseData;
import com.example.file_upload.mngt.Entity.UploadImage;
import com.example.file_upload.mngt.Service.UploadImageService;

import org.springframework.core.io.Resource; 


@RestController
@RequestMapping("/attachment")

public class UploadImageController {

	@Autowired
	private UploadImageService uploadImageService;
		
		
	@PostMapping
	public ResponseData uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		UploadImage uploadImage = uploadImageService.saveImage(file);

	    String downloadURL = ServletUriComponentsBuilder	// → It is a utility class in Spring that helps you build full URIs (URLs) dynamically based on the current HTTP request context.
	    		.fromCurrentContextPath()	//  → Gets the base URL from the request (http://localhost:8080).
	            .path("/attachment/download/")	//  → Adds sub-paths like /attachment/download/.
	            .path(uploadImage.getId())	// → Appends the actual file ID to the URL.
	            .toUriString();		// → Converts everything into a final string URL.

	    return new ResponseData(
	    		uploadImage.getFileName(),
	            downloadURL,
	            file.getContentType(),
	            file.getSize()
	    );

}

		
		
	@GetMapping("/download/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception{
		UploadImage uploadImage = null;
		uploadImage = uploadImageService.getImage(fileId);
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(uploadImage.getFileType()))	// → MediaType.parseMediaType(...) is used to convert a string like "application/pdf" or "image/png" into a MediaType object in Spring.
					.header(HttpHeaders.CONTENT_DISPOSITION,		// → header(...): tells browser to download it with a specific filename.
							// → C_D: Browser shows Download prompt instead of opening it


					"uploadImage; filename=\"" + uploadImage.getFileName()
					+"\"")
					.body(new ByteArrayResource(uploadImage.getData()));
	}
	
	
		
}
