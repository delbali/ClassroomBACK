package it.itresources.springtut.springtutorial.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

public class DocumentEntity {

	@OneToMany
	(cascade=CascadeType.DETACH,
    fetch=FetchType.LAZY,
    mappedBy="document")
	List<Employee> members = new ArrayList<>();
}
