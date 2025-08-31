package com.example.file_upload.mngt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.file_upload.mngt.Entity.ImageFolder;


@Repository

public interface ImageFolderRepo extends JpaRepository<ImageFolder, Integer>{
	
}