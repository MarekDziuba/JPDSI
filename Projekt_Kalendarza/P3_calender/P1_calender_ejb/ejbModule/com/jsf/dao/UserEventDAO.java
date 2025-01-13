package com.jsf.dao;

import java.util.List;

import com.jsf.entities.UserData;
import com.jsf.entities.UserEvent;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class UserEventDAO {

	@PersistenceContext
	EntityManager em;

	public void create(UserEvent userEvent) {
		em.persist(userEvent);
	}

	public UserEvent merge(UserEvent userEvent) {
		return em.merge(userEvent);
	}

	public void remove(UserEvent userEvent) {
		em.remove(em.merge(userEvent));
	}

	public UserEvent find(Object id) {
		return em.find(UserEvent.class, id);
	}
	
	public List<UserEvent> getAllUserEvents() {
        return em.createQuery("SELECT ue FROM UserEvent ue", UserEvent.class).getResultList();
    }
	
}
