package it.itresources.springtut.springtutorial.repository;

import it.itresources.springtut.springtutorial.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Long> {

    List<GradeEntity> findAllByClassroomId (Long id) ;
}
