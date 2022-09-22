package it.itresources.springtut.springtutorial.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.repository.UserRepository;
import it.itresources.springtut.springtutorial.services.ServiceUser;

@Service
public class ServiceUserImpl implements ServiceUser{

	private final UserRepository userRepository;
	
	@Autowired
	public ServiceUserImpl (UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	public Boolean checkUserExistance (String username) throws UsernameNotFoundException {
    	Boolean check=userRepository.existsByUsername(username);
        return check;
    }
	
	public Optional<UserEntity> registration(UserEntity newUser) {
		return Optional.of(this.userRepository.save(newUser));
	}
	
	public Optional<UserEntity> loadByUsername (String username) 
    {              
    	return userRepository.findByUsername(username);
    }
}
