package it.itresources.springtut.springtutorial.mapper;

import java.io.IOException;

import it.itresources.springtut.springtutorial.entity.DocumentEntity;
import it.itresources.springtut.springtutorial.model.dto.DocumentDTO;
import it.itresources.springtut.springtutorial.model.dto.DocumentListDTO;
import it.itresources.springtut.springtutorial.model.request.FileUploadRequest;

public class DocumentMapper {
	
	public static DocumentEntity requestToEntity(FileUploadRequest request) throws IOException
	{
		DocumentEntity document=new DocumentEntity();
		
		document.setData(request.getFile().getBytes());
		document.setDescription(request.getDescription());
		document.setName(request.getFile().getOriginalFilename());
		document.setType(request.getFile().getContentType());
		document.setCreator(null);
		document.setUploadedTo(null);
		return document;
	}
	
	public static DocumentDTO entityToDTO(DocumentEntity document)
	{
		DocumentDTO dto = new DocumentDTO();
		dto.setCreator(UserMapper.entityToDto(document.getCreator()));
		dto.setData(document.getData());
		dto.setDescription(document.getDescription());
		dto.setId(document.getId());
		dto.setName(document.getName());
		dto.setType(document.getType());
		dto.setUploadedTo(ClassroomMapper.entityToDTO(document.getUploadedTo()));	
		return dto;
		
	}
	
	public static DocumentListDTO entityToListDTO (DocumentEntity document)
	{
		DocumentListDTO listDto = new DocumentListDTO();
		listDto.setClassroom(document.getUploadedTo().getTitle());
		listDto.setCreator(document.getCreator().getUsername());
		listDto.setId(document.getId());
		listDto.setName(document.getName());
		listDto.setType(document.getType());
		
		return listDto;
	}

}
