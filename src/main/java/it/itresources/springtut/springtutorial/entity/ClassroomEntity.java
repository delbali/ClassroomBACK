package it.itresources.springtut.springtutorial.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(mappedBy="classrooms")
	private List<UserEntity> subscribers=new ArrayList<>();

	
}
