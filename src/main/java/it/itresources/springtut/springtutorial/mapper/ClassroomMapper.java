package it.itresources.springtut.springtutorial.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.model.dto.*;
import org.hibernate.mapping.Map;

import it.itresources.springtut.springtutorial.entity.ClassroomEntity;
import it.itresources.springtut.springtutorial.model.request.ClassroomNewRequest;

public class ClassroomMapper {
	
	public static ClassroomDTO entityToDTO(ClassroomEntity classroom)
	{
		ClassroomDTO dto = new ClassroomDTO();
		dto.setCreatedBy(classroom.getCreatedBy());
		dto.setDescription(classroom.getDescription());
		dto.setId(classroom.getId());
		List<UserDTO> subscribers = new ArrayList<>();
		if(classroom.getSubscribers()!=null)
		{
			classroom.getSubscribers().forEach(entity->{
				subscribers.add(UserMapper.entityToDto(entity));
			});
		}
		dto.setSubscribers(subscribers);
		dto.setTitle(classroom.getTitle());
		
		List<DocumentListDTO> uploads = new ArrayList<>();
		if(classroom.getUploads()!=null)
		{
			classroom.getUploads().forEach(entity->{
				uploads.add(DocumentMapper.entityToListDTO(entity));
			});
		}
		
		dto.setUploads(uploads);
		dto.setCreatorName(classroom.getCreatorName());

		List<GradeDTO> grades= new ArrayList<>();
		if (classroom.getGrades()!=null)
		{
			classroom.getGrades().forEach(entity->{
				grades.add(GradeMapper.entityToDto(entity));
			});
		}
		dto.setGrades(grades);
		return dto;
		
	}
	
	public static ClassroomListDTO entityToListDTO(ClassroomEntity classroom)
	{
		ClassroomListDTO dto = new ClassroomListDTO();
		List<UserDTO> list = new ArrayList<>();
		classroom.getSubscribers().forEach(entity->{
			list.add(UserMapper.entityToDto(entity));
		});
		
		dto.setCreatedBy(classroom.getCreatedBy());
		dto.setDescription(classroom.getDescription());
		dto.setSubscribers(list);
		dto.setTitle(classroom.getTitle());
		dto.setCreatorName(classroom.getCreatorName());
		dto.setId(classroom.getId());
		return dto;
		
	}
	
	public static ClassroomEntity requestToEntity (ClassroomNewRequest request, String creatorName)
	{
		ClassroomEntity entity = new ClassroomEntity();
		entity.setCreatedBy(request.getCreatedBy());
		entity.setDescription(request.getDescription());
		entity.setTitle(request.getTitle());
		entity.setCreatorName(creatorName);
		entity.setSubscribers(null);
		entity.setUploads(null);


		return entity;
	}

}
