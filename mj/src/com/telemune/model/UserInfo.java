package com.telemune.model;

public class UserInfo {
	
	private int uId;
	private String uname;
	
	private String upassword;
	private String ccCode;
	public String getCcCode() {
		return ccCode;
	}
	public void setCcCode(String ccCode) {
		this.ccCode = ccCode;
	}
	private String email;
	private String mobileNo;
	private String roleId;
	private String roleId2;
	private String roleName;
	private String roleName2;
	private String country;
	private String createDate;
    private boolean dashaDetail;
	private boolean houseDetail;
	private boolean specialKundli;
	private boolean kundli;
	private String customerCode="";
	private String tokenId="";
	private String access="";
	private String prefix="MJK";

        /**
	 * @return the roleName2
	 */
	public String getRoleName2() {
		return roleName2;
	}
	/**
	 * @param roleName2 the roleName2 to set
	 */
	public void setRoleName2(String roleName2) {
		this.roleName2 = roleName2;
	}
		/**
	 * @return the roleId2
	 */
	public String getRoleId2() {
		return roleId2;
	}
	/**
	 * @param roleId2 the roleId2 to set
	 */
	public void setRoleId2(String roleId2) {
		this.roleId2 = roleId2;
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
                this.tokenId =tokenId;
        }	

	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
        public boolean isDashaDetail() {
		return dashaDetail;
	}	
	public void setDashaDetail(boolean dashaDetail) {
		this.dashaDetail = dashaDetail;
	}

	public boolean isHouseDetail() {
		return houseDetail;
	}
	public void setHouseDetail(boolean houseDetail) {
		this.houseDetail = houseDetail;
	}

	public String getAccess() {
                return this.access;
        }
        public void setAccess(String access) {
                this.access = access;
        }
	
	public String getPrefix() {
                return this.prefix;
        }
        public void setPrefix(String prefix) {
                this.prefix = prefix;
        }
	public boolean isSpecialKundli() {
			return specialKundli;
		}
		public void setSpecialKundli(boolean specialKundli) {
			this.specialKundli = specialKundli;
		}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserInfo [uId=" + uId + ", uname=" + uname + ", upassword=" + upassword + ", ccCode=" + ccCode
				+ ", email=" + email + ", mobileNo=" + mobileNo + ", roleId=" + roleId + ", roleId2=" + roleId2
				+ ", roleName=" + roleName + ", roleName2=" + roleName2 + ", country=" + country + ", createDate="
				+ createDate + ", dashaDetail=" + dashaDetail + ", houseDetail=" + houseDetail + ", specialKundli="
				+ specialKundli + ", kundli=" + kundli + ", customerCode=" + customerCode + ", tokenId=" + tokenId
				+ ", access=" + access + ", prefix=" + prefix + "]";
	}
	
	
	

}
