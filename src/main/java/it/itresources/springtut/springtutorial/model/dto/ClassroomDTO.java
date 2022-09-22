package it.itresources.springtut.springtutorial.model.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public class ClassroomDTO {
	
	@NotNull
	private Long id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	@NotNull
	private String createdBy;
	
	
	private Map<Long, String> subscribers;
	
	
	private Map<Long, String> uploads;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	

	public ClassroomDTO(@NotNull Long id, @NotNull String title, @NotNull String description, @NotNull String createdBy,
			@NotNull Map<Long, String> subscribers, @NotNull Map<Long, String> uploads) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.createdBy = createdBy;
		this.subscribers = subscribers;
		this.uploads = uploads;
	}

	public Map<Long, String> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Map<Long, String> subscribers) {
		this.subscribers = subscribers;
	}

	public Map<Long, String> getUploads() {
		return uploads;
	}

	public void setUploads(Map<Long, String> uploads) {
		this.uploads = uploads;
	}

	public ClassroomDTO() {
		super();
	}
	
	

}
