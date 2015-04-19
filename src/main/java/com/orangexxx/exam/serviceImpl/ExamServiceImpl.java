package com.orangexxx.exam.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.stereotype.Service;

import com.orangexxx.exam.beans.User;
import com.orangexxx.exam.service.ExamService;

@Service("examService")
public class ExamServiceImpl implements ExamService {

	public static final String RESULT="D:/Exam/result.txt";
	@Override
	public void addResultRow(User user) {
		// TODO Auto-generated method stub
		synchronized (this) {
			try {
				File result = new File(RESULT);
				OutputStreamWriter writer = null;
				FileOutputStream out = new FileOutputStream(result, true);

				if (!result.exists()) {
					result.createNewFile();
				}
				writer = new OutputStreamWriter(out, "utf-8");
				writer.write(user.getUserID() + " " + user.getUserName() + " " + user.getDepartment()
						+ " " + user.getScore() + "\r\n");
				writer.flush();
				writer.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*public static void main(String[] args) {
		ExamService service = new ExamServiceImpl();
		User user1 = new User("123", "xyz", "东湖分行");
		user1.setScore(90);
		User user2 = new User("345", "abc", "黄龙支行");
		user2.setScore(100);
		service.addResultRow(user1);
		service.addResultRow(user2);
	}*/
}
