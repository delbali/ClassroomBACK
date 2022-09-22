package it.itresources.springtut.springtutorial.model.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

public class DocumentDTO {
	
	@NotNull
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String type;
	
	@NotNull
	private String description;
	
	@NotNull
	@Lob
	private byte[] data;
	
	@NotNull
	private UserDTO creator;
	
	@NotNull
	private ClassroomDTO uploadedTo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public UserDTO getCreator() {
		return creator;
	}

	public void setCreator(UserDTO creator) {
		this.creator = creator;
	}

	public ClassroomDTO getUploadedTo() {
		return uploadedTo;
	}

	public void setUploadedTo(ClassroomDTO uploadedTo) {
		this.uploadedTo = uploadedTo;
	}

	public DocumentDTO(@NotNull Long id, @NotNull String name, @NotNull String type, @NotNull String description,
			@NotNull byte[] data, @NotNull UserDTO creator, @NotNull ClassroomDTO uploadedTo) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.data = data;
		this.creator = creator;
		this.uploadedTo = uploadedTo;
	}

	public DocumentDTO() {
		super();
	}
	
	

}
