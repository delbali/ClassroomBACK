package it.itresources.springtut.springtutorial.services;

import it.itresources.springtut.springtutorial.entity.ClassroomEntity;
import it.itresources.springtut.springtutorial.model.dto.ClassroomDTO;
import it.itresources.springtut.springtutorial.model.dto.ClassroomListDTO;
import it.itresources.springtut.springtutorial.model.request.ClassroomNewRequest;

import java.util.List;
import java.util.Optional;

public interface ServiceClassroom {
    public Optional<ClassroomEntity> loadClassroom(Long id);
    public List<ClassroomListDTO> classroomList();
    public ClassroomDTO createClassroom(ClassroomNewRequest request, String creatorName);
    public ClassroomDTO loadDTO (Long id);
    public Boolean saveStudentInClassroom(Long id, Long myId);
    public Boolean deleteStudentFromClassroom(Long id, Long myId);
    public Boolean checkSubscribers (Long id);
    public void deleteClassroom (Long id);
    public List<String> findByCreatedBy (String createdBy);
}
