package it.itresources.springtut.springtutorial.entity;

import javax.persistence.*;

@Entity
@Table(name = "grades")
public class GradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private Long grade;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassroomEntity classroom;

    public GradeEntity(Long id, Long grade, UserEntity student, ClassroomEntity classroom) {
        this.id = id;
        this.grade = grade;
        this.student = student;
        this.classroom = classroom;
    }

    public GradeEntity() {
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

    public UserEntity getStudent() {
        return student;
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    public ClassroomEntity getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomEntity classroom) {
        this.classroom = classroom;
    }
}
