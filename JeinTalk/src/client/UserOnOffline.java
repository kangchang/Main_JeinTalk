package client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.ConnectionPool;

public class UserOnOffline extends Thread {

	private static String getUserNameFromDbon;
	private static String getUserNameFromDboff;
	private ConnectionPool cp;
	
	ResultSet resulton;
	ResultSet resultoff;

	public UserOnOffline(ConnectionPool cp) {
		this.cp = cp;
	}
	
	@Override
	public void run() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer sqlon = new StringBuffer();
		try {
			conn = cp.getConnection();
			while(true) {
				sqlon.setLength(0);
				sqlon.append("SELECT username From user WHERE flag=0");
	
				pstmt = conn.prepareStatement(sqlon.toString());
				resulton = pstmt.executeQuery();
	
				getUserNameFromDbon = null;
				ChatRoom.textArea_1.setText("");
				while (resulton.next()) {
					getUserNameFromDbon = resulton.getString("username");
					ChatRoom.textArea_1.append(getUserNameFromDbon + "\n");
				}
					sqlon.setLength(0);
					sqlon.append("SELECT username From user WHERE flag=1");
	
					pstmt = conn.prepareStatement(sqlon.toString());
					resultoff = pstmt.executeQuery();
					getUserNameFromDboff = null;
					ChatRoom.textArea.setText("");
					while (resultoff.next()) {
						getUserNameFromDboff = resultoff.getString("username");
						ChatRoom.textArea.append(getUserNameFromDboff + "\n");
					}
					
					Thread.sleep(5000);
			}
		} catch (SQLException | InterruptedException e) {
			/* ( 예외처리 )
			 * */
			e.printStackTrace();
		} finally {
			try {
				cp.releaseConnection(conn);
				pstmt.close();
				resulton.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
