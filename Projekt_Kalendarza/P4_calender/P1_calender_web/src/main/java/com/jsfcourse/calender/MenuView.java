package com.jsfcourse.calender;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.servlet.http.HttpSession;

import com.jsf.entities.UserData;

@Named
@ViewScoped
public class MenuView implements Serializable {
	
	private static final String PAGE_MAIN = "/pages/calender/basic?faces-redirect=true";
	private static final String PAGE_SETTING = "/pages/settings/settings?faces-redirect=true";

    private static final long serialVersionUID = 1L;

    private MenuModel model;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();

        //First submenu
        DefaultSubMenu firstSubmenu = DefaultSubMenu.builder()
                .label("Options")
                .expanded(true)
                .build();

        DefaultMenuItem item = DefaultMenuItem.builder()
                .value("Save (Non-Ajax)")
                .icon("pi pi-save")
                .ajax(false)
                .command("#{menuView.save}")
                .update("messages")
                .build();
        firstSubmenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .value("Update")
                .icon("pi pi-refresh")
                .command("#{menuView.update}")
                .update("messages")
                .build();
        firstSubmenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .value("Delete")
                .icon("pi pi-times")
                .command("#{menuView.delete}")
                .update("messages")
                .build();
        firstSubmenu.getElements().add(item);

        model.getElements().add(firstSubmenu);

        //Second submenu
        DefaultSubMenu secondSubmenu = DefaultSubMenu.builder()
                .label("Navigations")
                .expanded(false)
                .build();

        item = DefaultMenuItem.builder()
                .value("Website")
                .url("http://www.primefaces.org")
                .icon("pi pi-external-link")
                .build();
        secondSubmenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .value("Internal")
                .icon("pi pi-upload")
                .command("#{menuView.redirect}")
                .build();
        secondSubmenu.getElements().add(item);

        model.getElements().add(secondSubmenu);
    }

    public MenuModel getModel() {
        return model;
    }

    public void redirect() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath());
    }

    public void save() {
        addMessage("Save", "Data saved");
    }

    public void update() {
        addMessage("Update", "Data updated");
    }

    public void delete() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Delete", "Data deleted");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void sleepAndSave() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        save();
    }

    public void sleepAndUpdate() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        update();
    }

    public void sleepAndDelete() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        delete();
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public String mainPage() {
		return PAGE_MAIN;
	}
    
    public String settingPage() {
		return PAGE_SETTING;
	}
}