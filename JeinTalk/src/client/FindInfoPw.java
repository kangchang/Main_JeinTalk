package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import db.ConnectionPool;

public class FindInfoPw {

	private JFrame frame;
	private JTextField textPw1;
	private JTextField textPw2;

	private String id;
	private String userName;
	private String mail;
	private ConnectionPool cp;
	
	public FindInfoPw(String id, String userName, String mail, ConnectionPool cp) {
		this.id = id;
		this.userName = userName;
		this.mail = mail;
		this.cp = cp;
		
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 332, 269);
		frame.getContentPane().setLayout(null);
		
		textPw1 = new JTextField();
		textPw1.setColumns(10);
		textPw1.setBounds(143, 54, 150, 32);
		frame.getContentPane().add(textPw1);
		
		textPw2 = new JTextField();
		textPw2.setColumns(10);
		textPw2.setBounds(143, 108, 150, 32);
		frame.getContentPane().add(textPw2);
		
		JLabel lblNewLabel = new JLabel("비밀번호");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel.setBounds(34, 54, 83, 32);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("비밀번호 확인");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(34, 108, 83, 32);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnButton = new JButton("수정");
		btnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnButton.setBounds(34, 166, 259, 43);
		frame.getContentPane().add(btnButton);
		frame.setVisible(true);
		
		//이벤트 등록
		btnButton.addActionListener(event->{
			Connection conn = null;
			PreparedStatement pstmt = null;
			StringBuffer sql = null;
			sql = new StringBuffer();
			int result;
			//입력한 비밀번호가 같으면 비밀번호 변경
			if(textPw1.getText().equals(textPw2.getText())) {
				try {
					conn = cp.getConnection();
					sql.append("UPDATE user SET pw=? where id=? and username=? and email=?");
				
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, textPw1.getText());
					pstmt.setString(2, id);
					pstmt.setString(3, userName);
					pstmt.setString(4, mail);
					
					result = pstmt.executeUpdate();
					if(result == 1) {
						//frame 종료
						frame.dispose();
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					try {
						cp.releaseConnection(conn);
						pstmt.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
