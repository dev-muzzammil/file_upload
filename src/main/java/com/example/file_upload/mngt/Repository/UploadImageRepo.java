package com.example.file_upload.mngt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.file_upload.mngt.Entity.UploadImage;


@Repository

public interface UploadImageRepo extends JpaRepository<UploadImage, String>{

	boolean existsByFileName(String fileName);

}

