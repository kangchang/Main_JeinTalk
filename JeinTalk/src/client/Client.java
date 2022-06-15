package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db.ConnectionPool;

public class Client {

	private static String client_name;
	protected static JFrame frame;
	protected static JTextField textField;
	protected static JPasswordField textField_1;
	protected static JTextField textField_2;
	protected static JTextField textField_3;
	private static JLabel alertMessage;

	private static SimpleDateFormat date = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
	private static String loginDate = date.format(System.currentTimeMillis());
	

	public static void main(String[] args) throws SQLException {
		ConnectionPool cp = ConnectionPool.getInstance("jdbc:mariadb://192.168.0.227:3306/jeintalk", "root", "245134", 5, 10);
		
		
		// =====================================Swing=====================================
		
		frame = new JFrame("JeinTalk");
		frame.setBounds(120, 120, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel.setBounds(180, 30, 80, 40);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(50, 88, 108, 28);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(50, 120, 108, 28);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Ip");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(50, 150, 108, 28);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Port");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_4.setBounds(50, 180, 108, 28);
		frame.getContentPane().add(lblNewLabel_4);

		textField = new JTextField();
		textField.setBounds(190, 86, 129, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.revalidate();
		textField.repaint();

		textField_1 = new JPasswordField();
		textField_1.setColumns(10);
		textField_1.setBounds(190, 126, 129, 21);
		frame.getContentPane().add(textField_1);
		textField_1.revalidate();
		textField_1.repaint();
		
		textField_2 = new JTextField("192.168.0.###");
		textField_2.setColumns(10);
		textField_2.setBounds(190, 156, 129, 21);
		frame.getContentPane().add(textField_2);
		textField_2.revalidate();
		textField_2.repaint();
		
		textField_3 = new JTextField("0000");
		textField_3.setColumns(10);
		textField_3.setBounds(190, 186, 129, 21);
		frame.getContentPane().add(textField_3);
		textField_3.revalidate();
		textField_3.repaint();
		
		
		

		textField_1.addKeyListener(new KeyAdapter() {
			Connection conn = cp.getConnection();
			PreparedStatement pstmt = null;
			StringBuffer sql = new StringBuffer();
			ResultSet result = null;
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textField_1.getText().equals("") != true) {
						if (e.isControlDown()) { // Ctrl + Enter = 줄 바꿈
							
						} else { // Enter = 전송
							try {
								// 로그인 확인
								String ip = textField_2.getText();
								int port = Integer.parseInt(textField_3.getText());
								
								sql.setLength(0);
								sql.append("SELECT id, pw, username FROM user WHERE id=?");
								pstmt = conn.prepareStatement(sql.toString());
								pstmt.setString(1, textField.getText());
								result = pstmt.executeQuery();
								String getIdFromDb = null;
								String getPwFromDb = null;
								String getUserNameFromeDb = null;
								while (result.next()) {
									getIdFromDb = result.getString("id");
									getPwFromDb = result.getString("pw");
									getUserNameFromeDb = result.getString("username");
								}
								
								if (textField.getText().equals(getIdFromDb)
										&& textField_1.getText().equals(getPwFromDb)) {
									ChatRoom chatroom = new ChatRoom(getIdFromDb, getUserNameFromeDb, loginDate, ip, port, cp);
									
									// flag 1만들기 (온라인)
									sql.setLength(0);
									sql.append("UPDATE user SET flag=1 WHERE id=?");
									pstmt = conn.prepareStatement(sql.toString());
									pstmt.setString(1, getIdFromDb);
									pstmt.executeUpdate();
									
									chatroom.runChatRoom();
									frame.setVisible(false);
								} else {
									alertMessage.setText("ID및 Password를 확인해주세요.");
								}
								
							} catch (Exception e1) {
								e1.printStackTrace();
							} finally {
								//connection 반환
								try {
									cp.releaseConnection(conn);
									pstmt.close();
									result.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
							e.consume();
						}
					}
				}
			}
		});
		
		
		

		alertMessage = new JLabel();
		alertMessage.setBounds(190, 205, 180, 21);
		alertMessage.setForeground(Color.red);
		frame.getContentPane().add(alertMessage);
		alertMessage.revalidate();
		alertMessage.repaint();

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("굴림", Font.PLAIN, 24));
		btnLogin.setBounds(331, 86, 110, 62);
		frame.getContentPane().add(btnLogin);
		btnLogin.revalidate();
		btnLogin.repaint();

		JButton btnSign = new JButton("회원가입");
		btnSign.setBounds(125, 229, 100, 23);
		frame.getContentPane().add(btnSign);
		btnSign.revalidate();
		btnSign.repaint();

		JButton btnIdPassFind = new JButton("ID/Pw 찾기");
		btnIdPassFind.setBounds(240, 229, 100, 23);
		frame.getContentPane().add(btnIdPassFind);
		btnIdPassFind.revalidate();
		btnIdPassFind.repaint();

		// 회원가입
		btnSign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SignUp Sign = new SignUp();
				Sign.Singup(cp);
			}
		});

		// 회원정보찾기
		btnIdPassFind.addActionListener(event -> {
			new FindInfo(cp);
		});

		// 로그인성공시
		btnLogin.addActionListener(new ActionListener() {
			Connection conn = cp.getConnection();
			PreparedStatement pstmt = null;
			StringBuffer sql = new StringBuffer();
			ResultSet result = null;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					// 로그인 확인
					String ip = textField_2.getText();
					int port = Integer.parseInt(textField_3.getText());
					
					sql.setLength(0);
					sql.append("SELECT id, pw, username, flag FROM user WHERE id=?");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, textField.getText());
					result = pstmt.executeQuery();
					String getIdFromDb = null;
					String getPwFromDb = null;
					String getUserNameFromeDb = null;
					int getFlagFromDb = 0;
					while (result.next()) {
						getIdFromDb = result.getString("id");
						getPwFromDb = result.getString("pw");
						getUserNameFromeDb = result.getString("username");
						getFlagFromDb = result.getInt("flag");
					}
					if(getFlagFromDb == 0) {
						// flag 1만들기 (온라인)
						sql.setLength(0);
						sql.append("UPDATE user SET flag=1 WHERE id=?");
						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, getIdFromDb);
						pstmt.executeUpdate();
						
						if (textField.getText().equals(getIdFromDb) && textField_1.getText().equals(getPwFromDb)) {
							ChatRoom chatroom = new ChatRoom(getIdFromDb, getUserNameFromeDb, loginDate, ip, port, cp);
							chatroom.runChatRoom();
							alertMessage.setText("");
							frame.setVisible(false);
							
						} else {
							alertMessage.setText("ID및 Password를 확인해주세요.");
						}
					}else {
						alertMessage.setText("이미 로그인 된 계정입니다.");
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					//connection 반환
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
		// =====================================Swing=====================================
	}

}