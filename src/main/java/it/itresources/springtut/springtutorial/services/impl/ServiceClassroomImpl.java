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
import it.itresources.springtut.springtutorial.mapper.ClassroomMapper;
import it.itresources.springtut.springtutorial.repository.ClassroomRepository;
import it.itresources.springtut.springtutorial.services.ServiceClassroom;

@Service
public class ServiceClassroomImpl implements ServiceClassroom{
	
	private final ClassroomRepository classroomRepository;
	
	public ServiceClassroomImpl (ClassroomRepository classroomRepository)
	{
		this.classroomRepository=classroomRepository;
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
	
	public ClassroomDTO createClassroom(ClassroomNewRequest request)
	{
		return ClassroomMapper.entityToDTO(classroomRepository.save(ClassroomMapper.requestToEntity(request)));
	}

}
