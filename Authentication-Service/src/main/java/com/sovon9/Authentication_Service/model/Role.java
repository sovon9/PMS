package com.sovon9.Authentication_Service.model;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Role
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roleID;
	@Column(nullable=false, unique=true)
	private String role;
    @ManyToMany(mappedBy="roles")
	private Set<User> user;
	
	public Role()
	{
		super();
	}
	
	public Role(String role)
	{
		super();
		this.role = role;
	}
	
	public Long getRoleID()
	{
		return roleID;
	}

	public void setRoleID(Long roleID)
	{
		this.roleID = roleID;
	}

	public Set<User> getUser()
	{
		return user;
	}

	public void setUser(Set<User> user)
	{
		this.user = user;
	}

	public String getRole()
	{
		return role;
	}
	public void setRole(String role)
	{
		this.role = role;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(role, roleID, user);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(role, other.role) && Objects.equals(roleID, other.roleID)
				&& Objects.equals(user, other.user);
	}
	
}
