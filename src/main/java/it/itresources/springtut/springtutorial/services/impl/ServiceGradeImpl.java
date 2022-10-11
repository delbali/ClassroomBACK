package it.itresources.springtut.springtutorial.services.impl;

import it.itresources.springtut.springtutorial.entity.GradeEntity;
import it.itresources.springtut.springtutorial.mapper.UserMapper;
import it.itresources.springtut.springtutorial.model.dto.GradeDTO;
import it.itresources.springtut.springtutorial.model.dto.UserDetailsDTO;
import it.itresources.springtut.springtutorial.model.request.NewGradeRequest;
import it.itresources.springtut.springtutorial.repository.ClassroomRepository;
import it.itresources.springtut.springtutorial.repository.GradeRepository;
import it.itresources.springtut.springtutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceGradeImpl {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    @Autowired
    public ServiceGradeImpl (UserRepository userRepository, ClassroomRepository classroomRepository, GradeRepository gradeRepository)
    {
        this.userRepository=userRepository;
        this.classroomRepository=classroomRepository;
        this.gradeRepository=gradeRepository;
    }

    public List<GradeEntity> getGrades (Long id)
    {
        return this.gradeRepository.findAllByClassroomId(id);
    }

    public UserDetailsDTO assignGrades (NewGradeRequest request)
    {
        GradeEntity entity = new GradeEntity();
        entity.setClassroom(classroomRepository.getById(request.getClassroomId()));
        entity.setGrade(request.getNewGrade());
        entity.setStudent(userRepository.getById(request.getStudentId()));

        if (gradeRepository.save(entity)!=null)
        {
            return UserMapper.entityToDetails(userRepository.getById(request.getStudentId()));
        }
        else {
            return null;
        }
    }
}
