package org.centenaire.edition;

public class ExtraInfoStudent {
	private boolean hasApbNum;
	private String apbNum;
	private boolean hasApogeeNum;
	private String apogeeNum;
	private boolean hasEmail;
	private String email;
	
	
	public ExtraInfoStudent(boolean hasApbNum, String apbNum, boolean hasApogeeNum, String apogeeNum, boolean hasEmail,
			String email) {
		super();
		this.hasApbNum = hasApbNum;
		this.apbNum = apbNum;
		this.hasApogeeNum = hasApogeeNum;
		this.apogeeNum = apogeeNum;
		this.hasEmail = hasEmail;
		this.email = email;
	}
	
	public ExtraInfoStudent() {
		super();
		this.hasApbNum = false;
		this.apbNum = "";
		this.hasApogeeNum = false;
		this.apogeeNum = "";
		this.hasEmail = false;
		this.email = "";
	}


	public boolean isHasApbNum() {
		return hasApbNum;
	}


	public void setHasApbNum(boolean hasApbNum) {
		this.hasApbNum = hasApbNum;
	}


	public String getApbNum() {
		return apbNum;
	}


	public void setApbNum(String apbNum) {
		this.apbNum = apbNum;
	}


	public boolean isHasApogeeNum() {
		return hasApogeeNum;
	}


	public void setHasApogeeNum(boolean hasApogeeNum) {
		this.hasApogeeNum = hasApogeeNum;
	}


	public String getApogeeNum() {
		return apogeeNum;
	}


	public void setApogeeNum(String apogeeNum) {
		this.apogeeNum = apogeeNum;
	}


	public boolean isHasEmail() {
		return hasEmail;
	}


	public void setHasEmail(boolean hasEmail) {
		this.hasEmail = hasEmail;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
