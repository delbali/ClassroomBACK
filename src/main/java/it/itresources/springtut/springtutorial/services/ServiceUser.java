package it.itresources.springtut.springtutorial.services;

import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.model.dto.UserListDTO;

import java.util.List;
import java.util.Optional;

public interface ServiceUser {
    public Boolean checkUserExistance(String username);
    public Optional<List<UserEntity>> getAllStudents();
    public Optional<UserEntity> registration(UserEntity newUser);
    public Optional<UserEntity> loadByUsername(String username);
    public List<UserListDTO> userList(Long classroomId);
    public Boolean checkIfTeacher(Long id);
    public Optional<UserEntity> loadById (Long id);
    public List<String> getTeacherClassrooms (Long id);

}
