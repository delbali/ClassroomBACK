package it.itresources.springtut.springtutorial.model.dto;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private List<String> roles;
    private String firstName;
    private String lastName;
    private List<String> classrooms;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, List<String> roles, String firstName, String lastName, List<String> classrooms ) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.firstName=firstName;
        this.lastName=lastName;
        this.classrooms=classrooms;
    }

    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public UserDTO id(Long id) {
        setId(id);
        return this;
    }

    public UserDTO username(String username) {
        setUsername(username);
        return this;
    }

    public UserDTO roles(List<String> roles) {
        setRoles(roles);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", username='" + getUsername() + "'" +
            ", roles='" + getRoles() + "'" +
            "}";
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
}
