package it.itresources.springtut.springtutorial.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.itresources.springtut.springtutorial.entity.DocumentEntity;
import it.itresources.springtut.springtutorial.mapper.DocumentMapper;
import it.itresources.springtut.springtutorial.model.dto.DocumentListDTO;
import it.itresources.springtut.springtutorial.model.request.FileUploadRequest;
import it.itresources.springtut.springtutorial.services.impl.ServiceClassroomImpl;
import it.itresources.springtut.springtutorial.services.impl.ServiceDocumentImpl;
import it.itresources.springtut.springtutorial.services.impl.ServiceUserImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/classrooms/{id}/files")
public class DocumentController {
	
	private final ServiceDocumentImpl serviceDocumentImpl;
	private final ServiceClassroomImpl serviceClassroomImpl;
	private final ServiceUserImpl serviceUserImpl;
	
	@Autowired
	public DocumentController (ServiceUserImpl serviceUserImpl, ServiceDocumentImpl serviceDocumentImpl, ServiceClassroomImpl serviceClassroomImpl) {
		
		this.serviceDocumentImpl=serviceDocumentImpl;
		this.serviceClassroomImpl=serviceClassroomImpl;
		this.serviceUserImpl=serviceUserImpl;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public ResponseEntity<?> upload(@ModelAttribute FileUploadRequest request,
			@PathVariable(value = "id") Long id)
	{
		if (serviceDocumentImpl.teacherUpload(request, id))
		{
			return ResponseEntity.status(HttpStatus.CREATED).body("Uploaded the file successfully: " + request.getFile().getOriginalFilename() + " !");
		} else {
		
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file: " + request.getFile().getOriginalFilename() + " !");
		}
		
	}
	
	@GetMapping("/{documentId}")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public void download(@PathVariable(value = "id") Long id, @PathVariable(value="documentId") Long documentId, HttpServletResponse response) {
		DocumentEntity document = serviceDocumentImpl.downloadDocument(documentId).get();
		if (document != null) {
			try {
				response.getOutputStream().write(document.getData());
				response.flushBuffer();
			} catch (IOException e) {

			}
		}
	}
		

	@GetMapping("")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<?> downloadDocumentsList(@PathVariable(value = "id") Long id)
	{
		List<DocumentListDTO> documentList = serviceDocumentImpl.downloadDocumentsList(id);
		
		if(documentList!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(documentList);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not download the List!");
		}
		
	}
}
