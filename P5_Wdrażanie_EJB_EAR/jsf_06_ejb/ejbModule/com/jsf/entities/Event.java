package com.jsf.entities;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the events database table.
 * 
 */
@Entity
@Table(name="events")
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_events;

	private String date_event;

	private String description;

	private String name_event;

	private String time_event;

	//bi-directional many-to-one association to EventsType
	@ManyToOne
	@JoinColumn(name="Id_type")
	private EventsType eventsType;

	//bi-directional many-to-one association to UserData
	@ManyToOne
	@JoinColumn(name="Id_user")
	private UserData userData;

	public Event() {
	}

	public int getId_events() {
		return this.id_events;
	}

	public void setId_events(int id_events) {
		this.id_events = id_events;
	}

	public String getDate_event() {
		return this.date_event;
	}

	public void setDate_event(String date_event) {
		this.date_event = date_event;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName_event() {
		return this.name_event;
	}

	public void setName_event(String name_event) {
		this.name_event = name_event;
	}

	public String getTime_event() {
		return this.time_event;
	}

	public void setTime_event(String time_event) {
		this.time_event = time_event;
	}

	public EventsType getEventsType() {
		return this.eventsType;
	}

	public void setEventsType(EventsType eventsType) {
		this.eventsType = eventsType;
	}

	public UserData getUserData() {
		return this.userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

}