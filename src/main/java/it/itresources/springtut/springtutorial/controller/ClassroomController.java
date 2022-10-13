package it.itresources.springtut.springtutorial.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import it.itresources.springtut.springtutorial.entity.ClassroomEntity;
import it.itresources.springtut.springtutorial.entity.RoleEntity;
import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.mapper.ClassroomMapper;
import it.itresources.springtut.springtutorial.mapper.GradeMapper;
import it.itresources.springtut.springtutorial.model.dto.*;
import it.itresources.springtut.springtutorial.services.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import it.itresources.springtut.springtutorial.mapper.UserMapper;
import it.itresources.springtut.springtutorial.model.request.ClassroomNewRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {
	
	private final ServiceClassroomImpl serviceClassroomImpl;
	private final ServiceDocumentImpl serviceDocumentImpl;
	private final ServiceUserImpl serviceUserImpl;
	private final ServiceGradeImpl serviceGradeImpl;
	private final ServiceRoleImpl serviceRoleImpl;

	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
	
	@Autowired
	public ClassroomController (ServiceGradeImpl serviceGradeImpl, ServiceRoleImpl serviceRoleImpl, ServiceUserImpl serviceUserImpl, ServiceClassroomImpl serviceClassroomImpl, ServiceDocumentImpl serviceDocumentImpl)
	{
		this.serviceGradeImpl=serviceGradeImpl;
		this.serviceRoleImpl = serviceRoleImpl;
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
		List<GradeDTO> grades=new ArrayList<>();
		serviceGradeImpl.getGrades(id).forEach(entity->{
			grades.add(GradeMapper.entityToDto(entity));
		});
		dto.setGrades(grades);
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

	/* sistemare */
	@PatchMapping("")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<?> getClassroomId(@Valid @RequestBody String title){

		System.out.println("Loading classroom id from title");
		Long id = serviceClassroomImpl.loadByTitle(title);

		if(id!=null)
		{
			return ResponseEntity.ok().body(id);
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	@PutMapping ("/{id}/members/{me}")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<?> addMe(@PathVariable(value = "id") Long id, @PathVariable(value = "me") Long myId)
	{
		if (serviceUserImpl.checkIfTeacher(myId))
		{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Teacher can not subscribe to classrooms!");
		}
		System.out.println("ao");
		Boolean ok= false;
		ClassroomDTO dto = serviceClassroomImpl.loadDTO(id);
		List<UserDTO> userSubscribers = new ArrayList<>();
		dto.getSubscribers().forEach(subscriber->{
			userSubscribers.add(UserMapper.entityToDto(serviceUserImpl.loadByUsername(subscriber.getUsername()).get()));
		});
		for (int i=0; i<userSubscribers.size(); i++)
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
				return ResponseEntity.status(HttpStatus.CREATED).body(ClassroomMapper.entityToDTO(serviceClassroomImpl.loadClassroom(id).get()));
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
		System.out.println("hemlos");
		Boolean ok=false;
		ClassroomDTO dto = serviceClassroomImpl.loadDTO(id);
		List<UserDTO> userSubscribers = new ArrayList<>();
		dto.getSubscribers().forEach(subscriber->{
			userSubscribers.add(UserMapper.entityToDto(serviceUserImpl.loadByUsername(subscriber.getUsername()).get()));
		});
		for (int i=0; i<userSubscribers.size(); i++ )
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
				return ResponseEntity.status(HttpStatus.CREATED).body(ClassroomMapper.entityToDTO(serviceClassroomImpl.loadClassroom(id).get()));
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

	@PostMapping ("/{id}/members/{studentId}")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public ResponseEntity<?> addOneToClassroom (@PathVariable(value = "id") Long id, @PathVariable(value = "studentId") Long myId, @Valid @RequestHeader String teacherUsername)
	{
		if (serviceUserImpl.checkIfTeacher(myId))
		{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Teacher can not subscribe to classrooms!");
		}

		ClassroomDTO dto = serviceClassroomImpl.loadDTO(id);
		System.out.println("TEACHER USERNAME: " +teacherUsername);
		System.out.println("CREATOR USERNAME: "+dto.getCreatedBy());
		Boolean ok=false;

		if (!dto.getCreatedBy().contains(teacherUsername))
		{
			return ResponseEntity.badRequest().build();
		}
		List<UserDTO> userSubscribers = new ArrayList<>();
		dto.getSubscribers().forEach(subscriber->{
			userSubscribers.add(UserMapper.entityToDto(serviceUserImpl.loadByUsername(subscriber.getUsername()).get()));
		});
		for (int i=0; i<userSubscribers.size(); i++)
		{
			if (userSubscribers.get(i).getId()==myId)
			{
				ok=true;
				break;
			}
		}
		if (ok==true)
		{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("User is already subscribed to this classroom");
		} else if (ok==false)
		{
			if(serviceClassroomImpl.saveStudentInClassroom(id, myId)==true)
			{
				return ResponseEntity.status(HttpStatus.CREATED).body(ClassroomMapper.entityToDTO(serviceClassroomImpl.loadClassroom(id).get()));
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There was a problem processing your request");
			}

		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("There was a problem processing your request");
		}
	}

	@GetMapping("/{id}/members/{studentId}")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public ResponseEntity<?> deleteOneFromClassroom(@PathVariable(value = "id") Long id, @PathVariable(value = "studentId") Long studentId, @Valid @RequestHeader String teacherUsername)
	{
		ClassroomDTO dto = serviceClassroomImpl.loadDTO(id);
		System.out.println("TEACHER USERNAME: " +teacherUsername);
		System.out.println("CREATOR USERNAME: "+dto.getCreatedBy());
		Boolean ok=false;

		if (!dto.getCreatedBy().contains(teacherUsername))
		{
			return ResponseEntity.badRequest().build();
		}
		List<UserDTO> userSubscribers = new ArrayList<>();
		dto.getSubscribers().forEach(subscriber->{
			userSubscribers.add(UserMapper.entityToDto(serviceUserImpl.loadByUsername(subscriber.getUsername()).get()));
		});
		for (int i=0; i<userSubscribers.size(); i++ )
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
				return ResponseEntity.ok().body(ClassroomMapper.entityToDTO(serviceClassroomImpl.loadClassroom(id).get()));
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

	@GetMapping("/{id}/nonmembers")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public ResponseEntity<?> getUnsubscribedStudents(@PathVariable(value = "id") Long id)
	{
		List<UserEntity> allStudents = serviceUserImpl.getAllStudents().get();
		List<UserDTO> students = new ArrayList<>();
		ClassroomEntity classroom= serviceClassroomImpl.loadClassroom(id).get();

		RoleEntity teacherRole = serviceRoleImpl.findRoleByName("ROLE_TEACHER").get();

		allStudents.forEach(student->{
			if (!student.getRoles().contains(teacherRole))
			{
				if (!student.getClassrooms().contains(classroom)) {
					students.add(UserMapper.entityToDto(student));
				}
			}
		});


		if(students!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(students);
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not download the List!");
		}
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public ResponseEntity<?> deleteClassroom(@PathVariable(value = "id") Long id, @Valid @RequestHeader String teacherUsername)
	{
		if (!serviceClassroomImpl.checkSubscribers(id))
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The are still Users subscribed to this classroom");
		}
		if (ClassroomMapper.entityToDTO(serviceClassroomImpl.loadClassroom(id).get()).getCreatedBy()==teacherUsername)
		{
			return ResponseEntity.badRequest().build();
		}
		serviceClassroomImpl.deleteClassroom(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
