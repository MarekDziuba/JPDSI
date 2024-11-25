package com.jsfcourse.person;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.jsf.dao.PersonDAO;
import com.jsf.entities.UserData;

@Named
@ViewScoped
public class PersonEditGETBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PERSON_LIST = "personList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private UserData userdata = new UserData();
	private UserData loaded = null;

	@Inject
	FacesContext context;

	@EJB
	PersonDAO personDAO;

	public UserData getUserdata() {
		return userdata;
	}

	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && userdata.getId_user() != null) {
				loaded = personDAO.find(userdata.getId_user());
			}
			if (loaded != null) {
				userdata = loaded;
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
				// if (!context.isPostback()) { // possible redirect
				// context.getExternalContext().redirect("personList.xhtml");
				// context.responseComplete();
				// }
			}
		}

	}

	public String saveData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (userdata.getId_user() == null) {
				// new record
				personDAO.create(userdata);
			} else {
				// existing record
				personDAO.merge(userdata);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PERSON_LIST;
	}
}
