package it.itresources.springtut.springtutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.itresources.springtut.springtutorial.entity.ClassroomEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long>{
    @Query(value = "SELECT cla.title FROM CLASSROOMS as cla INNER JOIN USERS as us ON us.id = ?1", nativeQuery = true)
    List<String> findClassroomsByCreatorId(Long id);

    Optional<ClassroomEntity> findByTitle(String title);
}
