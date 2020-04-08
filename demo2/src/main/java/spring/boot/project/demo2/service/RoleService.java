package spring.boot.project.demo2.service;

import java.util.List;

import spring.boot.project.demo2.entity.Role;

public interface RoleService {

	public List<Role> findAll();

	public Role findById(int id);
	
	public Role findByRoleName(String roleName);

	public void save(Role theRole);

	public void deleteById(int id);
	
}
