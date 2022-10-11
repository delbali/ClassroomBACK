package it.itresources.springtut.springtutorial.model.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserDetailsDTO {

    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private List<String> classrooms;

    private List<GradeDTO> grades;

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

    public List<GradeDTO> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeDTO> grades) {
        this.grades = grades;
    }

    public UserDetailsDTO() {
    }

    public UserDetailsDTO(Long id, String username, String firstName, String lastName, List<String> classrooms, List<GradeDTO> grades) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classrooms = classrooms;
        this.grades = grades;
    }
}
