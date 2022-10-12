package it.itresources.springtut.springtutorial.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.itresources.springtut.springtutorial.model.dto.UserProfileDTO;
import it.itresources.springtut.springtutorial.model.request.PasswordUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	private final PasswordEncoder encoder;
	@Autowired
	public ServiceUserImpl(PasswordEncoder encoder, ClassroomRepository classroomRepository, UserRepository userRepository) {
		this.encoder=encoder;
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
		List<String> teacherClassrooms = classroomRepository.findClassroomsByCreatorId(id);
		System.out.println(teacherClassrooms);
	    return teacherClassrooms;
	}

	public UserProfileDTO updateUser(UserProfileDTO profile)
	{
		UserEntity user= userRepository.getById(profile.getId());
		user.setUsername(profile.getUsername());
		user.setFirstName(profile.getFirstName());
		user.setLastName(profile.getLastName());
		user.setAddress(profile.getAddress());
		return UserMapper.entityToProfile(userRepository.save(user));
	}

	public Boolean updatePassword (PasswordUpdateRequest request, UserEntity user)
	{
		if(encoder.matches(request.getOldPassword(), user.getPassword()))
		{
			System.out.println("Passwords match");
			user.setPassword(encoder.encode(request.getNewPassword()));
			userRepository.save(user);
			return true;
		} else {
			return false;
		}
	}
}
