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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.jsf.dao.UserDataDAO;
import com.jsf.entities.UserData;


@Named
@RequestScoped
public class RegisterBB {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String username) {
		this.username = username;
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
	
	
	@Inject
	UserDataDAO userDataDAO;

	private UserData userdata = new UserData();
	
	public UserData getUserdata() {
		return userdata;
	}
	
	public String doRegister() {
	    if (!pass.equals(passrep)) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match!", null));
	        return PAGE_STAY_AT_THE_SAME;
	    }

	    List<UserData> allUsers = userDataDAO.getAllUserDatas();
	    for (UserData user : allUsers) {
	        if (user.getLogin().equals(login)) {
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login already exists!", null));
	            return PAGE_STAY_AT_THE_SAME;
	        }
	    }

	    UserData newUser = new UserData();
	    newUser.setUsername(username);
	    newUser.setEmail(email);
	    newUser.setLogin(login);
	    newUser.setPassword(pass);
	    
	    LocalDateTime currentTime = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedDate = currentTime.format(formatter);
	    newUser.setUser_create(formattedDate);
	    newUser.setLast_change(formattedDate);

	    userDataDAO.create(newUser);

	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration successful!", null));
	    return PAGE_MAIN;
	}
	
	public String newLogin() {
		return PAGE_LOGIN;
	}
	
	
}
