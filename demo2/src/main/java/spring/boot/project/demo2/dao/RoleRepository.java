package spring.boot.project.demo2.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.project.demo2.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	public Role findByRoleName(String roleName);
	
}
