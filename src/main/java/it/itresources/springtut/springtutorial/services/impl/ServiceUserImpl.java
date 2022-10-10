package it.itresources.springtut.springtutorial.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.itresources.springtut.springtutorial.entity.ClassroomEntity;
import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.mapper.UserMapper;
import it.itresources.springtut.springtutorial.model.dto.UserListDTO;
import it.itresources.springtut.springtutorial.repository.ClassroomRepository;
import it.itresources.springtut.springtutorial.repository.UserRepository;
import it.itresources.springtut.springtutorial.services.ServiceUser;

import javax.swing.text.html.Option;

@Service
public class ServiceUserImpl implements ServiceUser {

	private final UserRepository userRepository;
	private final ClassroomRepository classroomRepository;

	@Autowired
	public ServiceUserImpl(ClassroomRepository classroomRepository, UserRepository userRepository) {
		this.userRepository = userRepository;
		this.classroomRepository = classroomRepository;
	}

	public Boolean checkUserExistance(String username) throws UsernameNotFoundException {
		Boolean check = userRepository.existsByUsername(username);
		return check;
	}

	public Optional<List<UserEntity>> getAllStudents(){return Optional.of(userRepository.findAll());}
	public Optional<UserEntity> registration(UserEntity newUser) {
		return Optional.of(this.userRepository.save(newUser));
	}

	public Optional<UserEntity> loadByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public List<UserListDTO> userList(Long classroomId) {
		List<UserListDTO> dtoList = new ArrayList<>();
		classroomRepository.findById(classroomId).get().getSubscribers().forEach(user -> {
			dtoList.add(UserMapper.entityToListDTO(user));
		});
		return dtoList;
	}

	public Boolean checkIfTeacher(Long id) {
		Boolean teacher = null;
		UserEntity user = userRepository.findById(id).get();
		for (int i = 0; i < user.getRoles().size(); i++) {
			if (user.getRoles().get(i).getName() == "ROLE_TEACHER") {
				teacher=true;
			} else {
				teacher=false;
			}
		}
		return teacher;
	}

	public Optional<UserEntity> loadById (Long id)
	{
		return this.userRepository.findById(id);
	}
	public List<String> getTeacherClassrooms (Long id)
	{
		List<String> teacherClassrooms = new ArrayList<>();
		return null;
	}
}
