package it.itresources.springtut.springtutorial.model.request;

import javax.validation.constraints.NotNull;

public class NewGradeRequest {
    @NotNull
    private Long classroomId;

    @NotNull
    private Long studentId;

    @NotNull
    private Long newGrade;

    public NewGradeRequest() {
    }

    public NewGradeRequest(Long classroomId, Long studentId, Long newGrade) {
        this.classroomId = classroomId;
        this.studentId = studentId;
        this.newGrade = newGrade;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getNewGrade() {
        return newGrade;
    }

    public void setNewGrade(Long newGrade) {
        this.newGrade = newGrade;
    }
}
