package client;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db.ConnectionPool;

public class FindInfo {
	private JFrame frame;
	private JTextField FindIdUserName;
	private JTextField FindIdMail;
	private JTextField FindPwId;
	private JTextField FindPwUserName;
	private JTextField FindPwMail;

	private ConnectionPool cp;

	public FindInfo(ConnectionPool cp) {
		this.cp = cp;
		initialize(cp);
	}

	private void initialize(ConnectionPool cp) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 417);

		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("ID/PW 찾기");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(176, 10, 76, 49);
		frame.getContentPane().add(lblNewLabel);

		FindIdUserName = new JTextField();
		FindIdUserName.setBounds(119, 96, 184, 34);
		frame.getContentPane().add(FindIdUserName);
		FindIdUserName.setColumns(10);

		FindIdMail = new JTextField();
		FindIdMail.setColumns(10);
		FindIdMail.setBounds(119, 140, 184, 34);
		frame.getContentPane().add(FindIdMail);

		FindPwId = new JTextField();
		FindPwId.setColumns(10);
		FindPwId.setBounds(119, 226, 184, 34);
		frame.getContentPane().add(FindPwId);

		FindPwUserName = new JTextField();
		FindPwUserName.setColumns(10);
		FindPwUserName.setBounds(119, 270, 184, 34);
		frame.getContentPane().add(FindPwUserName);

		FindPwMail = new JTextField();
		FindPwMail.setColumns(10);
		FindPwMail.setBounds(119, 314, 184, 34);
		frame.getContentPane().add(FindPwMail);

		JLabel lblId = new JLabel("ID 찾기");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setFont(new Font("굴림", Font.PLAIN, 12));
		lblId.setBounds(12, 49, 76, 49);
		frame.getContentPane().add(lblId);

		JLabel lblPass = new JLabel("PW 찾기");
		lblPass.setHorizontalAlignment(SwingConstants.CENTER);
		lblPass.setFont(new Font("굴림", Font.PLAIN, 12));
		lblPass.setBounds(12, 179, 76, 49);
		frame.getContentPane().add(lblPass);

		JLabel lblNewLabel_1_1 = new JLabel("이름");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(12, 88, 76, 49);
		frame.getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("E-Mail");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(12, 132, 76, 49);
		frame.getContentPane().add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_1_1 = new JLabel("이름");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(12, 266, 76, 49);
		frame.getContentPane().add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_2_1 = new JLabel("E-Mail");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_1.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_1_2_1.setBounds(12, 310, 76, 49);
		frame.getContentPane().add(lblNewLabel_1_2_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("ID");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_1_1_1_1.setBounds(12, 218, 76, 49);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);

		JButton btnFindID = new JButton("ID 찾기");
		btnFindID.setBounds(315, 96, 97, 78);
		frame.getContentPane().add(btnFindID);

		JButton btnFindPW = new JButton("PW 찾기");
		btnFindPW.setBounds(315, 248, 97, 78);
		frame.getContentPane().add(btnFindPW);

		btnFindID.addActionListener(event -> {
			String userName = FindIdUserName.getText();
			String mail = FindIdMail.getText();

			FindIdUserName.setText("");
			FindIdMail.setText("");
			new FindInfoId(userName, mail, cp);
		});

		btnFindPW.addActionListener(event -> {
			String id = FindPwId.getText();
			String userName = FindPwUserName.getText();
			String mail = FindPwMail.getText();
			Connection conn = null;
			PreparedStatement pstmt = null;
			StringBuffer sql = new StringBuffer();
			ResultSet result = null;
			int flag = 0;
			
			// 변경예정
			try {
				conn = cp.getConnection();
				sql.append("SELECT EXISTS(SELECT id FROM user" + " WHERE id=? AND username=? AND email=?) AS result");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, id);
				pstmt.setString(2, userName);
				pstmt.setString(3, mail);
				result = pstmt.executeQuery();

				while (result.next()) {
					flag = result.getInt("result");
				}
				if (flag == 1) {
					new FindInfoPw(id, userName, mail, cp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				FindPwId.setText("");
				FindPwUserName.setText("");
				FindPwMail.setText("");
			} finally {
				try {
					cp.releaseConnection(conn);
					pstmt.close();
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
}