package com.jsf.dao;

import java.util.ArrayList;
import java.util.List;

import com.jsf.entities.UserData;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserDataDAO {

	@PersistenceContext
	EntityManager em;
	
	public void create(UserData userdata) {
		em.persist(userdata);
	}
	
	public UserData find(Object id) {
		return em.find(UserData.class, id);
	}
	
	public UserData merge(UserData userdata) {
		return em.merge(userdata);
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
	    ArrayList<String> roles = new ArrayList<>();

	    if (userData != null && userData.getId_user() != 0) {
	        
	        List<String> roleNames = em.createQuery(
	                "SELECT ru.roleName.r_name " +
	                "FROM RoleUser ru " +
	                "WHERE ru.id_user = :userId", 
	                String.class
	            )
	            .setParameter("userId", userData.getId_user())
	            .getResultList();


	        for (String role : roleNames) {
	            if ("user".equalsIgnoreCase(role)) {
	                roles.add("user");
	            } else if ("employee".equalsIgnoreCase(role)) {
	                roles.add("employee");
	            }
	        }
	    } else {
	        System.out.println("UserData jest null lub id_user jest 0");
	    }

	    return roles;
	}

	public List<Object[]> getAllUsersWithRoles() {
        return em.createNativeQuery("""
            SELECT u.id_user, u.username, u.email, u.login, u.user_create, u.last_change, r.r_name
            FROM user_data u
            LEFT JOIN role_user ru ON u.id_user = ru.id_user
            LEFT JOIN role_name r ON ru.id_role = r.id_role
        """).getResultList();
    }




	
	

	
}
