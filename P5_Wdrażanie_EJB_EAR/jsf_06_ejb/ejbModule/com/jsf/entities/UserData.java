package com.jsf.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

/**
 * The persistent class for the user_data database table.
 * 
 */
@Entity
@Table(name="user_data")
@NamedQuery(name="UserData.findAll", query="SELECT u FROM UserData u")
public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_user;

	private String email;

	private String last_change;

	private String login;

	private String password;

	private String user_create;

	private String username;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="userData")
	private List<Event> events;

	//bi-directional one-to-one association to RoleUser
	@OneToOne
	@JoinColumn(name="Id_user")
	private RoleUser roleUser;

	public UserData() {
	}

	public Integer getId_user() {
		return this.id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLast_change() {
		return this.last_change;
	}

	public void setLast_change(String last_change) {
		this.last_change = last_change;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser_create() {
		return this.user_create;
	}

	public void setUser_create(String user_create) {
		this.user_create = user_create;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setUserData(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setUserData(null);

		return event;
	}

	public RoleUser getRoleUser() {
		return this.roleUser;
	}

	public void setRoleUser(RoleUser roleUser) {
		this.roleUser = roleUser;
	}

}