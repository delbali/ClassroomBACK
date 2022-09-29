package it.itresources.springtut.springtutorial.model.dto;

import javax.validation.constraints.NotNull;

public class UserListDTO {

	@NotNull
	private Long id;
	
	@NotNull
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserListDTO(@NotNull Long id, @NotNull String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public UserListDTO() {
		super();
	}
	
	
	
}
