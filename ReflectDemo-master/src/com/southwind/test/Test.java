package com.southwind.test;

import java.sql.Connection;

import com.southwind.entity.Student;
import com.southwind.entity.User;
import com.southwind.util.MyQueryRunner;
import com.southwind.util.JDBCTools;

public class Test {
	public static void main(String[] args) {
		
//		Connection conn = JDBCTools.getConnection();
//		String sql = "select * from users where id = ?";
//		MyQueryRunner myQueryRunner = new MyQueryRunner();
//		User user = (User) myQueryRunner.query(conn, sql, User.class, 30);
//		System.out.println(user);
		
		Connection conn = JDBCTools.getConnection();
		String sql = "select * from student where id = ?";
		MyQueryRunner myQueryRunner = new MyQueryRunner();
		Student student = (Student) myQueryRunner.query(conn, sql, Student.class, 2);
		System.out.println(student);
	}
}
