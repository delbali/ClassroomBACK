package it.itresources.springtut.springtutorial.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import it.itresources.springtut.springtutorial.mapper.ClassroomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.itresources.springtut.springtutorial.mapper.UserMapper;
import it.itresources.springtut.springtutorial.model.dto.ClassroomDTO;
import it.itresources.springtut.springtutorial.model.dto.ClassroomListDTO;
import it.itresources.springtut.springtutorial.model.dto.DocumentListDTO;
import it.itresources.springtut.springtutorial.model.dto.UserDTO;
import it.itresources.springtut.springtutorial.model.dto.UserListDTO;
import it.itresources.springtut.springtutorial.model.request.ClassroomNewRequest;
import it.itresources.springtut.springtutorial.services.impl.ServiceClassroomImpl;
import it.itresources.springtut.springtutorial.services.impl.ServiceDocumentImpl;
import it.itresources.springtut.springtutorial.services.impl.ServiceUserImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {
	
	private final ServiceClassroomImpl serviceClassroomImpl;
	private final ServiceDocumentImpl serviceDocumentImpl;
	private final ServiceUserImpl serviceUserImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
	
	@Autowired
	public ClassroomController (ServiceUserImpl serviceUserImpl, ServiceClassroomImpl serviceClassroomImpl, ServiceDocumentImpl serviceDocumentImpl)
	{
		this.serviceClassroomImpl=serviceClassroomImpl;
		this.serviceDocumentImpl=serviceDocumentImpl;
		this.serviceUserImpl=serviceUserImpl;
	}
	

	@GetMapping("")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<?> classroomsList()
	{
		List<ClassroomListDTO> classroomList = serviceClassroomImpl.classroomList();
		
		if(classroomList!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(classroomList);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not download the List!");
		}
		
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<?> getClassroom(@PathVariable(value = "id") Long id)
	{
		ClassroomDTO dto= ClassroomMapper.entityToDTO(serviceClassroomImpl.loadClassroom(id).get());

		if(dto!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not get the specific Classroom Details!");
		}

	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public ResponseEntity<?> createClassroom(@Valid @RequestBody ClassroomNewRequest request){

		String creatorName=UserMapper.getFullNameFromUsername(serviceUserImpl.loadByUsername(request.getCreatedBy()).get());
		ClassroomDTO dto = serviceClassroomImpl.createClassroom(request, creatorName);
		if(dto!=null)
		{
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not create the Classroom!");
		}
	}
	
	@PostMapping("/{id}/members/{me}")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<?> addMe(@PathVariable(value = "id") Long id, @PathVariable(value = "me") Long myId)
	{
		Boolean ok= false;
		ClassroomDTO dto = serviceClassroomImpl.loadDTO(id);
		List<UserDTO> userSubscribers = new ArrayList<>();
		dto.getSubscribers().forEach(subscriber->{
			userSubscribers.add(UserMapper.entityToDto(serviceUserImpl.loadByUsername(subscriber).get()));
		});
		for (int i=0; i<=userSubscribers.size(); i++ )
		{
			if (userSubscribers.get(i).getId()==myId)
			{
				ok=true;
				break;
			}
		}
		if (ok==true)
		{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("You are already subscribed to this classroom");
		} else if (ok==false)
		{
			if(serviceClassroomImpl.saveStudentInClassroom(id, myId)==true)
			{
				return ResponseEntity.status(HttpStatus.CREATED).body("You have been successfully added to the classroom");
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There was a problem processing your request");
			}
			
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There was a problem processing your request");
		}
	}
	
	@DeleteMapping("/{id}/members/{me}")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<?> deleteMe(@PathVariable(value = "id") Long id, @PathVariable(value = "me") Long myId)
	{
		Boolean ok=false;
		ClassroomDTO dto = serviceClassroomImpl.loadDTO(id);
		List<UserDTO> userSubscribers = new ArrayList<>();
		dto.getSubscribers().forEach(subscriber->{
			userSubscribers.add(UserMapper.entityToDto(serviceUserImpl.loadByUsername(subscriber).get()));
		});
		for (int i=0; i<=userSubscribers.size(); i++ )
		{
			if (userSubscribers.get(i).getId()==myId)
			{
				ok=true;
				break;
			}
		}
		if (ok=true)
		{
			if(serviceClassroomImpl.deleteStudentFromClassroom(id, myId)==true)
			{
				return ResponseEntity.status(HttpStatus.CREATED).body("You have been successfully deleted from the classroom");
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There was a problem processing your request");
			}
		} else if (ok=false)
		{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("You are not subscribed to this classroom!");
			
			
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There was a problem processing your request");
		}
	}
	
	@DeleteMapping("/{id}/members/{studentId}")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public ResponseEntity<?> deleteOneFromClassroom(@PathVariable(value = "id") Long id, @PathVariable(value = "studentId") Long studentId)
	{
		Boolean ok=false;
		ClassroomDTO dto = serviceClassroomImpl.loadDTO(id);
		List<UserDTO> userSubscribers = new ArrayList<>();
		dto.getSubscribers().forEach(subscriber->{
			userSubscribers.add(UserMapper.entityToDto(serviceUserImpl.loadByUsername(subscriber).get()));
		});
		for (int i=0; i<=userSubscribers.size(); i++ )
		{
			if (userSubscribers.get(i).getId()==studentId)
			{
				ok=true;
				break;
			}
		}
		if (ok=true)
		{
			if(serviceClassroomImpl.deleteStudentFromClassroom(id, studentId)==true)
			{
				return ResponseEntity.status(HttpStatus.CREATED).body("Student successfully deleted from the classroom");
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There was a problem processing your request");
			}
		} else if (ok=false)
		{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("The student is not subscribed to this classroom!");
			
			
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There was a problem processing your request");
		}
	}
	
	@GetMapping("/{id}/members")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public ResponseEntity<?> studentsList(@PathVariable(value = "id") Long id)
	{
		List<UserListDTO> userList = serviceUserImpl.userList(id);
		
		if(userList!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(userList);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not download the List!");
		}
		
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public ResponseEntity<?> deleteClassroom(@PathVariable(value = "id") Long id)
	{
		if (!serviceClassroomImpl.checkSubscribers(id))
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The are still Users subscribed to this classroom");
		}
		serviceClassroomImpl.deleteClassroom(id);
		return ResponseEntity.status(HttpStatus.OK).body("Classroom deleted successfully");
	}

}
