package com.example.file_upload.mngt.Entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor				

public class UploadImage {

	@Id
	
// -> UUID = Universally Unique Identifier
	@GeneratedValue(generator = "uuid")
	
// -> Define a custom ID generator using UUID version 2 for unique file identifiers
	@GenericGenerator(name = "uuid", strategy = "uuid2")	
		
// -> We use String for id because UUID is string-based and it gives us a globally unique identifier for every file.	
	private String id;	
		
	private String fileName;
	private String fileType;
		
	@Lob
	private byte[] data;

}

