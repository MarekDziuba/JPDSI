package com.jsf.dao;

import java.util.ArrayList;
import java.util.List;

import com.jsf.entities.UserData;
import com.jsf.entities.UserEvent;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class UserDataDAO {

	@PersistenceContext
	EntityManager em;
	
	public void create(UserData userdata) {
		em.persist(userdata);
	}
	
	public List<UserData> getAllUserDatas() {
        return em.createQuery("SELECT ud FROM UserData ud", UserData.class).getResultList();
    }
	
	public UserData getUserFromDatabase(String login, String pass) {
		
		UserData u = null;

		 List<UserData> userDatas = getAllUserDatas();
	    	for (UserData userData : userDatas) {
		
 		
		if (login.equals(userData.getLogin()) && pass.equals(userData.getPassword())) {
			u = new UserData();
			u.setLogin(userData.getLogin());
			u.setPassword(userData.getPassword());
			u.setUsername(userData.getUsername());
			u.setId_user(userData.getId_user());
		}
	    	}
		return u;
	}
	
	public List<String> getUserRolesFromDatabase(UserData userData) {
			
			ArrayList<String> roles = new ArrayList<String>();
			
			
			List<UserData> userDatas = getAllUserDatas();
	    	for (UserData userRole : userDatas) {
	    		
	    		if (userData.getLogin().equals(userRole.getLogin())) {
					roles.add("user");
				}
	    	}
	    	
			/*
			if (userData.getLogin().equals("Mati1")) {
				roles.add("user");
			}
			if (userData.getLogin().equals("user2")) {
				roles.add("employee");
			}
			if (userData.getLogin().equals("user3")) {
				roles.add("admin");
			}
			*/
	    	
			return roles;
	}
	
	

	
}
