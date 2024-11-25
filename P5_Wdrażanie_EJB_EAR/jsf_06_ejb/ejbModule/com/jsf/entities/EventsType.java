package com.jsf.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the events_type database table.
 * 
 */
@Entity
@Table(name="events_type")
@NamedQuery(name="EventsType.findAll", query="SELECT e FROM EventsType e")
public class EventsType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_type;

	private String color;

	private String r_name;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="eventsType")
	private List<Event> events;

	public EventsType() {
	}

	public int getId_type() {
		return this.id_type;
	}

	public void setId_type(int id_type) {
		this.id_type = id_type;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getR_name() {
		return this.r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setEventsType(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setEventsType(null);

		return event;
	}

}