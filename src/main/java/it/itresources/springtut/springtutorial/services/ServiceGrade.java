package it.itresources.springtut.springtutorial.services;

import it.itresources.springtut.springtutorial.entity.GradeEntity;

import java.util.List;

public interface ServiceGrade {
    public List<GradeEntity> getGrades (Long id);
}
