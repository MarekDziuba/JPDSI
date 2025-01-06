package com.jsf.entities;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the role_user database table.
 * 
 */
@Entity
@Table(name="role_user")
@NamedQuery(name="RoleUser.findAll", query="SELECT r FROM RoleUser r")
public class RoleUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int id_user;

	private String role_add;

	//bi-directional many-to-one association to RoleName
	@ManyToOne
	@JoinColumn(name="Id_role")
	private RoleName roleName;

	//bi-directional one-to-one association to UserData
	@OneToOne(mappedBy="roleUser")
	private UserData userData;

	public RoleUser() {
	}

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

	public UserData getUserData() {
		return this.userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

}