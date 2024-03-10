package com.yskinfy.webemail.util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="zenovel_login")
public class ZenovelLoginUtil {

	@Id
	@Column(name="userid")
	private String userId;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="contact_nbr")
	private String contactNbr;
	
	@Column(name="email")
	private String email;
	
	@Column(name="role")
	private String role;
	
	@Column(name="last_logged_in")
	private String lastLoggedIn;
	
	@Column(name="created_on")
	private String createdOn;
	
	@Column(name="token")
	private String token;
	
	@Column(name="is_token_valid")
	private String isTokenValid;
	
	@Column(name="is_new_user")
	private String isNewUser;
	
	@Column(name="unsuccessful_logins")
	private String unsuccessfulLogins;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContactNbr() {
		return contactNbr;
	}

	public void setContactNbr(String contactNbr) {
		this.contactNbr = contactNbr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(String lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIsTokenValid() {
		return isTokenValid;
	}

	public void setIsTokenValid(String isTokenValid) {
		this.isTokenValid = isTokenValid;
	}

	public String getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}

	public String getUnsuccessfulLogins() {
		return unsuccessfulLogins;
	}

	public void setUnsuccessfulLogins(String unsuccessfulLogins) {
		this.unsuccessfulLogins = unsuccessfulLogins;
	}
	
	
	
}
