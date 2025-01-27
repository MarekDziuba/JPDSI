package com.jsf.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "role_name")
@NamedQuery(name = "RoleName.findAll", query = "SELECT r FROM RoleName r")
public class RoleName implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Id_role") // Mapowanie do kolumny w bazie danych
    private String id_role;

    @Column(name = "R_name")
    private String r_name;

    @Column(name = "Active")
    private byte active;

    // Relacja z RoleUser
    @OneToMany(mappedBy = "roleName")
    private List<RoleUser> roleUsers;

    public RoleName() {}

    // Getters i setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_role() {
        return this.id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }

    public String getR_name() {
        return this.r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public byte getActive() {
        return this.active;
    }

    public void setActive(byte active) {
        this.active = active;
    }

    public List<RoleUser> getRoleUsers() {
        return this.roleUsers;
    }

    public void setRoleUsers(List<RoleUser> roleUsers) {
        this.roleUsers = roleUsers;
    }
}
