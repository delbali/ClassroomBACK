package it.itresources.springtut.springtutorial.controller;

import java.util.ArrayList;
import java.util.Optional;

import it.itresources.springtut.springtutorial.model.request.PasswordUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.exception.UsernameAlreadyExistsException;
import it.itresources.springtut.springtutorial.mapper.UserMapper;
import it.itresources.springtut.springtutorial.model.dto.UserDTO;
import it.itresources.springtut.springtutorial.security.PrincipalUtils;
import it.itresources.springtut.springtutorial.services.impl.ServiceUserImpl;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/me")
public class MeController {

	    Logger logger= LoggerFactory.getLogger(MeController.class);
	    private final ServiceUserImpl serviceUserImpl;
	    private final PasswordEncoder passwordEncoder;


	    @Autowired
	    public MeController(ServiceUserImpl serviceUserImpl,PasswordEncoder passwordEncoder){
	        this.serviceUserImpl=serviceUserImpl;
	        this.passwordEncoder=passwordEncoder;
	    }

	  
	   
	    @GetMapping("")
		@PreAuthorize("hasRole('ROLE_STUDENT')")
	    public ResponseEntity<UserDTO> me() {
	        logger.info("User request to get user information");
			System.out.println("capire");
	        SecurityContext securityContext = SecurityContextHolder.getContext();
	        Authentication authentication = securityContext.getAuthentication();
	        String username = PrincipalUtils.extractPrincipal(authentication);

	        Optional<UserEntity> entity=serviceUserImpl.loadByUsername(username);
	        return new ResponseEntity<>(UserMapper.entityToDto(entity.get()),
	                entity.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	    }

		@PatchMapping ("")
		@PreAuthorize("hasRole('ROLE_STUDENT')")
		public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordUpdateRequest request)
		{
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication authentication = securityContext.getAuthentication();
			String username = PrincipalUtils.extractPrincipal(authentication);
			if(serviceUserImpl.checkUserExistance(username))
			{
				System.out.println("Updating password request");
				if (serviceUserImpl.updatePassword(request, serviceUserImpl.loadByUsername(username).get()))
				{
					return ResponseEntity.ok().build();
				}
				else return ResponseEntity.badRequest().body("Error in checking the old and new User Passwords");
			} else return ResponseEntity.badRequest().body("Error in checking the user existance");
		}
	    /*@PutMapping("")
	    public ResponseEntity<?> updateMe(@RequestBody UserUpdateRequest updateRequest){
	        logger.info("User request to update user information");

	        SecurityContext securityContext = SecurityContextHolder.getContext();
	        Authentication authentication = securityContext.getAuthentication();
	        String username = PrincipalUtils.extractPrincipal(authentication);
	        if(!username.equals( updateRequest.getUsername()) && serviceUserImpl.checkUserExistance(updateRequest.getUsername())){
	            throw (new UsernameAlreadyExistsException( ));
	        }
	        Employee userEntity = serviceUserImpl.loadUserOptional(username).get();
	        
	        serviceUnitImpl.get(updateRequest.getUnit()).ifPresent(x->userEntity.setUnit(x));
	        Employee ret= serviceUserImpl.updateUser(UserMapper.updateToEntity(
	                updateRequest,
	                userEntity.getHiredDate(),
	                userEntity.getRoles(),
	                userEntity.getPassword(),
	                userEntity.getUnit(),
	                userEntity.getId())).get();
	        return ResponseEntity.ok().body(UserMapper.entityToDto(ret));
	    }*/

	}



