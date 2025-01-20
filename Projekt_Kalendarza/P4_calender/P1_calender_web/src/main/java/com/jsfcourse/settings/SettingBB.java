package com.jsfcourse.settings;

import java.util.List;

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

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named
@SessionScoped
public class SettingBB implements Serializable {
	private int id_user;
    private String username;
    private String login;
    private String email;
    private String password;
    private String confirmPassword;

    @Inject
    private UserDataDAO userDataDAO; // DAO do obsługi użytkownika w bazie

    private UserData currentUser; // Aktualny użytkownik

    @PostConstruct
    public void init() {
    	FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
        Integer userId = (Integer) session.getAttribute("loggedUserId");

        if (userId != null) {
            currentUser = userDataDAO.find(userId); // Pobranie użytkownika z bazy danych
            if (currentUser != null) {
                // Przypisz wartości do pól formularza
                this.username = currentUser.getUsername();
                this.login = currentUser.getLogin();
                this.email = currentUser.getEmail();
            } else {
                // Jeśli użytkownik nie został znaleziony
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User not found in database."));
            }
        } else {
            // Jeśli ID użytkownika nie jest dostępne w sesji
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User ID not found in session."));
        }
    }


    public void saveChanges() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (currentUser == null || currentUser.getId_user() == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User data not loaded."));
            return;
        }

        if (!password.equals(confirmPassword)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Passwords do not match."));
            return;
        }

        // Zaktualizuj dane użytkownika
        currentUser.setUsername(username);
        currentUser.setLogin(login);
        currentUser.setEmail(email);

        if (!password.isEmpty()) {
            currentUser.setPassword(password);
        }

        LocalDateTime now = LocalDateTime.now();
	    String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        currentUser.setLast_change(formattedDate); // Ustaw ostatnią zmianę

        try {
            userDataDAO.merge(currentUser); // Zapis do bazy
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Profile updated successfully. Logout to see changes."));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Could not update profile: " + e.getMessage()));
            
        }
    }



    // Gettery i settery
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public int getId_user() {
		return this.id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
}

