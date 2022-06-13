package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;

public class UserOnOffline extends Thread {

	private static String getUserNameFromDbon;
	private static String getUserNameFromDboff;
	DBConnection dbconnon = new DBConnection();
	Connection connon = dbconnon.getConnection();
	PreparedStatement pstmton = null;
	StringBuffer sqlon = new StringBuffer();

	ResultSet resulton;
	ResultSet resultoff;

	@Override
	public void run() {
		try {
			while(true) {
				sqlon.setLength(0);
				sqlon.append("SELECT username From user WHERE flag=0");
	
				pstmton = connon.prepareStatement(sqlon.toString());
				resulton = pstmton.executeQuery();
	
				getUserNameFromDbon = null;
				ChatRoom.textArea_1.setText("");
				while (resulton.next()) {
					getUserNameFromDbon = resulton.getString("username");
					ChatRoom.textArea_1.append(getUserNameFromDbon + "\n");
	
				}
					sqlon.setLength(0);
					sqlon.append("SELECT username From user WHERE flag=1");
	
					pstmton = connon.prepareStatement(sqlon.toString());
					resultoff = pstmton.executeQuery();
					getUserNameFromDboff = null;
					ChatRoom.textArea.setText("");
					while (resultoff.next()) {
						getUserNameFromDboff = resultoff.getString("username");
						ChatRoom.textArea.append(getUserNameFromDboff + "\n");
					}
					Thread.sleep(5000);
			}
				
		} catch (SQLException | InterruptedException e) {

		}
	}

}
