package it.itresources.springtut.springtutorial.services;

import it.itresources.springtut.springtutorial.entity.GradeEntity;
import it.itresources.springtut.springtutorial.model.dto.UserDetailsDTO;
import it.itresources.springtut.springtutorial.model.request.NewGradeRequest;
import org.apache.catalina.User;

import java.util.List;

public interface ServiceGrade {
    public List<GradeEntity> getGrades (Long id);

    public UserDetailsDTO assignGrades (NewGradeRequest request);
}
