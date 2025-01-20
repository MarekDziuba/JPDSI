package com.jsf.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_events database table.
 * 
 */
@Entity
@Table(name="user_events")
@NamedQuery(name="UserEvent.findAll", query="SELECT u FROM UserEvent u")
public class UserEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_events;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date end_date_event;

	private String name_event;

	@Temporal(TemporalType.TIMESTAMP)
	private Date start_date_event;

	//bi-directional many-to-one association to EventsType
	@ManyToOne
	@JoinColumn(name="Id_type")
	private EventsType eventsType;

	//bi-directional many-to-one association to UserData
	@ManyToOne
	@JoinColumn(name="Id_user")
	private UserData userData;

	public UserEvent() {
	}

	public int getId_events() {
		return this.id_events;
	}

	public void setId_events(int id_events) {
		this.id_events = id_events;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEnd_date_event() {
		return this.end_date_event;
	}

	public void setEnd_date_event(Date end_date_event) {
		this.end_date_event = end_date_event;
	}

	public String getName_event() {
		return this.name_event;
	}

	public void setName_event(String name_event) {
		this.name_event = name_event;
	}

	public Date getStart_date_event() {
		return this.start_date_event;
	}

	public void setStart_date_event(Date start_date_event) {
		this.start_date_event = start_date_event;
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