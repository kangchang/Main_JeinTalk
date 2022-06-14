package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import db.ConnectionPool;

public class SignUp {
	
	private static JFrame Signframe;
	private static JTextField SigntextField;
	private static JLabel SignlblNewLabel_2;
	private static JTextField SigntextField_1;
	private static JLabel SignlblNewLabel_3;
	private static JTextField SigntextField_2;
	private static JLabel SignlblNewLabel_4;
	private static JTextField SigntextField_3;
	private static JLabel SignlblNewLabel_5;
	private static JTextField SigntextField_4;
	private static JLabel SignlblNewLabel_6;
	public int CheckResult = 0;
	
	public void Singup(ConnectionPool cp) {
		
		Signframe = new JFrame();
		Signframe.setBounds(100, 100, 720, 555);
		Signframe.getContentPane().setLayout(null);
		Signframe.setResizable(false);
		Signframe.setVisible(true);
		
		JLabel SignlblNewLabel = new JLabel("회원가입");
		SignlblNewLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		SignlblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SignlblNewLabel.setBounds(278, 50, 120, 60);
		Signframe.getContentPane().add(SignlblNewLabel);
		
		JLabel SignlblNewLabel_1 = new JLabel("이름");
		SignlblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 14));
		SignlblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		SignlblNewLabel_1.setBounds(188, 140, 133, 30);
		Signframe.getContentPane().add(SignlblNewLabel_1);
		
		SigntextField = new JTextField();
		SigntextField.setBounds(333, 145, 125, 21);
		SigntextField.setColumns(10);
		Signframe.getContentPane().add(SigntextField);
		
		SignlblNewLabel_2 = new JLabel("ID");
		SignlblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		SignlblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 14));
		SignlblNewLabel_2.setBounds(188, 175, 133, 30);
		Signframe.getContentPane().add(SignlblNewLabel_2);
		
		SigntextField_1 = new JTextField();
		SigntextField_1.setColumns(10);
		SigntextField_1.setBounds(333, 180, 125, 21);
		Signframe.getContentPane().add(SigntextField_1);
		
		SignlblNewLabel_3 = new JLabel("E-Mail");
		SignlblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		SignlblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 14));
		SignlblNewLabel_3.setBounds(188, 218, 133, 30);
		Signframe.getContentPane().add(SignlblNewLabel_3);
		
		SigntextField_2 = new JTextField();
		SigntextField_2.setColumns(10);
		SigntextField_2.setBounds(333, 223, 125, 21);
		Signframe.getContentPane().add(SigntextField_2);
		
		SignlblNewLabel_4 = new JLabel("Password");
		SignlblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		SignlblNewLabel_4.setFont(new Font("굴림", Font.PLAIN, 14));
		SignlblNewLabel_4.setBounds(188, 257, 133, 30);
		Signframe.getContentPane().add(SignlblNewLabel_4);
		
		SigntextField_3 = new JTextField();
		SigntextField_3.setColumns(10);
		SigntextField_3.setBounds(333, 262, 125, 21);
		Signframe.getContentPane().add(SigntextField_3);
		
		SignlblNewLabel_5 = new JLabel("Password 확인");
		SignlblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		SignlblNewLabel_5.setFont(new Font("굴림", Font.PLAIN, 14));
		SignlblNewLabel_5.setBounds(188, 306, 133, 30);
		Signframe.getContentPane().add(SignlblNewLabel_5);
		
		SigntextField_4 = new JTextField();
		SigntextField_4.setColumns(10);
		SigntextField_4.setBounds(333, 311, 125, 21);
		Signframe.getContentPane().add(SigntextField_4);

		SignlblNewLabel_6 = new JLabel("");
		SignlblNewLabel_6.setBounds(333, 205, 133, 15);
		Signframe.getContentPane().add(SignlblNewLabel_6);
		
		JButton SignbtnNewButton = new JButton("중복확인");
		SignbtnNewButton.setBounds(503, 179, 91, 23);
		Signframe.getContentPane().add(SignbtnNewButton);
		
		SignbtnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstmt = null;
				StringBuffer sql = new StringBuffer();
				ResultSet result = null;
				try {
					conn = cp.getConnection();
					sql.append("SELECT id FROM user WHERE id=?");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, SigntextField_1.getText());
					result = pstmt.executeQuery();
					
					CheckResult = 0;
					
					while(result.next()) {
						CheckResult = 1;
					}
					if(CheckResult == 1) {
						SigntextField_1.setForeground(Color.red);
						SignlblNewLabel_6.setText("이미 등록된 ID입니다.");
					}else if(CheckResult == 0) {
						SigntextField_1.setForeground(Color.green);
						SignlblNewLabel_6.setText("사용가능한 ID입니다.");
					}
					
				} catch (SQLException exc) {
					exc.printStackTrace();
				} finally {
					try {
						cp.releaseConnection(conn);
						pstmt.close();
						result.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
            
        });
		
		JButton SignbtnNewButton_1 = new JButton("등록");
		SignbtnNewButton_1.setBounds(230, 386, 91, 23);
		Signframe.getContentPane().add(SignbtnNewButton_1);
		SignbtnNewButton_1.setEnabled(false);
		
		SigntextField_4.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				Check();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				Check();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				Check();
			}
			// 전체 입력 확인
			public void Check() {
				if(CheckResult == 0 && SigntextField_3.getText().equals(SigntextField_4.getText()) && !SigntextField.getText().equals("") && !SigntextField_1.getText().equals("") && !SigntextField_2.getText().equals("") && !SigntextField_3.getText().equals("") && !SigntextField_4.getText().equals("")) {
					SignbtnNewButton_1.setEnabled(true);
				}else if(CheckResult == 1 || !SigntextField_3.getText().equals(SigntextField_4.getText()) && !SigntextField.getText().equals("") && !SigntextField_1.getText().equals("") && !SigntextField_2.getText().equals("") && !SigntextField_3.getText().equals("") && !SigntextField_4.getText().equals("")) {
					SignbtnNewButton_1.setEnabled(false);
				}
			}
		});
		
		SignbtnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstmt = null;
				StringBuffer sql = new StringBuffer();
				try {
					conn = cp.getConnection();
					sql.append("INSERT INTO user(id, pw, username, email, flag) VALUES(?,?,?,?,?)");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, SigntextField_1.getText());
					pstmt.setString(2, SigntextField_3.getText());
					pstmt.setString(3, SigntextField.getText());
					pstmt.setString(4, SigntextField_2.getText());
					pstmt.setInt(5, 0);
					
					pstmt.executeUpdate();
					
					Signframe.dispose();
					
				} catch (SQLException exc) {
					exc.printStackTrace();
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
		
		JButton SignbtnNewButton_1_1 = new JButton("취소");
		SignbtnNewButton_1_1.setBounds(379, 386, 91, 23);
		Signframe.getContentPane().add(SignbtnNewButton_1_1);
		
		SignbtnNewButton_1_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					Signframe.dispose();
			}
            
        });
	}
}


