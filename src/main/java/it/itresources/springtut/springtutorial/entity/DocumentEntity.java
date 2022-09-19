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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
public class DocumentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column()
	private String name;
	
	@Column()
	private String type;
	
	@Column()
	private String description;
	
	@Lob
	private byte[] data;
	
	@Column()
	private String uploadedBy;
	
	@ManyToOne
	@JoinColumn(name = "classroom_id",nullable = false)
	private ClassroomEntity uploadedTo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public ClassroomEntity getUploadedTo() {
		return uploadedTo;
	}

	public void setUploadedTo(ClassroomEntity uploadedTo) {
		this.uploadedTo = uploadedTo;
	}

	public DocumentEntity(Long id, String name, String type, String description, byte[] data, String uploadedBy,
			ClassroomEntity uploadedTo) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.data = data;
		this.uploadedBy = uploadedBy;
		this.uploadedTo = uploadedTo;
	}

	public DocumentEntity() {
		super();
	}
	
	
}
