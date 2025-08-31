package com.example.file_upload.mngt.Service;

import org.springframework.web.multipart.MultipartFile;


import com.example.file_upload.mngt.Entity.UploadImage;

public interface UploadImageService {

	UploadImage saveImage(MultipartFile file) throws Exception ;
	
	UploadImage getImage(String fileId) throws Exception ;
}
