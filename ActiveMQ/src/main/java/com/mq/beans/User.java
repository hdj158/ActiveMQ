package com.mq.beans;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8662717316716675984L;
	private String userName;
	private String userBill;
	private String password;
	private String mail;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserBill() {
		return userBill;
	}
	public void setUserBill(String userBill) {
		this.userBill = userBill;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", userBill=" + userBill + ", password=" + password + ", mail=" + mail
				+ "]";
	}
	
	
}
