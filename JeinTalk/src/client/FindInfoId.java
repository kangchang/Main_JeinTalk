package client;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import db.ConnectionPool;

public class FindInfoId {

	private JFrame frame;
	private String userName;
	private String mail;
	private String id = "";
	private ConnectionPool cp;

	public FindInfoId(String userName, String mail, ConnectionPool cp) {
		this.userName = userName;
		this.mail = mail;
		this.cp = cp;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		ResultSet result = null;
		
		try {
			conn = cp.getConnection();
			
			sql.append("SELECT id FROM user WHERE username=? AND email=?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userName);
			pstmt.setString(2, mail);
			result = pstmt.executeQuery();
			while(result.next()) {
				this.id = result.getString("id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.releaseConnection(conn);
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 359, 215);
		frame.getContentPane().setLayout(null);
		
		JLabel FindId = new JLabel("해당 정보로 가입된 아이디가 없습니다.");
		FindId.setFont(new Font("굴림", Font.PLAIN, 15));
		FindId.setHorizontalAlignment(SwingConstants.CENTER);
		FindId.setBounds(0, 32, 343, 59);
		if(!this.id.equals("")) {
			FindId.setText("조회된 아이디는 " + id + " 입니다.");
		}else {
			FindId.setText("해당 정보로 가입된 아이디가 없습니다.");
		}
		frame.getContentPane().add(FindId);
		
		JButton btnNewButton = new JButton("확인");
		btnNewButton.setBounds(110, 101, 126, 42);
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
		
		btnNewButton.addActionListener(event->{
			frame.setVisible(false);
		});
	}
}
