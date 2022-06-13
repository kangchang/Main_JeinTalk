package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBConnection;

public class ServerInThread extends Thread {

	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private String user;
	
	DBConnection dbconn = new DBConnection();
	Connection conn = dbconn.getConnection();
	PreparedStatement pstmt = null;
	StringBuffer sql = new StringBuffer();

	public ServerInThread(Socket socket1, Socket socket2) {
		try {
			this.in = new ObjectInputStream(socket1.getInputStream());
			this.out = new ObjectOutputStream(socket1.getOutputStream());
			MainServer.list.add(out);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				user = (String) in.readObject();
				send();
//				sql.append("SELECT flag FROM user WHERE id=?");
//				pstmt = conn.prepareStatement(sql.toString());
//				pstmt.setString(1, textField.getText());
//				ResultSet result = pstmt.executeQuery();
//				String getIdFromDb = null;
//				String getPwFromDb = null;
//				String getUserNameFromeDb = null;
//				while(result.next()) {
//					getIdFromDb = result.getString("id");
//					getPwFromDb = result.getString("pw");
//					getUserNameFromeDb = result.getString("username");
//				}
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
	}

	public void send() {
		for (int i = 0; i < (MainServer.list).size(); i++) {
			try {
				(MainServer.list.get(i)).writeObject((Object) (user));
				(MainServer.list.get(i)).flush();
			} catch (IOException e) {
				MainServer.list.remove(i);
				i--;
			}
		}
	}
}
