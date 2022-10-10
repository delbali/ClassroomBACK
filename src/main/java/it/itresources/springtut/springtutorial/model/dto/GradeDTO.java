package it.itresources.springtut.springtutorial.model.dto;

import javax.validation.constraints.NotNull;

public class GradeDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long grade;

    @NotNull
    private Long classroom;

    @NotNull
    private Long student;

    public GradeDTO(Long id, Long grade, Long classroom, Long student) {
        this.id = id;
        this.grade = grade;
        this.classroom = classroom;
        this.student = student;
    }

    public GradeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public Long getClassroom() {
        return classroom;
    }

    public void setClassroom(Long classroom) {
        this.classroom = classroom;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }
}
