package com.jsf.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.jsf.entities.UserData;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class PersonDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(UserData userdata) {
		em.persist(userdata);
	}

	public UserData merge(UserData userdata) {
		return em.merge(userdata);
	}

	public void remove(UserData userdata) {
		em.remove(em.merge(userdata));
	}

	public UserData find(Object id) {
		return em.find(UserData.class, id);
	}

	public List<UserData> getFullList() {
		List<UserData> list = null;

		Query query = em.createQuery("select e from Event e");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<UserData> getList(Map<String, Object> searchParams) {
		List<UserData> list = null;

		// 1. Build query string with parameters
		String select = "select e ";
		String from = "from Event e ";
		String where = "";
		String orderby = "order by e.id_user asc";

		// search for surname
		String id_user = (String) searchParams.get("id_user");
		if (id_user != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "e.id_user like :id_user ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (id_user != null) {
			query.setParameter("id_user", id_user+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
