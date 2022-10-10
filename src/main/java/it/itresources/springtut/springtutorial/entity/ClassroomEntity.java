package it.itresources.springtut.springtutorial.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "classrooms")
public class ClassroomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String title;

    @Column()
    private String description;

    @Column()
    private String createdBy;

    @Column()
    private String creatorName;

    @ManyToMany(mappedBy = "classrooms")
    private List<UserEntity> subscribers = new ArrayList<>();

    @OneToMany
            (cascade = CascadeType.ALL,
                    fetch = FetchType.LAZY,
                    mappedBy = "uploadedTo")
    List<DocumentEntity> uploads = new ArrayList<>();

    @OneToMany
            (cascade = CascadeType.ALL,
                    fetch = FetchType.LAZY,
                    mappedBy = "classroom")
    List<GradeEntity> grades = new ArrayList<>();

    public List<GradeEntity> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeEntity> grades) {
        this.grades = grades;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<UserEntity> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<UserEntity> subscribers) {
        this.subscribers = subscribers;
    }

    public List<DocumentEntity> getUploads() {
        return uploads;
    }

    public void setUploads(List<DocumentEntity> uploads) {
        this.uploads = uploads;
    }

    public ClassroomEntity(Long id, String title, String description, String createdBy, String creatorName, List<UserEntity> subscribers,
                           List<DocumentEntity> uploads) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.creatorName = creatorName;
        this.subscribers = subscribers;
        this.uploads = uploads;
    }

    public ClassroomEntity() {
        super();
    }


}
