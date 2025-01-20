package com.jsf.dao;

import java.util.ArrayList;
import java.util.List;

import com.jsf.entities.RoleName;
import com.jsf.entities.RoleUser;

import jakarta.ejb.Stateless;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;

@Stateless
public class RoleUserDAO {

	
	
	@PersistenceContext
	EntityManager em;
	
	public void create(RoleUser roleuser) {
		em.persist(roleuser);
	}
	
	public List<RoleUser> getAllRoles() {
        return em.createQuery("SELECT ru FROM RoleUser ru", RoleUser.class).getResultList();
    }
	
	public RoleUser getRoleFromDatabase() {
			
		RoleUser u = null;
	
			 List<RoleUser> roleUsers = getAllRoles();
		    	for (RoleUser roleUser : roleUsers) {
			
				u = new RoleUser();
				u.setId_user(roleUser.getId_user());
				u.setRoleName(roleUser.getRoleName());
				u.setRole_add(roleUser.getRole_add());
			
		    	}
			return u;
		}
	
	public RoleName getRoleById(String idRole) {
	    return em.createQuery("SELECT r FROM RoleName r WHERE r.id_role = :idRole", RoleName.class)
	             .setParameter("idRole", idRole)
	             .getSingleResult();
	}
	
}
