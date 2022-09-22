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
		HashMap<Long, String> subscribers = new HashMap<>();
		if(classroom.getSubscribers()!=null)
		{
			classroom.getSubscribers().forEach(entity->{
				subscribers.put(entity.getId(), entity.getUsername());
			});
		}
		dto.setSubscribers(subscribers);
		dto.setTitle(classroom.getTitle());
		
		HashMap<Long, String> uploads = new HashMap<>();
		if(classroom.getUploads()!=null)
		{
			classroom.getUploads().forEach(entity->{
				uploads.put(entity.getId(),entity.getName());
			});
		}
		
		dto.setUploads(uploads);
		
		return dto;
		
	}
	
	public static ClassroomListDTO entityToListDTO(ClassroomEntity classroom)
	{
		ClassroomListDTO dto = new ClassroomListDTO();
		HashMap map = new HashMap();
		classroom.getSubscribers().forEach(entity->{
			map.put(entity.getId(), entity.getUsername());
		});
		
		dto.setCreatedBy(classroom.getCreatedBy());
		dto.setDescription(classroom.getDescription());
		dto.setSubscribers(map);
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
