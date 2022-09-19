package it.itresources.springtut.springtutorial.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.itresources.springtut.springtutorial.entity.RoleEntity;
import it.itresources.springtut.springtutorial.repository.RoleRepository;
import it.itresources.springtut.springtutorial.services.ServiceRole;

@Service
public class ServiceRoleImpl implements ServiceRole{
	
	private final RoleRepository roleRepository;
	
	@Autowired
	public ServiceRoleImpl (RoleRepository roleRepository) {
		this.roleRepository=roleRepository;
	}
	
	public Optional<RoleEntity> findRoleByName(String roleName) {

		return roleRepository.findByName(roleName);
	}

}
