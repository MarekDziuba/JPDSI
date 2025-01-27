package com.jsfcourse.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsf.dao.UserDataDAO;
import com.jsf.entities.UserData;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class LazyView{

    @Inject
    private UserDataDAO userDataDAO;

    private List<UserData> users;
    private Map<Integer, List<String>> userRoles; 

    @PostConstruct
    public void init() {
        List<Object[]> results = userDataDAO.getAllUsersWithRoles();
        users = new ArrayList<>();
        userRoles = new HashMap<>();

        for (Object[] row : results) {
            int userId = ((Number) row[0]).intValue();

            if (users.stream().noneMatch(u -> u.getId_user() == userId)) {
                UserData user = new UserData();
                user.setId_user(userId);
                user.setUsername((String) row[1]);
                user.setEmail((String) row[2]);
                user.setLogin((String) row[3]);
                user.setUser_create((String) row[4]);
                user.setLast_change((String) row[5]);
                users.add(user);
            }

            String roleName = (String) row[6];
            if (roleName != null) {
                userRoles.computeIfAbsent(userId, k -> new ArrayList<>()).add(roleName);
            }
        }
    }

    public List<UserData> getUsers() {
        return users;
    }

    public Map<Integer, List<String>> getUserRoles() {
        return userRoles;
    }


    public String getRolesAsString(int userId) {
        List<String> roles = userRoles.get(userId);
        if (roles != null && !roles.isEmpty()) {
            return String.join(", ", roles);
        }
        return "No roles";
    }
}
