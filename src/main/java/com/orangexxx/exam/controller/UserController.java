package com.orangexxx.exam.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.orangexxx.exam.beans.Message;
import com.orangexxx.exam.beans.User;
import com.orangexxx.exam.service.UserService;

@Controller
public class UserController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/login")
	@ResponseBody
	public Message login (@RequestParam(value="userID") String userID,
			@RequestParam(value="password") String password,
			HttpSession session) {
		User user = userService.getUserById(userID);
		if (user == null) {
			return new Message(0, "用户ID不存在");
		}
		
		if (!user.getPassword().equals(password)) {
			return new Message(0, "用户密码错误");
		}
		
		if (!user.isNew()) {
			return new Message(0, "用户已经参加过考试！");
		}
		session.setAttribute("user", user);
		return new Message(1, "用户登录成功");
	}

	@RequestMapping("/registry")
	@ResponseBody
	public Message registry(@RequestParam(value = "userID") String userID,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "department") String department) {
		User user = new User(userID, userName, password, department);
		if (userService.isUserExist(userID)) {
			return new Message(0, "用户ID已注册");
		} 
		userService.saveUserInfo(user);
		return new Message(1, "注册成功");
	}
	
	@RequestMapping("/modify")
	@ResponseBody
	public Message modify(@RequestParam(value = "userID") String userID,
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "old_password") String oldPwd,
			@RequestParam(value = "new_password") String newPwd,
			@RequestParam(value = "department") String department) {
		User user = userService.getUserById(userID);
		if (user == null) {
			return new Message(0, "用户ID不存在");
		}
		
		if (!user.getPassword().equals(oldPwd)) {
			return new Message(0, "用户密码错误");
		}
		user.setUserName(userName);
		user.setPassword(newPwd);
		user.setDepartment(department);
		userService.saveUserInfo(user);
		
		return new Message(1, "用户信息修改成功");
	}
	
}
