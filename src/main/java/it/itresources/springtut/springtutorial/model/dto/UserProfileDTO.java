package it.itresources.springtut.springtutorial.model.dto;

import java.util.List;

public class UserProfileDTO {

    private Long id;

    private String username;

    private List<String> roles;

    private String firstName;

    private String lastName;

    private List<String> classrooms;

    private String address;

    private String subscriptionDate;

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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

    public List<String> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<String> classrooms) {
        this.classrooms = classrooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public UserProfileDTO(Long id, String username, List<String> roles, String firstName, String lastName, List<String> classrooms, String address, String subscriptionDate) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classrooms = classrooms;
        this.address = address;
        this.subscriptionDate = subscriptionDate;
    }

    public UserProfileDTO() {
    }
}
