package it.itresources.springtut.springtutorial.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.itresources.springtut.springtutorial.entity.ClassroomEntity;
import it.itresources.springtut.springtutorial.entity.RoleEntity;
import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.model.UserDetailImpl;
import it.itresources.springtut.springtutorial.model.dto.UserDTO;
import it.itresources.springtut.springtutorial.model.dto.UserListDTO;
import it.itresources.springtut.springtutorial.model.request.UserRegistrationRequest;

public class UserMapper {
    
    public static UserDTO entityToDto(UserEntity entity) {
        if (entity == null) {
            return new UserDTO();
        }
        List<String> roles = entity.getRoles().stream().map(i -> i.getName()).collect(Collectors.toList());
        return new UserDTO(entity.getId(), entity.getUsername(), roles);
    }

    public static UserDTO userDetailToDto(UserDetailImpl userDetail) {
        if (userDetail == null) {
            return null;
        }

        List<String> roles = userDetail.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());

        UserDTO user = new UserDTO(userDetail.getId(), userDetail.getUsername(), roles);

        return user;
    }
    
    public static UserEntity signupToEntity (UserRegistrationRequest request, List<RoleEntity> roles, String password ) {
    	
    	UserEntity newUser=new UserEntity();
    	
    	newUser.setUsername(request.getUsername());
    	newUser.setPassword(password);
    	newUser.setFirstName(request.getFirstName());
    	newUser.setLastName(request.getLastName());
    	newUser.setClassrooms(null);
    	newUser.setRoles(roles);
    	newUser.setAddress(request.getAddress());
    	newUser.setSubscriptionDate(LocalDate.now());
  
    	return newUser;
    }

    public static UserListDTO entityToListDTO (UserEntity user)
    {
    	return new UserListDTO(user.getId(), user.getUsername());
    }

}
