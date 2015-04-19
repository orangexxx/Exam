package com.orangexxx.exam.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.orangexxx.exam.beans.Message;
import com.orangexxx.exam.beans.User;
import com.orangexxx.exam.service.ExamService;
import com.orangexxx.exam.service.UserService;

@Controller
public class ExamController {
	@Resource
	private ExamService examService;
	
	@Resource 
	private UserService userService;
	
	@RequestMapping("/start")
	@ResponseBody	
	public Message start(HttpSession session) {
		User currUser = (User)session.getAttribute("user");
		if (currUser!=null) {
			currUser.setNew(false);
			userService.saveUserInfo(currUser);
			return new Message(1, "done");
		} else {
			return new Message(0, "用户信息有误");
		}
	}
	
	@RequestMapping("/exam")
	@ResponseBody
	public Message exam(@RequestParam(value="result") String result, HttpSession session) {
		int index = 0;
		int score = 0;
		for (;index <9;index ++) {
			char ch = result.charAt(index);
			switch (ch) {
			case '0': score += 6;
				break;
			case '1': score += 3;
				break;
			}
		}
		
		for (;index<25;index ++) {
			char ch = result.charAt(index);
			switch (ch) {
			case '0': score += 5;
				break;
			case '1': score += 2;
				break;
			}
		}
		
		for (;index<29;index ++ ) {
			char ch = result.charAt(index);
			if (ch == '1') {
				score +=5;
			}
			
		}
		
		for (;index <33;index ++) {
			char ch = result.charAt(index);
			switch (ch) {
			case '0': score += 1;
				break;
			case '1': score += 2;
				break;
			case '2': score += 3;
				break;
			case '3': score +=4;
				break;
			case '4': score +=5;
				break;
			}
		}
		User currUser = (User)session.getAttribute("user");
		if(currUser != null) {
			User user = userService.getUserById(currUser.getUserID());
			user.setScore(score);
			examService.addResultRow(user);
			userService.saveUserInfo(user);
			return new Message(1, "测评完成");
		} else {
			return new Message(0, "用户信息不正确！");
		}
	}
}
