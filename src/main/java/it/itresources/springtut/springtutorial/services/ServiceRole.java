package it.itresources.springtut.springtutorial.services;

import java.util.Optional;

import it.itresources.springtut.springtutorial.entity.RoleEntity;

public interface ServiceRole {

	public Optional<RoleEntity> findRoleByName(String roleName);
}
