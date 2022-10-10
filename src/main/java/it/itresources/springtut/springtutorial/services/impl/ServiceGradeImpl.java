package it.itresources.springtut.springtutorial.services.impl;

import it.itresources.springtut.springtutorial.entity.GradeEntity;
import it.itresources.springtut.springtutorial.model.dto.GradeDTO;
import it.itresources.springtut.springtutorial.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceGradeImpl {

    private final GradeRepository gradeRepository;

    @Autowired
    public ServiceGradeImpl (GradeRepository gradeRepository)
    {
        this.gradeRepository=gradeRepository;
    }

    public List<GradeEntity> getGrades (Long id)
    {
        return this.gradeRepository.findAllByClassroomId(id);
    }


}
