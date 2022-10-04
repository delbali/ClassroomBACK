package it.itresources.springtut.springtutorial.model.dto;

import java.util.List;
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
	private List<String> subscribers;

	@NotNull
	private String creatorName;

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
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

	public List<String> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<String> subscribers) {
		this.subscribers = subscribers;
	}

	public ClassroomListDTO(@NotNull String title, @NotNull String description, @NotNull String createdBy,
			@NotNull List<String> subscribers, @NotNull String creatorName) {
		super();
		this.title = title;
		this.description = description;
		this.createdBy = createdBy;
		this.subscribers = subscribers;
		this.creatorName=creatorName;
	}

	public ClassroomListDTO() {
		super();
	}
	
	
}
