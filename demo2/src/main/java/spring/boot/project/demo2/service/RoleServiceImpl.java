package spring.boot.project.demo2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.project.demo2.dao.RoleRepository;
import spring.boot.project.demo2.entity.Role;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(int id) {
		Optional<Role> result = roleRepository.findById(id);
		Role theRole = null;
		if(result.isPresent()) {
			theRole = result.get();
		}
		else {
			throw new RuntimeException("Did not find role with id : " + id);
		}
		return theRole;
	}

	@Override
	public Role findByRoleName(String roleName) {
		return roleRepository.findByRoleName(roleName);
	}

	@Override
	public void save(Role theRole) {
		roleRepository.save(theRole);

	}

	@Override
	public void deleteById(int id) {
		roleRepository.deleteById(id);

	}

}
