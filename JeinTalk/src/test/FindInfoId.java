package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import db.DBConnection;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;

public class FindInfoId {

	private JFrame frame;
	private String userName;
	private String mail;
	private String id = "";

	public FindInfoId(String userName, String mail) {
			
		this.userName = userName;
		this.mail = mail;
		
		DBConnection dbcon = new DBConnection();
		Connection con = dbcon.getConnection();
		PreparedStatement prstmt = null;
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id FROM user WHERE username=? AND email=?");
		try {
			
			prstmt = con.prepareStatement(sql.toString());
			prstmt.setString(1, userName);
			prstmt.setString(2, mail);
			
			ResultSet result = prstmt.executeQuery();
			while(result.next()) {
				this.id = result.getString("id");
			}
			System.out.println(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			prstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
