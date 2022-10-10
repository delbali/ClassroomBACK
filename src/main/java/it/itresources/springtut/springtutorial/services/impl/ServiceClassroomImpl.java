package it.itresources.springtut.springtutorial.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.itresources.springtut.springtutorial.model.*;
import it.itresources.springtut.springtutorial.model.dto.ClassroomDTO;
import it.itresources.springtut.springtutorial.model.dto.ClassroomListDTO;
import it.itresources.springtut.springtutorial.model.request.ClassroomNewRequest;
import it.itresources.springtut.springtutorial.entity.ClassroomEntity;
import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.mapper.ClassroomMapper;
import it.itresources.springtut.springtutorial.repository.ClassroomRepository;
import it.itresources.springtut.springtutorial.repository.UserRepository;
import it.itresources.springtut.springtutorial.services.ServiceClassroom;

@Service
public class ServiceClassroomImpl implements ServiceClassroom{
	
	private final ClassroomRepository classroomRepository;
	private final UserRepository userRepository;
	
	public ServiceClassroomImpl (UserRepository userRepository, ClassroomRepository classroomRepository)
	{
		this.classroomRepository=classroomRepository;
		this.userRepository=userRepository;
	}
	
	public Optional<ClassroomEntity> loadClassroom(Long id)
	{
		return classroomRepository.findById(id);
	}
	
	public List<ClassroomListDTO> classroomList()
	{
		List<ClassroomEntity> entityList = classroomRepository.findAll();
		List<ClassroomListDTO> dtoList = new ArrayList<>();
		
		entityList.forEach(entity->{
			dtoList.add(ClassroomMapper.entityToListDTO(entity));
		});;
		
		return dtoList;
	}
	
	public ClassroomDTO createClassroom(ClassroomNewRequest request, String creatorName)
	{
		return ClassroomMapper.entityToDTO(classroomRepository.save(ClassroomMapper.requestToEntity(request, creatorName)));
	}
	
	public ClassroomDTO loadDTO (Long id)
	{
		return ClassroomMapper.entityToDTO(loadClassroom(id).get());
	}
	
	public Boolean saveStudentInClassroom(Long id, Long myId)
	{
		ClassroomEntity entity=loadClassroom(id).get();
		UserEntity user=userRepository.findById(myId).get();
		System.out.println(myId);
		if (user.getClassrooms()!=null)
		{
			user.getClassrooms().add(entity);
			userRepository.save(user);
			System.out.println("ecchime"+entity.getSubscribers());
			return true;
		} else {
			return false;
		}
	}
	public Boolean deleteStudentFromClassroom(Long id, Long myId)
	{
		ClassroomEntity entity=loadClassroom(id).get();
		UserEntity user=userRepository.findById(myId).get();
		System.out.println(myId);
		if (user.getClassrooms()!=null)
		{
			user.getClassrooms().remove(entity);
			userRepository.save(user);
			System.out.println("ecchime"+entity.getSubscribers());
			return true;
		} else {
			return false;
		}
	}
	public Boolean checkSubscribers (Long id)
	{
		if (loadClassroom(id).get().getSubscribers()==null)
		{
			return false;
		}
		return true;
	}
	
	public void deleteClassroom (Long id) {
		
		classroomRepository.deleteById(id);
	}

	public Long loadByTitle (String title)
	{
		return this.classroomRepository.findByTitle(title).get().getId();
	}
}
