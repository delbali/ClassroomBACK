package it.itresources.springtut.springtutorial.model.request;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadRequest {
	
	@NotNull
	private MultipartFile file;
	@NotNull
	private String username;
	
	private String description;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FileUploadRequest(@NotNull MultipartFile file, @NotNull String username, String description) {
		super();
		this.file = file;
		this.username = username;
		this.description = description;
	}
	
	
	
	
	
	

}
