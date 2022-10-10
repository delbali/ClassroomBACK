package it.itresources.springtut.springtutorial.mapper;

import it.itresources.springtut.springtutorial.entity.GradeEntity;
import it.itresources.springtut.springtutorial.model.dto.GradeDTO;

public class GradeMapper {

    public static GradeDTO entityToDto (GradeEntity entity)
    {
        GradeDTO dto=new GradeDTO();
        dto.setId(entity.getId());
        dto.setGrade(entity.getGrade());
        dto.setStudent(entity.getStudent().getId());
        dto.setClassroom(null);
        return dto;
    }
}
