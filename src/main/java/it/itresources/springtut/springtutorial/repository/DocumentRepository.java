package it.itresources.springtut.springtutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.itresources.springtut.springtutorial.entity.DocumentEntity;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long>{

}
