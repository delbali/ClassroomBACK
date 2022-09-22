package it.itresources.springtut.springtutorial.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.itresources.springtut.springtutorial.entity.DocumentEntity;
import it.itresources.springtut.springtutorial.mapper.DocumentMapper;
import it.itresources.springtut.springtutorial.model.dto.DocumentListDTO;
import it.itresources.springtut.springtutorial.model.request.FileUploadRequest;
import it.itresources.springtut.springtutorial.repository.ClassroomRepository;
import it.itresources.springtut.springtutorial.repository.DocumentRepository;
import it.itresources.springtut.springtutorial.repository.UserRepository;
import it.itresources.springtut.springtutorial.services.ServiceDocument;

@Service
public class ServiceDocumentImpl implements ServiceDocument {

	private final DocumentRepository documentRepository;
	private final ClassroomRepository classroomRepository;
	private final UserRepository userRepository;

	@Autowired
	public ServiceDocumentImpl(UserRepository userRepository, ClassroomRepository classroomRepository, DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
		this.classroomRepository = classroomRepository;
		this.userRepository = userRepository;

	}

	public Optional<DocumentEntity> uploadDocument(DocumentEntity document) {
		return Optional.of(documentRepository.save(document));
	}

	public Optional<DocumentEntity> downloadDocument(Long id) {
		return documentRepository.findById(id);
	}

	public Boolean teacherUpload(FileUploadRequest request, Long id) {
		
		try {

			DocumentEntity document = DocumentMapper.requestToEntity(request);

			document.setUploadedTo(classroomRepository.findById(id).get());

			document.setCreator(userRepository.findByUsername(request.getUsername()).get());
			documentRepository.save(document);
			
			return true;
		} catch (Exception e) {
			return false;		
		}
	}
	
	public List<DocumentListDTO> downloadDocumentsList (Long id)
	{
		
		List<DocumentListDTO> documentsList = new ArrayList<>();
		classroomRepository.findById(id).get().getUploads().forEach(document->{
			documentsList.add(DocumentMapper.entityToListDTO(document));
		});
		
		return documentsList;
	}

}
