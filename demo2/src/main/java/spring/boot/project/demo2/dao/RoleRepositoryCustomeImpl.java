package spring.boot.project.demo2.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.boot.project.demo2.entity.Role;

@Repository
@Transactional(readOnly = true)
public class RoleRepositoryCustomeImpl implements RoleRepositoryCustom {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Role findByRoleName(String theRole) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Role> theQuery = currentSession.createQuery("from Role where name=:name",Role.class);
		theQuery.setParameter("name", theRole);
		
		Role role = null;
		
		try {
			role = theQuery.getSingleResult();
		}
		catch(Exception e) {
			role = null;
		}
		return role;
	}

}
