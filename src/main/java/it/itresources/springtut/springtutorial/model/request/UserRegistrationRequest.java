package it.itresources.springtut.springtutorial.model.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserRegistrationRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private List<String> roles;


    public UserRegistrationRequest() {
    }

    public UserRegistrationRequest(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
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
