package com.orangexxx.exam.service;

import java.util.List;

import com.orangexxx.exam.beans.User;

public interface UserService {
	
	public User getUserById(String userID);
	
	public void saveUserInfo(User user);
	
	public List<User> getUserList();
	
	public boolean isUserExist(String userID);

}
