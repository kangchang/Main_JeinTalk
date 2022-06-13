package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import db.DBConnection;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class FindInfoPw {

	private JFrame frame;
	private JTextField textPw1;
	private JTextField textPw2;

	private String id;
	private String userName;
	private String mail;
	
	public FindInfoPw(String id, String userName, String mail) {
		this.id = id;
		this.userName = userName;
		this.mail = mail;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
			//입력한 비밀번호가 같으면 비밀번호 변경
			if(textPw1.getText().equals(textPw2.getText())) {
				
				DBConnection dbcon = new DBConnection();
				Connection con = dbcon.getConnection();
				PreparedStatement prestmt = null;
				
				StringBuffer sql = new StringBuffer();
				sql.append("UPDATE user SET pw=? where id=? and username=? and email=?");
				
				try {
					prestmt = con.prepareStatement(sql.toString());
					prestmt.setString(1, textPw1.getText());
					prestmt.setString(2, id);
					prestmt.setString(3, userName);
					prestmt.setString(4, mail);
					
					int result = prestmt.executeUpdate();
					System.out.println("zz");
					if(result == 1) {
						//frame 종료
						frame.dispose();
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
