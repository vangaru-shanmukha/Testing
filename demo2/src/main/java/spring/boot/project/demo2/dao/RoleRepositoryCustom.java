package spring.boot.project.demo2.dao;

import spring.boot.project.demo2.entity.Role;

public interface RoleRepositoryCustom {

	Role findByRoleName(String theRole);	
	
}
