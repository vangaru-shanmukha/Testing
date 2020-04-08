package spring.boot.project.demo2.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import spring.boot.project.demo2.dao.UserRepository;
import spring.boot.project.demo2.entity.Role;
import spring.boot.project.demo2.entity.User;

@Service
public class UserServiceImpl implements UserService {

	// need to inject user dao
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(String userName) {
		Optional<User> result = userRepository.findById(userName);
		User user = null;
		if(result.isPresent()) {
			user = result.get();
		}
		else {
			throw new RuntimeException("Did not find a user");
		}
		return user;
	}

	@Override
	public void save(User theUser) {
		userRepository.save(theUser);
	}

	@Override
	public void deleteById(String userName) {
		userRepository.deleteById(userName);
		
	}
}
