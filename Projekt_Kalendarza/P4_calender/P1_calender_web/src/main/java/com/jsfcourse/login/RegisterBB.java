package com.jsfcourse.login;

import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.jsf.dao.UserDataDAO;
import com.jsf.dao.RoleUserDAO;

import com.jsf.entities.UserData;
import com.jsf.entities.RoleName;
import com.jsf.entities.RoleUser;

@Named
@RequestScoped
public class RegisterBB {
	@PersistenceContext
	EntityManager em;
	
	private static final String PAGE_MAIN = "/pages/calender/basic?faces-redirect=true";
	private static final String PAGE_LOGIN = "/pages/login";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String username;
	private String email;
	private String login;
	private String pass;
	private String passrep;
	
	private String last_change;
	private String user_create;
	
	private int id_user;
	private String id_role;
	private String role_add;

	FacesContext ctx = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
    Integer loggedUserId = (Integer) session.getAttribute("loggedUserId");
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getPassrep() {
		return passrep;
	}

	public void setPassrep(String passrep) {
		this.passrep = passrep;
	}

	
	public String getLast_change() {
		return last_change;
	}

	public void setLast_change(String last_change) {
		this.last_change = last_change;
	}
	
	public String getUser_create() {
		return user_create;
	}

	public void setUser_create(String user_create) {
		this.user_create = user_create;
	}
	
	
	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	
	public String getId_role() {
		return id_role;
	}

	public void setId_role(String id_role) {
		this.id_role = id_role;
	}
	
	public String getRole_add() {
		return role_add;
	}

	public void setURole_add(String role_add) {
		this.role_add = role_add;
	}
	
	@Inject
	UserDataDAO userDataDAO;
	@Inject
	RoleUserDAO roleUserDAO;

	private UserData userdata = new UserData();
	
	public UserData getUserdata() {
		return userdata;
	}
	
	private RoleUser roleuser = new RoleUser();
		
	public RoleUser getRoleuser() {
			return roleuser;
	}
	
	public String doRegister() {
	    // Walidacja haseł
	    if (!pass.equals(passrep)) {
	        FacesContext.getCurrentInstance().addMessage(null, 
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match!", null));
	        return PAGE_STAY_AT_THE_SAME;
	    }

	    // Walidacja unikalności loginu
	    List<UserData> allUsers = userDataDAO.getAllUserDatas();
	    for (UserData user : allUsers) {
	        if (user.getLogin().equals(login)) {
	            FacesContext.getCurrentInstance().addMessage(null, 
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login already exists!", null));
	            return PAGE_STAY_AT_THE_SAME;
	        }
	    }

	    // Tworzenie użytkownika
	    UserData newUser = new UserData();
	    newUser.setUsername(username);
	    newUser.setEmail(email);
	    newUser.setLogin(login);
	    newUser.setPassword(pass);

	    LocalDateTime now = LocalDateTime.now();
	    String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	    newUser.setUser_create(formattedDate);
	    newUser.setLast_change(formattedDate);

	    userDataDAO.create(newUser);

	    	// Przypisanie roli użytkownikowi
		    RoleName userRole = roleUserDAO.getRoleById("U");
		    int newUserId = newUser.getId_user();
		    
		    RoleUser newRole = new RoleUser();
		    newRole.setId_user(newUserId);
		    newRole.setRole_add(formattedDate);
		    newRole.setRoleName(userRole);
	
		    roleUserDAO.create(newRole);

	    FacesContext.getCurrentInstance().addMessage(null,
	        new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration successful!", null));
	    return PAGE_MAIN;
	}

	
	public String newLogin() {
		return PAGE_LOGIN;
	}
	
	
}
