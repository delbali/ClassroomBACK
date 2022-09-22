package it.itresources.springtut.springtutorial.model.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;

public class ClassroomListDTO {

	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	@NotNull
	private String createdBy;
	
	@NotNull
	private Map<Long, String> subscribers;

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

	public Map<Long, String> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Map<Long, String> subscribers) {
		this.subscribers = subscribers;
	}

	public ClassroomListDTO(@NotNull String title, @NotNull String description, @NotNull String createdBy,
			@NotNull Map<Long, String> subscribers) {
		super();
		this.title = title;
		this.description = description;
		this.createdBy = createdBy;
		this.subscribers = subscribers;
	}

	public ClassroomListDTO() {
		super();
	}
	
	
}
