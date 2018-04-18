package ua.logos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {

	static Connection conn;
	
	public static void main(String[] args) throws SQLException{
		String url = "jdbc:mysql://localhost:3306/university";
		String user = "root";
		String password = "1111";
		
		conn = DriverManager.getConnection(url, user, password);
		System.out.println("Connected? " + !conn.isClosed());
		
		createTable();
		for (int i = 1; i < 50; i++) {
		addStudent(i);
		}
		
		selectStudents();
		selectStudentById(10);
		conn.close();
	}
	
	static void createTable() throws SQLException {
		String dropQuery = "DROP TABLE IF EXISTS student";
		String query = "CREATE TABLE student("
				+ "id INT PRIMARY KEY AUTO_INCREMENT,"
				+ "full_name VARCHAR(30),"
				+ "age INT "
				+ ");";
		Statement stmt = conn.createStatement();
		stmt.execute(dropQuery);
		stmt.execute(query);
		
		stmt.close();
	}
	
	static void addStudent(int i) throws SQLException{
		String query = "INSERT INTO student(full_name, age) VALUES(?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, "John Doe #" + i);
		pstmt.setInt(2, 23 + i);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
	static void selectStudents() throws SQLException{
		String query = "SELECT * FROM student;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		List<String> students = new ArrayList<>();
		
		while(rs.next()) {
			students.add("ID: " + rs.getInt("id") + "\t | "
					+ "Full Name: " + rs.getString("full_name") + "\t | "
			+"Age: " + rs.getInt("age"));
		}
		pstmt.close();
		students.forEach(System.out::println);
		
	}
	static void selectStudentById(int id) throws SQLException{
		System.out.println("Select student by ID");
		String query = "SELECT * FROM student WHERE id = ?;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		List<String> students = new ArrayList<>();
		
		while(rs.next()) {
			students.add("ID: " + rs.getInt("id") + "\t | "
					+ "Full Name: " + rs.getString("full_name") + "\t | "
			+"Age: " + rs.getInt("age"));
		}
		pstmt.close();
		students.forEach(System.out::println);
	}

}
