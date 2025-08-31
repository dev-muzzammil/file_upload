package com.example.file_upload.mngt.Service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.example.file_upload.mngt.Entity.ImageFolder;

public interface ImageFolderService {

	ImageFolder saveFolder(MultipartFile file) throws IOException;
	
	Resource loadFileAsResource(String filename) throws MalformedURLException;
	
}
