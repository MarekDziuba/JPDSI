package com.jsf.entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "role_user")
@NamedQuery(name = "RoleUser.findAll", query = "SELECT r FROM RoleUser r")
public class RoleUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Id_user")
    private int id_user;

    @Column(name = "Role_add")
    private String role_add;

    // Relacja z RoleName (tylko jedna mapuje kolumnę Id_role)
    @ManyToOne
    @JoinColumn(name = "Id_role", referencedColumnName = "Id_role")
    private RoleName roleName;

    public RoleUser() {}

    // Getters i setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return this.id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getRole_add() {
        return this.role_add;
    }

    public void setRole_add(String role_add) {
        this.role_add = role_add;
    }

    public RoleName getRoleName() {
        return this.roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
