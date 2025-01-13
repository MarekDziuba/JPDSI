package com.jsf.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the role_name database table.
 * 
 */
@Entity
@Table(name="role_name")
@NamedQuery(name="RoleName.findAll", query="SELECT r FROM RoleName r")
public class RoleName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private byte active;

	private String id_role;

	private String r_name;

	//bi-directional many-to-one association to RoleUser
	@OneToMany(mappedBy="roleName")
	private List<RoleUser> roleUsers;

	public RoleName() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
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

	public List<RoleUser> getRoleUsers() {
		return this.roleUsers;
	}

	public void setRoleUsers(List<RoleUser> roleUsers) {
		this.roleUsers = roleUsers;
	}

	public RoleUser addRoleUser(RoleUser roleUser) {
		getRoleUsers().add(roleUser);
		roleUser.setRoleName(this);

		return roleUser;
	}

	public RoleUser removeRoleUser(RoleUser roleUser) {
		getRoleUsers().remove(roleUser);
		roleUser.setRoleName(null);

		return roleUser;
	}

}