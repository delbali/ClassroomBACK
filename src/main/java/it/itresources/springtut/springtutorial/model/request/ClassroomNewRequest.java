package it.itresources.springtut.springtutorial.model.request;

import javax.validation.constraints.NotNull;

public class ClassroomNewRequest {
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	@NotNull
	private String createdBy;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public ClassroomNewRequest(@NotNull String title, @NotNull String description, @NotNull String createdBy) {
		super();
		this.title = title;
		this.description = description;
		this.createdBy = createdBy;
	}

	public ClassroomNewRequest() {
		super();
	}
	
	

}
