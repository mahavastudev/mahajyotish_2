package com.telemune.model;

public class UserLinkDescription {
	
	private int linkId;
	private int roleId;
	private int roleId2;
	private String linkName;
	private String userNme;
	private String customerCode="";
	private String tokenId="";
	private String clientName="";
    private String ccCode;
    private String activationLink;
    
    
    
	/**
	 * @return the activationLink
	 */
	public String getActivationLink() {
		return activationLink;
	}
	/**
	 * @param activationLink the activationLink to set
	 */
	public void setActivationLink(String activationLink) {
		this.activationLink = activationLink;
	}
	/**
	 * @return the roleId2
	 */
	public int getRoleId2() {
		return roleId2;
	}
	/**
	 * @param roleId2 the roleId2 to set
	 */
	public void setRoleId2(int roleId2) {
		this.roleId2 = roleId2;
	}
	public String getCcCode() {
		return ccCode;
	}
	public void setCcCode(String ccCode) {
		this.ccCode = ccCode;
	}
	public String getClientName() {
                return this.clientName;
        }
        public void setClientName(String clientName) {
                this.clientName = clientName;
        }

	public String getCustomerCode() {
                return customerCode;
        }
        public void setCustomerCode(String customerCode) {
                this.customerCode = customerCode;
        }	
	
	public String getTokenId() {
                return tokenId;
        }
        public void setTokenId(String tokenId) {
                this.tokenId = tokenId;
        }
	
	public String getUserNme() {
		return userNme;
	}
	public void setUserNme(String userNme) {
		this.userNme = userNme;
	}
	private String password;
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private int userId;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserLinkDescription [linkId=" + linkId + ", roleId=" + roleId + ", roleId2=" + roleId2 + ", linkName="
				+ linkName + ", userNme=" + userNme + ", customerCode=" + customerCode + ", tokenId=" + tokenId
				+ ", clientName=" + clientName + ", ccCode=" + ccCode + ", activationLink=" + activationLink
				+ ", password=" + password + ", userId=" + userId + "]";
	}
	
	
	
}
