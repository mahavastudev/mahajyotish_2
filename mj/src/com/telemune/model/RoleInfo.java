package com.telemune.model;

import java.util.List;

public class RoleInfo {
	
	private int roleId;
	private String roleName;
	private String roleDesc;
	private List<Integer> accessIdLst;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	@Override
	public String toString() {
		return "RoleInfo [roleId=" + roleId + ", roleName=" + roleName
				+ ", roleDesc=" + roleDesc + "]";
	}
	public List<Integer> getAccessIdLst() {
		return accessIdLst;
	}
	public void setAccessIdLst(List<Integer> accessIdLst) {
		this.accessIdLst = accessIdLst;
	}
	
    

}
