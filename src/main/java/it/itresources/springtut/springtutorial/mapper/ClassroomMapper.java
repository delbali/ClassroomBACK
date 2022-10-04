package it.itresources.springtut.springtutorial.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.mapping.Map;

import it.itresources.springtut.springtutorial.entity.ClassroomEntity;
import it.itresources.springtut.springtutorial.model.dto.ClassroomDTO;
import it.itresources.springtut.springtutorial.model.dto.ClassroomListDTO;
import it.itresources.springtut.springtutorial.model.request.ClassroomNewRequest;

public class ClassroomMapper {
	
	public static ClassroomDTO entityToDTO(ClassroomEntity classroom)
	{
		ClassroomDTO dto = new ClassroomDTO();
		dto.setCreatedBy(classroom.getCreatedBy());
		dto.setDescription(classroom.getDescription());
		dto.setId(classroom.getId());
		List<String> subscribers = new ArrayList<>();
		if(classroom.getSubscribers()!=null)
		{
			classroom.getSubscribers().forEach(entity->{
				subscribers.add(entity.getUsername());
			});
		}
		dto.setSubscribers(subscribers);
		dto.setTitle(classroom.getTitle());
		
		List<String> uploads = new ArrayList<>();
		if(classroom.getUploads()!=null)
		{
			classroom.getUploads().forEach(entity->{
				uploads.add(entity.getName());
			});
		}
		
		dto.setUploads(uploads);
		
		return dto;
		
	}
	
	public static ClassroomListDTO entityToListDTO(ClassroomEntity classroom)
	{
		ClassroomListDTO dto = new ClassroomListDTO();
		List<String> list = new ArrayList<>();
		classroom.getSubscribers().forEach(entity->{
			list.add(entity.getUsername());
		});
		
		dto.setCreatedBy(classroom.getCreatedBy());
		dto.setDescription(classroom.getDescription());
		dto.setSubscribers(list);
		dto.setTitle(classroom.getTitle());
		
		return dto;
		
	}
	
	public static ClassroomEntity requestToEntity (ClassroomNewRequest request)
	{
		ClassroomEntity entity = new ClassroomEntity();
		entity.setCreatedBy(request.getCreatedBy());
		entity.setDescription(request.getDescription());
		entity.setTitle(request.getTitle());
		entity.setSubscribers(null);
		entity.setUploads(null);
		
		return entity;
	}

}
