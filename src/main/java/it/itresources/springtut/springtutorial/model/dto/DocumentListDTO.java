package it.itresources.springtut.springtutorial.model.dto;

import javax.validation.constraints.NotNull;

public class DocumentListDTO {
	
	@NotNull
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String type;
	
	@NotNull
	private String creator;
	
	@NotNull
	private String classroom;

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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public DocumentListDTO(@NotNull Long id, @NotNull String name, @NotNull String type, @NotNull String creator,
			@NotNull String classroom) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.creator = creator;
		this.classroom = classroom;
	}

	public DocumentListDTO() {
		super();
	}
	
	

}
