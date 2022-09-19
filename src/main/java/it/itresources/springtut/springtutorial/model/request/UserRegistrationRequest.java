package it.itresources.springtut.springtutorial.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserRegistrationRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;
    
    @NotNull
    private String firstName;
    
    @NotNull
    private String lastName;
    
    @NotNull
    private String address;
    
    @NotNull
    private String subscriptionDate;
    
    @NotNull
    private List<String> roles;



	public UserRegistrationRequest(@NotNull String username, @NotNull String password, @NotNull String firstName,
			@NotNull String lastName, @NotNull String address, @NotNull String subscriptionDate,
			@NotNull List<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.subscriptionDate = subscriptionDate;
		this.roles = roles;
	}


	public String getSubscriptionDate() {
		return subscriptionDate;
	}


	public void setSubscriptionDate(String subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}


    
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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

	public UserRegistrationRequest() {
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public UserRegistrationRequest username(String username) {
        setUsername(username);
        return this;
    }

    public UserRegistrationRequest password(String password) {
        setPassword(password);
        return this;
    }

    public UserRegistrationRequest roles(List<String> roles) {
        setRoles(roles);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", roles='" + getRoles() + "'" +
            "}";
    }

}
