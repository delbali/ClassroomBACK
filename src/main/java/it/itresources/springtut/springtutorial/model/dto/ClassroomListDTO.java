package it.itresources.springtut.springtutorial.model.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public class ClassroomListDTO {

	@NotNull
	private Long id;

	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	@NotNull
	private String createdBy;
	
	@NotNull
	private List<UserDTO> subscribers;

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

	public List<UserDTO> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<UserDTO> subscribers) {
		this.subscribers = subscribers;
	}

	public ClassroomListDTO(@NotNull Long id, @NotNull String title, @NotNull String description, @NotNull String createdBy,
			@NotNull List<UserDTO> subscribers, @NotNull String creatorName) {
		super();
		this.id=id;
		this.title = title;
		this.description = description;
		this.createdBy = createdBy;
		this.subscribers = subscribers;
		this.creatorName=creatorName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClassroomListDTO() {
		super();
	}
	
	
}
