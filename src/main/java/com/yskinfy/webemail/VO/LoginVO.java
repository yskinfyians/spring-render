package com.yskinfy.webemail.VO;

public class LoginVO {

	private String username;
	private String bcryptPassword;
	private String encodedPassword;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBcryptPassword() {
		return bcryptPassword;
	}
	public void setBcryptPassword(String bcryptPassword) {
		this.bcryptPassword = bcryptPassword;
	}
	public String getEncodedPassword() {
		return encodedPassword;
	}
	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}
	
	
}
