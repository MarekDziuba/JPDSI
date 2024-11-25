package com.jsfcourse.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;

import com.jsf.dao.PersonDAO;
import com.jsf.entities.UserData;

@Named
@RequestScoped
public class PersonListBB {
	private static final String PAGE_PERSON_EDIT = "personEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String username;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	PersonDAO personDAO;
		
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<UserData> getFullList(){
		return personDAO.getFullList();
	}

	public List<UserData> getList(){
		List<UserData> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (username != null && username.length() > 0){
			searchParams.put("username", username);
		}
		
		//2. Get list
		list = personDAO.getList(searchParams);
		
		return list;
	}

	public String newUserdata(){
		UserData event = new UserData();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("event", event);
		
		return PAGE_PERSON_EDIT;
	}

	public String editPerson(UserData event){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("event", event);
		
		return PAGE_PERSON_EDIT;
	}

	public String deletePerson(UserData event){
		personDAO.remove(event);
		return PAGE_STAY_AT_THE_SAME;
	}
}
