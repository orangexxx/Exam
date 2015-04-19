package com.orangexxx.exam.beans;

public class User {

	private String userID;
	private String userName;
	private String password;
	private String department;
	private boolean isNew;
	private int score;
	
	public User() {
		
	}
	
	public User(String userID, String userName, String password, String department) {
		this.isNew = true;
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.department = department;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
}
