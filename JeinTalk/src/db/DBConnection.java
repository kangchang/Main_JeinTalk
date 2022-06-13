package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
	
	private static final String DRIVER = "org.mariadb.jdbc.Driver";
	private Connection conn;
	
	public DBConnection() {
		this.conn = connectToDB();
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	private Connection connectToDB() {
		
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(
						"jdbc:mariadb://192.168.0.211:3306/jeintalk",
						"root",
						"root"
					);
			if(conn != null) {
				System.out.println("DB연결성공");
				return conn;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
