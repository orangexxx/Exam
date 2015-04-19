package com.orangexxx.exam.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangexxx.exam.beans.User;
import com.orangexxx.exam.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	public static final String PATH = "D:/Exam/users/";

	private ObjectMapper mapper;

	public UserServiceImpl() {
		mapper = new ObjectMapper();
	}

	@Override
	public User getUserById(String userID) {
		// TODO Auto-generated method stub
		synchronized (this) {
			File userInfo = new File(PATH + userID + ".json");
			try {
				if (!userInfo.exists()) {
					return null;
				} else {
					return mapper.readValue(userInfo, User.class);
				}
			} catch (JsonParseException e) {
				e.printStackTrace();
				return null;
			} catch (JsonMappingException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public void saveUserInfo(User user) {
		// TODO Auto-generated method stub
		synchronized (this) {
			try {
				File users = new File(PATH);
				if (!users.exists()) {
					users.mkdirs();
				}
				File userInfo = new File(PATH + user.getUserID() + ".json");
				if (!userInfo.exists()) {
					userInfo.createNewFile();
				}
				mapper.writeValue(userInfo, user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		File usersDir = new File(PATH);
		if (!usersDir.exists()) {
			return null;
		}
		for (String user : usersDir.list()) {
			users.add(getUserById(user.substring(0, user.indexOf("."))));
		}
		return users;
	}
	
	@Override
	public boolean isUserExist(String userID) {
		File usersDir = new File(PATH);
		String fileName = userID + ".json";
		if (!usersDir.exists()) {
			return false;
		}
		for (String user : usersDir.list()) {
			if (fileName.equals(user)) {
				return true;
			}
		}
		return false;
	}

	/*public static void main(String[] args) {
		UserService service = new UserServiceImpl();
		
		 * User user=service.getUserById("123456");
		 * System.out.println(user.getPassword());
		 * System.out.println(user.getDepartment());
		 
		// service.saveUserInfo(user);
		List<User> users = service.getUserList();
		for (User user : users) {
			System.out.println(user.getUserID());
		}
		User u = new User("12", "111", "ffff");
		service.saveUserInfo(u);
	}*/

}
