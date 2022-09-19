package it.itresources.springtut.springtutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.itresources.springtut.springtutorial.entity.ClassroomEntity;

public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long>{

}
