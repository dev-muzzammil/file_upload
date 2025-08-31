package com.example.file_upload.mngt.Service;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.file_upload.mngt.Entity.UploadImage;
import com.example.file_upload.mngt.Repository.UploadImageRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class UploadImageImpl implements UploadImageService {

	@Autowired
	private UploadImageRepo uploadImageRepo;
		
		
	@Override
	public UploadImage saveImage(MultipartFile file) throws Exception {
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	    // Check for duplicate file name
	    if (uploadImageRepo.existsByFileName(fileName)) {
	        throw new Exception("A file with this name already exists: " + fileName);
	    }
	    
	    String contentType = file.getContentType();
	    	if (contentType == null || !(contentType.contains("jpeg")
	    			|| contentType.contains("png") || contentType.contains("pdf"))) {
	    		throw new Exception("Invalid file type, Only JPEG, PNG, & PDF files are allowed");
	    	}

	    // Prepare and save attachment
	    UploadImage uploadImage = new UploadImage();
	    uploadImage.setFileName(fileName);
	    uploadImage.setFileType(file.getContentType());
	    uploadImage.setData(file.getBytes());

	    return uploadImageRepo.save(uploadImage);
	}

		
		
	@Override
	public UploadImage getImage(String fileId) throws Exception {
		return uploadImageRepo
				.findById(fileId)
				.orElseThrow
				(() -> new Exception("File not found with Id: " + fileId));
	}



}

